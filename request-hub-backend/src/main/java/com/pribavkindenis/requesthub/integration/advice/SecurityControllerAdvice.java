package com.pribavkindenis.requesthub.integration.advice;

import com.pribavkindenis.requesthub.model.security.UserInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class SecurityControllerAdvice {

    @ModelAttribute
    public UserInfo specificPrincipal(Authentication a) {
        return a instanceof UsernamePasswordAuthenticationToken
                ? (UserInfo) a.getPrincipal()
                : null;
    }

}
