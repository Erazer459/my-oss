package io.github.franzli347.foss.web.controller;

import io.github.franzli347.foss.model.dto.ImageSimilarity;
import io.github.franzli347.foss.web.service.IovService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<ImageSimilarity> imageDiff(@Parameter(description = "图片id列表") @RequestBody List<String> ids) {
        return iovService.imageDiff(ids);
    }
}
