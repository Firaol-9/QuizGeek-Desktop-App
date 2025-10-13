package com.quiz_geek.backend.services;

import com.quiz_geek.backend.mappers.AssessmentMapper;
import com.quiz_geek.backend.models.common.Assessment;
import com.quiz_geek.backend.models.common.AssessmentAccessibility;
import com.quiz_geek.backend.payload.requests.AssessmentRequest;
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

    public Assessment createAssessment(AssessmentRequest assessmentRequest, String userId) {

        if (assessmentRequest.getAccessibility().getAssessmentAccessibility() == AssessmentAccessibility.PRIVATE) {
            // hash the password before saving
            assessmentRequest.getAccessibility().setPassword(passwordEncoder.encode(assessmentRequest.getAccessibility().getPassword()));
        } else {
            // clear password if not private
            assessmentRequest.getAccessibility().setPassword(null);
        }
        Assessment assessment = AssessmentMapper.toEntity(assessmentRequest, userId);
        return assessmentRepository.save(assessment);
    }

    public List<Assessment> getAll() {
        return assessmentRepository.findAll();
    }
}
