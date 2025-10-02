package com.quiz_geek.backend.controllers;

import com.quiz_geek.backend.exceptions.UserNotFoundException;
import com.quiz_geek.backend.models.User;
import com.quiz_geek.backend.payload.responses.UserResponse;
import com.quiz_geek.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        // userDetails.getUsername() is your email (since we mapped it that way)
        User user = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse response = new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getRole(),
                ""
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id){
        return userService.getById(id);
    }

    @GetMapping("/by-email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }
}
