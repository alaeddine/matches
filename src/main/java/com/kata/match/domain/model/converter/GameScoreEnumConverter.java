package com.kata.match.domain.model.converter;

import javax.persistence.AttributeConverter;

import com.kata.match.domain.model.GameScoreEnumEntity;

public class GameScoreEnumConverter implements AttributeConverter<GameScoreEnumEntity, String> {
    @Override
    public String convertToDatabaseColumn(final GameScoreEnumEntity gameScoreEnumEntity) {
        switch (gameScoreEnumEntity) {
        case ZERO:
            return GameScoreEnumEntity.ZERO.getValue();
        case FIFTEEN:
            return GameScoreEnumEntity.FIFTEEN.getValue();
        case THIRTY:
            return GameScoreEnumEntity.THIRTY.getValue();
        case FORTY:
            return GameScoreEnumEntity.FORTY.getValue();
        default:
            throw new IllegalArgumentException("Unknown" + gameScoreEnumEntity);
        }
    }

    @Override
    public GameScoreEnumEntity convertToEntityAttribute(final String dbData) {
        switch (dbData) {
        case "0":
            return GameScoreEnumEntity.ZERO;
        case "15":
            return GameScoreEnumEntity.FIFTEEN;
        case "30":
            return GameScoreEnumEntity.THIRTY;
        case "40":
            return GameScoreEnumEntity.FORTY;
        default:
            throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}
