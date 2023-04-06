package com.example.registrationsystem.dto;

import com.example.registrationsystem.models.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Data transfer object that is used in
 * {@link RequestDtoWithPagination}
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestDtoForResponse {
    /**
     * Status of request
     */
    @NotNull
    private Status status;
    /**
     * Text of request
     */
    @NotNull
    @Size(max = 500)
    private String requestText;
    /**
     * Date of creation of request
     */
    @NotNull
    private LocalDate dateOfCreation;
    /**
     * Request owner username
     */
    @NotNull
    private String userName;
}
