package com.kata.match.domain.model;

public enum GameScoreEnumEntity {
    ZERO("0"), FIFTEEN("15"), THIRTY("30"), FORTY("40"), ADV("ADV"), DEUCE("DEUCE");
    String score;

    GameScoreEnumEntity(String score) {
        this.score = score;
    }

    public String getValue() {
        return score;
    }
}
