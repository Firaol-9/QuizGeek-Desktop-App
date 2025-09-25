package com.quiz_geek.backend.controllers;

import com.quiz_geek.backend.models.User;
import com.quiz_geek.backend.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository repository){
        this.userRepository = repository;
    }

    @GetMapping
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id){
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }
}
