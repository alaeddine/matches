package com.kata.match.service.rest.impl;

import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.match.api.dto.match.CreateMatch;
import com.kata.match.api.dto.match.Match;
import com.kata.match.api.dto.match.MatchScore;
import com.kata.match.api.dto.match.MatchStatusEnum;
import com.kata.match.api.dto.match.PlayerEnum;
import com.kata.match.api.resource.MatchController;
import com.kata.match.domain.model.MatchEntity;
import com.kata.match.domain.service.MatchService;
import com.kata.match.service.mapper.MatchMapper;
import com.kata.match.service.mapper.MatchStatusEnumMapper;
import com.kata.match.service.mapper.PlayerEnumMapper;

@Service
public class MatchControllerImpl implements MatchController {

    @Context
    HttpResponse response;
    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchMapper matchMapper;
    @Autowired
    private PlayerEnumMapper playerEnumMapper;
    @Autowired
    private MatchStatusEnumMapper matchStatusEnumMapper;

    @Override
    public Match createMatch(CreateMatch createMatch) {

        MatchEntity retMatch = matchService.createMatch(matchMapper.fromCreateDto(createMatch));

        return matchMapper.fromEntity(retMatch);
    }

    @Override
    public Match winPoint(Long matchId, PlayerEnum player) {
        MatchEntity retMatch = matchService.winPoint(matchId, playerEnumMapper.fromDto(player));
        return matchMapper.fromEntity(retMatch);
    }

    @Override
    public MatchScore getScore(Long matchId) {
        MatchEntity retMatch = matchService.getScore(matchId);
        Match match = matchMapper.fromEntity(retMatch);
        return MatchScore.builder()
                .player1Score(match.matchScore()
                        .player1Score())
                .player2Score(match.matchScore()
                        .player2Score())
                .winnerName(match.matchScore()
                        .winnerName())
                .winner(match.matchScore()
                        .winner())
                .build();
    }

    @Override
    public MatchStatusEnum getMatchStatus(Long matchId) {
        return matchStatusEnumMapper.fromEntity(matchService.getMatchStatus(matchId));
    }

    @Override
    public PlayerEnum getWinner(Long matchId) {
        return playerEnumMapper.fromEntity(matchService.getWinner(matchId));
    }

    @Override
    public String ping() {
        return "ping: response from server";
    }
}
