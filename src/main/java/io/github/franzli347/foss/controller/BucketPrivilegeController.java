package io.github.franzli347.foss.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.franzli347.foss.annotation.CheckBucketPrivilege;
import io.github.franzli347.foss.common.AuthConstant;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.common.ResultCode;
import io.github.franzli347.foss.entity.BucketPrivilege;
import io.github.franzli347.foss.service.BucketPrivilegeService;
import io.github.franzli347.foss.service.UserService;
import io.github.franzli347.foss.support.userSupport.LoginUserProvider;
import io.github.franzli347.foss.utils.AuthUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @ClassName BucketPermissonController
 * @Author AlanC
 * @Date 2023/1/11 21:56
 **/
@RequestMapping("/bucket/privilege")
@RestController
@Tag(name = "桶权限管理")
public class BucketPrivilegeController {
    private final BucketPrivilegeService privilegeService;
    private final LoginUserProvider loginUserProvider;
    private final UserService userService;

    public BucketPrivilegeController(BucketPrivilegeService privilegeService, LoginUserProvider loginUserProvider, UserService service) {
        this.privilegeService = privilegeService;
        this.loginUserProvider = loginUserProvider;
        this.userService = service;
    }
    @PostMapping("/set/{username}")
    @Operation(summary = "设定特定用户特定bucket的读写权限,权限类型只能为r或rw(需要此bucket所有权)")
    @Parameter(name = "username",description = "被授权用户名")
    @CheckBucketPrivilege(spelString = "#privilege.bid",argType = AuthConstant.BID,privilege = AuthConstant.OWNER)
    public Result set(@RequestBody BucketPrivilege privilege,@PathVariable String username){
        privilege.setUid(Optional
                .ofNullable(userService.getUserByUsername(username))
                .orElseThrow(()->new RuntimeException("用户不存在"))
                .getId());
        AuthUtil.privilegeCheck(privilege.getUid(),privilege.getPrivilege());
        privilegeService.checkPrivilegeExist(privilege);
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(privilegeService.setPrivilege(privilege))
                .build();
    }
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除指定权限信息(需要此bucket所有权)")
    @Parameter(name = "id",description = "权限信息id")
    @CheckBucketPrivilege(spelString = "#id",argType = AuthConstant.PRIVILEGE_ID,privilege = AuthConstant.OWNER)
    public Result delete(@PathVariable int id){
        BucketPrivilege info=Optional.ofNullable(privilegeService.getById(id)).orElseThrow(()->new RuntimeException("权限信息不存在"));
        if (info.getUid()==loginUserProvider.getLoginUser().getId()){
            throw new RuntimeException("无法删除自己的权限");
        }
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(privilegeService.removeById(id))
                .build();
    }
    @GetMapping("/getBucketPrivilege/{bid}")
    @Operation(summary = "获取指定bucket的授权信息(需要此bucket所有权)")
    @Parameter(name="bid",description = "bucket id")
    @CheckBucketPrivilege(spelString = "#bid",argType = AuthConstant.BID,privilege = AuthConstant.OWNER)
    public Result getBucketPrivilege(@PathVariable int bid){
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(privilegeService.getBucketPrivilegeByBid(bid))
                .build();
    }
    @GetMapping("/getAll")
    @Operation(summary = "获取当前用户所有的bucket权限信息")
    public Result getAllPrivilegeInfo(){
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(privilegeService.getAllPrivilegeInfo(loginUserProvider.getLoginUser().getId()))
                .build();
    }
    @GetMapping("/getSimplePrivilegeInfo")
    @Operation(summary = "获取用户当前所有bucket权限信息(bucketId-权限-isOwner)格式")
    public Result getSimplePrivilegeInfo(){
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(StpUtil.getPermissionList())
                .build();
    }
    @PostMapping("/update/{id}/{privilege}")
    @Operation(summary = "更新授权信息,只可更新权限(需要此bucket所有权)")
    @Parameter(name = "id",description = "权限信息id")
    @Parameter(name = "privilege",description = "权限类别")
    @CheckBucketPrivilege(spelString = "#id",argType = AuthConstant.PRIVILEGE_ID,privilege = AuthConstant.OWNER)
    public Result update(@PathVariable int id,@PathVariable String privilege){
        AuthUtil.privilegeCheck(privilegeService.getById(id).getUid(),privilege);
        return Result.builder()
                .code(ResultCode.CODE_SUCCESS)
                .data(privilegeService.updatePrivilege(id,privilege))
                .build();
    }

}
