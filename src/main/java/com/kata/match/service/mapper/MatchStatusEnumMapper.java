package com.kata.match.service.mapper;

import org.springframework.stereotype.Component;

import com.kata.match.api.dto.match.MatchStatusEnum;
import com.kata.match.domain.model.MatchStatusEnumEntity;

@Component
public class MatchStatusEnumMapper {

    public MatchStatusEnum fromEntity(MatchStatusEnumEntity entity) {

        if (entity == null) {
            return null;
        }

        MatchStatusEnum matchStatusEnum = null;
        switch (entity) {
        case STARTED: matchStatusEnum = MatchStatusEnum.STARTED;
            break;
        case FINISHED: matchStatusEnum = MatchStatusEnum.FINISHED;
            break;
        }

        return matchStatusEnum;
    }
}
