package com.pribavkindenis.requesthub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authentication {

    @Size(min = 1, max = 255)
    @NotBlank
    private String username;

    @Size(min = 1, max = 255)
    @NotBlank
    private String password;

}
