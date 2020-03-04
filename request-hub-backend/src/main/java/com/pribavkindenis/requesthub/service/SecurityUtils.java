package com.pribavkindenis.requesthub.service;

import com.pribavkindenis.requesthub.model.enumerate.Authority;
import com.pribavkindenis.requesthub.model.security.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecurityUtils {

    public boolean isAdmin() {
        var userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userInfo.getAuthorities().contains(Authority.ROLE_ADMIN);
    }

}
