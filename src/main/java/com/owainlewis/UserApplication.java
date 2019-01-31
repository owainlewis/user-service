package com.owainlewis;

import com.owainlewis.health.ServiceHealthCheck;
import com.owainlewis.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class UserApplication extends Application<UserConfiguration> {

    public static void main(final String[] args) throws Exception {
        new UserApplication().run(args);
    }

    @Override
    public String getName() {
        return "UserService";
    }

    @Override
    public void initialize(final Bootstrap<UserConfiguration> bootstrap) {
    }

    @Override
    public void run(final UserConfiguration configuration,
                    final Environment environment) {

        environment.healthChecks().register("ServiceCheck", new ServiceHealthCheck());
        environment.jersey().register(new UserResource());
    }
}
