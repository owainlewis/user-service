package com.owainlewis.resources;

import com.owainlewis.auth.jwt.AccessTokenPrincipal;
import com.owainlewis.core.User;
import com.owainlewis.db.UserDAO;
import io.dropwizard.auth.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    private final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    private final UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    public List<User> index(@Auth AccessTokenPrincipal tokenPrincipal) {
        LOG.info("Listing users for user {}", tokenPrincipal.getName());
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
