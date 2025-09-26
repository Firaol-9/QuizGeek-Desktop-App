package com.quiz_geek.backend.models;

public enum QuestionsAccessibility {
    PRIVATE("Private"),
    PUBLIC("Public");

    private final String label;

    QuestionsAccessibility(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
