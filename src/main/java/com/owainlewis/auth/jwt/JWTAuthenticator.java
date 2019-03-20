package com.owainlewis.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.owainlewis.models.AuthConfig;
import io.dropwizard.auth.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public final class JWTAuthenticator implements Authenticator<String, AccessTokenPrincipal> {

    private AuthConfig authConfig;

    private final Logger LOG = LoggerFactory.getLogger(JWTAuthenticator.class);

    public JWTAuthenticator(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    @Override
    public Optional<AccessTokenPrincipal> authenticate(String accessToken)  {
        LOG.info("Access token {}", accessToken);

        try {
            Algorithm algorithm = Algorithm.HMAC256(authConfig.getSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("forward")
                    .build();
            DecodedJWT jwt = verifier.verify(accessToken);
            return Optional.of(new AccessTokenPrincipal(jwt));
        } catch (JWTVerificationException exception){
            LOG.error("Failed to authenticate user", exception);
            return Optional.empty();
        }
    }
}
