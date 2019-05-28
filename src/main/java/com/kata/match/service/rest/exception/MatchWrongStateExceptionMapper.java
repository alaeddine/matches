package com.kata.match.service.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.kata.match.domain.exception.MatchWrongStateException;

@Provider
@Component
public class MatchWrongStateExceptionMapper
        implements ExceptionMapper<MatchWrongStateException> {

    @Override
    public Response toResponse(final MatchWrongStateException exception) {
        return Response.status(Status.NOT_ACCEPTABLE)
                .build();
    }
}