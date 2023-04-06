package com.example.registrationsystem.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.example.registrationsystem.service.impl.AuthenticationServiceImpl;

/**
 * Exception that be thrown in method {@link AuthenticationServiceImpl#login}
 * if password is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("Invalid Password");
    }
}
