package com.owainlewis;

import com.owainlewis.auth.jwt.JWTAuthenticationFilter;
import com.owainlewis.db.UserDAO;
import com.owainlewis.health.UserServiceHealthCheck;
import com.owainlewis.resources.UserResource;
import com.sun.tools.doclint.Env;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

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
    public void run(final UserConfiguration configuration, final Environment environment) {
        UserDAO dao = buildDAO(configuration, environment);
        registerResources(environment, dao);
    }

    private UserDAO buildDAO(UserConfiguration configuration, Environment environment) {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        return jdbi.onDemand(UserDAO.class);
    }

    private void registerResources(Environment environment, UserDAO dao) {
        environment.healthChecks().register("running", new UserServiceHealthCheck());
        environment.jersey().register(JWTAuthenticationFilter.class);
        environment.jersey().register(new UserResource(dao));
    }
}
