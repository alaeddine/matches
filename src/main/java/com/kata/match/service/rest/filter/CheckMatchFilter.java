package com.kata.match.service.rest.filter;

import static com.kata.match.api.annotation.MatchContext.MATCH_CHECK_PRIORITY;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kata.match.api.annotation.MatchContext;
import com.kata.match.api.resource.MatchController;
import com.kata.match.domain.service.MatchService;

/**
 * filter to check uri params
 */
@Provider
@Component
@Priority(MATCH_CHECK_PRIORITY)
@MatchContext
public class CheckMatchFilter implements ContainerRequestFilter {
    @Autowired
    MatchService matchService;

    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        String matchIdStr = requestContext.getUriInfo()
                .getPathParameters()
                .getFirst(MatchController.MATCH_ID);
        if (matchIdStr == null) {
            throw new RuntimeException("No match id found. Did you define your resources properly ?");
        }
        Long matchId;
        try {
            matchId = Long.parseLong(matchIdStr.trim());
        } catch (NumberFormatException ex) {
            throw new RuntimeException("wrong format for the matchId");
        }
    }
}
