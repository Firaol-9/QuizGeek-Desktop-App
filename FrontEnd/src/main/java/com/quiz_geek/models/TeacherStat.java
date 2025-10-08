package com.quiz_geek.models;

import java.time.LocalDate;
import java.util.Map;

public class TeacherStat {
    private final int totalAssessments;
    private final int totalStudents;
    private final Map<LocalDate, Integer> participationByDate;

    public TeacherStat(int totalAssessments, int totalStudents, Map<LocalDate, Integer> participationByDate) {
        this.totalAssessments = totalAssessments;
        this.totalStudents = totalStudents;
        this.participationByDate = participationByDate;
    }

    public int getTotalAssessments() { return totalAssessments; }
    public int getTotalStudents() { return totalStudents; }
    public Map<LocalDate, Integer> getParticipationByDate() { return participationByDate; }
}


