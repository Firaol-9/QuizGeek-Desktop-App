package com.quiz_geek.payloads.response;

import com.quiz_geek.payloads.UserDTO;

public class LoginResponseDTO {
    private String token;
    private UserDTO userResponse;

    public LoginResponseDTO() {}

    public LoginResponseDTO(String token, UserDTO userResponse) {
        this.token = token;
        this.userResponse = userResponse;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public UserDTO getUserResponse() { return userResponse; }
    public void setUserResponse(UserDTO userResponse) { this.userResponse = userResponse; }
}

