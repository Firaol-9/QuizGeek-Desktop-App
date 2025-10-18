package com.quiz_geek.backend.payload.responses;

import com.quiz_geek.backend.models.common.Accessibility;
import com.quiz_geek.backend.models.common.AssessmentDifficulty;
import com.quiz_geek.backend.models.common.Question;
import com.quiz_geek.backend.models.common.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentResponse {
    private String title;
    private String subject;
    private Accessibility accessibility;
    private AssessmentDifficulty difficulty;
    private Type type;
    private List<Question> questionList;
    private String description;
    private String createdOn;
}
