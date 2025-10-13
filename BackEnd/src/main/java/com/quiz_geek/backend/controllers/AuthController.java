package com.quiz_geek.backend.controllers;

import com.quiz_geek.backend.mappers.UserMapper;
import com.quiz_geek.backend.models.common.User;
import com.quiz_geek.backend.payload.requests.LoginRequest;
import com.quiz_geek.backend.payload.responses.AuthResponse;
import com.quiz_geek.backend.payload.requests.SignupRequest;
import com.quiz_geek.backend.payload.responses.UserResponse;
import com.quiz_geek.backend.services.UserService;
import com.quiz_geek.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthController(UserService userService,
                          UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@RequestBody SignupRequest signupRequest){
        User user = userService.registerUser(signupRequest);
        UserResponse response = userMapper.toResponse(user, "successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest){
        User user = userService.authenticateUser(loginRequest);
        UserResponse response = userMapper.toResponse(user, "successful");
        String token = jwtUtil.generateToken(user.getId(), user.getRole().name());
        AuthResponse authResponse = new AuthResponse(token, response);
        return ResponseEntity.ok(authResponse);
    }
}
