package com.quiz_geek.models;

import java.util.List;
import java.util.UUID;

public abstract class Assessment {
    private final String id;
    private String subject, assessmentTitle, password;
    private QuestionsDifficulty difficulty;
    private QuestionsAccessibility accessibility;
    List<Question> questionsList;

    public Assessment(String assessmentTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsAccessibility accessibility ){
        this.id = UUID.randomUUID().toString();
        this.assessmentTitle = assessmentTitle;
        this.subject = subject;
        this.questionsList = questionsList;
        this.difficulty = difficulty;
        this.accessibility = accessibility;
    }

    public Assessment (String assessmentTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsAccessibility accessibility, String password){
        this( assessmentTitle, subject, questionsList, difficulty, accessibility);
        if ( accessibility == QuestionsAccessibility.PRIVATE) this.password = password;
        else this.password = null;
    }

    public List<Question> getQuestions(){
        return questionsList;
    }

    public Question getQuestion(int index){ return questionsList.get(index);}

    public String getSubject(){return subject;}

    public String getAssessmentTitle(){return assessmentTitle;}

    public QuestionsDifficulty getDifficulty(){return difficulty;}

    public QuestionsAccessibility getAccessibility(){return accessibility;}

    public String getPassword(){ return password; }

    public String getId(){ return id; }

    public int getNumberOfQuestions(){return questionsList.size();}

    @Override
    public String toString(){
        String str = "";
        for (Question q : questionsList){
            str = str + q.toString();
        }
        return assessmentTitle + "\n" + subject + "\n" + difficulty + "\n" + accessibility  + "\n" + str;
    }

    public abstract EvaluationResult evaluate(StudentSubmission submission);

}