package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.models.User;
import com.example.registrationsystem.repository.UserRepository;
import com.example.registrationsystem.security.JwtProvider;
import com.example.registrationsystem.security.SecurityProperties;
import com.example.registrationsystem.service.AuthenticationService;
import com.example.registrationsystem.util.exception.InvalidPasswordException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link AuthenticationService}.
 * Works with {@link UserRepository}
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;
    private JwtProvider jwtProvider;
    private StringRedisTemplate redisTemplate;
    private SecurityProperties securityProperties;
    private PasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     * For login uses method {@link UserRepository#findUserByEmail(String)}
     * @param email    from {@link User}
     * @param password from {@link User}
     * @return userId from {@link User}
     */
    @Override
    public long login(String email, String password) {
        log.info("Request for user with email: {}", email);
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("Invalid password");
            throw new InvalidPasswordException();
        }
        log.info("Return user id");
        return user.getId();
    }

    /**
     * {@inheritDoc}
     * Uses method {@link JwtProvider#generateAccessToken(String)}
     * @param userId from {@link User}
     * @return Access token
     */
    @Override
    public String generateAccessToken(long userId) {
        log.info("Request to generate access token");
        String accessToken = jwtProvider.generateAccessToken(String.valueOf(userId));
        log.info("Return access token");
        return accessToken;
    }

    /**
     * {@inheritDoc}
     * Uses method {@link JwtProvider#generateRefreshToken(String)}
     * @param userId from {@link User}
     * @return Refresh token
     */
    @Override
    public String generateRefreshToken(long userId) {
        log.info("Request to generate refresh token");
        String refreshToken = jwtProvider.generateRefreshToken(String.valueOf(userId));
        redisTemplate.opsForValue().set(String.valueOf(userId), refreshToken,
                securityProperties.getRefreshTokenTimeoutInMinutes(), TimeUnit.MINUTES);
        log.info("Return refresh token");
        return refreshToken;
    }
}
