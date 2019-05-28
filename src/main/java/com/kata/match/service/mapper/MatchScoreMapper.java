package com.kata.match.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kata.match.api.dto.match.MatchScore;
import com.kata.match.domain.model.MatchEntity;
import com.kata.match.domain.model.PlayerEnumEntity;

@Component
public class MatchScoreMapper {
    @Autowired
    PlayerScoreMapper playerScoreMapper;

    public MatchScore fromEntity(MatchEntity entity) {
        if (entity == null) {
            return null;
        }

        return MatchScore.builder()
                .player1Score(playerScoreMapper.fromEntity(entity.player1Score()))
                .player2Score(playerScoreMapper.fromEntity(entity.player2Score()))
                .winner(entity.winner())
                .winnerName(calculateWinnerName(entity))
                .build();
    }

    private String calculateWinnerName(MatchEntity entity) {
        String winner = "";
        if (entity.winner() == PlayerEnumEntity.PLAYER1) {
            winner = entity.player1();
        } else if (entity.winner() == PlayerEnumEntity.PLAYER2) {
            winner = entity.player2();
        }
        return winner;
    }
}
