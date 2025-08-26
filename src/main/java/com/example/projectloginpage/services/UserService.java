package com.example.projectloginpage.services;
import com.example.projectloginpage.exceptions.EmailAlreadyExistsException;
import com.example.projectloginpage.exceptions.EmailNotFoundException;
import com.example.projectloginpage.exceptions.IncorrectPasswordException;
import com.example.projectloginpage.exceptions.PasswordMismatchException;
import com.example.projectloginpage.models.User;

import java.util.*;

public class UserService {
    public static UserService instance;

    private Map<String, User> users = new HashMap<>();

    public static User currentUser;

    private UserService(){};

    //returns the singleton instance
    public static UserService getInstance(){
        if(instance == null){
            instance = new UserService();
        }
        return instance;
    }

    //temporary user
    public void addUser(){
        users.put("student@gmail.com", new User("student", "student@gmail.com", "1234", "student"));
        users.put("teacher@gmail.com", new User("teacher", "teacher@gmail.com", "1234", "teacher"));
    }

    public boolean signUp(String userName, String email, String password, String confirmPassword, String career) throws EmailAlreadyExistsException, PasswordMismatchException {
        if (users.containsKey(email)) throw new EmailAlreadyExistsException("Email already exists. Try to login.");
        if (!password.equals(confirmPassword)) throw new PasswordMismatchException("Password Mismatch!");

        User user = new User(userName, email, password, career);
        users.put(email, user );
        this.setCurrentUser(user);
        return true;
    }

    public boolean login(String email, String password) throws EmailNotFoundException, IncorrectPasswordException {
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
