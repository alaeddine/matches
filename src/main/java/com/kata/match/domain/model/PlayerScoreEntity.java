package com.kata.match.domain.model;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

import com.kata.match.domain.model.converter.GameScoreEnumConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class PlayerScoreEntity {
    @Default
    @Convert(converter = GameScoreEnumConverter.class)
    private GameScoreEnumEntity gameScore = GameScoreEnumEntity.ZERO;
}
