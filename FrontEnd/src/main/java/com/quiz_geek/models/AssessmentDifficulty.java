package com.quiz_geek.models;

public enum AssessmentDifficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String label;

    AssessmentDifficulty(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
