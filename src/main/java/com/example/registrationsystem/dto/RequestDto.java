package com.example.registrationsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.registrationsystem.controller.UserController;

/**
 * Data transfer object that is used in
 * {@link UserController#createNewRequest(RequestDto)}
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    /**
     * User id
     */
    @NotNull
    private long userId;
    /**
     * Text of request
     */
    @NotNull
    @Size(max = 500)
    private String requestText;
}
