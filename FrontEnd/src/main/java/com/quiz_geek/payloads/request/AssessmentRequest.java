package com.quiz_geek.payloads.request;

import com.quiz_geek.models.Accessibility;
import com.quiz_geek.models.AssessmentDifficulty;
import com.quiz_geek.models.Question;
import com.quiz_geek.models.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentRequest {
    private String title;
    private String subject;
    private Accessibility accessibility;
    private AssessmentDifficulty difficulty;
    private Type type;//assessmentType with password
    private List<Question> questionList;
    private String description;
}
