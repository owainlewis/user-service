package com.owainlewis.resources;

import com.codahale.metrics.annotation.Timed;
import com.owainlewis.api.UserResponse;
import com.owainlewis.core.User;
import com.owainlewis.db.UserDAO;
import org.eclipse.jetty.http.HttpStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO dao;

    public UserResource(UserDAO dao) {
        this.dao = dao;
    }

    @GET
    @Timed
    public UserResponse<List<User>> index() {
        List<User> users = dao.getUsers();
        return new UserResponse<>(HttpStatus.OK_200, users);
    }

    @POST
    @Timed
    public UserResponse<User> createUser(@NotNull @Valid final User user) {
        User u = new User(user.getFirst(), user.getLast(), user.getEmail());
        dao.createUser(u);
        return new UserResponse<User>(HttpStatus.CREATED_201, u);
    }
}
