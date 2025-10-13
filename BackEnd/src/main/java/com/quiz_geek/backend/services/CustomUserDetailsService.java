package com.quiz_geek.backend.services;

import com.quiz_geek.backend.models.common.CustomUserDetails;
import com.quiz_geek.backend.models.common.User;
import com.quiz_geek.backend.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // your JPA repository

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: "));
        return new CustomUserDetails(user);
    }
}

