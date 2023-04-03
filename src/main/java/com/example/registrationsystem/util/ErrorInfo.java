package com.example.registrationsystem.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * CLass that stores error information.
 */
@Getter
@AllArgsConstructor
public class ErrorInfo {
    private final StringBuffer uri;
    private final ErrorType type;
    private final String exceptionMsg;
    private final LocalDateTime timeStamp;
}
