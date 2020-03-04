package com.pribavkindenis.requesthub.service;

import com.pribavkindenis.requesthub.config.security.JwtTokenService;
import com.pribavkindenis.requesthub.model.dto.Authentication;
import com.pribavkindenis.requesthub.model.security.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public String authenticate(Authentication dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var user = (UserInfo) authentication.getPrincipal();
        return jwtTokenService.buildToken(user);
    }

}
