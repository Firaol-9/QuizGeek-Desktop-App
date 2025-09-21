package com.quiz_geek.models;

import com.quiz_geek.exceptions.InvalidQuestionException;

import java.util.List;

public class Question {

    private String questionText;
    private List<String> options;
    private String correctAnswer;

    public Question (String questionText, List<String> options, String correctAnswer){
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText(){ return questionText; }

    public List<String> getOptions(){ return options; }

    public boolean isCorrect(String answer){
        if ( answer == null) return false;
        return answer.equals(correctAnswer);
    }

    public String getCorrectAnswer(){ return correctAnswer; }

    public void validate(){
        if (questionText.trim().isEmpty()) throw new InvalidQuestionException("Question cannot be empty.");
        if (options == null || options.size() < 2 ) throw new InvalidQuestionException("At least 2 options are required.");
        for(String option : options){
            if (option.trim().isEmpty()) throw new InvalidQuestionException("Option cannot be empty.");
        }
        if (correctAnswer == null || correctAnswer.isBlank()) throw new InvalidQuestionException("A valid correct answer must be selected.");
    }

    public String toString(){
        return questionText + "\n" + options + "\n" + correctAnswer + "\n";
    }
}
