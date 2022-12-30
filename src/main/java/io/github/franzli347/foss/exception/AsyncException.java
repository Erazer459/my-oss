package io.github.franzli347.foss.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName AsyncException
 * @Author AlanC
 * @Date 2022/12/28 20:56
 **/
@Data
@AllArgsConstructor
public class AsyncException extends Exception {
    private String errorMessage;
}
