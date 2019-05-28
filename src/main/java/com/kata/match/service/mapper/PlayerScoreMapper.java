package com.kata.match.service.mapper;

import org.springframework.stereotype.Component;

import com.kata.match.api.dto.match.PlayerScore;
import com.kata.match.domain.model.PlayerScoreEntity;

@Component
public class PlayerScoreMapper {

    public PlayerScore fromEntity(PlayerScoreEntity entity) {

        if (entity == null) {
            return null;
        }

        return PlayerScore.builder()
                .gameScore(entity.gameScore()
                        .getValue())
                .build();
    }
}
