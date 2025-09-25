package com.quiz_geek.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "assessments")
public class Assessment {
    @Id
    private String id;

    private String title;
    private String description;
    private int totalQuestions;

    public Assessment() {}
    public Assessment(String title, String description, int totalQuestions) {
        this.title = title;
        this.description = description;
        this.totalQuestions = totalQuestions;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
}
