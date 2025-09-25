package com.quiz_geek.exceptions;

public class PasswordMismatchException extends UserException{
    public PasswordMismatchException(String message){
        super(message);
    }
}
