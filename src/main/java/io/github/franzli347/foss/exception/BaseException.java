package io.github.franzli347.foss.exception;

/**
 * 自定义异常
 * @author FranzLi
 */
public class BaseException extends RuntimeException{

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable t){
        super(t);
    }

}
