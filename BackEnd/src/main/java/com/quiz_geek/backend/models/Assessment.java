package com.quiz_geek.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "assessments")
public class Assessment {
    @Id
    private String id;

    private String title;
    private String subject;
    private QuestionsDifficulty difficulty;
    private QuestionsAccessibility accessibility;
    private String password;
    private List<Question> questionsList;

    public Assessment() {}

    public Assessment(String id, String title, String subject, QuestionsDifficulty difficulty, 
                     QuestionsAccessibility accessibility, String password, List<Question> questionsList) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.difficulty = difficulty;
        this.accessibility = accessibility;
        this.password = password;
        this.questionsList = questionsList;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
    public QuestionsDifficulty getDifficulty() { return difficulty; }
    public void setDifficulty(QuestionsDifficulty difficulty) { this.difficulty = difficulty; }
    
    public QuestionsAccessibility getAccessibility() { return accessibility; }
    public void setAccessibility(QuestionsAccessibility accessibility) { this.accessibility = accessibility; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public List<Question> getQuestionsList() { return questionsList; }
    public void setQuestionsList(List<Question> questionsList) { this.questionsList = questionsList; }
}
