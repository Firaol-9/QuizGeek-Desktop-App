package com.quiz_geek.models;

import java.time.LocalDate;
import java.util.Map;

public class StudentStat {
    private final int dailyStreak;
    private final int questionsCompleted;
    private final int assessmentsEnrolled;
    // key: date, value: number of participations on that day
    private final Map<LocalDate, Integer> participationByDate;
    // overall assessment status breakdown
    private final int completedAssessments;
    private final int inProgressAssessments;
    private final int notStartedAssessments;
    // overall correctness breakdown
    private final int correctAnswers;
    private final int wrongAnswers;
    // enrolled types breakdown (used by bar chart)
    private final int practiceQuestionsCount;
    private final int worksheetsCount;
    private final int examsCount;

    public StudentStat(
            int dailyStreak,
            int questionsCompleted,
            int assessmentsEnrolled,
            Map<LocalDate, Integer> participationByDate,
            int completedAssessments,
            int inProgressAssessments,
            int notStartedAssessments,
            int correctAnswers,
            int wrongAnswers,
            int practiceQuestionsCount,
            int worksheetsCount,
            int examsCount
    ) {
        this.dailyStreak = dailyStreak;
        this.questionsCompleted = questionsCompleted;
        this.assessmentsEnrolled = assessmentsEnrolled;
        this.participationByDate = participationByDate;
        this.completedAssessments = completedAssessments;
        this.inProgressAssessments = inProgressAssessments;
        this.notStartedAssessments = notStartedAssessments;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.practiceQuestionsCount = practiceQuestionsCount;
        this.worksheetsCount = worksheetsCount;
        this.examsCount = examsCount;
    }

    public int getDailyStreak() { return dailyStreak; }
    public int getQuestionsCompleted() { return questionsCompleted; }
    public int getAssessmentsEnrolled() { return assessmentsEnrolled; }
    public Map<LocalDate, Integer> getParticipationByDate() { return participationByDate; }
    public int getCompletedAssessments() { return completedAssessments; }
    public int getInProgressAssessments() { return inProgressAssessments; }
    public int getNotStartedAssessments() { return notStartedAssessments; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getWrongAnswers() { return wrongAnswers; }
    public int getPracticeQuestionsCount() { return practiceQuestionsCount; }
    public int getWorksheetsCount() { return worksheetsCount; }
    public int getExamsCount() { return examsCount; }
}


