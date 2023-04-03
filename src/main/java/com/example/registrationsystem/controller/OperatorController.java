package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.RequestDtoWithPagination;
import com.example.registrationsystem.mapper.RequestMapper;
import com.example.registrationsystem.models.enums.Order;
import com.example.registrationsystem.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/operator")
public class OperatorController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestMapper requestMapper;

    @GetMapping
    public RequestDtoWithPagination findAllRequests(@RequestParam Order order, @RequestParam int page) {
        return requestMapper.toRequestDtoWithPagination(requestService.findAllSentRequests(order, page));
    }

    @GetMapping("/by-username")
    public RequestDtoWithPagination findAllRequestsByUserName(@RequestParam String userName,
                                                              @RequestParam Order order,
                                                              @RequestParam int page) {
        return requestMapper.toRequestDtoWithPagination(requestService.findAllSentRequestsByUserName(userName, order, page));
    }

    @PatchMapping("/accept")
    public void acceptRequest(@RequestParam long requestId) {
        requestService.changeStatusSentToAccepted(requestId);
    }

    @PatchMapping("/reject")
    public void rejectRequest(@RequestParam long requestId) {
        requestService.changeStatusSentToRejected(requestId);
    }
}
