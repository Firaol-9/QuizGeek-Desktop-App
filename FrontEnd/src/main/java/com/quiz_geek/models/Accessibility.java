package com.quiz_geek.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accessibility {
    private AssessmentAccessibility assessmentAccessibility;
    private String password;
}
