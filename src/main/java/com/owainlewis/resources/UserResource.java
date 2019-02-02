package com.owainlewis.resources;

import com.codahale.metrics.annotation.Timed;
import com.owainlewis.core.User;
import com.owainlewis.db.MySQLUserDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final MySQLUserDAO dao;

    public UserResource(MySQLUserDAO dao) {
        this.dao = dao;
    }

    @GET
    @Timed
    public List<User> index() {
        return dao.getUsers();
    }
}
