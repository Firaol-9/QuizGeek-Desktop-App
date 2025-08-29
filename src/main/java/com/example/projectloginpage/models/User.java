package com.example.projectloginpage.models;

public class User {
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

    public String getFullName(){ return fullName;}
    public String getEmail(){ return email;}
    public UserRole getRole(){ return role;}
    public String getPassword() { return password;}
}
