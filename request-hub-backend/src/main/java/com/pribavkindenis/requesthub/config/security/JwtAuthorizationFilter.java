package com.pribavkindenis.requesthub.config.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenService jwtTokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtTokenService jwtTokenService) {
        super(authenticationManager);
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        var tokenCookie = WebUtils.getCookie(request, JwtTokenService.TOKEN_COOKIE);
        if (tokenCookie != null) {
            var token = tokenCookie.getValue();
            var authentication = jwtTokenService.parseToken(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

}
