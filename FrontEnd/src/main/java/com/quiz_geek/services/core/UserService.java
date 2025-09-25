package com.quiz_geek.services.core;

import com.quiz_geek.exceptions.EmailAlreadyExistsException;
import com.quiz_geek.exceptions.EmailNotFoundException;
import com.quiz_geek.exceptions.IncorrectPasswordException;
import com.quiz_geek.exceptions.PasswordMismatchException;
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

    //TODO: remove hard-coded users one connected to server
    public void addUser(){
        users.put("student@gmail.com", new User("student", "student@gmail.com", "1234", UserRole.STUDENT));
        users.put("teacher@gmail.com", new User("teacher", "teacher@gmail.com", "1234", UserRole.TEACHER));
    }

    public boolean signUp(String userName, String email, String password, String confirmPassword, UserRole role)
            throws EmailAlreadyExistsException, PasswordMismatchException {
        if (users.containsKey(email)) throw new EmailAlreadyExistsException("Email already exists. Try to login.");
        if (!password.equals(confirmPassword)) throw new PasswordMismatchException("Password Mismatch!");

        User user = new User(userName, email, password, role);
        users.put(email, user );
        this.setCurrentUser(user);
        return true;
    }

    public boolean login(String email, String password)
            throws EmailNotFoundException, IncorrectPasswordException {
        User user = users.get(email);
        if ( user == null) throw new EmailNotFoundException("E-mail not found. Signup first.");
        if ( !user.getPassword().equals(password)) throw new IncorrectPasswordException("Incorrect password!!");
        this.setCurrentUser(user);
        return true;
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
