package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.entity.Bucket;
import io.github.franzli347.foss.service.BucketService;
import io.github.franzli347.foss.support.userSupport.LoginUserProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author FranzLi
 */
@RequestMapping("/bucket")
public class BucketController {

    private final BucketService bucketService;

    private final LoginUserProvider loginUserProvider;

    public BucketController(BucketService bucketService, LoginUserProvider loginUserProvider) {
        this.bucketService = bucketService;
        this.loginUserProvider = loginUserProvider;
    }


    @PostMapping("list")
    @Operation(summary = "获取登录用户的所有bucket")
    @Parameters({
            @Parameter(name = "page", description = "页码"),
            @Parameter(name = "size", description = "每页数量"),
    })
    //TODO validate
    public Result list(int current,int size) {
        int loginUserId = Optional.ofNullable(loginUserProvider.getLoginUser()).orElseThrow(() -> new RuntimeException("loginUserProvider exception")).getId();
        List<Bucket> bucketsByUserIdWithPage = bucketService.getBucketsByUserIdWithPage(loginUserId, current, size);
        return Result.builder().code(200).data(bucketsByUserIdWithPage).build();
    }


}
