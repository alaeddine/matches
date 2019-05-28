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
    public static final GameScoreEnumEntity GAME_SCORE_ZERO = GameScoreEnumEntity.ZERO;
    public static final GameScoreEnumEntity GAME_SCORE_FIFTEEN = GameScoreEnumEntity.FIFTEEN;
    public static final GameScoreEnumEntity GAME_SCORE_THIRTY = GameScoreEnumEntity.THIRTY;
    public static final GameScoreEnumEntity GAME_SCORE_FORTY = GameScoreEnumEntity.FORTY;
    public static final GameScoreEnumEntity GAME_SCORE_ADV = GameScoreEnumEntity.ADV;
    public static final GameScoreEnumEntity GAME_SCORE_DEUCE = GameScoreEnumEntity.DEUCE;
    public final Long MATCH_ID = new Random().nextLong();
    public final String PLAYER_1_NAME = "player1";
    public final String PLAYER_2_NAME = "player2";
    @Autowired
    MatchMapper matchMapper;

    public MatchEntity matchZeroVSZeroScoreEntity() {
        return MatchEntity.builder()
                .player1(PLAYER_1_NAME)
                .player2(PLAYER_2_NAME)
                .build();
    }

    public MatchEntity matchScoreEntity(GameScoreEnumEntity gs1, GameScoreEnumEntity gs2) {
        return MatchEntity.builder()
                .player1(PLAYER_1_NAME)
                .player2(PLAYER_2_NAME)
                .player1Score(playerScore(gs1))
                .player2Score(playerScore(gs2))
                .build();
    }

    public Match matchScoreDto(GameScoreEnumEntity gs1, GameScoreEnumEntity gs2) {
        return matchMapper.fromEntity(matchScoreEntity(gs1, gs2));
    }

    public MatchEntity matchFortyVSThirtyScoreEntity() {
        return matchScoreEntity(GAME_SCORE_FORTY, GAME_SCORE_THIRTY);
    }

    public MatchEntity matchFortyVSFortyScoreEntity() {
        return matchScoreEntity(GAME_SCORE_FORTY, GAME_SCORE_FORTY);
    }

    public Match matchZeroVSZeroScoreDto() {
        return matchMapper.fromEntity(matchScoreEntity(GAME_SCORE_ZERO, GAME_SCORE_ZERO));
    }

    public PlayerScoreEntity playerScore(GameScoreEnumEntity gameScoreEnumEntity) {
        return PlayerScoreEntity.builder()
                .gameScore(gameScoreEnumEntity)
                .build();
    }
}
