package com.example.registrationsystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
