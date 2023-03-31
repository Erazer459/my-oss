package io.github.franzli347.foss.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
public class WsResult extends Result {
    String userId;
    String wsTag;
}
