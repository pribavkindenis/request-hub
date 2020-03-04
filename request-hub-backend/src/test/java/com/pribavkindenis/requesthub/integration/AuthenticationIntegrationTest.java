package com.pribavkindenis.requesthub.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pribavkindenis.requesthub.config.security.JwtTokenService;
import com.pribavkindenis.requesthub.integration.advice.GlobalExceptionHandler;
import com.pribavkindenis.requesthub.model.dto.ApiErrorResponse;
import com.pribavkindenis.requesthub.model.dto.AuthenticationDto;
import com.pribavkindenis.requesthub.model.enumerate.ApiError;
import com.pribavkindenis.requesthub.model.enumerate.Authority;
import com.pribavkindenis.requesthub.model.jpa.Privilege;
import com.pribavkindenis.requesthub.model.jpa.Role;
import com.pribavkindenis.requesthub.model.jpa.User;
import com.pribavkindenis.requesthub.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(authenticationController)
                             .setControllerAdvice(globalExceptionHandler)
                             .build();
        userRepository.saveAll(buildUsers());
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Transactional
    @Test
    public void successfulAuthenticationTest() throws Exception {
        // given
        var authenticationDto = buildAuthenticationDto("admin", "admin");
        var content = objectMapper.writeValueAsString(authenticationDto);

        // when
        var result = mvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                        .andReturn();
        var response = result.getResponse();
        var tokenCookie = response.getCookie(JwtTokenService.TOKEN_COOKIE);

        // then
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertNotNull(tokenCookie);
    }

    @Transactional
    @Test
    public void failedAuthenticationTest() throws Exception {
        // given
        var authenticationDto = buildAuthenticationDto("samsepi0l", "420-nice-69");
        var content = objectMapper.writeValueAsString(authenticationDto);

        // when
        var result = mvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                        .andReturn();
        var response = result.getResponse();
        var apiErrorResponse = buildApiErrorResponse(response);

        // then
        Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), apiErrorResponse.getStatus());
        Assert.assertEquals(ApiError.UNAUTHORIZED, apiErrorResponse.getError());
    }

    @Transactional
    @Test
    public void invalidJsonAuthenticationTest() throws Exception {
        // given
        var invalidJson = "I AM JSON, DUDE, BELIEVE ME";

        // when
        var result = mvc.perform(post("/api/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                        .andReturn();
        var response = result.getResponse();
        var apiErrorResponse = buildApiErrorResponse(response);

        // then
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), apiErrorResponse.getStatus());
        Assert.assertEquals(ApiError.INVALID_JSON, apiErrorResponse.getError());
    }

    @Transactional
    @Test
    public void unprocessableJsonAuthenticationTest() throws Exception {
        // given
        var unprocessableJson = generateUnprocessableJson();

        // when
        var result = mvc.perform(post("/api/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(unprocessableJson))
                        .andReturn();
        var response = result.getResponse();
        var apiErrorResponse = buildApiErrorResponse(response);

        // then
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
        Assert.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), apiErrorResponse.getStatus());
        Assert.assertEquals(ApiError.UNPROCESSABLE_JSON, apiErrorResponse.getError());
    }

    private String generateUnprocessableJson() {
        var password = generateRandomPassword(1984);
        return String.format("{\"password\":\"%s\"}", password);
    }

    private String generateRandomPassword(long length) {
        return new Random().ints('a', 'z')
                           .limit(length)
                           .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                           .toString();
    }

    private ApiErrorResponse buildApiErrorResponse(MockHttpServletResponse response)
            throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(response.getContentAsString(), ApiErrorResponse.class);
    }

    private List<User> buildUsers() {
        var createRequest = buildPrivilege(Authority.CREATE_REQUEST);
        var readRequest = buildPrivilege(Authority.READ_REQUEST);
        var updateRequest = buildPrivilege(Authority.UPDATE_REQUEST);
        var deleteRequest = buildPrivilege(Authority.DELETE_REQUEST);

        var userRole = buildRole(Authority.ROLE_USER, createRequest, readRequest);
        var adminRole = buildRole(Authority.ROLE_ADMIN, updateRequest, deleteRequest);

        var admin = buildUser("admin", "admin", userRole, adminRole);
        var user = buildUser("samsepi0l", "MrRobot", userRole, adminRole);

        return Arrays.asList(admin, user);
    }

    private Privilege buildPrivilege(Authority authority) {
        return Privilege.builder()
                        .privilege(authority)
                        .build();
    }

    private Role buildRole(Authority authority, Privilege... privileges) {
        return Role.builder()
                   .role(authority)
                   .privileges(Arrays.asList(privileges))
                   .build();
    }

    private User buildUser(String username, String password, Role... roles) {
        var encodedPassword = passwordEncoder.encode(password);
        return User.builder()
                   .username(username)
                   .password(encodedPassword)
                   .roles(Arrays.asList(roles))
                   .build();
    }

    private AuthenticationDto buildAuthenticationDto(String username, String password) {
        return AuthenticationDto.builder()
                                .username(username)
                                .password(password)
                                .build();
    }

}
