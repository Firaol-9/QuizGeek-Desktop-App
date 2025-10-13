package com.quiz_geek.backend.services;

import com.quiz_geek.backend.mappers.UserMapper;
import com.quiz_geek.backend.payload.requests.LoginRequest;
import com.quiz_geek.backend.payload.requests.SignupRequest;
import com.quiz_geek.backend.models.common.User;
import com.quiz_geek.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserMapper userMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User registerUser(SignupRequest signupRequest){
        if ( userRepository.existsByEmail(signupRequest.getEmail())){
            throw new RuntimeException("Email already in use");
        }
        User user = userMapper.toEntity(signupRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User authenticateUser(LoginRequest loginRequest){

        User user = userRepository.findByEmail(loginRequest.getEmail()).
                orElseThrow( ()-> new BadCredentialsException("Invalid email!") );

        if ( !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new BadCredentialsException("Invalid password!");
        }

        return user;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getById(String id){
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean userExistsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public User createUser(User user){
        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
