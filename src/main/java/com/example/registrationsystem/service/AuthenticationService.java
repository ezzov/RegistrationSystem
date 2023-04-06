package com.example.registrationsystem.service;

import com.example.registrationsystem.models.User;

/**
 * Service for authentication
 */
public interface AuthenticationService {

    /**
     * Method that proceeds login
     * @param email from {@link User}
     * @param password from {@link User}
     * @return userId from {@link User}
     */
    long login(String email, String password);

    /**
     * Method that generates access token
     * @param userId from {@link User}
     * @return Access token
     */
    String generateAccessToken(long userId);

    /**
     * Method that generates refresh token
     * @param userId from {@link User}
     * @return Refresh token
     */
    String generateRefreshToken(long userId);
}
