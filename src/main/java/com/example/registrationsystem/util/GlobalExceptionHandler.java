package com.example.registrationsystem.util;

import com.example.registrationsystem.util.exception.CanNotChangeStatusException;
import com.example.registrationsystem.util.exception.CanNotUpdateRequestException;
import com.example.registrationsystem.util.exception.InvalidPageIndexException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req,
                                                         Exception e,
                                                         ErrorType errorType) {
        Throwable rootCause = Optional.ofNullable(NestedExceptionUtils.getRootCause(e)).orElse(e);
        log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        return ResponseEntity.status(errorType.getStatus())
                .body(new ErrorInfo(req.getRequestURL(), errorType,
                        e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleEntityNotFoundException(HttpServletRequest req,
                                                                   EntityNotFoundException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.ENTITY_NOT_FOUND);
    }

    @ExceptionHandler(CanNotChangeStatusException.class)
    public ResponseEntity<ErrorInfo> handleCanNotSendForReviewException(HttpServletRequest req,
                                                                        CanNotChangeStatusException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_REQUEST);
    }

    @ExceptionHandler(CanNotUpdateRequestException.class)
    public ResponseEntity<ErrorInfo> handleCanNotUpdateRequestException(HttpServletRequest req,
                                                                        CanNotUpdateRequestException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPageIndexException.class)
    public ResponseEntity<ErrorInfo> handleInvalidPageIndexException(HttpServletRequest req,
                                                                     InvalidPageIndexException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_REQUEST);
    }
}
