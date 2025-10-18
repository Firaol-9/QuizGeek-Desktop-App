package com.quiz_geek.payloads.response;

import com.quiz_geek.models.Accessibility;
import com.quiz_geek.models.AssessmentDifficulty;
import com.quiz_geek.models.Question;
import com.quiz_geek.models.Type;
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
