package io.github.franzli347.foss.common;

/**
 * stream流工具，将checked exception转化为unchecked exception
 * @author FranzLi
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}