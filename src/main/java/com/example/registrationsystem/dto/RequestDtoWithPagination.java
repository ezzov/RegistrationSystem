package com.example.registrationsystem.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import com.example.registrationsystem.controller.UserController;
import com.example.registrationsystem.controller.OperatorController;

import java.util.List;

/**
 * Data transfer object that is used in response of methods
 * in {@link UserController}
 * and {@link OperatorController}
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestDtoWithPagination {
    /**
     * Current page of list requests
     */
    @Min(0)
    private int currentPage;
    /**
     * Number of total pages
     */
    private int totalPages;
    /**
     * List of request data
     */
    private List<RequestDtoForResponse> requests;
}
