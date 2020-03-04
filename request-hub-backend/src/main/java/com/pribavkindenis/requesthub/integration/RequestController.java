package com.pribavkindenis.requesthub.integration;

import com.pribavkindenis.requesthub.annotation.security.CanCreateRequest;
import com.pribavkindenis.requesthub.annotation.security.CanDeleteRequest;
import com.pribavkindenis.requesthub.annotation.security.CanReadRequest;
import com.pribavkindenis.requesthub.annotation.security.CanUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/request")
@RestController
public class RequestController {

    @CanCreateRequest
    @PostMapping
    public String create() {
        return "create";
    }

    @CanReadRequest
    @GetMapping
    public String read() {
        return "read";
    }

    @CanUpdateRequest
    @PutMapping
    public String update() {
        return "update";
    }

    @CanDeleteRequest
    @DeleteMapping
    public String delete() {
        return "delete";
    }

}
