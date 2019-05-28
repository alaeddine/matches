package com.kata.match.domain.exception;

public class NotFoundMatchException extends RuntimeException {

    private static final long serialVersionUID = -5365630135856068164L;

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
