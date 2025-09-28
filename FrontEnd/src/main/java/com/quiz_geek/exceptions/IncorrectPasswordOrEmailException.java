package com.quiz_geek.exceptions;

public class IncorrectPasswordOrEmailException extends UserException{
    public IncorrectPasswordOrEmailException(String message){
        super(message);
    }
}
