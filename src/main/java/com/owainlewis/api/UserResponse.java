package com.owainlewis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse<T> {
    private long code;

    private T response;

    public UserResponse() {
    }

    public UserResponse(long code, T response) {
        this.code = code;
        this.response = response;
    }

    @JsonProperty
    public long getCode() {
        return code;
    }

    @JsonProperty
    public T getResponse() {
        return response;
    }
}
