package com.quiz_geek.models;

import java.util.List;

public class EvaluationResult {
    private final String assessmentId;
    private final String studentId;
    private final int score;
    private final int total;
    private final String status; // e.g., "Completed", "Pending Review"
    private List<AssessmentFeedback> assessmentFeedbacks;

    public EvaluationResult(String assessmentId, String studentId, int score, int total, String status) {
        this.assessmentId = assessmentId;
        this.studentId = studentId;
        this.score = score;
        this.total = total;
        this.status = status;
    }

    public String getAssessmentId() { return assessmentId; }
    public String getStudentId() { return studentId; }
    public int getScore() { return score; }
    public int getTotal() { return total; }
    public String getStatus() { return status; }
    public List<AssessmentFeedback> getAssessmentFeedbacks(){ return assessmentFeedbacks; }
    public void setAssessmentFeedbacks(List<AssessmentFeedback> feedbacks){ this.assessmentFeedbacks = feedbacks;}

    @Override
    public String toString() {
        if ("Completed".equals(status)) {
            return score + "/" + total;
        } else {
            return "Status: " + status;
        }
    }
}

