package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<User> list() {
        return jdbcTemplate.query(
                "select * from users",new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User user(int id) {
        return jdbcTemplate.query(
                "select * from users where id=?",new Object[] {id},
                new BeanPropertyRowMapper<>(User.class)).stream()
                .findAny().orElse(null);
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("insert into users (name,age) values (?,?)",
                user.getName(),user.getAge());
    }

    @Override
    public void update(User user, int id) {
        jdbcTemplate.update("update users set name=?,age=? where id=?",user.getName(),
                user.getAge(),id);
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("delete from users where id=?",id);
    }
}
