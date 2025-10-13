package com.quiz_geek.services.student;

import com.quiz_geek.models.Assessment;
import com.quiz_geek.models.AssessmentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AssessmentResultService {
    private static final List<AssessmentResult> assessmentResultList = new ArrayList<>();
    private static final AssessmentResultService instance = new AssessmentResultService();
    Random random = random = new Random();

    private AssessmentResultService(){    }

    public static AssessmentResultService getInstance(){
        return instance;
    }

    public void addAssessmentResultList(){
        for (Assessment assessment: StudentAssessmentService.getInstance().getAssessmentsList().values()) {
            int totalQuestions = assessment.getNumberOfQuestions();
            int correctQuestions = random.nextInt(totalQuestions);
            AssessmentResult assessmentResult = new AssessmentResult(
                    assessment.getSubject(),
                    assessment.getAssessmentTitle(),
                    assessment.getDifficulty().toString(),
                    totalQuestions,
                    correctQuestions,
                    assessment
            );
        }
    }

    public List<AssessmentResult> getAssessmentResultList(){
        return assessmentResultList;
    }
}
