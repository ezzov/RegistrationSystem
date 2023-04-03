package com.example.registrationsystem.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CanNotSendForReviewException extends RuntimeException {
    public CanNotSendForReviewException() {
        super("This request can not be sent for review");
    }
}
