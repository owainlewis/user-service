package com.owainlewis.db;

import com.owainlewis.core.User;
import com.owainlewis.core.mapper.UserMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;
import java.util.Optional;

@RegisterMapper(UserMapper.class)
public interface UserDAO {
    @SqlQuery("SELECT * FROM users;")
    public List<User> getUsers();

    @SqlQuery("SELECT * FROM USERS where id = :id")
    Optional<User> findById(@Bind("id") int id);

    @SqlUpdate("INSERT INTO users(first, last, email) VALUES(:first, :last, :email)")
    void createUser(@BindBean final User user);

    @SqlUpdate("DELETE FROM users WHERE id = :id")
    int deleteById(@Bind("id") int id);
}
