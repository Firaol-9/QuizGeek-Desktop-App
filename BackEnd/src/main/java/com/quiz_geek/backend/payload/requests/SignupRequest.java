package com.quiz_geek.backend.payload.requests;

import com.quiz_geek.backend.models.UserRole;
import lombok.Data;

@Data
public class SignupRequest {
    private String fullName;
    private String email;
    private String password;
    private UserRole role;
}
