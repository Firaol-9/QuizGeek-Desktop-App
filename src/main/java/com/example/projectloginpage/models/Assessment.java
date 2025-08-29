package com.example.projectloginpage.models;

import java.util.ArrayList;
import java.util.List;

public class Assessment {
    private String subject, quizTitle, password;
    private QuestionsDifficulty difficulty;
    private QuestionsType type;
    private QuestionsAccessibility accessibility;
    private int timeLimit;
    List<Question> questionsList = new ArrayList<>();

    public Assessment(String quizTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsType type, QuestionsAccessibility accessibility ){
        this.quizTitle = quizTitle;
        this.subject = subject;
        this.questionsList = questionsList;
        this.difficulty = difficulty;
        this.type = type;
        this.accessibility = accessibility;
    }

    public Assessment(String quizTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsType type, QuestionsAccessibility accessibility, int timeLimit, String password){
        this.quizTitle = quizTitle;
        this.subject = subject;
        this.questionsList = questionsList;
        this.difficulty = difficulty;
        this.type = type;
        this.accessibility = accessibility;
        this.timeLimit = timeLimit;
        this.password = password;
    }

    public Assessment(String quizTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsType type, QuestionsAccessibility accessibility, int timeLimit){
        this.quizTitle = quizTitle;
        this.subject = subject;
        this.questionsList = questionsList;
        this.difficulty = difficulty;
        this.type = type;
        this.accessibility = accessibility;
        this.timeLimit = timeLimit;
    }

    public Assessment(String quizTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsType type, QuestionsAccessibility accessibility, String password){
        this.quizTitle = quizTitle;
        this.subject = subject;
        this.questionsList = questionsList;
        this.difficulty = difficulty;
        this.type = type;
        this.accessibility = accessibility;
        this.password = password;
    }

    public List<Question> getQuestions(){
        return questionsList;
    }

    public String toString(){
        String str = "";
        for (Question q : questionsList){
            str = str + q.toString();
        }
        return quizTitle + "\n" + subject + "\n" + difficulty + "\n" + type + "\n" + accessibility + "\n" + timeLimit + "\n" + str;
    }

}