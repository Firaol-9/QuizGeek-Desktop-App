package com.quiz_geek.exceptions;

public class EmailNotFoundException extends UserException {
    public EmailNotFoundException(String message){
        super(message);
    }
}
