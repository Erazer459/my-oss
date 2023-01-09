package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.entity.Bucket;
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

    public BucketController(BucketService bucketService, LoginUserProvider loginUserProvider) {
        this.bucketService = bucketService;
        this.loginUserProvider = loginUserProvider;
    }

    @PostMapping("list/{page}/{size}")
    @Operation(summary = "获取登录用户的所有bucket")
    @Parameter(name = "page", description = "页码")
    @Parameter(name = "size", description = "每页数量")
    public Result list(@PathVariable int page, @PathVariable int size) {
        //TODO validate
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
        //TODO validate
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


    @DeleteMapping("delete/{id}")
    @Operation(summary = "删除bucket")
    @Parameter(name = "id", description = "bucket id")
    public Result delete(@PathVariable int id) {
        //TODO: bucket is empty validation
        //TODO: validate privilege
        return Result
                .builder()
                .code(200)
                .data(bucketService.removeById(id))
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
        return Result
                .builder()
                .code(200)
                .data(bucketService.save(bucket))
                .build();
    }

    @PutMapping("/create")
    @Operation(summary = "为指定用户创建bucket")
    public Result create2Usr(@RequestBody Bucket bucket) {//TODO 当前用户如何管理此bucket?应该使用共享表存储共享关系
        bucket.setUsedSize(0.0);
        return Result
                .builder()
                .code(200)
                .data(bucketService.save(bucket))
                .build();
    }


    @PostMapping("/update")
    @Operation(summary = "更新bucket信息")
    public Result update(@RequestBody Bucket bucket) {
        //TODO: privilege validate
        return Result
                .builder()
                .code(200)
                .data(bucketService.updateById(bucket))
                .build();
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "获取bucket信息")
    @Parameter(name = "id", description = "bucket id")
    public Result get(@PathVariable int id) {
        return Result
                .builder()
                .code(200)
                .data(bucketService.getById(id))
                .build();
    }



}
