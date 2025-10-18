package com.quiz_geek.backend.repositories;

import com.quiz_geek.backend.models.common.Assessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssessmentRepository extends MongoRepository<Assessment, String> {
    Optional<Assessment> findById(String id);
    List<Assessment> findByTeacherId(String teacherId);
}
