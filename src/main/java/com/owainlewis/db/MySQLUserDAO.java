package com.owainlewis.db;

import com.owainlewis.core.User;
import com.owainlewis.core.UserMapper;
import org.skife.jdbi.v2.DBI;

import java.util.List;

public class MySQLUserDAO implements UserDAO {
    private final DBI dbi;

    public MySQLUserDAO(DBI dbi) {
        this.dbi = dbi;
    }

   public  List<User> getUsers() {
        return dbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users ORDER BY id ASC")
                        .map(new UserMapper())
                        .list());
    }
}
