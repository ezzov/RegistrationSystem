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

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestDtoForResponse {

    @NotNull
    private Status status;

    @NotNull
    @Size(max = 500)
    private String requestText;

    @NotNull
    private LocalDate dateOfCreation;

    @NotNull
    private String userName;
}
