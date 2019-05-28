package com.kata.match.api.dto.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Match {
    @JsonProperty("matchScore")
    MatchScore matchScore;
    @JsonProperty("status")
    MatchStatusEnum status;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("player1")
    private String player1;
    @JsonProperty("player2")
    private String player2;
    @JsonProperty("deuceActivated")
    private Boolean deuceActivated;
}
