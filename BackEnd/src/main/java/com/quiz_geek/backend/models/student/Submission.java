package com.quiz_geek.backend.models.student;

import com.quiz_geek.backend.models.common.Answer;
import com.quiz_geek.backend.models.common.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "submissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    @Id
    private String id;
    private String studentId;
    private String assessmentId;
    private String startedOn;
    private String completedOn;
    private SubmissionStatus status;
    private List<Answer> answerList;
    private int score;
}
