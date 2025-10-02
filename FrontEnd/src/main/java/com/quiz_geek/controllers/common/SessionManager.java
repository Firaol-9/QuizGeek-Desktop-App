package com.quiz_geek.controllers.common;

public class SessionManager {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        SessionManager.token = token;
    }
}

