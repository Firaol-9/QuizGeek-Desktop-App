package com.quiz_geek.backend.controllers;

import com.quiz_geek.backend.mappers.UserMapper;
import com.quiz_geek.backend.models.common.User;
import com.quiz_geek.backend.models.common.UserRole;
import com.quiz_geek.backend.payload.responses.AuthResponse;
import com.quiz_geek.backend.payload.responses.UserResponse;
import com.quiz_geek.backend.services.UserService;
import com.quiz_geek.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class OAuth2Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/google")
    public ResponseEntity<AuthResponse> googleLogin(@RequestBody Map<String, String> googleUser) {
        try {
            String email = googleUser.get("email");
            String fullName = googleUser.get("name");
            String googleId = googleUser.get("id");
            
            if (email == null || fullName == null) {
                return ResponseEntity.badRequest().build();
            }

            // Check if user exists, if not create new user
            Optional<User> existingUser = userService.getUserByEmail(email);
            User user;
            
            if (existingUser.isPresent()) {
                user = existingUser.get();
            } else {
                // Create new user with default role as STUDENT
                user = new User(fullName, email, "google_oauth_" + googleId, UserRole.STUDENT);
                user = userService.createUser(user);
            }

            // Generate JWT token
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            
            // Create response
            UserResponse userResponse = userMapper.toResponse(user, "successful");
            AuthResponse authResponse = new AuthResponse(token, userResponse);
            
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
