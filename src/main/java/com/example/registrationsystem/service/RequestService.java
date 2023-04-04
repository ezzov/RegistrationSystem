package com.example.registrationsystem.service;

import com.example.registrationsystem.models.Request;
import com.example.registrationsystem.models.enums.Order;
import org.springframework.data.domain.Page;

public interface RequestService {

    Request save(Request request);

    Request editRequestDraft(Request request);

    void changeStatusDraftToSent(long requestId);

    Page<Request> findRequestsByUserId(long userId, Order order, int page);

    Page<Request> findAllSentRequests(Order order, int page);

    Page<Request> findAllSentRequestsByUserName(String userName, Order order, int page);

    void changeStatusSentToAccepted(long requestId);

    void changeStatusSentToRejected(long requestId);
}
