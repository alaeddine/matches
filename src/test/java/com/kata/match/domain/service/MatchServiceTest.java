package com.kata.match.domain.service;

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
    public void Should_player1_win_When_score_is_forty_Vs_thirty_and_player1_mark_point() {
        doReturn(matchTestUtil.matchFortyVSThirtyScoreEntity()).when(matchDao)
                .findOne(anyLong());

        PlayerEnumEntity expectedWinner = PlayerEnumEntity.PLAYER1;
        MatchEntity matchEntity = matchService.winPoint(0L, PlayerEnumEntity.PLAYER1);

        assertThat(matchEntity.winner()).isEqualTo(expectedWinner);
        assertThat(matchEntity.status()).isEqualTo(MatchStatusEnumEntity.FINISHED);
        assertThat(matchEntity.player1Score()
                .gameScore()).isEqualTo(matchTestUtil.GAME_SCORE_ZERO);
        assertThat(matchEntity.player2Score()
                .gameScore()).isEqualTo(matchTestUtil.GAME_SCORE_ZERO);
    }

    @Test
    public void Should_player2_win_fifteen_When_score_is_zero_Vs_zero_and_player2_mark_point() {
        doReturn(matchTestUtil.matchZeroVSZeroScoreEntity()).when(matchDao)
                .findOne(anyLong());

        PlayerEnumEntity expectedWinner = PlayerEnumEntity.NO_ONE;
        MatchEntity matchEntity = matchService.winPoint(0L, PlayerEnumEntity.PLAYER2);

        assertThat(matchEntity.winner()).isEqualTo(expectedWinner);
        assertThat(matchEntity.status()).isEqualTo(MatchStatusEnumEntity.STARTED);
        assertThat(matchEntity.player1Score()
                .gameScore()).isEqualTo(matchTestUtil.GAME_SCORE_ZERO);
        assertThat(matchEntity.player2Score()
                .gameScore()).isEqualTo(matchTestUtil.GAME_SCORE_FIFTEEN);
    }
}
