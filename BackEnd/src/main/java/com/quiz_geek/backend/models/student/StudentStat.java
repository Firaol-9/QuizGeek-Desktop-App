package com.quiz_geek.backend.models.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "studentStat")
public class StudentStat {
    @Id
    private String id;
    private String studentId;
    private int dailyStreak;
    private int totalQuestionsCompleted;
    private int totalAssessmentsEnrolled;
    private int totalAssessmentsStarted;
    private int totalAssessmentsCompleted;
    private double averageCompletionRate;
}
