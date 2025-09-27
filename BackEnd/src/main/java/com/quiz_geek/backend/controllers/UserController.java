package com.quiz_geek.backend.controllers;

import com.quiz_geek.backend.exceptions.UserNotFoundException;
import com.quiz_geek.backend.models.User;
import com.quiz_geek.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
