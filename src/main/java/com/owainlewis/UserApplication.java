package com.owainlewis;

import com.owainlewis.db.MySQLUserDAO;
import com.owainlewis.resources.UserResource;
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
    public void run(final UserConfiguration configuration,
                    final Environment environment) {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        final MySQLUserDAO dao = new MySQLUserDAO(jdbi);

        environment.jersey().register(new UserResource(dao));
    }
}
