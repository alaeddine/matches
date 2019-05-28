package com.kata.match.service.rest;

import static com.kata.match.api.resource.MatchController.MATCHES_SCORE;
import static com.kata.match.api.resource.MatchController.MATCHES_STATUS;
import static com.kata.match.api.resource.MatchController.MATCHES_WINNER;
import static com.kata.match.api.resource.MatchController.MATCHES_WIN_POINT;
import static com.kata.match.mocks.MatchTestUtil.GAME_SCORE_FIFTEEN;
import static com.kata.match.mocks.MatchTestUtil.GAME_SCORE_FORTY;
import static com.kata.match.mocks.MatchTestUtil.GAME_SCORE_THIRTY;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kata.match.api.dto.match.MatchStatusEnum;
import com.kata.match.api.dto.match.PlayerEnum;
import com.kata.match.domain.model.MatchEntity;
import com.kata.match.domain.model.PlayerEnumEntity;
import com.kata.match.domain.service.MatchService;
import com.kata.match.mocks.MatchTestUtil;
import com.kata.match.service.mapper.MatchMapper;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles({ "test" })
public class MatchControllerTest {

    @Autowired
    MatchMapper matchMapper;

    @Autowired
    MatchTestUtil matchTestUtil;

    @SpyBean
    private MatchService matchService;

    @Test
    public void should_create_match_zero_score() {
        doReturn(matchTestUtil.matchZeroVSZeroScoreEntity()).when(matchService)
                .createMatch(any());

        JSONObject body = new JSONObject()
                .put("player1Name", "player1")
                .put("player2Name", "player2");

        given().contentType(ContentType.JSON)
                .body(body.toString())
                .when()
                .post("tennis/matches")
                .peek()
                .then()
                .statusCode(Status.OK.getStatusCode())
                .body("player1", equalTo(matchTestUtil.matchZeroVSZeroScoreDto()
                        .player1()))
                .body("player2", equalTo(matchTestUtil.matchZeroVSZeroScoreDto()
                        .player2()))
                .body("matchScore.player1Score.gameScore", equalTo(matchTestUtil.matchZeroVSZeroScoreDto()
                        .matchScore()
                        .player1Score()
                        .gameScore()))

                .body("matchScore.player2Score.gameScore", equalTo(matchTestUtil.matchZeroVSZeroScoreDto()
                        .matchScore()
                        .player1Score()
                        .gameScore()));
    }

    /**
     * when the score is 15/40 and player2 win a point => player2 win
     */
    @Test
    public void Should_player2_win_When_score_is_thirty_forty_and_player2_mark_point() {
        Long newMatchId = matchService.createMatch(matchTestUtil.matchScoreEntity(GAME_SCORE_FIFTEEN, GAME_SCORE_FORTY))
                .id();
        given().contentType(ContentType.JSON)
                .body("{}")
                .when()
                //matches/{matchId}/players/{playerId}/goal
                .post("tennis/matches/" + newMatchId + "/players/" + PlayerEnum.PLAYER2 + MATCHES_WIN_POINT)
                .peek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("matchScore.player1Score.gameScore", equalTo("0"))
                .body("matchScore.winner", equalTo(PlayerEnum.PLAYER2.name()))
                .body("matchScore.winnerName", equalTo(matchTestUtil.matchFortyVSFortyScoreEntity()
                        .player2()));
    }

    @Test
    public void Should_return_score_forty_thirty() {
        Long newMatchId = matchService.createMatch(matchTestUtil.matchFortyVSThirtyScoreEntity())
                .id();
        given().contentType(ContentType.JSON)
                .when()
                //matches/{matchId}/players/{playerId}/score
                .get("tennis/matches/" + newMatchId + MATCHES_SCORE)
                .peek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("player1Score.gameScore", equalTo("40"))
                .body("player2Score.gameScore", equalTo("30"))
                .body("winner", equalTo(PlayerEnum.NO_ONE.name()))
                .body("winnerName", equalTo(""));
    }

    @Test
    public void Should_return_match_FINISHED() {
        //player1 win
        Long newMatchId = matchService.createMatch(matchTestUtil.matchFortyVSThirtyScoreEntity())
                .id();
        MatchEntity matchEntity = matchService.winPoint(newMatchId, PlayerEnumEntity.PLAYER1);

        given()
                .when()
                //matches/{matchId}/players/{playerId}/score
                .get("tennis/matches/" + newMatchId + MATCHES_STATUS)
                .peek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(equalTo("\"" + MatchStatusEnum.FINISHED.name() + "\""));
    }

    @Test
    public void Should_return_PLAYER1_is_winner() {
        //player1 win
        Long newMatchId = matchService.createMatch(matchTestUtil.matchScoreEntity(GAME_SCORE_FORTY, GAME_SCORE_THIRTY))
                .id();
        MatchEntity matchEntity = matchService.winPoint(newMatchId, PlayerEnumEntity.PLAYER1);

        given()
                .when()
                //matches/{matchId}/players/{playerId}/winner
                .get("tennis/matches/" + newMatchId + MATCHES_WINNER)
                .peek()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(equalTo("\"" + PlayerEnum.PLAYER1.name() + "\""));
    }
}
