package com.example.registrationsystem.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestDtoWithPagination {

    @Min(0)
    private int currentPage;

    private int totalPages;

    private List<RequestDtoForResponse> requests;
}
