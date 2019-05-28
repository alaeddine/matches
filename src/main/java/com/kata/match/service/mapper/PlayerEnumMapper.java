package com.kata.match.service.mapper;

import org.springframework.stereotype.Component;

import com.kata.match.api.dto.match.PlayerEnum;
import com.kata.match.domain.model.PlayerEnumEntity;

@Component
public class PlayerEnumMapper {

    public PlayerEnum fromEntity(PlayerEnumEntity entity) {

        if (entity == null) {
            return null;
        }

        PlayerEnum playerEnum = null;
        switch (entity) {
        case PLAYER1: playerEnum = PlayerEnum.PLAYER1;
            break;
        case PLAYER2: playerEnum = PlayerEnum.PLAYER2;
            break;
        case ALL: playerEnum = PlayerEnum.ALL;
            break;
        }

        return playerEnum;
    }

    public PlayerEnumEntity fromDto(PlayerEnum dto) {

        if (dto == null) {
            return null;
        }

        PlayerEnumEntity playerEnum = null;
        switch (dto) {
        case PLAYER1: playerEnum = PlayerEnumEntity.PLAYER1;
            break;
        case PLAYER2: playerEnum = PlayerEnumEntity.PLAYER2;
            break;
        case ALL: playerEnum = PlayerEnumEntity.ALL;
            break;
        }

        return playerEnum;
    }
}
