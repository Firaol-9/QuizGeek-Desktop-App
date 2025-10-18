package com.quiz_geek.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type{
    private AssessmentType assessmentType;
    private double timeLimit;
}
