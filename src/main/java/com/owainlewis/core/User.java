package com.owainlewis.core;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class User {

    private long id;

    private final String firstName;

    private final String lastName;

    private final String email;

    public User(long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
