package com.kata.match.domain.exception;

public class InternalServerException extends RuntimeException {

    private static final long serialVersionUID = -5365630138856068164L;

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }
}
