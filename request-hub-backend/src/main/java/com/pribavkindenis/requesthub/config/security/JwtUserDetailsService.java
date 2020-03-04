package com.pribavkindenis.requesthub.config.security;

import com.pribavkindenis.requesthub.model.jpa.Privilege;
import com.pribavkindenis.requesthub.model.jpa.Role;
import com.pribavkindenis.requesthub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                                 .orElseThrow(() -> new UsernameNotFoundException(username));
        var authorities = user.getRoles().stream()
                              .map(Role::getPrivileges)
                              .flatMap(List::stream)
                              .map(Privilege::getPrivilege)
                              .collect(Collectors.toSet());
        return User.builder()
                   .username(user.getUsername())
                   .password(user.getPassword())
                   .authorities(authorities)
                   .accountExpired(false)
                   .accountLocked(false)
                   .credentialsExpired(false)
                   .disabled(false)
                   .build();
    }

}
