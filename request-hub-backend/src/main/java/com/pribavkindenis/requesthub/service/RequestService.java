package com.pribavkindenis.requesthub.service;

import com.pribavkindenis.requesthub.exception.RequestNotBelongToUserException;
import com.pribavkindenis.requesthub.mapper.Mapper;
import com.pribavkindenis.requesthub.model.dto.RequestDto;
import com.pribavkindenis.requesthub.model.jpa.Request;
import com.pribavkindenis.requesthub.model.jpa.User;
import com.pribavkindenis.requesthub.repository.RequestRepository;
import com.pribavkindenis.requesthub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class RequestService {

    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final Mapper<Request, RequestDto> mapper;

    @Transactional
    public RequestDto createRequest(RequestDto requestDto, Long userId) {
        var user = findUserById(userId);
        var request = mapper.dtoToEntity(requestDto);
        request.setUser(user);
        return mapper.entityToDto(requestRepository.save(request));
    }

    public RequestDto readRequest(Long requestId, Long userId) {
        var request = findRequestById(requestId);
        checkRequestBelongToUser(request, userId);
        return mapper.entityToDto(request);
    }

    public RequestDto updateRequest(Long requestId, RequestDto requestDto) {
        var request = findRequestById(requestId);
        mapper.updateEntity(request, requestDto);
        return mapper.entityToDto(requestRepository.save(request));
    }

    public RequestDto deleteRequest(Long requestId) {
        var request = findRequestById(requestId);
        requestRepository.delete(request);
        return mapper.entityToDto(request);
    }

    private void checkRequestBelongToUser(Request request, Long userId) {
        if (isRequestNotBelongToUser(request, userId)) {
            throw new RequestNotBelongToUserException(request.getId(), userId);
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findUserById(userId)
                             .orElseThrow(() -> userNotFoundById(userId));
    }

    private Request findRequestById(Long requestId) {
        return requestRepository.findById(requestId)
                                .orElseThrow(() -> requestNotFoundById(requestId));
    }

    private boolean isRequestNotBelongToUser(Request request, Long userId) {
        return !securityUtils.isAdmin() && !userId.equals(request.getUser().getId());
    }

    private EntityNotFoundException requestNotFoundById(Long id) {
        return new EntityNotFoundException(String.format("Request not found by id: %s", id));
    }

    private EntityNotFoundException userNotFoundById(Long id) {
        return new EntityNotFoundException(String.format("User not found by id: %s", id));
    }

}
