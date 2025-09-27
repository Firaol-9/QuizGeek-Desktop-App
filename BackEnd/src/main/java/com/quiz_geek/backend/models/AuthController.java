package com.quiz_geek.backend.models;

import com.quiz_geek.backend.mappers.UserMapper;
import com.quiz_geek.backend.payload.requests.LoginRequest;
import com.quiz_geek.backend.payload.responses.MessageResponse;
import com.quiz_geek.backend.payload.requests.SignupRequest;
import com.quiz_geek.backend.payload.responses.UserResponse;
import com.quiz_geek.backend.services.UserService;
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

    public AuthController(UserService userService,
                          UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
        userService.registerUser(signupRequest);
        return ResponseEntity.ok( new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody LoginRequest loginRequest){
        User user = userService.authenticateUser(loginRequest);
        UserResponse response = userMapper.toResponse(user, "successful");
        return ResponseEntity.ok(response);
    }
}
