package com.example.registrationsystem.util;

import com.example.registrationsystem.util.exception.AssignRoleRightsException;
import com.example.registrationsystem.util.exception.CanNotChangeStatusException;
import com.example.registrationsystem.util.exception.CanNotUpdateRequestException;
import com.example.registrationsystem.util.exception.InvalidPageIndexException;
import com.example.registrationsystem.util.exception.InvalidPasswordException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * RestControllerAdvice that handles all exceptions
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Method that handles log and get exception
     * @param req {@link HttpServletRequest}
     * @param e {@link Exception}
     * @param errorType {@link ErrorType}
     * @return {@link ResponseEntity} of {@link ErrorInfo}
     */
    private ResponseEntity<ErrorInfo> logAndGetErrorInfo(HttpServletRequest req,
                                                         Exception e,
                                                         ErrorType errorType) {
        Throwable rootCause = Optional.ofNullable(NestedExceptionUtils.getRootCause(e)).orElse(e);
        log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        return ResponseEntity.status(errorType.getStatus())
                .body(new ErrorInfo(req.getRequestURL(), errorType,
                        e.getMessage(), LocalDateTime.now()));
    }

    /**
     * Method that handles custom exceptions
     * @param req {@link HttpServletRequest}
     * @param ex {@link EntityNotFoundException}
     * @return {@link ResponseEntity} of {@link ErrorInfo}
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleEntityNotFoundException(HttpServletRequest req,
                                                                   EntityNotFoundException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.ENTITY_NOT_FOUND);
    }

    /**
     * Method that handles custom exceptions
     * @param req {@link HttpServletRequest}
     * @param ex {@link CanNotChangeStatusException}
     * @return {@link ResponseEntity} of {@link ErrorInfo}
     */
    @ExceptionHandler(CanNotChangeStatusException.class)
    public ResponseEntity<ErrorInfo> handleCanNotSendForReviewException(HttpServletRequest req,
                                                                        CanNotChangeStatusException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_REQUEST);
    }

    /**
     * Method that handles custom exceptions
     * @param req {@link HttpServletRequest}
     * @param ex {@link CanNotUpdateRequestException}
     * @return {@link ResponseEntity} of {@link ErrorInfo}
     */
    @ExceptionHandler(CanNotUpdateRequestException.class)
    public ResponseEntity<ErrorInfo> handleCanNotUpdateRequestException(HttpServletRequest req,
                                                                        CanNotUpdateRequestException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_REQUEST);
    }

    /**
     * Method that handles custom exceptions
     * @param req {@link HttpServletRequest}
     * @param ex {@link InvalidPageIndexException}
     * @return {@link ResponseEntity} of {@link ErrorInfo}
     */
    @ExceptionHandler(InvalidPageIndexException.class)
    public ResponseEntity<ErrorInfo> handleInvalidPageIndexException(HttpServletRequest req,
                                                                     InvalidPageIndexException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_REQUEST);
    }

    /**
     * Method that handles custom exceptions
     * @param req {@link HttpServletRequest}
     * @param ex {@link AssignRoleRightsException}
     * @return {@link ResponseEntity} of {@link ErrorInfo}
     */
    @ExceptionHandler(AssignRoleRightsException.class)
    public ResponseEntity<ErrorInfo> handleAssignRoleRightsException(HttpServletRequest req,
                                                                     AssignRoleRightsException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_REQUEST);
    }

    /**
     * Method that handles custom exceptions
     * @param req {@link HttpServletRequest}
     * @param ex {@link InvalidPasswordException}
     * @return {@link ResponseEntity} of {@link ErrorInfo}
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorInfo> handleInvalidPasswordException(HttpServletRequest req,
                                                                    InvalidPasswordException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.BAD_REQUEST);
    }
}
