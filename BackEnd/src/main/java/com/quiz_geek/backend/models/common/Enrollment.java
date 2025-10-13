package com.quiz_geek.backend.models.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    private String id;
    private String studentId;
    private String teacherId;
    private String assessmentId;
    private String enrolledOn;
}
