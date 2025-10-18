package com.quiz_geek.models;

import java.util.List;
import java.util.UUID;

public abstract class Assessment {
    private final String id;
    private String subject, assessmentTitle, password;
    private AssessmentDifficulty difficulty;
    private AssessmentAccessibility accessibility;
    List<Question> questionsList;

    public Assessment(String assessmentTitle, String subject, List<Question> questionsList, AssessmentDifficulty difficulty, AssessmentAccessibility accessibility ){
        this.id = UUID.randomUUID().toString();
        this.assessmentTitle = assessmentTitle;
        this.subject = subject;
        this.questionsList = questionsList;
        this.difficulty = difficulty;
        this.accessibility = accessibility;
    }

    public Assessment (String assessmentTitle, String subject, List<Question> questionsList, AssessmentDifficulty difficulty, AssessmentAccessibility accessibility, String password){
        this( assessmentTitle, subject, questionsList, difficulty, accessibility);
        if ( accessibility == AssessmentAccessibility.PRIVATE) this.password = password;
        else this.password = null;
    }

    public List<Question> getQuestions(){
        return questionsList;
    }

    public Question getQuestion(int index){ return questionsList.get(index);}

    public String getSubject(){return subject;}

    public String getAssessmentTitle(){return assessmentTitle;}

    public AssessmentDifficulty getDifficulty(){return difficulty;}

    public AssessmentAccessibility getAccessibility(){return accessibility;}

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