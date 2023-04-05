package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.models.User;
import com.example.registrationsystem.repository.UserRepository;
import com.example.registrationsystem.security.JwtProvider;
import com.example.registrationsystem.security.SecurityProperties;
import com.example.registrationsystem.service.AuthenticationService;
import com.example.registrationsystem.util.exception.InvalidPasswordException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final StringRedisTemplate redisTemplate;
    private final SecurityProperties securityProperties;
    private final PasswordEncoder passwordEncoder;

    @Override
    public long login(String email, String password) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException();
        }
        return user.getId();
    }

    @Override
    public String generateAccessToken(long userId) {
        String accessToken = jwtProvider.generateAccessToken(String.valueOf(userId));
        return accessToken;
    }

    @Override
    public String generateRefreshToken(long userId) {
        String refreshToken = jwtProvider.generateRefreshToken(String.valueOf(userId));
        redisTemplate.opsForValue().set(String.valueOf(userId), refreshToken,
                securityProperties.getRefreshTokenTimeoutInMinutes(), TimeUnit.MINUTES);
        return refreshToken;
    }
}
