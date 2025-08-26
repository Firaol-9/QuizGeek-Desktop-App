package com.example.projectloginpage.exceptions;

public class EmailAlreadyExistsException extends UserException{
    public EmailAlreadyExistsException(String message){
        super(message);
    }
}
