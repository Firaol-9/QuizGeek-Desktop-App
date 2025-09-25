package com.quiz_geek.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String fullName;
    private String email;
    private String password;
    private UserRole role;

    public User(String fullName, String email, String password, UserRole role){
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId(){ return id; }
    public String getFullName(){ return fullName;}
    public String getEmail(){ return email;}
    public UserRole getRole(){ return role;}
    public String getPassword() { return password;}
}
