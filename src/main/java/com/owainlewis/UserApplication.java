package com.owainlewis;

import com.owainlewis.auth.jwt.AccessTokenPrincipal;
import com.owainlewis.auth.jwt.JWTAuthenticator;
import com.owainlewis.db.UserDAO;
import com.owainlewis.health.UserServiceHealthCheck;
import com.owainlewis.models.AuthConfig;
import com.owainlewis.resources.LoginResource;
import com.owainlewis.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public final class UserApplication extends Application<ApplicationConfiguration> {

    public static void main(final String[] args) throws Exception {
        new UserApplication().run(args);
    }

    @Override
    public String getName() {
        return "UserService";
    }

    @Override
    public void initialize(final Bootstrap<ApplicationConfiguration> bootstrap) {
    }

    @Override
    public void run(final ApplicationConfiguration configuration, final Environment environment) {
        registerResources(configuration, environment);
    }

    private UserDAO buildDAO(ApplicationConfiguration configuration, Environment environment) {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        return jdbi.onDemand(UserDAO.class);
    }

    private void registerResources(ApplicationConfiguration configuration, Environment environment) {
        final UserDAO dao = buildDAO(configuration, environment);
        final AuthConfig authConfig = configuration.getAuth();

        environment.healthChecks().register("serviceRunning", new UserServiceHealthCheck());

        configureJWTAUth(configuration, environment);

        environment.jersey().register(new UserResource(dao));
        environment.jersey().register(new LoginResource(authConfig));
    }

    /**
     * Configure a JWT authenticator that will be used to restrict access to our API
     *
     * @param configuration Dropwizard configuration
     * @param environment Dropwizard environment
     */
    private void configureJWTAUth(ApplicationConfiguration configuration, Environment environment) {
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<AccessTokenPrincipal>()
                        .setAuthenticator(new JWTAuthenticator(configuration.getAuth()))
                        .setPrefix("Bearer")
                        .buildAuthFilter()));

        // Bind our custom principal to the @Auth annotation
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(AccessTokenPrincipal.class));
    }
}
