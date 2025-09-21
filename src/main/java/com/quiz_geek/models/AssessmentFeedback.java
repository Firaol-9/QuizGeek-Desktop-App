package com.quiz_geek.models;

public record AssessmentFeedback(
        String studentAnswer,
        String correctAnswer,
        boolean isCorrect
) {}
