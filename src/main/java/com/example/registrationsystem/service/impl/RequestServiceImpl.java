package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.models.Request;
import com.example.registrationsystem.models.User;
import com.example.registrationsystem.models.enums.Order;
import com.example.registrationsystem.models.enums.Status;
import com.example.registrationsystem.repository.RequestRepository;
import com.example.registrationsystem.repository.UserRepository;
import com.example.registrationsystem.service.RequestService;
import com.example.registrationsystem.util.exception.CanNotChangeStatusException;
import com.example.registrationsystem.util.exception.CanNotUpdateRequestException;
import com.example.registrationsystem.util.exception.InvalidPageIndexException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RequestService}.
 * Works with {@link RequestRepository} and {@link UserRepository}.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {

    private RequestRepository requestRepository;
    private UserRepository userRepository;

    /**
     * {@inheritDoc}
     * Uses method {@link RequestRepository#save(Object)}
     * @param request {@link Request}
     * @return {@link Request}
     */
    @Override
    @Transactional
    public Request save(Request request) {
        log.info("Request to save new request to database");
        request.setStatus(Status.DRAFT);
        request.setDateOfCreation(LocalDate.now());
        Request savedRequest = requestRepository.save(request);
        log.info("Return the saved request");
        return savedRequest;
    }

    /**
     * {@inheritDoc}
     * Uses method {@link RequestRepository#save(Object)}
     * @param request {@link Request}
     * @return {@link Request}
     */
    @Override
    @Transactional
    public Request editRequestDraft(Request request) {
        log.info("Request to get request with id {} from database", request.getId());
        Request existRequest = requestRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Request not found by id = " + request.getId()));
        if (existRequest.getStatus().equals(Status.DRAFT)) {
            existRequest.setRequestText(request.getRequestText());
            log.info("Request to save updated request to database");
            return requestRepository.save(existRequest);
        } else {
            log.error("Can not edit request because status is not DRAFT");
            throw new CanNotUpdateRequestException();
        }
    }

    /**
     * {@inheritDoc}
     * Uses method {@link RequestRepository#save(Object)}
     * and add '-' after all letters in request text
     * @param requestId from {@link Request}
     */
    @Override
    @Transactional
    public void changeStatusDraftToSent(long requestId) {
        log.info("Request to get request with id {} from database", requestId);
        Request existRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found by id = " + requestId));
        String requestText = existRequest.getRequestText();
        String newText = Arrays.stream(requestText.split(""))
                .map(x -> x + '-')
                .collect(Collectors.joining())
                .replaceFirst(".$", "");
        if (existRequest.getStatus().equals(Status.DRAFT)) {
            existRequest.setStatus(Status.SENT);
            existRequest.setRequestText(newText);
            log.info("Request to save updated request to database");
            requestRepository.save(existRequest);
        } else {
            log.error("Can not change request status");
            throw new CanNotChangeStatusException();
        }
    }

    /**
     * {@inheritDoc}
     * Uses method {@link RequestRepository#findAllByUserOrderByDateOfCreationAsc}
     * or {@link RequestRepository#findAllByUserOrderByDateOfCreationDesc}
     * @param userId from {@link User}
     * @param order  {@link Order}
     * @param page   page number
     * @return {@link Page} of {@link Request}
     */
    @Override
    public Page<Request> findRequestsByUserId(long userId, Order order, int page) {
        log.info("Request for user with id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id = " + userId));
        PageRequest pageRequest = PageRequest.of(page, 5);
        log.info("Request for page of requests");
        Page<Request> pageableRequests =
                order == Order.ASC
                        ? requestRepository.findAllByUserOrderByDateOfCreationAsc(user, pageRequest)
                        : requestRepository.findAllByUserOrderByDateOfCreationDesc(user, pageRequest);
        if (page < 0 || page >= pageableRequests.getTotalPages()) {
            log.error("Invalid page");
            throw new InvalidPageIndexException();
        }
        log.info("Return page {} of requests", page);
        return pageableRequests;
    }

    /**
     * {@inheritDoc}
     * Uses method {@link RequestRepository#findAllSentRequestsOrderByDateOfCreationAsc}
     * or {@link RequestRepository#findAllSentRequestsOrderByDateOfCreationDesc}
     * @param order {@link Order}
     * @param page  page number
     * @return {@link Page} of {@link Request}
     */
    @Override
    public Page<Request> findAllSentRequests(Order order, int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        log.info("Request for page of requests");
        Page<Request> pageableRequests =
                order == Order.ASC
                        ? requestRepository.findAllSentRequestsOrderByDateOfCreationAsc(pageRequest)
                        : requestRepository.findAllSentRequestsOrderByDateOfCreationDesc(pageRequest);
        if (page < 0 || page >= pageableRequests.getTotalPages()) {
            log.error("Invalid page");
            throw new InvalidPageIndexException();
        }
        log.info("Return page {} of requests", page);
        return pageableRequests;
    }

    /**
     * {@inheritDoc}
     * Uses method {@link RequestRepository#findSentRequestsByUserOrderByCreationAsc}
     * or {@link RequestRepository#findSentRequestsByUserOrderByCreationDesc}
     * @param userName from {@link User}
     * @param order    {@link Order}
     * @param page     page number
     * @return {@link Page} of {@link Request}
     */
    @Override
    public Page<Request> findAllSentRequestsByUserName(String userName, Order order, int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        log.info("Request for page of requests");
        Page<Request> pageableRequests =
                order == Order.ASC
                        ? requestRepository.findSentRequestsByUserOrderByCreationAsc(userName, pageRequest)
                        : requestRepository.findSentRequestsByUserOrderByCreationDesc(userName, pageRequest);
        if (page < 0 || page >= pageableRequests.getTotalPages()) {
            log.error("Invalid page");
            throw new InvalidPageIndexException();
        }
        log.info("Return page {} of requests", page);
        return pageableRequests;
    }

    /**
     * {@inheritDoc}
     * Uses method {@link RequestRepository#save(Object)}
     * @param requestId from {@link Request}
     */
    @Override
    @Transactional
    public void changeStatusSentToAccepted(long requestId) {
        log.info("Request to get request with id {} from database", requestId);
        Request existRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found by id = " + requestId));
        if (existRequest.getStatus().equals(Status.SENT)) {
            existRequest.setStatus(Status.ACCEPTED);
            log.info("Request to save updated request to database");
            requestRepository.save(existRequest);
        } else {
            log.error("Can not change request status");
            throw new CanNotChangeStatusException();
        }
    }

    /**
     * {@inheritDoc}
     * Uses method {@link RequestRepository#save(Object)}
     * @param requestId from {@link Request}
     */
    @Override
    @Transactional
    public void changeStatusSentToRejected(long requestId) {
        log.info("Request to get request with id {} from database", requestId);
        Request existRequest = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Request not found by id = " + requestId));
        if (existRequest.getStatus().equals(Status.SENT)) {
            existRequest.setStatus(Status.REJECTED);
            log.info("Request to save updated request to database");
            requestRepository.save(existRequest);
        } else {
            log.error("Can not change request status");
            throw new CanNotChangeStatusException();
        }
    }
}
