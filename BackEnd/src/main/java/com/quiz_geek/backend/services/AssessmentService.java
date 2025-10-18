package com.quiz_geek.backend.services;

import com.quiz_geek.backend.mappers.AssessmentMapper;
import com.quiz_geek.backend.models.common.Assessment;
import com.quiz_geek.backend.models.common.AssessmentAccessibility;
import com.quiz_geek.backend.payload.requests.AssessmentRequest;
import com.quiz_geek.backend.payload.responses.AssessmentResponse;
import com.quiz_geek.backend.repositories.AssessmentRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssessmentService {

    private final AssessmentRepository assessmentRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AssessmentService(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    public Assessment createAssessment(AssessmentRequest assessmentRequest, String userId) {

        if (assessmentRequest.getAccessibility().getAssessmentAccessibility() == AssessmentAccessibility.PRIVATE) {
            assessmentRequest.getAccessibility().setPassword(passwordEncoder.encode(assessmentRequest.getAccessibility().getPassword()));
        } else {
            assessmentRequest.getAccessibility().setPassword(null);
        }
        Assessment assessment = AssessmentMapper.toEntity(assessmentRequest, userId);
        return assessmentRepository.save(assessment);
    }

    public Assessment getAssessment(String id){
        return assessmentRepository.findById(id).
                orElseThrow( () -> new RuntimeException("Assessment not found"));
    }

    public List<AssessmentResponse> getAssessmentsByTeacherId(String teacherId){
        return assessmentRepository.findByTeacherId(teacherId)
                .stream()
                .map(AssessmentMapper :: toResponse)
                .collect(Collectors.toList());
    }

    public List<Assessment> getAll() {
        return assessmentRepository.findAll();
    }
}
