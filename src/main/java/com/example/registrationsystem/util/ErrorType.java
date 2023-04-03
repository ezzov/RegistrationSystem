package com.example.registrationsystem.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum that store error type.
 */
@Getter
@AllArgsConstructor
public enum ErrorType {
    ENTITY_NOT_FOUND(HttpStatus.UNPROCESSABLE_ENTITY),
    BAD_REQUEST(HttpStatus.BAD_REQUEST);

    private final HttpStatus status;
}
