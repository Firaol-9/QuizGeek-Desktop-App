package com.quiz_geek.exceptions;

public class EmailAlreadyExistsException extends UserException{
    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
