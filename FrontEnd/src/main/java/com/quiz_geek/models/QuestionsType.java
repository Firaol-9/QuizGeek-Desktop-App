package com.quiz_geek.models;

public enum QuestionsType {
    EXAM("Exam"),
    PRACTICEQUESTION("PracticeQuestion"),
    WORKSHEET("Worksheet");

    String label;

    QuestionsType (String label){
        this.label = label;
    }

    @Override
    public String toString(){ return label; }
}
