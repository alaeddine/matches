package com.kata.match.domain.exception;

/**
 * raised when trying to perform an operation that is not allowed in the current match state. for example marking goal
 * when the match status is #{{@link com.kata.match.api.dto.match.MatchStatusEnum#FINISHED}}
 */
public class MatchWrongStateException extends RuntimeException {

    public MatchWrongStateException(String message) {
        super(message);
    }

    public MatchWrongStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public MatchWrongStateException(Throwable cause) {
        super(cause);
    }
}
