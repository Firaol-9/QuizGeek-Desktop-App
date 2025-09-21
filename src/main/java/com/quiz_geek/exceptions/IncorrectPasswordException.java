package com.quiz_geek.exceptions;

public class IncorrectPasswordException extends UserException{
    public IncorrectPasswordException(String message){
        super(message);
    }
}
