package com.example.registrationsystem.util.exception;

import com.example.registrationsystem.service.impl.RequestServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that be thrown in method {@link RequestServiceImpl#editRequestDraft}
 * if request can not be updated.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CanNotUpdateRequestException extends RuntimeException {
    public CanNotUpdateRequestException() {
        super("Only the draft of request can be changed");
    }
}
