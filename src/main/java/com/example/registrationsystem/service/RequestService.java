package com.example.registrationsystem.service;

import com.example.registrationsystem.models.Request;
import com.example.registrationsystem.models.User;
import com.example.registrationsystem.models.enums.Order;
import org.springframework.data.domain.Page;

/**
 * Service that works with {@link Request}
 */
public interface RequestService {

    /**
     * Method that saves new request
     * @param request {@link Request}
     * @return {@link Request}
     */
    Request save(Request request);

    /**
     * Method that edits request
     * @param request {@link Request}
     * @return {@link Request}
     */
    Request editRequestDraft(Request request);

    /**
     * Method that changes status DRAFT to SENT
     * @param requestId from {@link Request}
     */
    void changeStatusDraftToSent(long requestId);

    /**
     * Method that finds all requests of user
     * @param userId from {@link User}
     * @param order {@link Order}
     * @param page page number
     * @return {@link Page} of {@link Request}
     */
    Page<Request> findRequestsByUserId(long userId, Order order, int page);

    /**
     * Method that finds all requests with status SENT
     * @param order {@link Order}
     * @param page page number
     * @return {@link Page} of {@link Request}
     */
    Page<Request> findAllSentRequests(Order order, int page);

    /**
     * Method that finds all requests with status SENT by username or part of username
     * @param userName from {@link User}
     * @param order {@link Order}
     * @param page page number
     * @return {@link Page} of {@link Request}
     */
    Page<Request> findAllSentRequestsByUserName(String userName, Order order, int page);

    /**
     * Method that changes request status from SENT to ACCEPTED
     * @param requestId from {@link Request}
     */
    void changeStatusSentToAccepted(long requestId);

    /**
     * Method that changes request status from SENT to REJECTED
     * @param requestId from {@link Request}
     */
    void changeStatusSentToRejected(long requestId);
}
