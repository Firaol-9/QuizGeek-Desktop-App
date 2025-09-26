package com.quiz_geek.backend.services;

import com.quiz_geek.backend.models.Assessment;
import com.quiz_geek.backend.models.QuestionsAccessibility;
import com.quiz_geek.backend.repositories.AssessmentRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AssessmentService(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    public Assessment createAssessment(Assessment assessment) {
        if (assessment.getAccessibility() == QuestionsAccessibility.PRIVATE) {
            // hash the password before saving
            assessment.setPassword(passwordEncoder.encode(assessment.getPassword()));
        } else {
            // clear password if not private
            assessment.setPassword(null);
        }
        return assessmentRepository.save(assessment);
    }

    public List<Assessment> getAll() {
        return assessmentRepository.findAll();
    }
}
