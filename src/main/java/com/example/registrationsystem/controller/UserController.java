package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.EditRequestDto;
import com.example.registrationsystem.dto.RequestDto;
import com.example.registrationsystem.dto.RequestDtoWithPagination;
import com.example.registrationsystem.mapper.RequestMapper;
import com.example.registrationsystem.models.enums.Order;
import com.example.registrationsystem.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestMapper requestMapper;

    @PostMapping("/new")
    public void createNewRequest(@RequestBody RequestDto requestDto) {
        requestService.save(requestMapper.requestDtoToRequest(requestDto));
    }

    @PatchMapping("/edit")
    public void editDraftRequest(@RequestBody EditRequestDto editRequestDto) {
        requestService.editRequestDraft(requestMapper.editRequestDtoToRequest(editRequestDto));
    }

    @PatchMapping("/send")
    public void sendForReview(@RequestParam long requestId) {
        requestService.changeStatusDraftToSent(requestId);
    }

    @GetMapping("/all-requests/{userId}")
    public RequestDtoWithPagination findRequestsByUserId(@PathVariable long userId,
                                                         @RequestParam Order order,
                                                         @RequestParam int page) {
        return requestMapper.toRequestDtoWithPagination(requestService.findRequestsByUserId(userId, order, page));
    }
}
