package com.example.registrationsystem.controller;

import com.example.registrationsystem.dto.EditRequestDto;
import com.example.registrationsystem.dto.RequestDto;
import com.example.registrationsystem.dto.RequestDtoWithPagination;
import com.example.registrationsystem.mapper.RequestMapper;
import com.example.registrationsystem.models.enums.Order;
import com.example.registrationsystem.security.JwtProvider;
import com.example.registrationsystem.service.RequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller that handles requests of users with role USER
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private RequestService requestService;
    private RequestMapper requestMapper;
    private JwtProvider jwtProvider;

    /**
     * End-point that creates new request
     * @param requestDto {@link RequestDto} from request body
     */
    @PostMapping("/new")
    public void createNewRequest(@RequestBody RequestDto requestDto) {
        log.info("Request for creating new request");
        requestService.save(requestMapper.requestDtoToRequest(requestDto));
    }

    /**
     * End-point that edits draft request
     * @param editRequestDto {@link EditRequestDto} from request body
     */
    @PatchMapping("/edit")
    public void editDraftRequest(@RequestBody EditRequestDto editRequestDto) {
        log.info("Request for editing DRAFT request");
        requestService.editRequestDraft(requestMapper.editRequestDtoToRequest(editRequestDto));
    }

    /**
     * End-point that sends draft request for review
     * @param requestId from request query parameters
     */
    @PatchMapping("/send")
    public void sendForReview(@RequestParam long requestId) {
        log.info("Request for changing status of DRAFT request to SENT");
        requestService.changeStatusDraftToSent(requestId);
    }

    /**
     * End-point that finds all requests of user
     * @param authHeader from authorization header
     * @param order from request query parameters
     * @param page from request query parameters
     * @return {@link RequestDtoWithPagination} from request body
     */
    @GetMapping("/all-requests")
    public RequestDtoWithPagination findRequestsByUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                                         @RequestParam Order order,
                                                         @RequestParam int page) {
        log.info("Request for getting user id from header");
        long userId = jwtProvider.getUserIdFromToken(authHeader.substring(7));
        log.info("Return page {} of requests of user with user id {} in {} order", page, userId, order);
        return requestMapper.toRequestDtoWithPagination(requestService.findRequestsByUserId(userId, order, page));
    }
}
