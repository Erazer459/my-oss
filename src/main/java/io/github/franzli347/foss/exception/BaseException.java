package io.github.franzli347.foss.exception;

/**
 * 自定义异常
 * @author FranzLi
 */
public class BaseException extends RuntimeException{
    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

}
