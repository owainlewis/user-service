package com.owainlewis.health;

import com.codahale.metrics.health.HealthCheck;

public class UserServiceHealthCheck extends HealthCheck {
    @Override
    protected Result check() {
        return Result.healthy();
    }
}
