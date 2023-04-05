package com.example.registrationsystem.service;

public interface AuthenticationService {

    long login(String email, String password);

    String generateAccessToken(long userId);

    String generateRefreshToken(long userId);
}
