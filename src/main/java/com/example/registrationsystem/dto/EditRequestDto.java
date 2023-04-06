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
 * {@link UserController#editDraftRequest(EditRequestDto)}
 */
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EditRequestDto {
    /**
     * Request id
     */
    @NotNull
    private long id;
    /**
     * New request text
     */
    @NotNull
    @Size(max = 500)
    private String requestText;

}
