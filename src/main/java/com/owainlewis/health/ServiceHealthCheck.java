package com.owainlewis.health;

import com.codahale.metrics.health.HealthCheck;

public class ServiceHealthCheck extends HealthCheck {

    public ServiceHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
