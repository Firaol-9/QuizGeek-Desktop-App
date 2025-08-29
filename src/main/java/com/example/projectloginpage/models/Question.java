package com.example.projectloginpage.models;

import com.example.projectloginpage.exceptions.InvalidQuestionException;

import java.util.List;

public class Question {

    private String questionText;
    private List<String> options;
    private int answerIndex;

    public Question (String questionText, List<String> options, int answerIndex){
        this.questionText = questionText;
        this.options = options;
        this.answerIndex = answerIndex;
    }

    public String getQuestionText(){ return questionText; }
    public List<String> getOptions(){ return options; }
    public int getAnswerIndex(){ return answerIndex; }

    public void validate(){
        if (questionText.trim().isEmpty()) throw new InvalidQuestionException("Question cannot be empty.");
        if (options == null || options.size() < 2 ) throw new InvalidQuestionException("At least 2 options are required.");
        for(String option : options){
            if (option.trim().isEmpty()) throw new InvalidQuestionException("Option cannot be empty.");
        }
        if (answerIndex < 0 || answerIndex >= options.size()) throw new InvalidQuestionException("A valid correct answer must be selected.");
    }

    public String toString(){
        return questionText + "\n" + options + "\n" + answerIndex + "\n";
    }
}
