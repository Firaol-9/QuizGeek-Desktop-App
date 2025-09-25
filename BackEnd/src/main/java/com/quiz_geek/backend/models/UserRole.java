package com.quiz_geek.backend.models;

public enum UserRole {
    STUDENT("Student"),
    TEACHER("Teacher");

    private final String label;

    UserRole(String label){
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}