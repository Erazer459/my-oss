package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.annotation.CheckBucketPrivilege;
import io.github.franzli347.foss.common.AuthConstant;
import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.entity.Bucket;
import io.github.franzli347.foss.entity.BucketPrivilege;
import io.github.franzli347.foss.service.BucketPrivilegeService;
import io.github.franzli347.foss.service.BucketService;
import io.github.franzli347.foss.support.userSupport.LoginUserProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author FranzLi
 */
@RequestMapping("/bucket")
@RestController
@Tag(name = "桶管理模块")
public class BucketController {
    private final BucketService bucketService;
    private final LoginUserProvider loginUserProvider;
    private final BucketPrivilegeService bucketPrivilegeService;

    public BucketController(BucketService bucketService, LoginUserProvider loginUserProvider, BucketPrivilegeService bucketPrivilegeService) {
        this.bucketService = bucketService;
        this.loginUserProvider = loginUserProvider;
        this.bucketPrivilegeService = bucketPrivilegeService;
    }

    @PostMapping("list/{page}/{size}")
    @Operation(summary = "获取登录用户的所有bucket")
    @Parameter(name = "page", description = "页码")
    @Parameter(name = "size", description = "每页数量")
    public Result list(@PathVariable int page, @PathVariable int size) {
        return Result
                .builder()
                .code(200)
                .data(bucketService
                        .getBucketsByUserIdWithPage(Optional
                                        .ofNullable(loginUserProvider.getLoginUser())
                                        .orElseThrow(() -> new RuntimeException("loginUserProvider exception"))
                                        .getId()
                        , page, size))
                .build();
    }


    @PostMapping("listAll/{page}/{size}")
    @Operation(summary = "获取登录用户的所有有权限的bucket")
    @Parameter(name = "page", description = "页码")
    @Parameter(name = "size", description = "每页数量")
    public Result listAll(@PathVariable int page, @PathVariable int size) {
        return Result
                .builder()
                .code(200)
                .data(bucketService
                        .listAll(Optional
                                .ofNullable(loginUserProvider.getLoginUser())
                                .orElseThrow(() -> new RuntimeException("loginUserProvider exception"))
                                .getId()
                                , page, size))
                .build();
    }


    @DeleteMapping("delete/{bid}")
    @Operation(summary = "删除bucket")
    @Parameter(name = "bid", description = "bucket id")
    @CheckBucketPrivilege(spelString = "#bid",argType = AuthConstant.BID,privilege={AuthConstant.OWNER})
    public Result delete(@PathVariable int bid) {
        return Result
                .builder()
                .code(200)
                .data(bucketService.removeById(bid))
                .build();
    }

    @PutMapping
    @Operation(summary = "为当前登录用户创建bucket")
    public Result create(@RequestBody Bucket bucket) {
        bucket.setUid(Optional
                .ofNullable(loginUserProvider.getLoginUser())
                .orElseThrow(() -> new RuntimeException("loginUserProvider exception"))
                .getId());
        bucket.setUsedSize(0.0);
        boolean success=bucketService.save(bucket);
        bucketPrivilegeService.setPrivilege(BucketPrivilege
                .builder().bid(bucket.getId())
                .uid(bucket.getUid())
                .privilege(AuthConstant.READWRITE)
                .build());
        return Result
                .builder()
                .code(200)
                .data(success)
                .build();
    }

    @PutMapping("/create")
    @Operation(summary = "为指定用户创建bucket")
    public Result create2Usr(@RequestBody Bucket bucket) {
        bucket.setUsedSize(0.0);
        return Result
                .builder()
                .code(200)
                .data(bucketService.save(bucket))
                .build();
    }


    @PostMapping("/update")
    @Operation(summary = "更新bucket信息")
    @CheckBucketPrivilege(spelString = "#bucket.id",argType = AuthConstant.BID,privilege=AuthConstant.OWNER)
    public Result update(@RequestBody Bucket bucket) {
        return Result
                .builder()
                .code(200)
                .data(bucketService.updateById(bucket))
                .build();
    }

    @GetMapping("/get/{bid}")
    @Operation(summary = "获取bucket信息")
    @Parameter(name = "id", description = "bucket id")
    @CheckBucketPrivilege(spelString = "#bid",argType = AuthConstant.BID,privilege = {AuthConstant.ONLYREAD, AuthConstant.OWNER, AuthConstant.READWRITE})
    public Result get(@PathVariable int bid) {
        return Result
                .builder()
                .code(200)
                .data(bucketService.getById(bid))
                .build();
    }



}
