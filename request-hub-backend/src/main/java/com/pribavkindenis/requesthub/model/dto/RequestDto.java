package com.pribavkindenis.requesthub.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @Size(min = 1, max = 100)
    @NotNull
    @JsonProperty("header")
    private String header;

    @Size(min = 1, max = 500)
    @NotNull
    @JsonProperty("description")
    private String description;

}
