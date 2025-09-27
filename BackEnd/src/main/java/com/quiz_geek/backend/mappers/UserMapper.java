package com.quiz_geek.backend.mappers;

import com.quiz_geek.backend.payload.requests.SignupRequest;
import com.quiz_geek.backend.payload.responses.UserResponse;
import com.quiz_geek.backend.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user, String message){
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getRole(),
                message
        );
    }

    public User toEntity(SignupRequest request){
        return new User(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );
    }
}
