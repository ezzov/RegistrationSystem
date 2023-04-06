package com.example.registrationsystem.models.enums;

import com.example.registrationsystem.service.RequestService;
import com.example.registrationsystem.controller.UserController;
import com.example.registrationsystem.controller.OperatorController;

/**
 * Enum is used in {@link RequestService},
 * {@link UserController}
 * and {@link OperatorController}
 */
public enum Order {
    ASC,
    DESC;
}
