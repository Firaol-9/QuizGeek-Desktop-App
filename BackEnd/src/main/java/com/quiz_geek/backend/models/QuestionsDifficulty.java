package com.quiz_geek.backend.models;

public enum QuestionsDifficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String label;

    QuestionsDifficulty(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
