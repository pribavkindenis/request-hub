package com.pribavkindenis.requesthub.model.enumerate;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {

    // Roles
    ROLE_ADMIN,
    ROLE_USER,

    // Privileges
    CREATE_REQUEST,
    READ_REQUEST,
    UPDATE_REQUEST,
    DELETE_REQUEST;

    @Override
    public String getAuthority() {
        return name();
    }

}
