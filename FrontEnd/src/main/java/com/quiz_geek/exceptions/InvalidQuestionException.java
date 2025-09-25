package com.quiz_geek.exceptions;

public class InvalidQuestionException extends RuntimeException {
    public InvalidQuestionException(String message) {
        super(message);
    }
}
