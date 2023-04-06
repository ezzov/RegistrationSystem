package com.example.registrationsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.registrationsystem.controller.AuthenticationController;

/**
 * Data transfer object that is used in response of
 * {@link AuthenticationController#login(LoginDto)}
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccessAndRefreshTokensDto {
    /**
     * Access token
     */
    @NotBlank
    private String accessToken;
    /**
     * Refreshed access token
     */
    @NotBlank
    private String refreshToken;
}
