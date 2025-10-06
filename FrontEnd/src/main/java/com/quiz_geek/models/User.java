package com.quiz_geek.models;

import java.util.UUID;

public class User {
    private String fullName;
    private String email;
    private UserRole role;
    private String id;

    public User(String fullName, String email, UserRole role){
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public String getId(){ return id; }
    public String getFullName(){ return fullName;}
    public String getEmail(){ return email;}
    public UserRole getRole(){ return role;}
}
