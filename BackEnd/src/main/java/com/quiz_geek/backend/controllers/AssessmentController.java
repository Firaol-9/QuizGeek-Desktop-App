package com.quiz_geek.backend.controllers;

import com.quiz_geek.backend.models.Assessment;
import com.quiz_geek.backend.repositories.AssessmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentRepository repository;

    public AssessmentController(AssessmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Assessment> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Assessment create(@RequestBody Assessment assessment) {
        return repository.save(assessment);
    }
}
