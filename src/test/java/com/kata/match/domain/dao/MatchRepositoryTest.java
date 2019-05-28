package com.kata.match.domain.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.kata.match.domain.model.MatchEntity;
import com.kata.match.mocks.MatchTestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MatchRepositoryTest {

    @Autowired
    MatchDao matchDao;

    @Autowired
    MatchTestUtil matchTestUtil;

    @Test
    public void should_save_new_match_with_zero_score() {
        MatchEntity resultMatch = matchDao.save(matchTestUtil.matchZeroVSZeroScoreEntity());
        assertThat(resultMatch.player1()).isEqualTo(matchTestUtil.matchZeroVSZeroScoreEntity()
                .player1());
        assertThat(resultMatch.player2()).isEqualTo(matchTestUtil.matchZeroVSZeroScoreEntity()
                .player2());
        assertThat(resultMatch.player1Score()
                .gameScore()).isEqualTo(matchTestUtil.GAME_SCORE_ZERO);
    }

    @Test
    public void should_save_new_match_with_score_forty_thirty() {
        MatchEntity resultMatch = matchDao.save(matchTestUtil.matchFortyVSThirtyScoreEntity());
        assertThat(resultMatch.player1()).isEqualTo(matchTestUtil.PLAYER_1_NAME);
        assertThat(resultMatch.player2()).isEqualTo(matchTestUtil.PLAYER_2_NAME);
        assertThat(resultMatch.player1Score()
                .gameScore()).isEqualTo(matchTestUtil.GAME_SCORE_FORTY);
        assertThat(resultMatch.player2Score()
                .gameScore()).isEqualTo(matchTestUtil.GAME_SCORE_THIRTY);
    }

    @Test
    public void should_return_true_when_match_id_exist() {
        MatchEntity resultMatch = matchDao.save(matchTestUtil.matchFortyVSThirtyScoreEntity());
        resultMatch.id();

        assertThat(matchDao.existsByMatchId(resultMatch.id())).isTrue();
    }

    @Test
    public void should_return_false_when_match_id_not_exist() {
        Long wrongMatchId = 9999999L;

        assertThat(matchDao.existsByMatchId(wrongMatchId)).isFalse();
    }
}
