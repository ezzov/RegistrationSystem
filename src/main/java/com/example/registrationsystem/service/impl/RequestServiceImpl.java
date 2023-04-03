package com.example.registrationsystem.service.impl;

import com.example.registrationsystem.models.Request;
import com.example.registrationsystem.models.User;
import com.example.registrationsystem.models.enums.Order;
import com.example.registrationsystem.models.enums.Status;
import com.example.registrationsystem.repository.RequestRepository;
import com.example.registrationsystem.repository.UserRepository;
import com.example.registrationsystem.service.RequestService;
import com.example.registrationsystem.util.exception.CanNotSendForReviewException;
import com.example.registrationsystem.util.exception.CanNotUpdateRequestException;
import com.example.registrationsystem.util.exception.InvalidPageIndexException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Request save(Request request) {
        request.setStatus(Status.DRAFT);
        request.setDateOfCreation(LocalDate.now());
        Request savedRequest = requestRepository.save(request);
        return savedRequest;
    }

    @Override
    @Transactional
    public Request editRequestDraft(Request request) {
        Request existRequest = requestRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Request not found by id = " + request.getId()));
        if (existRequest.getStatus().equals(Status.DRAFT)) {
            existRequest.setRequestText(request.getRequestText());
            return requestRepository.save(existRequest);
        } else {
            throw new CanNotUpdateRequestException();
        }
    }

    @Override
    @Transactional
    public void changeStatusDraftToSent(long requestId) {
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
            requestRepository.save(existRequest);
        } else {
            throw new CanNotSendForReviewException();
        }
    }

    @Override
    public Page<Request> findRequestsByUserId(long userId, Order order, int page) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id = " + userId));
        PageRequest pageRequest = PageRequest.of(page, 5);
        Page<Request> pagebleRequests =
                order == Order.ASC
                        ? requestRepository.findAllByUserOrderByDateOfCreationAsc(user, pageRequest)
                        : requestRepository.findAllByUserOrderByDateOfCreationDesc(user, pageRequest);
        if (page >= pagebleRequests.getTotalPages()) {
            throw new InvalidPageIndexException();
        }
        return pagebleRequests;
    }
}
