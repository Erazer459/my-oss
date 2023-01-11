package io.github.franzli347.foss.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @ClassName wsResult
 * @Author AlanC
 * @Date 2023/1/7 15:29
 **/
@Data
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class WsResult extends Result{
    String userId;
    String wsTag;
}
