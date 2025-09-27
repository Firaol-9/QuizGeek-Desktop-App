package com.quiz_geek.backend.payload.responses;

import com.quiz_geek.backend.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String fullName;
    private UserRole role;
    private String message;
}
