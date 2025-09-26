package com.quiz_geek.backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
