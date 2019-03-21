package com.owainlewis.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.owainlewis.models.AuthConfig;
import io.dropwizard.auth.Authenticator;
import lombok.extern.slf4j.Slf4j;
import java.util.Optional;

@Slf4j
public final class JWTAuthenticator implements Authenticator<String, AccessTokenPrincipal> {

    private AuthConfig authConfig;

    public JWTAuthenticator(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    @Override
    public Optional<AccessTokenPrincipal> authenticate(String accessToken)  {
        log.info("Access token {}", accessToken);

        try {
            Algorithm algorithm = Algorithm.HMAC256(authConfig.getSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("forward")
                    .build();
            DecodedJWT jwt = verifier.verify(accessToken);
            return Optional.of(new AccessTokenPrincipal(jwt));
        } catch (JWTVerificationException exception){
            log.error("Failed to authenticate user", exception);
            return Optional.empty();
        }
    }
}
