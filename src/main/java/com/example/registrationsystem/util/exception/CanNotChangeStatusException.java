package com.example.registrationsystem.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.registrationsystem.service.impl.RequestServiceImpl;

/**
 * Exception that be thrown in methods {@link RequestServiceImpl}
 * if user status can not be changed.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CanNotChangeStatusException extends RuntimeException {
    public CanNotChangeStatusException() {
        super("This action is not possible");
    }
}
