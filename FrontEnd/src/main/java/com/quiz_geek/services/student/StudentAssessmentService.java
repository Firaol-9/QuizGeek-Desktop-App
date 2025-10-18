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

// ---------- BIOLOGY QUESTIONS ----------
        List<String> bioOptions1 = List.of(
                "It is the powerhouse of the cell.",
                "It controls cell division.",
                "It stores genetic information.",
                "It helps photosynthesis."
        );

        List<String> bioOptions2 = List.of(
                "Golgi body packages and transports proteins.",
                "It produces energy through respiration.",
                "It stores waste materials.",
                "It produces ribosomes."
        );

        List<String> bioOptions3 = List.of(
                "Regulates substances entering and leaving the cell.",
                "Controls all cell activities.",
                "Provides structural support to the cell.",
                "Contains digestive enzymes."
        );

        List<String> bioOptions4 = List.of(
                "It controls all cell activities.",
                "It produces proteins.",
                "It stores energy in fat form.",
                "It helps in cell division."
        );

        List<Question> bioQuestions = new ArrayList<>();
        bioQuestions.add(new Question("What is the function of mitochondria?", bioOptions1, "It is the powerhouse of the cell."));
        bioQuestions.add(new Question("What is the function of the Golgi apparatus?", bioOptions2, "Golgi body packages and transports proteins."));
        bioQuestions.add(new Question("What is the function of the cell membrane?", bioOptions3, "Regulates substances entering and leaving the cell."));
        bioQuestions.add(new Question("What is the main function of the nucleus?", bioOptions4, "It controls all cell activities."));

// ---------- MATH QUESTIONS ----------
        List<String> mathOptions1 = List.of("4", "5", "6", "7");
        List<String> mathOptions2 = List.of("6", "8", "9", "12");
        List<String> mathOptions3 = List.of("x = 5", "x = 3", "x = 2", "x = 1");
        List<String> mathOptions4 = List.of("πr²", "2πr", "r²", "4πr²");

        List<Question> mathQuestions = new ArrayList<>();
        mathQuestions.add(new Question("What is 2 + 2?", mathOptions1, "4"));
        mathQuestions.add(new Question("What is 3 × 3?", mathOptions2, "9"));
        mathQuestions.add(new Question("If 2x + 1 = 7, what is x?", mathOptions3, "x = 3"));
        mathQuestions.add(new Question("What is the area of a circle formula?", mathOptions4, "πr²"));

// ---------- HISTORY QUESTIONS ----------
        List<String> histOptions1 = List.of("1945", "1918", "1939", "1950");
        List<String> histOptions2 = List.of("Abraham Lincoln", "George Washington", "Thomas Jefferson", "John Adams");
        List<String> histOptions3 = List.of("Egypt", "Greece", "China", "Rome");
        List<String> histOptions4 = List.of("The French Revolution", "The Industrial Revolution", "The American Revolution", "World War I");

        List<Question> histQuestions = new ArrayList<>();
        histQuestions.add(new Question("When did World War II end?", histOptions1, "1945"));
        histQuestions.add(new Question("Who was the first president of the United States?", histOptions2, "George Washington"));
        histQuestions.add(new Question("The pyramids are located in which ancient civilization?", histOptions3, "Egypt"));
        histQuestions.add(new Question("Which revolution began in 1775?", histOptions4, "The American Revolution"));

// ---------- CREATE ASSESSMENTS ----------

// Difficulties and accessibilities
        AssessmentDifficulty hard = AssessmentDifficulty.HARD;
        AssessmentDifficulty medium = AssessmentDifficulty.MEDIUM;
        AssessmentAccessibility publicAccess = AssessmentAccessibility.PUBLIC;
        AssessmentAccessibility privateAccess = AssessmentAccessibility.PRIVATE;

// --- BIOLOGY ASSESSMENTS ---
        for (int i = 1; i <= 3; i++) {
            Worksheet worksheet = new Worksheet("Bio Worksheet " + i, "Biology", bioQuestions, medium, privateAccess, "BIO" + i + "123");
            assessmentList.put(worksheet.getId(), worksheet);

            Exam exam = new Exam("Biology Exam " + i, "Biology", bioQuestions, hard, publicAccess, 60); // 60 mins
            assessmentList.put(exam.getId(), exam);

            PracticeQuestion pq = new PracticeQuestion("Biology Practice " + i, "Biology", bioQuestions, medium, publicAccess);
            assessmentList.put(pq.getId(), pq);
        }

// --- MATH ASSESSMENTS ---
        for (int i = 1; i <= 3; i++) {
            Worksheet worksheet = new Worksheet("Math Worksheet " + i, "Mathematics", mathQuestions, medium, privateAccess, "MATH" + i + "4321");
            assessmentList.put(worksheet.getId(), worksheet);

            Exam exam = new Exam("Mathematics Exam " + i, "Mathematics", mathQuestions, hard, publicAccess, 45);
            assessmentList.put(exam.getId(), exam);

            PracticeQuestion pq = new PracticeQuestion("Math Practice " + i, "Mathematics", mathQuestions, medium, publicAccess);
            assessmentList.put(pq.getId(), pq);
        }

// --- HISTORY ASSESSMENTS ---
        for (int i = 1; i <= 3; i++) {
            Worksheet worksheet = new Worksheet("History Worksheet " + i, "History", histQuestions, medium, privateAccess, "HIST" + i + "999");
            assessmentList.put(worksheet.getId(), worksheet);

            Exam exam = new Exam("History Exam " + i, "History", histQuestions, hard, publicAccess, 50);
            assessmentList.put(exam.getId(), exam);

            PracticeQuestion pq = new PracticeQuestion("History Practice " + i, "History", histQuestions, medium, publicAccess);
            assessmentList.put(pq.getId(), pq);
        }

    }

}
