package io.github.franzli347.foss.model.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import ws.schild.jave.info.VideoSize;

/**
 * @ClassName VideoCompressArgs
 * @Author AlanC
 * @Date 2022/12/26 16:56
 **/
@Schema(description = "视频压缩参数")
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class VideoCompressArgs {
    @Schema(description = "视频分辨率,宽度*高度,2K:2560*1440或2048*1080 1080P:1920*1080 720P:1280*720 360P:480*360")
    private MyVideoSize videoSize;
    @Schema(description = "视频帧率")
    @Builder.Default
    private float frameRate=25;
    @Schema (description = "视频码率,单位bps")
    @Builder.Default
    private int bitRate=500000;
    @Hidden
    public String getVideoSizeAsFFArg(){
        return videoSize.getWidth()+"x"+ videoSize.getHeight();
    }
}
