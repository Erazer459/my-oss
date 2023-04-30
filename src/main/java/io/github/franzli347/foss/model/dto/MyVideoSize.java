package io.github.franzli347.foss.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @ClassName MyVideoSize
 * @Author AlanC
 * @Date 2023/4/30 7:56
 **/
@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MyVideoSize {
    private int Width;
    private int Height;
}
