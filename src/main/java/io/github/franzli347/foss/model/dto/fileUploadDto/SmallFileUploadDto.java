package io.github.franzli347.foss.model.dto.fileUploadDto;

import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.web.service.BucketService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SmallFileUploadDto {

    @Parameter(description = "目标桶id")
    @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket_id不存在")
    int bid;

    @Parameter(description = "文件md5",required = true)
    String md5;

}
