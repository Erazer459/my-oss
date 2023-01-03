package io.github.franzli347.foss.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import ws.schild.jave.info.VideoSize;

/**
 * @ClassName MyVideo
 * @Author AlanC
 * @Date 2022/12/26 15:27
 **/
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
@Schema(description = "视频信息")
public class MyVideo{
    @Schema(description = "音频抽样率(单位Hz)")
    private int sampleRate;
    @Schema(description = "音频格式")
    private String sampleFormat;//onlyVideo

    @Schema(description = "声音频道数目(1或2)")
    private int channels;
    @Schema(description = "视频分辨率,宽度*高度,4K:4096*2160 2K:2560*1440或2048*1080 1080P:1920*1080 720P:1280*720")
    private VideoSize videoSize;
    @Schema(description = "视频帧率(fps)")
    private float frameRate;
    @Schema (description = "视频码率,单位bps")
    private int bitRate;
    @Schema(description = "视频格式,常用H.264")
    private String pixelType;
    @Schema(description = "视频时长(秒)")
    private long videoLength;
    @Schema(description = "视频大小(MB)")
    private String size;

}
