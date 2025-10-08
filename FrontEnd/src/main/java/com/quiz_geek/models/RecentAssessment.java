package com.quiz_geek.models;

public class RecentAssessment {
    private final String subject;
    private final String type; // Exam | Practice question | Worksheet
    private final String status; // Pending | Completed
    private final String result; // e.g., 23/30 or --
    // Optional frequency for teacher dashboards (e.g., enrollment count)
    private final Integer frequency;

    public RecentAssessment(String subject, String type, String status, String result) {
        this.subject = subject;
        this.type = type;
        this.status = status;
        this.result = result;
        this.frequency = null;
    }

    public RecentAssessment(String subject, String type, int frequency) {
        this.subject = subject;
        this.type = type;
        this.status = null;
        this.result = null;
        this.frequency = frequency;
    }

    public String getSubject() { return subject; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getResult() { return result; }
    public Integer getFrequency() { return frequency; }
}


