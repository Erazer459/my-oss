package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.common.ValidatedGroup;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * @author FranzLi
 */
@RequestMapping("/bucket")
@RestController
@Validated
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
    public List<Bucket> list(@PathVariable @Parameter(name = "page", description = "页码") int page,
                             @PathVariable @Parameter(name = "size", description = "每页数量") int size) {
        return bucketService.getBucketsByUserIdWithPage(Optional.ofNullable(loginUserProvider.getLoginUser())
                        .orElseThrow(() -> new RuntimeException("loginUserProvider_exception"))
                        .getId()
                , page, size);
    }


    @PostMapping("listAll/{page}/{size}")
    @Operation(summary = "获取登录用户的所有有权限的bucket")
    public List<Bucket> listAll(@PathVariable @Parameter(name = "page", description = "页码") int page,
                          @PathVariable @Parameter(name = "size", description = "每页数量") int size) {
        return bucketService.listAll(Optional
                .ofNullable(loginUserProvider.getLoginUser())
                .orElseThrow(() -> new RuntimeException("loginUserProvider exception")).getId(), page, size);
    }


    @DeleteMapping("delete/{bid}")
    @Operation(summary = "删除bucket")
    @Parameter(name = "bid", description = "bucket id")
    @CheckBucketPrivilege(spelString = "#bid",argType = AuthConstant.BID,privilege={AuthConstant.OWNER})
    public boolean delete(@PathVariable int bid) {
        return bucketService.removeBucket(bid);
    }

    @PutMapping
    @Operation(summary = "为当前登录用户创建bucket")
    public boolean create(@RequestBody Bucket bucket) {
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
        return success;
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
