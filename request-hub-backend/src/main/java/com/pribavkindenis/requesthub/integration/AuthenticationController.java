package com.pribavkindenis.requesthub.integration;

import com.pribavkindenis.requesthub.model.dto.Authentication;
import com.pribavkindenis.requesthub.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/authenticate")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<String> authenticate(@Valid @RequestBody Authentication dto) {
        return new ResponseEntity<>(authenticationService.authenticate(dto), HttpStatus.OK);
    }

}
