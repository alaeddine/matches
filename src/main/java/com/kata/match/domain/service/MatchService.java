package com.kata.match.domain.service;

import com.kata.match.domain.exception.MatchWrongStateException;
import com.kata.match.domain.model.MatchEntity;
import com.kata.match.domain.model.MatchStatusEnumEntity;
import com.kata.match.domain.model.PlayerEnumEntity;

public interface MatchService {
    MatchEntity createMatch(MatchEntity matchEntity);

    MatchEntity winPoint(Long matchId, PlayerEnumEntity player) throws MatchWrongStateException;

    MatchEntity getScore(Long matchId) throws MatchWrongStateException;

    PlayerEnumEntity getWinner(Long matchId) throws MatchWrongStateException;

    Boolean existById(Long matchId);

    MatchStatusEnumEntity getMatchStatus(Long matchId);
}
