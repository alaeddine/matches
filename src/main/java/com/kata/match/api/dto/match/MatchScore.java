package com.kata.match.api.dto.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kata.match.domain.model.PlayerEnumEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchScore {
    @JsonProperty("winner")
    PlayerEnumEntity winner;
    @JsonProperty("winnerName")
    String winnerName;
    @JsonProperty("player1Score")
    private PlayerScore player1Score;
    @JsonProperty("player2Score")
    private PlayerScore player2Score;
}
