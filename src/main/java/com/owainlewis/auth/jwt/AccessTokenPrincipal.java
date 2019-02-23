package com.owainlewis.auth.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.Principal;
import java.util.Optional;

public final class AccessTokenPrincipal implements Principal {

    private final DecodedJWT accessToken;

    AccessTokenPrincipal(DecodedJWT accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Extract the principal name from a DecodedJWT
     *
     * @return the claim sub name or an empty string
     */
    @Override
    public String getName() {
        final Optional<String> subClaim = Optional.ofNullable(accessToken.getClaims().get("sub")).map(Claim::asString);
        return subClaim.orElse("");
    }
}
