package com.quiz_geek.backend.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "assessments")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Assessment {
    @Id
    private String id;
    private String teacherId;
    private String title;
    private String subject;
    private Accessibility accessibility;
    private AssessmentDifficulty difficulty;
    private Type type;
    private List<Question> questionsList;
    private String description;
    private String createdOn;
}