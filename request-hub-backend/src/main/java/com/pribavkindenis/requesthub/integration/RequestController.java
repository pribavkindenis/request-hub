package com.pribavkindenis.requesthub.integration;

import com.pribavkindenis.requesthub.annotation.security.CanCreateRequest;
import com.pribavkindenis.requesthub.annotation.security.CanDeleteRequest;
import com.pribavkindenis.requesthub.annotation.security.CanReadRequest;
import com.pribavkindenis.requesthub.annotation.security.CanUpdateRequest;
import com.pribavkindenis.requesthub.model.dto.RequestDto;
import com.pribavkindenis.requesthub.model.security.UserInfo;
import com.pribavkindenis.requesthub.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/request")
@RestController
public class RequestController {

    private final RequestService requestService;

    @CanCreateRequest
    @PostMapping
    public RequestDto createRequest(@Valid @RequestBody RequestDto requestDto,
                                    @ModelAttribute UserInfo userInfo) {
        return requestService.createRequest(requestDto, userInfo.getUserId());
    }

    @CanReadRequest
    @GetMapping("/{id}")
    public RequestDto readRequest(@PathVariable("id") Long requestId,
                                  @ModelAttribute UserInfo userInfo) {
        return requestService.readRequest(requestId, userInfo.getUserId());
    }

    @CanUpdateRequest
    @PutMapping("/{id}")
    public RequestDto updateRequest(@PathVariable("id") Long requestId,
                                    @Valid @RequestBody RequestDto requestDto) {
        return requestService.updateRequest(requestId, requestDto);
    }

    @CanDeleteRequest
    @DeleteMapping("/{id}")
    public RequestDto deleteRequest(@PathVariable("id") Long requestId) {
        return requestService.deleteRequest(requestId);
    }

}
