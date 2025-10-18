package com.quiz_geek.models;

public enum AssessmentAccessibility {
    PRIVATE("Private"),
    PUBLIC("Public");

    private final String label;

    AssessmentAccessibility(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
