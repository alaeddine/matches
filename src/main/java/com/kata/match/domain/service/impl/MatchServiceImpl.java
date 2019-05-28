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

        PlayerScoreEntity currentScoreEntity;
        PlayerScoreEntity otherScoreEntity;
        PlayerEnumEntity currentPlayer;
        if (player == PlayerEnumEntity.PLAYER1) {
            currentScoreEntity = matchEntity.player1Score();
            otherScoreEntity = matchEntity.player2Score();
            currentPlayer = PlayerEnumEntity.PLAYER1;
        } else {
            currentScoreEntity = matchEntity.player2Score();
            otherScoreEntity = matchEntity.player1Score();
            currentPlayer = PlayerEnumEntity.PLAYER2;
        }

        switch (currentScoreEntity.gameScore()) {
        case ZERO:
            currentScoreEntity.gameScore(GameScoreEnumEntity.FIFTEEN);
            break;
        case FIFTEEN:
            currentScoreEntity.gameScore(GameScoreEnumEntity.THIRTY);
            break;
        case THIRTY:
            currentScoreEntity.gameScore(GameScoreEnumEntity.FORTY);
            break;
        case FORTY:
            manageFortyState(currentScoreEntity, otherScoreEntity, matchEntity, currentPlayer);
            break;
        case DEUCE:
            manageDeuceState(currentScoreEntity, otherScoreEntity);
            break;
        case ADV:
            setMatchFinishedState(matchEntity, currentPlayer);
        }

        return matchEntity;
    }

    private void setMatchFinishedState(final MatchEntity matchEntity, final PlayerEnumEntity currentPlayer) {
        matchEntity.player1Score()
                .gameScore(GameScoreEnumEntity.ZERO);
        matchEntity.player2Score()
                .gameScore(GameScoreEnumEntity.ZERO);
        matchEntity.status(MatchStatusEnumEntity.FINISHED);
        matchEntity.winner(currentPlayer);
    }

    private void manageFortyState(PlayerScoreEntity currentScoreEntity, PlayerScoreEntity otherScoreEntity,
            MatchEntity matchEntity, PlayerEnumEntity currentPlayer) {
        if (otherScoreEntity.gameScore() == GameScoreEnumEntity.ADV) {
            currentScoreEntity.gameScore(GameScoreEnumEntity.DEUCE);
            otherScoreEntity.gameScore(GameScoreEnumEntity.DEUCE);
        } else if (otherScoreEntity.gameScore() == GameScoreEnumEntity.FORTY) {
            currentScoreEntity.gameScore(GameScoreEnumEntity.ADV);
        } else {// cases 0,15,30 => i'am a winner
            setMatchFinishedState(matchEntity, currentPlayer);
        }
    }

    private void manageDeuceState(PlayerScoreEntity currentScoreEntity, PlayerScoreEntity otherScoreEntity) {
        currentScoreEntity.gameScore(GameScoreEnumEntity.ADV);
        otherScoreEntity.gameScore(GameScoreEnumEntity.FORTY);
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
