package com.quiz_geek.backend.models.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("studentProfile")
public class StudentProfile {
    @Id
    private String id;
    private String fullName;
    private String email;
    private String password;
    private String school;
    private String grade;
    private String profilePicture;
    private String creationDate;
}
