package com.kata.match.api.resource;

import static com.kata.match.api.resource.MatchController.MATCHES_PATH;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.kata.match.api.annotation.MatchContext;
import com.kata.match.api.dto.match.CreateMatch;
import com.kata.match.api.dto.match.Match;
import com.kata.match.api.dto.match.MatchScore;
import com.kata.match.api.dto.match.MatchStatusEnum;
import com.kata.match.api.dto.match.PlayerEnum;

@Path(MATCHES_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Service
public interface MatchController {

    String MATCHES_PATH = "/matches";
    String MATCH_ID = "matchId";
    String MATCHES_MATCH_ID_PATH = "/{" + MATCH_ID + ": [0-9]+}";
    String PLAYERS_PATH = "/players";
    String PLAYER_ID = "playerId";
    String PLAYER_ID_PATH = "/{" + PLAYER_ID + "}";
    String FULL_PLAYERS_PATH = PLAYERS_PATH + PLAYER_ID_PATH;
    String MATCHES_WIN_POINT = "/goal";
    String MATCHES_SCORE = "/score";
    String MATCHES_STATUS = "/status";
    String MATCHES_WINNER = "/winner";

    /**
     * create and start a new match
     */
    @POST
    @ResponseStatus(HttpStatus.CREATED)
    Match createMatch(@BeanParam CreateMatch createMatch);

    /**
     * mark a goal for a player uri: matches/{matchId}/players/{playerId}/goal
     */
    @POST
    @Path(MATCHES_MATCH_ID_PATH + FULL_PLAYERS_PATH + MATCHES_WIN_POINT)
    @MatchContext
    Match winPoint(@PathParam(MATCH_ID) Long matchId, @PathParam(PLAYER_ID) PlayerEnum player);

    /**
     * returns the score for the given player, or for both of them. uri:  matches/{matchId}/players/{playerId}/score
     */
    @GET
    @Path(MATCHES_MATCH_ID_PATH + MATCHES_SCORE)
    @MatchContext
    MatchScore getScore(@PathParam(MATCH_ID) Long matchId);

    /**
     * returns the status of the match #{{@link MatchStatusEnum}}/>. uri:  matches/{matchId}/players/{playerId}/status
     */
    @GET
    @Path(MATCHES_MATCH_ID_PATH + MATCHES_STATUS)
    @MatchContext
    MatchStatusEnum getMatchStatus(@PathParam(MATCH_ID) Long matchId);

    /**
     * returns the winner if the match if finished. uri:  matches/{matchId}/players/{playerId}/winner
     */
    @GET
    @Path(MATCHES_MATCH_ID_PATH + MATCHES_WINNER)
    @MatchContext
    PlayerEnum getWinner(@PathParam(MATCH_ID) Long matchId);

    @GET
    String ping();
}
