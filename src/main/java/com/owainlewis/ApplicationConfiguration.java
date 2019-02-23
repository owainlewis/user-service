package com.owainlewis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.owainlewis.models.AuthConfig;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

final class ApplicationConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @NotNull
    @Getter
    @JsonProperty("auth")
    public AuthConfig auth;
}
