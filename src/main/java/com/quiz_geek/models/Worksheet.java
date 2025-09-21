package com.quiz_geek.models;

import java.util.ArrayList;
import java.util.List;

public class Worksheet extends Assessment{

    public Worksheet(String assessmentTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsAccessibility accessibility){
        super(assessmentTitle, subject, questionsList, difficulty, accessibility);
    }

    public Worksheet(String assessmentTitle, String subject, List<Question> questionsList, QuestionsDifficulty difficulty, QuestionsAccessibility accessibility, String password){
        super(assessmentTitle, subject, questionsList, difficulty, accessibility, password);
    }

    @Override
    public EvaluationResult evaluate(StudentSubmission submission) {
        int score = 0;
        List<AssessmentFeedback> feedbacks = new ArrayList<>();

        for (int i = 0; i < questionsList.size(); i++) {
            String studentAnswer = submission.getAnswer(i);
            String correctAnswer = questionsList.get(i).getCorrectAnswer();
            boolean isCorrect = questionsList.get(i).isCorrect(studentAnswer);

            if (isCorrect) {
                score++;
            }

            feedbacks.add( new AssessmentFeedback(studentAnswer, correctAnswer, isCorrect));
        }
        EvaluationResult evaluationResult = new EvaluationResult(getId(), submission.getStudentId(), score, getNumberOfQuestions(), "Completed");
        evaluationResult.setAssessmentFeedbacks(feedbacks);

        return evaluationResult;
    }
}
