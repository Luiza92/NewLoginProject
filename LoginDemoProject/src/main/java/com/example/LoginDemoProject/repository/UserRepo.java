package com.example.LoginDemoProject.repository;

import com.example.LoginDemoProject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Map;

@Repository
public class UserRepo implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private User user;


    @Override
    public int insert(User user) throws SQLException {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement statement = con.prepareStatement("insert into users (username, surname, email, password) values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, user.getUsername());
                    statement.setString(2, user.getSurname());
                    statement.setString(3, user.getEmail());
                    statement.setString(4, user.getPassword());
                    return statement;
                }
            }, holder);

            return ((Long) holder.getKey().longValue()).intValue();
        }


    @Override
    public User get(int id) throws SQLException {
        System.err.println("user id " + id);

        String sql = ("select * from users where id = ?  ;");
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);
        User user = new User();
        user.setUsername((String) result.get("username"));
        user.setSurname((String) result.get("surname"));
        user.setEmail((String) result.get("email"));
        user.setPassword((String) result.get("password"));

        Integer user_id = result.get("id") != null ? ((Long) result.get("id")).intValue() : null;
        user.setId(user_id);
        return user;
    }

    @Override
    public User getByUsername(String username) throws SQLException {
        System.err.println("username  " + username);

        return jdbcTemplate.query(
                "select * from users where username = ?;",
                new ResultSetExtractor<User>() {
                    @Override
                    public User extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        if (rs.next()) {
                            User u = new User();
                            u.setId(rs.getInt("id"));
                            u.setUsername(rs.getString("username"));
                            u.setSurname(rs.getString("username"));
                            u.setEmail(rs.getString("email"));
                            u.setPassword(rs.getString("password"));
                            return u;
                        }

                        return null;
                    }
                },
                username);
    }

    @Override
    public User getByUserId(String user_id) throws SQLException {
        return jdbcTemplate.query(
                "select * from users where user_id = ?;",
                new ResultSetExtractor<User>() {
                    @Override
                    public User extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        if (rs.next()) {
                            User u = new User();
                            u.setId(rs.getInt("id"));
                            u.setUsername(rs.getString("username"));
                            u.setSurname(rs.getString("surname"));
                            u.setEmail(rs.getString("email"));
                            u.setPassword(rs.getString("password"));
                            return u;
                        }
                        return null;
                    }
                },
                user_id);

    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "delete from users where id = ?;";
        int result = jdbcTemplate.update(sql, id);
        if (result == 0) {
            System.out.println("user has been  delete.");
            return false;
        }
        return true;
    }

    @Override
    public int update(User user) throws SQLException {
        String sql = "update users set username = ?, surname=?, email = ?, password = ? where id = ?";
        System.err.println(user.getUsername() + ", " + user.getSurname() + "," + user.getEmail() + ", " + user.getPassword() + ", " + user.getId());
        int result = jdbcTemplate.update(sql, user.getUsername(), user.getSurname(), user.getEmail(), user.getPassword(), user.getId());
        if (result > 0) {
            System.out.println("user has been update.");
            return user.getId();
        }
        return user.getId();

    }
}
