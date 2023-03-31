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
public class FileUploadTaskInitDto {

    @Schema(description = "目标桶id")
    @FiledExistInTable(colum = "id", serviceClz = BucketService.class, message = "bucket_id不存在")
    Integer bid;

    @Schema(description = "文件名")
    @Pattern(regexp = FileConstant.ILLEGAL_FILE_RE, message = FileConstant.FILE_NAME_ILLEGAL_MSG)
    String name;

    @Schema(description = "分块数量")
    @Min(value = FileConstant.MIN_CHUNKS_NUM, message = FileConstant.MIN_CHUNKS_MSG)
    int chunks;

    @Schema(description = "文件md5")
    String md5;

    @Schema(description = "文件大小(单位Byte)")
    @Min(value = FileConstant.MIN_FILE_SIZE, message = FileConstant.MIN_FILE_SIZE_MSG) long size;
}
