package com.quiz_geek.backend.controllers;

import com.quiz_geek.backend.models.common.Assessment;
import com.quiz_geek.backend.models.common.CustomUserDetails;
import com.quiz_geek.backend.payload.requests.AssessmentRequest;
import com.quiz_geek.backend.services.AssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@RequestBody AssessmentRequest request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        Assessment saved = assessmentService.createAssessment(request, userId);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Assessment> getAll(){
        return assessmentService.getAll();
    }
}
