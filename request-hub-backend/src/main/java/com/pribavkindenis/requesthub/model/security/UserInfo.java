package com.pribavkindenis.requesthub.model.security;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Hidden
@Builder
@Data
public class UserInfo implements UserDetails {

    private final Long userId;

    @Builder.Default
    private final String password = null;

    private final String username;

    private final Collection<? extends GrantedAuthority> authorities;

    @Builder.Default
    private final boolean accountNonExpired = true;

    @Builder.Default
    private final boolean accountNonLocked = true;

    @Builder.Default
    private final boolean credentialsNonExpired = true;

    @Builder.Default
    private final boolean enabled = true;

}
