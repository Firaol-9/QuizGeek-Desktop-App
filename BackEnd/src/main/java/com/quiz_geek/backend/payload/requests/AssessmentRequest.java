package com.quiz_geek.backend.payload.requests;

import com.quiz_geek.backend.models.common.Accessibility;
import com.quiz_geek.backend.models.common.AssessmentDifficulty;
import com.quiz_geek.backend.models.common.Question;
import com.quiz_geek.backend.models.common.Type;
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
