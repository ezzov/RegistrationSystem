package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.AccessAndRefreshTokensDto;
import com.example.registrationsystem.dto.LoginDto;
import com.example.registrationsystem.mapper.AuthenticationMapper;
import com.example.registrationsystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthenticationMapper authenticationMapper;

    @PostMapping
    public AccessAndRefreshTokensDto login(@RequestBody LoginDto loginDto) {
        long userId = authenticationService.login(loginDto.getEmail(), loginDto.getPassword());
        AccessAndRefreshTokensDto accessAndRefreshTokensDto = authenticationMapper.toAccessAndRefreshTokensDto(
                authenticationService.generateAccessToken(userId),
                authenticationService.generateRefreshToken(userId));
        return accessAndRefreshTokensDto;
    }
}
