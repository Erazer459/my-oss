package io.github.franzli347.foss.utils;

import io.github.franzli347.foss.common.ThrowingConsumer;
import io.github.franzli347.foss.exception.FileException;

import java.util.function.Consumer;

/**
 * Stream流工具类
 * @author FranzLi
 */
public class StreamUtil {

    private StreamUtil(){}
    public static <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, Exception> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new FileException(ex.getMessage());
            }
        };
    }
}
