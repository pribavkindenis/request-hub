package com.pribavkindenis.requesthub.integration;

import com.pribavkindenis.requesthub.model.dto.AuthenticationDto;
import com.pribavkindenis.requesthub.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "Authentication controller", description = "Controller allowing a user to authenticate and get JWT token")
@RequiredArgsConstructor
@RequestMapping("/api/authenticate")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Authentication", description = "Performs an authentication process")
    @PostMapping
    public ResponseEntity<String> authenticate(@Valid @RequestBody AuthenticationDto dto,
                                               HttpServletResponse response) {
        response.addCookie(authenticationService.authenticate(dto));
        return ResponseEntity.ok("OK");
    }

}
