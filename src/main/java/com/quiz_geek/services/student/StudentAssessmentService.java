package com.quiz_geek.services.student;

import com.quiz_geek.models.*;
import com.quiz_geek.services.core.AssessmentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentAssessmentService extends AssessmentService {

    private static final StudentAssessmentService instance = new StudentAssessmentService();
    private static final Map<String, Assessment> assessmentList = new HashMap<>();

    private StudentAssessmentService(){}

    public static StudentAssessmentService getInstance(){
        return instance;
    }

    @Override
    public Map<String,Assessment> getAssessmentsList(){
        return assessmentList;
    }

    public static EvaluationResult submit(String assessmentId, StudentSubmission submission) {
        Assessment assessment = assessmentList.get(assessmentId);
        if (assessment == null) {
            throw new IllegalArgumentException("Assessment not found: " + assessmentId);
        }
        return assessment.evaluate(submission);
    }

    //TODO: remove these hard-coded assessments once connected to the database
    public static void addAssessments(){
        List<String> options = new ArrayList<>();
        options.add("It is the power house of the cell.");
        options.add("It assists the transportation of protein.");
        options.add("It controls the whole system of the cell.");
        options.add("It controls objects that get in and get out from the cell.");

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is mitochondria?", options, "It is the power house of the cell."));
        questions.add(new Question("What is the use of golgi body?", options, "It assists the transportation of protein."));
        questions.add(new Question("What is the use of cell membrane?", options, "It controls objects that get in and get out from the cell."));
        questions.add(new Question("What is the use of nucleus?", options, "It controls the whole system of the cell."));


        QuestionsDifficulty d = QuestionsDifficulty.HARD;
        QuestionsAccessibility a = QuestionsAccessibility.PUBLIC;

        for (int i = 0; i < 10; i++) {
            Worksheet worksheet = new Worksheet("Test-1", "Biology", questions, d, QuestionsAccessibility.PRIVATE, "1234");
            assessmentList.put(worksheet.getId(), worksheet);
        }

        for (int i = 0; i < 10; i++) {
            Exam exam= new Exam("Test-1", "Biology", questions, d, a, 1);
            assessmentList.put(exam.getId(), exam);
        }

        for (int i = 0; i < 10; i++) {
            PracticeQuestion practiceQuestion = new PracticeQuestion("Test-1", "Biology", questions, d, a);
            assessmentList.put(practiceQuestion.getId(), practiceQuestion);
        }
    }

}
