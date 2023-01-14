package io.github.franzli347.foss.controller;

import io.github.franzli347.foss.dto.ImageSimilarity;
import io.github.franzli347.foss.service.IovService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author FranzLi
 */
@RestController
@Tag(name = "图片和视频二次利用")
public class SuController {
    IovService iovService;
    public SuController(IovService iovService) {
        this.iovService = iovService;
    }

    @PostMapping("/imageDiff")
    @Operation(summary = "图片相似度比较(可以用来去重)")
    public List<ImageSimilarity> imageDiff(@Parameter(description = "图片id列表") List<String> ids) {
        return iovService.imageDiff(ids);
    }
}
