package com.quiz_geek.models;

import java.util.List;

public class Exam extends Assessment{
    int timeLimitInMinutes;

    public Exam( String assessmentTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsAccessibility accessibility, int timeLimitInMinutes ){
        super(assessmentTitle, subject, questionsList, difficulty, accessibility);
        this.timeLimitInMinutes = timeLimitInMinutes;
    }

    public Exam( String assessmentTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsAccessibility accessibility, int timeLimitInMinutes, String password ){
        super(assessmentTitle, subject, questionsList, difficulty, accessibility, password);
        this.timeLimitInMinutes = timeLimitInMinutes;
    }

    public int getTimeLimitInMinutes(){ return timeLimitInMinutes; }

    @Override
    public EvaluationResult evaluate(StudentSubmission submission){
        return new EvaluationResult(getId(), submission.getStudentId(), -1,-1, "Pending");
    }
}
