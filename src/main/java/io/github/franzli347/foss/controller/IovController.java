package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.common.Result;
import io.github.franzli347.foss.service.IovService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

/**
 * @author FranzLi
 */
@RestController
@Tag(name = "图片和视频二次利用")
public class IovController {
    IovService iovService;
    public IovController(IovService iovService) {
        this.iovService = iovService;
    }
    @PostMapping("/imageDiff")
    public Result imageDiff(List<Serializable> ids) {
        return Result.builder()
                .code(200)
                .data(iovService.imageDiff(ids))
                .build();
    }

}
