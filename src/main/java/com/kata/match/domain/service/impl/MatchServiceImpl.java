package com.kata.match.domain.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.match.domain.dao.MatchDao;
import com.kata.match.domain.exception.MatchWrongStateException;
import com.kata.match.domain.model.GameScoreEnumEntity;
import com.kata.match.domain.model.MatchEntity;
import com.kata.match.domain.model.MatchStatusEnumEntity;
import com.kata.match.domain.model.PlayerEnumEntity;
import com.kata.match.domain.model.PlayerScoreEntity;
import com.kata.match.domain.service.MatchService;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchDao matchDao;

    @Override
    @Transactional
    public MatchEntity createMatch(MatchEntity newMatchEntity) {
        return matchDao.save(newMatchEntity);
    }

    @Override
    @Transactional
    public MatchEntity winPoint(final Long matchId, final PlayerEnumEntity player) {
        MatchEntity matchEntity = matchDao.findOne(matchId);
        if (matchEntity.status() == MatchStatusEnumEntity.FINISHED) {
            throw new MatchWrongStateException("the match is finished you can't mark goal");
        }

        if ((player != PlayerEnumEntity.PLAYER1) && (player != PlayerEnumEntity.PLAYER2)) {
            throw new IllegalArgumentException("not expected player. player should be PLAYER1 OR PLAYER2");
        }

        PlayerScoreEntity playerScoreEntity;
        PlayerEnumEntity currentPlayer;
        if (player == PlayerEnumEntity.PLAYER1) {
            playerScoreEntity = matchEntity.player1Score();
            currentPlayer = PlayerEnumEntity.PLAYER1;
        } else {
            playerScoreEntity = matchEntity.player2Score();
            currentPlayer = PlayerEnumEntity.PLAYER2;
        }

        Boolean isWinner = false;
        switch (playerScoreEntity.gameScore()) {
        case ZERO:
            playerScoreEntity.gameScore(GameScoreEnumEntity.FIFTEEN);
            break;
        case FIFTEEN:
            playerScoreEntity.gameScore(GameScoreEnumEntity.THIRTY);
            break;
        case THIRTY:
            playerScoreEntity.gameScore(GameScoreEnumEntity.FORTY);
            break;
        case FORTY:
            playerScoreEntity.gameScore(GameScoreEnumEntity.ZERO);
            isWinner = true;
        }

        if (isWinner) {
            matchEntity.status(MatchStatusEnumEntity.FINISHED);
            matchEntity.player1Score()
                    .gameScore(GameScoreEnumEntity.ZERO);
            matchEntity.player2Score()
                    .gameScore(GameScoreEnumEntity.ZERO);
            matchEntity.winner(currentPlayer);
        }

        return matchEntity;
    }

    @Override
    @Transactional
    public MatchEntity getScore(final Long matchId) {
        MatchEntity matchEntity = matchDao.findOne(matchId);
        return matchEntity;
    }

    @Override
    @Transactional
    public PlayerEnumEntity getWinner(final Long matchId) {
        MatchEntity matchEntity = matchDao.findOne(matchId);
        if (matchEntity.status() == MatchStatusEnumEntity.STARTED) {
            throw new MatchWrongStateException("the match is not finished yet to know the winner");
        }

        return matchEntity.winner();
    }

    @Override
    @Transactional
    public Boolean existById(final Long matchId) {
        return matchDao.existsByMatchId(matchId);
    }

    private Boolean incrementGameScore(GameScoreEnumEntity gameScore) {
        Boolean isWinner = false;
        switch (gameScore) {
        case ZERO:
            gameScore = GameScoreEnumEntity.FIFTEEN;
            break;
        case FIFTEEN:
            gameScore = GameScoreEnumEntity.THIRTY;
            break;
        case THIRTY:
            gameScore = GameScoreEnumEntity.FORTY;
            break;
        case FORTY:
            gameScore = GameScoreEnumEntity.ZERO;
            isWinner = true;
        }
        return isWinner;
    }

    @Override
    @Transactional
    public MatchStatusEnumEntity getMatchStatus(final Long matchId) {
        return matchDao.getMatchStatus(matchId);
    }
}
