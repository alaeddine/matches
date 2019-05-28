package com.kata.match.mocks;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kata.match.api.dto.match.Match;
import com.kata.match.domain.model.GameScoreEnumEntity;
import com.kata.match.domain.model.MatchEntity;
import com.kata.match.domain.model.PlayerScoreEntity;
import com.kata.match.service.mapper.MatchMapper;

@Component
public class MatchTestUtil {
    public final Long MATCH_ID = new Random().nextLong();
    public final String PLAYER_1_NAME = "player1";
    public final String PLAYER_2_NAME = "player2";
    public final GameScoreEnumEntity GAME_SCORE_ZERO = GameScoreEnumEntity.ZERO;
    public final GameScoreEnumEntity GAME_SCORE_FIFTEEN = GameScoreEnumEntity.FIFTEEN;
    public final GameScoreEnumEntity GAME_SCORE_THIRTY = GameScoreEnumEntity.THIRTY;
    public final GameScoreEnumEntity GAME_SCORE_FORTY = GameScoreEnumEntity.FORTY;
    @Autowired
    MatchMapper matchMapper;

    public MatchEntity matchZeroVSZeroScoreEntity() {
        return MatchEntity.builder()
                .player1(PLAYER_1_NAME)
                .player2(PLAYER_2_NAME)
                .build();
    }

    public MatchEntity matchFortyVSThirtyScoreEntity() {
        return MatchEntity.builder()
                .player1(PLAYER_1_NAME)
                .player2(PLAYER_2_NAME)
                .player1Score(playerScore(GAME_SCORE_FORTY))
                .player2Score(playerScore(GAME_SCORE_THIRTY))
                .build();
    }

    public MatchEntity matchFortyVSFortyScoreEntity() {
        return MatchEntity.builder()
                .player1(PLAYER_1_NAME)
                .player2(PLAYER_2_NAME)
                .player1Score(playerScore(GAME_SCORE_FORTY))
                .player2Score(playerScore(GAME_SCORE_FORTY))
                .build();
    }

    public Match matchZeroVSZeroScoreDto() {
        return matchMapper.fromEntity(matchZeroVSZeroScoreEntity());
    }

    public Match matchFourtyVSThirtyScoreDto() {
        return matchMapper.fromEntity(matchZeroVSZeroScoreEntity());
    }

    public Match matchFortyVSFortyScoreDto() {
        return matchMapper.fromEntity(matchZeroVSZeroScoreEntity());
    }

    public PlayerScoreEntity playerScore(GameScoreEnumEntity gameScoreEnumEntity) {
        return PlayerScoreEntity.builder()
                .gameScore(gameScoreEnumEntity)
                .build();
    }
}
