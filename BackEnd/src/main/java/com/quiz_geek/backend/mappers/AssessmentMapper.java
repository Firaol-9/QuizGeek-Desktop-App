package com.quiz_geek.backend.mappers;

import com.quiz_geek.backend.models.common.Assessment;
import com.quiz_geek.backend.payload.requests.AssessmentRequest;
import com.quiz_geek.backend.payload.responses.AssessmentResponse;

import java.time.LocalDateTime;

public class AssessmentMapper {
    public static Assessment toEntity(AssessmentRequest request, String teacherId){
        return Assessment.builder()
                .teacherId(teacherId)
                .title(request.getTitle())
                .subject(request.getSubject())
                .accessibility(request.getAccessibility())
                .difficulty(request.getDifficulty())
                .type(request.getType())
                .questionsList(request.getQuestionList())
                .description(request.getDescription())
                .createdOn(LocalDateTime.now().toString())
                .build();
    }

    public static AssessmentResponse toResponse(Assessment assessment){
        return AssessmentResponse.builder()
                .title(assessment.getTitle())
                .subject(assessment.getSubject())
                .accessibility(assessment.getAccessibility())
                .difficulty(assessment.getDifficulty())
                .type(assessment.getType())
                .questionList(assessment.getQuestionsList())
                .description(assessment.getDescription())
                .createdOn(assessment.getCreatedOn())
                .build();
    }
}
