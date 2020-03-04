package com.pribavkindenis.requesthub.model.enumerate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum ApiError {

    UNEXPECTED("Unexpected error"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    BAD_REQUEST("Bad request"),
    INVALID_JSON("Invalid json syntax"),
    UNPROCESSABLE_JSON("Unprocessable json object"),
    ENTITY_NOT_FOUND("Entity not found");

    private static final Map<String, ApiError> BY_NAME;

    static {
        BY_NAME = new HashMap<>();
        for (var error: ApiError.values()) {
            BY_NAME.put(error.name, error);
        }
    }

    @JsonValue
    public String name;

    @JsonCreator
    public ApiError errorByName(String name) {
        return BY_NAME.getOrDefault(name, null);
    }

}
