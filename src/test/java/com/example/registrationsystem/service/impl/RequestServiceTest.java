package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.models.Request;
import com.example.registrationsystem.models.User;
import com.example.registrationsystem.models.enums.Order;
import com.example.registrationsystem.models.enums.Status;
import com.example.registrationsystem.repository.RequestRepository;
import com.example.registrationsystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @InjectMocks
    private RequestServiceImpl requestService;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private UserRepository userRepository;

    private String textForOperator;
    private User user;
    private Request request1;
    private Request request2;
    private List<Request> requestList;
    private Page<Request> requestPage;

    @BeforeEach
    void setup() {
        textForOperator = "S-o-m-e- -u-s-e-f-u-l- -t-e-x-t";
        user = User.builder()
                .id(2l)
                .userName("Anna")
                .password("1234")
                .build();
        request1 = Request.builder()
                .id(3l)
                .status(Status.DRAFT)
                .requestText("Some useful text")
                .dateOfCreation(LocalDate.of(2022, 12, 1))
                .user(user)
                .build();
        request2 = Request.builder()
                .id(2l)
                .status(Status.SENT)
                .requestText("Urgent question")
                .dateOfCreation(LocalDate.of(2021, 12, 1))
                .user(user)
                .build();
        requestList = List.of(request1, request2);
        requestPage = new PageImpl<>(requestList);
    }

    @Test
    void save_shouldSaveAndReturnSavedRequest() {
        when(requestRepository.save(request1)).thenReturn(request1);
        Request savedRequest = requestService.save(request1);
        assertEquals(3l, savedRequest.getId());
    }

    @Test
    void editRequestDraft_shouldChangeRequest() {
        request1.setRequestText("Some more useful text");
        when(requestRepository.findById(any())).thenReturn(Optional.of(request1));
        when(requestRepository.save(request1)).thenReturn(request1);
        Request updatedRequest = requestService.editRequestDraft(request1);
        assertEquals("Some more useful text", updatedRequest.getRequestText());
    }

    @Test
    void changeStatusDraftToSent_shouldChangeRequestStatus() {
        when(requestRepository.findById(any())).thenReturn(Optional.of(request1));
        when(requestRepository.save(request1)).thenReturn(request1);
        requestService.changeStatusDraftToSent(3l);
        assertEquals(Status.SENT, request1.getStatus());
        assertEquals(textForOperator, request1.getRequestText());
    }

    @Test
    void findRequestsByUserId_shouldReturnPageOfRequests() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(requestRepository.findAllByUserOrderByDateOfCreationAsc(user, PageRequest.of(0, 5)))
                .thenReturn(requestPage);
        Page<Request> resultRequestPage = requestService.findRequestsByUserId(2l, Order.ASC, 0);
        assertEquals(1, resultRequestPage.getTotalPages());
        assertEquals(3l, resultRequestPage.getContent().get(0).getId());
    }
}