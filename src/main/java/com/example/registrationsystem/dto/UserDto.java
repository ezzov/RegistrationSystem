package com.example.registrationsystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.registrationsystem.controller.AdminController;

import java.util.Set;

/**
 * Data transfer object that is used in response of methods
 * in {@link AdminController}
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    /**
     * Username
     */
    @NotNull
    private String userName;
    /**
     * User email
     */
    @Email
    private String email;
    /**
     * Set of users roles
     */
    Set<RoleDto> roles;
}
