package com.quiz_geek.services.teacher;

import com.quiz_geek.models.*;
import com.quiz_geek.services.core.AssessmentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherAssessmentService extends AssessmentService{

    private static final TeacherAssessmentService instance = new TeacherAssessmentService();
    private static final Map<String, Assessment> assessmentsList = new HashMap<>();

    //singleton class
    private TeacherAssessmentService(){}

    public static TeacherAssessmentService getInstance(){ return instance; }

    @Override
    protected Map<String,Assessment> getAssessmentsList(){ return assessmentsList; }

    public static void addAssessment(String title, String subjectName, List<Question> questionsList,
                                     QuestionsDifficulty difficulty, QuestionsType type, QuestionsAccessibility accessibility,
                                     String timeLimitStr, String password) {
        validateAssessmentData(title, subjectName, questionsList, difficulty, type, accessibility, timeLimitStr, password);

        int timeLimit = 0;
        if ( type == QuestionsType.EXAM) timeLimit =  Integer.parseInt(timeLimitStr);

        if (type == QuestionsType.EXAM) {
            Exam exam = new Exam (title, subjectName, questionsList, difficulty, accessibility, timeLimit, password);
            assessmentsList.put(exam.getId(), exam);
        }else if (type == QuestionsType.PRACTICEQUESTION) {
            PracticeQuestion practiceQuestion = new PracticeQuestion(title, subjectName, questionsList, difficulty, accessibility, password);
            assessmentsList.put(practiceQuestion.getId(), practiceQuestion);
        } else if (type == QuestionsType.WORKSHEET){
            Worksheet worksheet = new Worksheet(title, subjectName, questionsList, difficulty, accessibility, password);
            assessmentsList.put(worksheet.getId(), worksheet);
        }
    }
}
