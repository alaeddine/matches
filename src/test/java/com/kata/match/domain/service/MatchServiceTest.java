package com.kata.match.domain.service;

import static com.kata.match.domain.model.MatchStatusEnumEntity.FINISHED;
import static com.kata.match.domain.model.MatchStatusEnumEntity.STARTED;
import static com.kata.match.domain.model.PlayerEnumEntity.NO_ONE;
import static com.kata.match.domain.model.PlayerEnumEntity.PLAYER1;
import static com.kata.match.domain.model.PlayerEnumEntity.PLAYER2;
import static com.kata.match.mocks.MatchTestUtil.GAME_SCORE_ADV;
import static com.kata.match.mocks.MatchTestUtil.GAME_SCORE_DEUCE;
import static com.kata.match.mocks.MatchTestUtil.GAME_SCORE_FIFTEEN;
import static com.kata.match.mocks.MatchTestUtil.GAME_SCORE_FORTY;
import static com.kata.match.mocks.MatchTestUtil.GAME_SCORE_ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.kata.match.domain.dao.MatchDao;
import com.kata.match.domain.model.GameScoreEnumEntity;
import com.kata.match.domain.model.MatchEntity;
import com.kata.match.domain.model.MatchStatusEnumEntity;
import com.kata.match.domain.model.PlayerEnumEntity;
import com.kata.match.mocks.MatchTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MatchServiceTest {

    @MockBean
    MatchDao matchDao = null;

    @Autowired
    MatchTestUtil matchTestUtil;

    @Autowired
    MatchService matchService;

    @Test
    public void when_score_is_0_0_and_player2_win_point_then_score_0_15() {
        testScore(PLAYER2, GAME_SCORE_ZERO, GAME_SCORE_ZERO, GAME_SCORE_ZERO, GAME_SCORE_FIFTEEN, NO_ONE, STARTED);
    }

    @Test
    public void when_score_is_40_40_and_player2_win_point_then_score_40_ADV() {
        testScore(PLAYER2, GAME_SCORE_FORTY, GAME_SCORE_FORTY, GAME_SCORE_FORTY, GAME_SCORE_ADV, NO_ONE, STARTED);
    }

    @Test
    public void when_score_is_40_ADV_and_player1_win_point_then_score_DEUCE_DEUCE() {
        testScore(PLAYER1, GAME_SCORE_FORTY, GAME_SCORE_ADV, GAME_SCORE_DEUCE, GAME_SCORE_DEUCE, NO_ONE, STARTED);
    }

    @Test
    public void when_score_is_40_ADV_and_player2_win_point_then_score_0_0() {
        testScore(PLAYER2, GAME_SCORE_FORTY, GAME_SCORE_ADV, GAME_SCORE_ZERO, GAME_SCORE_ZERO, PLAYER2, FINISHED);
    }

    @Test
    public void when_score_is_DEUCE_DEUCE_and_player1_win_point_then_score_ADV_40() {
        testScore(PLAYER1, GAME_SCORE_DEUCE, GAME_SCORE_DEUCE, GAME_SCORE_ADV, GAME_SCORE_FORTY, NO_ONE, STARTED);
    }

    /**
     * test the expected score (expectedgsPl1,expectedgsPl2) and the expected winner expectedWinner When playerWinPoint
     * win a point and the score was (beforeGsPl1,beforegsPl2)
     */
    void testScore(PlayerEnumEntity playerWinPoint, GameScoreEnumEntity beforeGsPl1, GameScoreEnumEntity beforeGsPl2,
            GameScoreEnumEntity expectedGsPl1, GameScoreEnumEntity expectedgsPl2, PlayerEnumEntity expectedWinner,
            MatchStatusEnumEntity expectedMatchStatus) {
        doReturn(matchTestUtil.matchScoreEntity(beforeGsPl1, beforeGsPl2)).when(matchDao)
                .findOne(anyLong());
        MatchEntity matchEntity = matchService.winPoint(1L, playerWinPoint);

        assertThat(matchEntity.winner()).isEqualTo(expectedWinner);
        assertThat(matchEntity.status()).isEqualTo(expectedMatchStatus);
        assertThat(matchEntity.player1Score()
                .gameScore()).isEqualTo(expectedGsPl1);
        assertThat(matchEntity.player2Score()
                .gameScore()).isEqualTo(expectedgsPl2);
    }
}
