package com.pribavkindenis.requesthub.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name = "Request", description = "Object representing request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("id")
    private Long id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
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
