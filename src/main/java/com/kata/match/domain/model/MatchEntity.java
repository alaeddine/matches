package com.kata.match.domain.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "MATCH")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class MatchEntity {
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    @Default
    MatchStatusEnumEntity status = MatchStatusEnumEntity.STARTED;
    @Column(name = "WINNER")
    @Enumerated(EnumType.STRING)
    @Default
    PlayerEnumEntity winner = PlayerEnumEntity.NO_ONE;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String player1;
    private String player2;
    @Default
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "gameScore", column = @Column(name = "P1_GAME_SCORE"))
    })
    private PlayerScoreEntity player1Score = PlayerScoreEntity.builder()
            .build();
    @Default
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "gameScore", column = @Column(name = "P2_GAME_SCORE"))
    })
    private PlayerScoreEntity player2Score = PlayerScoreEntity.builder()
            .build();
}
