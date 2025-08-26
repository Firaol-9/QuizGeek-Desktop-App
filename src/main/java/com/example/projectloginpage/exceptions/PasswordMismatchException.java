package com.example.projectloginpage.exceptions;

public class PasswordMismatchException extends UserException{
    public PasswordMismatchException(String message){
        super(message);
    }
}
