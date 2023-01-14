package io.github.franzli347.foss.exception;

public class BucketException extends BaseException{
    public BucketException(String message, Throwable cause) {
        super(message, cause);
    }

    public BucketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BucketException() {
    }

    public BucketException(String message) {
        super(message);
    }

    public BucketException(Throwable t) {
        super(t);
    }
}
