package com.quiz_geek.models;

import java.util.List;

public class StudentSubmission {
    private final String studentId;
    private final List<String> answers;  // simple case: one answer per question

    public StudentSubmission(String studentId, List<String> answers) {
        this.studentId = studentId;
        this.answers = answers;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAnswer(int index) {
        return answers.get(index);
    }

    public List<String> getAllAnswers() {
        return answers;
    }
}

