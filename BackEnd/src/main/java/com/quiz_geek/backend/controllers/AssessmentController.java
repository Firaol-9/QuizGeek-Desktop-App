package com.quiz_geek.backend.controllers;

import com.quiz_geek.backend.models.Assessment;
import com.quiz_geek.backend.services.AssessmentService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Assessment> createAssessment(@RequestBody Assessment assessment) {
        Assessment saved = assessmentService.createAssessment(assessment);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Assessment> getAll(){
        return assessmentService.getAll();
    }
}
