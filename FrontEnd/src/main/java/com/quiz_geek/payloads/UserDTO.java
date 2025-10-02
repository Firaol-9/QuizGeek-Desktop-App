package com.quiz_geek.payloads;

import com.quiz_geek.models.UserRole;

public class UserDTO {
    private String id;
    private String fullName;
    private UserRole role;
    private String message;

    public UserDTO() {}

    public UserDTO(String id, String fullName, UserRole role, String message) {
        this.id = id;
        this.fullName = fullName;
        this.role = role;
        this.message = message;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
