package com.pribavkindenis.requesthub.service;

import com.pribavkindenis.requesthub.config.security.JwtTokenService;
import com.pribavkindenis.requesthub.model.dto.AuthenticationDto;
import com.pribavkindenis.requesthub.model.security.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public Cookie authenticate(AuthenticationDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var user = (UserInfo) authentication.getPrincipal();
        var token = jwtTokenService.buildToken(user);
        var tokenCookie = new Cookie(JwtTokenService.TOKEN_COOKIE, token);
        tokenCookie.setMaxAge(jwtTokenService.getCookieExpireSeconds());
        return tokenCookie;
    }

}
