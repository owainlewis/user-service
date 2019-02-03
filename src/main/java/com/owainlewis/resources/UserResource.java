package com.owainlewis.resources;

import com.codahale.metrics.annotation.Timed;
import com.owainlewis.core.User;
import com.owainlewis.db.UserDAO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @Timed
    public List<User> index() {
        return userDAO.getUsers();
    }

    @GET
    @Path("/{id}")
    public Optional<User> get(@PathParam("id") Integer id){
        return userDAO.findById(id);
    }

    @POST
    @Timed
    public void create(@NotNull @Valid final User user) {
        User u = new User(user.getFirst(), user.getLast(), user.getEmail());
        userDAO.createUser(u);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        userDAO.deleteById(id);
    }
}
