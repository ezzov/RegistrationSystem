package com.example.registrationsystem.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.registrationsystem.service.impl.UserServiceImpl;

/**
 * Exception that be thrown in method {@link UserServiceImpl#setOperatorRole}
 * if role rights can not be assigned
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssignRoleRightsException extends RuntimeException {
    public AssignRoleRightsException(String message) {
        super(message);
    }
}
