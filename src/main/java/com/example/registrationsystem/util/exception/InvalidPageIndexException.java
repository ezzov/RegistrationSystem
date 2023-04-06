package com.example.registrationsystem.util.exception;

import com.example.registrationsystem.service.impl.RequestServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Exception that be thrown in methods {@link RequestServiceImpl}
 * if number page is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPageIndexException extends RuntimeException {
    public InvalidPageIndexException() {
        super("Invalid page index");
    }
}
