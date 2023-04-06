package com.example.registrationsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.registrationsystem.controller.AuthenticationController;

/**
 * Data transfer object that is used in
 * {@link AuthenticationController#login(LoginDto)}
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    /**
     * User email
     */
    @Email
    private String email;
    /**
     * User password
     */
    @NotNull
    private String password;
}
