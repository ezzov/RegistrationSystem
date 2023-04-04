package com.example.registrationsystem.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssignRoleRightsException extends RuntimeException {
    public AssignRoleRightsException(String message) {
        super(message);
    }
}
