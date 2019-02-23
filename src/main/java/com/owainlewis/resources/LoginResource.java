package com.owainlewis.resources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.owainlewis.models.AuthConfig;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class LoginResource {
    private AuthConfig authConfig;

    public LoginResource(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    @POST
    public Response index() {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            final String jwtToken = JWT.create()
                    .withIssuer("userService")
                    .withClaim("sub", "owain@owainlewis.com")
                    .sign(algorithm);
            return Response.status(Response.Status.OK)
                    .entity(jwtToken)
                    .build();
        } catch (JWTCreationException exception){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
