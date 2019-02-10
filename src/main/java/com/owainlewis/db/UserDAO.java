package com.owainlewis.db;

import com.owainlewis.core.User;
import com.owainlewis.core.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;

import java.util.List;
import java.util.Optional;

@RegisterMapper(UserMapper.class)
public interface UserDAO {
    @SqlQuery("SELECT * FROM users;")
    public List<User> getUsers();

    @SqlQuery("SELECT * FROM USERS where id = :id")
    @SingleValueResult
    Optional<User> findById(@Bind("id") long id);

    @SqlQuery("SELECT * FROM USERS where email = :email")
    @SingleValueResult
    Optional<User> findByEmail(@Bind("email") String email);

    @SqlUpdate("INSERT INTO users(first, last, email) VALUES(:first, :last, :email)")
    @GetGeneratedKeys
    long createUser(@BindBean final User user);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    int deleteById(@Bind("id") int id);
}
