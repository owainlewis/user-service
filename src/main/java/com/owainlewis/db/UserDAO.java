package com.owainlewis.db;

import com.owainlewis.core.User;
import com.owainlewis.core.UserMapper;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(UserMapper.class)
public interface UserDAO {
    @SqlQuery("select * from users;")
    public List<User> getUsers();

    @SqlUpdate("insert into users(first, last, email) values(:first, :last, :email)")
    void createUser(@BindBean final User user);
}
