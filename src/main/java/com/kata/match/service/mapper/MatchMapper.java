package com.kata.match.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kata.match.api.dto.match.CreateMatch;
import com.kata.match.api.dto.match.Match;
import com.kata.match.domain.model.MatchEntity;

@Component
public class MatchMapper {
    @Autowired
    PlayerScoreMapper playerScoreMapper;

    @Autowired
    MatchScoreMapper matchScoreMapper;

    @Autowired
    MatchStatusEnumMapper matchStatusEnumMapper;

    public Match fromEntity(MatchEntity entity) {
        if (entity == null) {
            return null;
        }

        return Match.builder()
                .id(entity.id())
                .player1(entity.player1())
                .player2(entity.player2())
                .matchScore(matchScoreMapper.fromEntity(entity))
                .status(matchStatusEnumMapper.fromEntity(entity.status()))
                .build();
    }

    public MatchEntity fromCreateDto(CreateMatch dto) {
        if (dto == null) {
            return null;
        }
        return MatchEntity.builder()
                .player1(dto.player1Name())
                .player2(dto.player2Name())
                .build();
    }
}
