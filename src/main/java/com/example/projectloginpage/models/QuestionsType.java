package com.example.projectloginpage.models;

public enum QuestionsType {
    EXAM("Exam"),
    WORKSHEET("Worksheet"),
    PRACTICEQUESTION("Practice Question");

    private final String label;

    QuestionsType(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
