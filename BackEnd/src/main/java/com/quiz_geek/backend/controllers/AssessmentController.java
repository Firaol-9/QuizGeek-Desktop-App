package com.quiz_geek.backend.controllers;

import com.quiz_geek.backend.mappers.AssessmentMapper;
import com.quiz_geek.backend.models.common.Assessment;
import com.quiz_geek.backend.models.common.CustomUserDetails;
import com.quiz_geek.backend.payload.requests.AssessmentRequest;
import com.quiz_geek.backend.payload.responses.AssessmentResponse;
import com.quiz_geek.backend.services.AssessmentService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<AssessmentResponse> createAssessment(@RequestBody AssessmentRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        Assessment saved = assessmentService.createAssessment(request, userId);
        return ResponseEntity.ok(AssessmentMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponse> getAssessment(@PathVariable String id){
        return ResponseEntity.ok(
                AssessmentMapper.toResponse(assessmentService.getAssessment(id))
        );
    }

    @GetMapping("/my-assessments")
    public ResponseEntity<List<AssessmentResponse> >getTeacherAssessments(Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        return ResponseEntity.ok(
                assessmentService.getAssessmentsByTeacherId(userId)
        );
    }

    @GetMapping
    public ResponseEntity<List<Assessment>> getAll(){
        return ResponseEntity.ok(
                assessmentService.getAll()
        );
    }
}
