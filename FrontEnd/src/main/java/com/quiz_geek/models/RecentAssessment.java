package com.quiz_geek.models;

public class RecentAssessment {
    private final String subject;
    private final String type; // Exam | Practice question | Worksheet
    private final String status; // Pending | Completed
    private final String result; // e.g., 23/30 or --

    public RecentAssessment(String subject, String type, String status, String result) {
        this.subject = subject;
        this.type = type;
        this.status = status;
        this.result = result;
    }

    public String getSubject() { return subject; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getResult() { return result; }
}


