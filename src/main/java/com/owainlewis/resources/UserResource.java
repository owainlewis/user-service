package com.owainlewis.resources;

import com.codahale.metrics.annotation.Timed;
import com.owainlewis.api.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @GET
    @Timed
    public List<User> index() {
        return Collections.singletonList(new User("Owain", "Lewis", "owain@owainlewis.com"));
    }
}
