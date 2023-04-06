package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.AccessAndRefreshTokensDto;
import com.example.registrationsystem.dto.LoginDto;
import com.example.registrationsystem.mapper.AuthenticationMapper;
import com.example.registrationsystem.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles requests to {@link AuthenticationService} and return response using {@link AuthenticationMapper}
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/login")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private AuthenticationMapper authenticationMapper;

    /**
     * End-point that allow user to proceed login process using
     * {@link AuthenticationService#login(String, String)}.
     * @param loginDto from request body
     * @return {@link AccessAndRefreshTokensDto} with access and refresh tokens
     */
    @PostMapping
    public AccessAndRefreshTokensDto login(@RequestBody LoginDto loginDto) {
        log.info("Request login for user with email: {}", loginDto.getEmail());
        long userId = authenticationService.login(loginDto.getEmail(), loginDto.getPassword());
        log.info("Return access and refresh tokens");
        return authenticationMapper.toAccessAndRefreshTokensDto(
                authenticationService.generateAccessToken(userId),
                authenticationService.generateRefreshToken(userId));
    }
}
