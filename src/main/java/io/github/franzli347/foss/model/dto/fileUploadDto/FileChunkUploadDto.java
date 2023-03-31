package io.github.franzli347.foss.model.dto.fileUploadDto;

import io.github.franzli347.foss.annotation.FiledExistInTable;
import io.github.franzli347.foss.common.constant.FileConstant;
import io.github.franzli347.foss.web.service.BucketService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FileChunkUploadDto {
    @Schema(description = "目标桶id")
    @FiledExistInTable(colum = "id",serviceClz = BucketService.class,message = "bucket_id不存在")
    int bid;

    @Schema(description = "文件名")
    @Pattern(regexp = FileConstant.ILLEGAL_FILE_RE,message = FileConstant.FILE_NAME_ILLEGAL_MSG)
    String name;

    @Schema(description = "分块数量")
    @Min(value = FileConstant.MIN_CHUNKS_NUM,message = FileConstant.MIN_CHUNKS_MSG)
    int chunks;

    @Schema(description = "当前上传分块序号")
    int chunk;

    @Schema(description = "文件大小(总文件大小，不是分块之后的)")
    @Min(value = FileConstant.MIN_FILE_SIZE,message = FileConstant.MIN_FILE_SIZE_MSG)
    Long size;

    @Schema(description = "文件md5(不是分块之后的)")
    String md5;


}
