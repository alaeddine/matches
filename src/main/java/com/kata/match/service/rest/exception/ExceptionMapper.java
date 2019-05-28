package com.kata.match.service.rest.exception;

import javax.ws.rs.core.Response;

public interface ExceptionMapper<E extends Throwable> {

    Response toResponse(E exception);
}