package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.RequestDtoWithPagination;
import com.example.registrationsystem.mapper.RequestMapper;
import com.example.registrationsystem.models.enums.Order;
import com.example.registrationsystem.service.RequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles requests of users with role OPERATOR
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/operator")
@AllArgsConstructor
public class OperatorController {

    private RequestService requestService;
    private RequestMapper requestMapper;

    /**
     * End-point that finds all requests
     * @param order from request query parameters
     * @param page from request query parameters
     * @return {@link RequestDtoWithPagination} with pages of requests
     */
    @GetMapping
    public RequestDtoWithPagination findAllRequests(@RequestParam Order order, @RequestParam int page) {
        log.info("Return page {} of all requests in {} order", page, order);
        return requestMapper.toRequestDtoWithPagination(requestService.findAllSentRequests(order, page));
    }

    /**
     * End-point that finds all requests by username or part of username
     * @param userName from request query parameters
     * @param order from request query parameters
     * @param page from request query parameters
     * @return {@link RequestDtoWithPagination} with pages of requests
     */
    @GetMapping("/by-username")
    public RequestDtoWithPagination findAllRequestsByUserName(@RequestParam String userName,
                                                              @RequestParam Order order,
                                                              @RequestParam int page) {
        log.info("Return page {} of all requests of user {} in {} order", page, userName, order);
        return requestMapper.toRequestDtoWithPagination(requestService.findAllSentRequestsByUserName(userName, order, page));
    }

    /**
     * End-point that accepts request
     * @param requestId from request query parameters
     */
    @PatchMapping("/accept")
    public void acceptRequest(@RequestParam long requestId) {
        log.info("Request for changing status to ACCEPTED of request with {} ", requestId);
        requestService.changeStatusSentToAccepted(requestId);
    }

    /**
     * End-point that rejects request
     * @param requestId from request query parameters
     */
    @PatchMapping("/reject")
    public void rejectRequest(@RequestParam long requestId) {
        log.info("Request for changing status to REJECTED of request with {} ", requestId);
        requestService.changeStatusSentToRejected(requestId);
    }
}
