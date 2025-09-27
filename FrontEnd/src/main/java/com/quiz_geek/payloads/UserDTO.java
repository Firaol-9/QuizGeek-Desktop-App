package com.quiz_geek.payloads;

import com.quiz_geek.models.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String fullName;
    private UserRole role;
    private String message;
}
