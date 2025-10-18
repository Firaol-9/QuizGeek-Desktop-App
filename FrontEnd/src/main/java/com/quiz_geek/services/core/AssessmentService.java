package com.quiz_geek.services.core;

import com.quiz_geek.exceptions.InvalidAssessmentException;
import com.quiz_geek.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AssessmentService {

    protected abstract Map<String,Assessment> getAssessmentsList();

    protected AssessmentService(){}

    protected static void validateAssessmentData(String title, String subjectName, List<Question> questionsList,
                                                 AssessmentDifficulty difficulty, AssessmentType type, AssessmentAccessibility accessibility,
                                                 String timeLimitStr, String password){

        if(title == null || title.isBlank()){
            throw new InvalidAssessmentException("Quiz title is empty.");
        }
        if(subjectName == null || subjectName.isBlank()){
            throw new InvalidAssessmentException("Subject is empty.");
        }
        if(questionsList.isEmpty()){
            throw new InvalidAssessmentException("No valid questions provided.");
        }

        if(type == AssessmentType.EXAM && (timeLimitStr == null || timeLimitStr.isBlank())){
            throw new InvalidAssessmentException("Exam must have time Limit");
        }

        if(accessibility == AssessmentAccessibility.PRIVATE && (password == null || password.isBlank())){
            throw new InvalidAssessmentException("Private assessments must have password.");
        }
    }

    public List<Assessment> getAllAssessments(){
        return new ArrayList<>(getAssessmentsList().values());
    }

    public Assessment getAssessment(String assessmentId){ return getAssessmentsList().get(assessmentId); }

    public List<Assessment> getExams(){
        List<Assessment> assessments = new ArrayList<>();
        for( Assessment assessment : getAssessmentsList().values()){
            System.out.println(assessment instanceof Exam);
            if (assessment instanceof Exam){
                assessments.add(assessment);
            }
        }
        return assessments;
    }

    public List<Assessment> getPracticeQuestions(){
        List<Assessment> assessments = new ArrayList<>();
        for( Assessment assessment : getAssessmentsList().values()){
            System.out.println(assessment instanceof PracticeQuestion);
            if (assessment instanceof PracticeQuestion){
                assessments.add(assessment);
            }
        }
        return assessments;
    }

    public List<Assessment> getWorksheets(){
        List<Assessment> assessments = new ArrayList<>();
        for( Assessment assessment : getAssessmentsList().values()){
            if (assessment instanceof Worksheet){
                assessments.add(assessment);
            }
        }
        return assessments;
    }

    public List<Assessment> getAssessmentsByDifficulty(AssessmentDifficulty difficulty){
        List<Assessment> assessments = new ArrayList<>();
        for( Assessment assessment : getAssessmentsList().values()){
            if (assessment.getDifficulty() == difficulty){
                assessments.add(assessment);
            }
        }
        return assessments;
    }

    public List<Assessment> getAssessmentsByAccessibility(AssessmentAccessibility accessibility){
        List<Assessment> assessments = new ArrayList<>();
        for( Assessment assessment : getAssessmentsList().values()){
            if (assessment.getAccessibility() == accessibility){
                assessments.add(assessment);
            }
        }
        return assessments;
    }
}
