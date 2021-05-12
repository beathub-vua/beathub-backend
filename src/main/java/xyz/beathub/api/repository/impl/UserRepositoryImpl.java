package xyz.beathub.api.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import xyz.beathub.api.model.User;
import xyz.beathub.api.repository.UserRepository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Value("${spring.sql.get_user}")
    private String getUserSql;

    private final JdbcTemplate template;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<User> getUsers() {
        return template.queryForList(getUserSql, User.class);
    }
}
