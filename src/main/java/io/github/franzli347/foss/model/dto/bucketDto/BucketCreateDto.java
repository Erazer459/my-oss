package io.github.franzli347.foss.model.dto.bucketDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;


@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BucketCreateDto {

    @Schema(description = "bucket名")
    private String name;

    @Schema(description = "bucket容量")
    @Min(value = 0, message = "必须大于等于0")
    private Double totalSize;

}
