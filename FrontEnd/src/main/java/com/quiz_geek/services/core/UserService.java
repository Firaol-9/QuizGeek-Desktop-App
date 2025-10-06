package com.quiz_geek.services.core;

import com.quiz_geek.exceptions.*;
import com.quiz_geek.models.User;
import com.quiz_geek.models.UserRole;

import java.util.*;

public class UserService {
    private static UserService instance;

    private Map<String, User> users = new HashMap<>();

    private static User currentUser;

    private UserService(){};

    //returns the singleton instance
    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    public void validateSignup(String userName, String email, String password, String confirmPassword, UserRole role)
            throws InvalidInputException, PasswordMismatchException {
        if ( userName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            throw new InvalidInputException("Invalid input! please fill all the fields.");
        }
        if (!password.equals(confirmPassword)) throw new PasswordMismatchException("Password Mismatch!");
    }

    public void validateLogin(String email, String password)
            throws InvalidInputException, IncorrectPasswordOrEmailException {
        if (email.isBlank() || password.isBlank())
            throw new InvalidInputException("Invalid input! please fill all the text fields.");
    }

    public Collection<User> getUsers(){
        return users.values();
    }

    public void setCurrentUser(User user){
        currentUser = user;
    }

    public static User getCurrentUser(){
        return currentUser;
    }
}
