package io.github.franzli347.foss.common;

/**
 * @author FranzLi
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;
}