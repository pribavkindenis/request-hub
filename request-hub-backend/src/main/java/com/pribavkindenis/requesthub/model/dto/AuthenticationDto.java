package com.pribavkindenis.requesthub.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Schema(name = "Credentials", description = "Username and password allowing to authenticate")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto {

    @Size(min = 1, max = 255)
    @NotBlank
    private String username;

    @Size(min = 1, max = 255)
    @NotBlank
    private String password;

}
