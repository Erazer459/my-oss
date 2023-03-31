package io.github.franzli347.foss.model.dto.bucketDto;

import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.common.ValidatedGroup;
import io.github.franzli347.foss.web.service.BucketService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;


@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

public class BucketUpdateDto {

    @Schema(description = "bucketId")
    @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket id不存在",groups = {ValidatedGroup.Update.class,ValidatedGroup.Remove.class})
    @NotNull(message = "id不能为空",groups = {ValidatedGroup.Update.class})
    private Integer id;

    @Schema(description = "bucket名")
    private String name;

    @Schema(description = "bucket容量")
    private Double totalSize;

}
