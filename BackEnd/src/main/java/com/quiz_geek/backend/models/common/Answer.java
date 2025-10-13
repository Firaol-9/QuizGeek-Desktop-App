package com.quiz_geek.backend.models.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private String id;
    private String selectedAnswer;
    private boolean isCorrect;
}
