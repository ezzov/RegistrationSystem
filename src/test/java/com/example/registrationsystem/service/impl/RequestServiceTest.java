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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    private Request request3;
    private Page<Request> requestPage;
    private Page<Request> requestPage1;

    @BeforeEach
    void setup() {
        textForOperator = "S-o-m-e- -u-s-e-f-u-l- -t-e-x-t";
        user = User.builder()
                .id(2L)
                .userName("Anna")
                .password("1234")
                .build();
        request1 = Request.builder()
                .id(3L)
                .status(Status.DRAFT)
                .requestText("Some useful text")
                .dateOfCreation(LocalDate.of(2022, 12, 1))
                .user(user)
                .build();
        request2 = Request.builder()
                .id(2L)
                .status(Status.SENT)
                .requestText("Urgent question")
                .dateOfCreation(LocalDate.of(2021, 12, 1))
                .user(user)
                .build();
        request3 = Request.builder()
                .id(4L)
                .status(Status.SENT)
                .requestText("Question")
                .dateOfCreation(LocalDate.of(2021, 10, 1))
                .user(user)
                .build();
        requestPage = new PageImpl<>(List.of(request1, request2));
        requestPage1 = new PageImpl<>(List.of(request2, request3));
    }

    @Test
    void save_shouldSaveAndReturnSavedRequest() {
        when(requestRepository.save(request1)).thenReturn(request1);
        Request savedRequest = requestService.save(request1);
        assertEquals(3L, savedRequest.getId());
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
        requestService.changeStatusDraftToSent(3L);
        verify(requestRepository, times(1)).save(request1);
        assertEquals(Status.SENT, request1.getStatus());
        assertEquals(textForOperator, request1.getRequestText());
    }

    @Test
    void findRequestsByUserId_shouldReturnPageOfRequests() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(requestRepository.findAllByUserOrderByDateOfCreationAsc(user, PageRequest.of(0, 5)))
                .thenReturn(requestPage);
        Page<Request> resultRequestPage = requestService.findRequestsByUserId(2L, Order.ASC, 0);
        assertEquals(1, resultRequestPage.getTotalPages());
        assertEquals(3L, resultRequestPage.getContent().get(0).getId());
    }


    @Test
    void findAllSentRequests_shouldReturnPageOfRequests() {
        when(requestRepository.findAllSentRequestsOrderByDateOfCreationAsc(PageRequest.of(0, 5)))
                .thenReturn(requestPage1);
        Page<Request> resultRequestPage = requestService.findAllSentRequests(Order.ASC, 0);
        assertEquals(1, resultRequestPage.getTotalPages());
        assertEquals(2L, resultRequestPage.getContent().get(0).getId());
    }

    @Test
    void findAllSentRequestsByUserName_shouldReturnPageOfRequests() {
        when(requestRepository.findSentRequestsByUserOrderByCreationAsc(user.getUserName(), PageRequest.of(0, 5)))
                .thenReturn(requestPage1);
        Page<Request> resultRequestPage = requestService.findAllSentRequestsByUserName("Anna", Order.ASC, 0);
        assertEquals(1, resultRequestPage.getTotalPages());
        assertEquals(2L, resultRequestPage.getContent().get(0).getId());
    }

    @Test
    void changeStatusSentToAccepted_shouldChangeStatusToAccepted() {
        when(requestRepository.findById(any())).thenReturn(Optional.of(request2));
        when(requestRepository.save(request2)).thenReturn(request2);
        requestService.changeStatusSentToAccepted(2L);
        verify(requestRepository, times(1)).save(request2);
        assertEquals(Status.ACCEPTED, request2.getStatus());
    }

    @Test
    void changeStatusSentToRejected_shouldChangeStatusToRejected() {
        when(requestRepository.findById(any())).thenReturn(Optional.of(request2));
        when(requestRepository.save(request2)).thenReturn(request2);
        requestService.changeStatusSentToRejected(2L);
        verify(requestRepository, times(1)).save(request2);
        assertEquals(Status.REJECTED, request2.getStatus());
    }
}