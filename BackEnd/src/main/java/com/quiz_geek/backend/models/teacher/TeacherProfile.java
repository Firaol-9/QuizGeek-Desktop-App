package com.quiz_geek.backend.models.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "teachersProfile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherProfile {
    @Id
    private String id;
    private String fullName;
    private String email;
    private String password;
    private String school;
    private List<Integer> teachingGrade;
    private List<String> subjectSpeciality;
    private String educationLevel;
    private String profilePicture;
}
