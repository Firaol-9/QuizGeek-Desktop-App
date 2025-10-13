package com.quiz_geek.backend.models.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teachersStat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachersStat {
    @Id
    private String id;
    private String teacherId;
    private int totalQuestionsCreated;
    private int totalAssessmentsCreated;
    private int studentEnrolled;
}
