package com.owainlewis.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.dropwizard.auth.AuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.regex.Pattern;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

public class JWTAuthenticationFilter <P extends Principal> extends AuthFilter<String, P> {

    private static final Pattern JWT_BEARER_TOKEN_PATTERN = Pattern.compile("Bearer ([^.]+\\.[^.]+\\.[^.]+)");

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private static final String TOKEN_SIGNING_STRING = "secret";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        final Optional<String> authHeader = getBearerTokenFromRequest(containerRequestContext);
    }

    private Optional<DecodedJWT> decodeAndVerifyJWTToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SIGNING_STRING);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            return Optional.of(jwt);
        } catch (JWTVerificationException e){
            logger.error("Failed to verify JWT token", e);
            return Optional.empty();
        }
    }

    private Optional<String> getBearerTokenFromRequest(ContainerRequestContext containerRequestContext) {
        String header = containerRequestContext.getHeaderString(AUTHORIZATION);
        if (header != null) {
            Optional<String> token = extractBearerToken(header);
            logger.info("Auth header {}", token);

            try {
                DecodedJWT jwt = JWT.decode(token.get());
                logger.info("JWT {}", jwt.getPayload());
            } catch (JWTDecodeException exception){
                //Invalid token
            }
        }

        return Optional.empty();
    }

    private Optional<String> extractBearerToken(String header) {
        if (header.startsWith("Bearer ")) {
            return Optional.of(header.substring(7));
        }
        return Optional.empty();
    }
}