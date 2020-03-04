package com.pribavkindenis.requesthub.annotation.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority(T(com.pribavkindenis.requesthub.model.enumerate.Authority).UPDATE_REQUEST)")
public @interface CanUpdateRequest { }