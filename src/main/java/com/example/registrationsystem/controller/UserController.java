package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.EditRequestDto;
import com.example.registrationsystem.dto.RequestDto;
import com.example.registrationsystem.dto.RequestDtoWithPagination;
import com.example.registrationsystem.mapper.RequestMapper;
import com.example.registrationsystem.models.enums.Order;
import com.example.registrationsystem.security.JwtProvider;
import com.example.registrationsystem.service.RequestService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {


    private RequestService requestService;
    private RequestMapper requestMapper;
    private JwtProvider jwtProvider;

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

    @GetMapping("/all-requests")
    public RequestDtoWithPagination findRequestsByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                         @RequestParam Order order,
                                                         @RequestParam int page) {
        long userId = jwtProvider.getUserIdFromToken(authHeader.substring(7));
        return requestMapper.toRequestDtoWithPagination(requestService.findRequestsByUserId(userId, order, page));
    }
}
