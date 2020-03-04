package com.pribavkindenis.requesthub.config.security;

import com.pribavkindenis.requesthub.model.enumerate.Authority;
import com.pribavkindenis.requesthub.model.security.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@PropertySource("classpath:jwt.properties")
@Service
public class JwtTokenService {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORITIES_CLAIM = "auth";
    private static final String USER_ID_CLAIM = "uid";

    @Value("${jwt.token.secret}")
    private String jwtSecret;

    @Value("${jwt.token.issuer}")
    private String tokenIssuer;

    @Value("${jwt.token.audience}")
    private String TokenAudience;

    @Value("${jwt.token.expire-millis}")
    private Long expireMillis;

    public String buildToken(UserInfo userInfo) {
        var userId = userInfo.getUserId();
        var username = userInfo.getUsername();
        var authoritiesNames = retrieveAuthoritiesNames(userInfo);
        return buildTokenInternal(userId, username, authoritiesNames);
    }

    private List<String> retrieveAuthoritiesNames(UserInfo userInfo) {
        return userInfo.getAuthorities().stream()
                       .map(GrantedAuthority::getAuthority)
                       .collect(Collectors.toList());
    }

    private String buildTokenInternal(Long userId, String username, List<String> authoritiesNames) {
        var sign = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return TOKEN_PREFIX + Jwts.builder()
                                  .signWith(sign, SignatureAlgorithm.HS512)
                                  .setHeaderParam("typ", "JWT")
                                  .setIssuer(tokenIssuer)
                                  .setAudience(TokenAudience)
                                  .setSubject(username)
                                  .setExpiration(getExpiration())
                                  .claim(AUTHORITIES_CLAIM, authoritiesNames)
                                  .claim(USER_ID_CLAIM, userId)
                                  .compact();
    }

    private Date getExpiration() {
        return Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + expireMillis));
    }

    public Authentication parseToken(String token) {
        Authentication authentication = null;
        if (isTokenValid(token)) {
            var parsedToken = parseTokenInternal(token);
            var userId = parsedToken.getBody().get(USER_ID_CLAIM, Long.class);
            var username = parsedToken.getBody().getSubject();
            var authorities = retrieveAuthorities(parsedToken);
            var userInfo = buildUserInfo(userId, username, authorities);
            authentication = new UsernamePasswordAuthenticationToken(userInfo, null, authorities);
        }
        return authentication;
    }

    private boolean isTokenValid(String token) {
        return StringUtils.isNotBlank(token) && token.startsWith(TOKEN_PREFIX);
    }

    private Jws<Claims> parseTokenInternal(String token) {
        return Jwts.parser()
                   .setSigningKey(jwtSecret.getBytes())
                   .parseClaimsJws(token.replace(TOKEN_PREFIX, ""));
    }

    private Set<? extends GrantedAuthority> retrieveAuthorities(Jws<Claims> token) {
        @SuppressWarnings("all") var authoritiesNames = (List<String>) token.getBody().get(AUTHORITIES_CLAIM);
        return authoritiesNames.stream()
                               .map(Authority::valueOf)
                               .collect(Collectors.toSet());
    }

    private UserInfo buildUserInfo(Long userId, String username, Collection<? extends GrantedAuthority> authorities) {
        return UserInfo.builder()
                       .userId(userId)
                       .username(username)
                       .authorities(authorities)
                       .build();
    }

}
