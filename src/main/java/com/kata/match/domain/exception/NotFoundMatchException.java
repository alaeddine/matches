package com.kata.match.domain.exception;

public class NotFoundMatchException extends RuntimeException {

    public NotFoundMatchException(String message) {
        super(message);
    }

    public NotFoundMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundMatchException(Throwable cause) {
        super(cause);
    }
}
