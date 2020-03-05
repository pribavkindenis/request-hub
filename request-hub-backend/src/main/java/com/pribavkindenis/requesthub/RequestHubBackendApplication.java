package com.pribavkindenis.requesthub;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "RequestHub",
        version = "0.0",
        description = "RequestHub simple API"
))
@SpringBootApplication
public class RequestHubBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RequestHubBackendApplication.class, args);
    }

}
