package com.owainlewis.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class User {

    @JsonProperty
    private long id;

    @JsonProperty
    private String first;

    @JsonProperty
    private String last;

    @JsonProperty
    private String email;

    public User() {

    }

    public User(long id, String first, String last, String email) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.email = email;
    }

    public User(String first, String last, String email) {
        this.first = first;
        this.last = last;
        this.email = email;
    }
}
