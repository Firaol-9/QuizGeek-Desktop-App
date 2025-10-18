package com.quiz_geek.services.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.quiz_geek.controllers.common.SessionManager;
import com.quiz_geek.exceptions.EmailAlreadyExistsException;
import com.quiz_geek.exceptions.IncorrectPasswordOrEmailException;
import com.quiz_geek.models.UserRole;
import com.quiz_geek.payloads.request.AssessmentRequest;
import com.quiz_geek.payloads.response.AssessmentResponse;
import com.quiz_geek.payloads.response.LoginResponseDTO;
import com.quiz_geek.payloads.UserDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiService {

    private static final String BASE_URL = "http://localhost:8080/api";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static UserDTO login(String email, String password) throws Exception {
        String json = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            LoginResponseDTO loginResponse = mapper.readValue(response.body(), LoginResponseDTO.class);
            SessionManager.setToken(loginResponse.getToken());
            return loginResponse.getUserResponse();
        } else if (response.statusCode() == 401) {
            throw new IncorrectPasswordOrEmailException("Invalid Password or Email!");
        } else {
            throw new RuntimeException("Server error: " + response.statusCode());
        }
    }

    public static UserDTO signup(String fullName, String email, String password, UserRole role)
                    throws IncorrectPasswordOrEmailException, RuntimeException, Exception {
        String json = String.format(
                "{\"fullName\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"role\":\"%s\"}",
                fullName, email, password, role.toString().toUpperCase()
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/signup"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if ( response.statusCode() == 200) {
            return mapper.readValue(response.body(), UserDTO.class);
        }
        else if ( response.statusCode() == 400)
            throw new EmailAlreadyExistsException("Email already in use!");
        else
            throw new RuntimeException("Server error: " + response.statusCode());
    }

    public static UserDTO googleLogin(String email, String name, String googleId) throws Exception {
        String json = String.format(
                "{\"email\":\"%s\",\"name\":\"%s\",\"id\":\"%s\"}",
                email, name, googleId
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/google"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            LoginResponseDTO loginResponse = mapper.readValue(response.body(), LoginResponseDTO.class);
            SessionManager.setToken(loginResponse.getToken());
            return loginResponse.getUserResponse();
        } else {
            throw new RuntimeException("Google login failed: " + response.statusCode());
        }
    }

    public static AssessmentResponse createAssessment(AssessmentRequest assessmentRequest) throws IOException, InterruptedException {
        String json = "" ;
        try{
            json = mapper.writeValueAsString(assessmentRequest);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        System.out.println(json);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/assessments"))
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println( response.statusCode());
        if ( response.statusCode() == 200){
            return mapper.readValue(response.body(), AssessmentResponse.class);
        } else{
            throw new RuntimeException("Error while creating exam");
        }
    }

    public static UserDTO getCurrentUser() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "auth/me"))
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return mapper.readValue(response.body(), UserDTO.class);
        } else {
            throw new RuntimeException("Failed to fetch user: " + response.statusCode());
        }
    }

}

