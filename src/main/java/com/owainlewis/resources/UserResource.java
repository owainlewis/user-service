package com.owainlewis.resources;

import com.codahale.metrics.annotation.Timed;
import com.owainlewis.auth.jwt.AccessTokenPrincipal;
import com.owainlewis.core.User;
import com.owainlewis.db.UserDAO;
import io.dropwizard.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.skife.jdbi.v2.sqlobject.customizers.TransactionIsolation;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
@Timed
@Slf4j
public final class UserResource {

    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    public List<User> index() {
        log.info("Listing users");
        return userDAO.getUsers();
    }

    @GET
    @Path("/{id}")
    public User get(@PathParam("id") Long id){
        return userDAO.findById(id).orElseThrow(NotFoundException::new);
    }

    @POST
    public User create(@NotNull @Valid final User user) {
        if (emailExists(user.getEmail())) {
            throw new BadRequestException("A user with that email address already exists");
        }

        User u = new User(user.getFirst(), user.getLast(), user.getEmail());

        long id = userDAO.createUser(u);

        return userDAO.findById(id).orElseThrow(InternalServerErrorException::new);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Integer id) {
        userDAO.deleteById(id);
    }

    private boolean emailExists(String email) {
        return userDAO.findByEmail(email).isPresent();
    }
}
