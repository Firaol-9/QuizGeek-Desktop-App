package com.quiz_geek.services.core;

import com.quiz_geek.exceptions.IncorrectPasswordOrEmailException;
import com.quiz_geek.models.UserRole;
import com.quiz_geek.payloads.UserDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiService {

    private static final String BASE_URL = "http://localhost:8080/api/auth";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static UserDTO login(String email, String password) throws Exception {
        String json = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());

        if ( response.statusCode() == 200)
            return mapper.readValue(response.body(), UserDTO.class);
        else if ( response.statusCode() == 401)
            throw new IncorrectPasswordOrEmailException("Invalid Password or Email!");
        else
            throw new RuntimeException("Sever error: " + response.statusCode());
    }

    public static UserDTO signup(String fullName, String email, String password, UserRole role)
                    throws IncorrectPasswordOrEmailException, RuntimeException, Exception {
        String json = String.format(
                "{\"fullName\":\"%s\",\"email\":\"%s\",\"password\":\"%s\",\"role\":\"%s\"}",
                fullName, email, password, role.toString().toUpperCase()
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/signup"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), UserDTO.class);
    }
}

