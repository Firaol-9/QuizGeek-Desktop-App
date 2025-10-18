package com.quiz_geek.models;

public enum AssessmentType {
    EXAM("Exam"),
    PRACTICEQUESTION("PracticeQuestion"),
    WORKSHEET("Worksheet");

    String label;

    AssessmentType(String label){
        this.label = label;
    }

    @Override
    public String toString(){ return label; }
}
