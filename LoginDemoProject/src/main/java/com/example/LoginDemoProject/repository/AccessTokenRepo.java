package com.example.LoginDemoProject.repository;

import com.example.LoginDemoProject.model.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Map;

@Repository
public class AccessTokenRepo implements AccessTokenRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public AccessToken get(int id) throws SQLException {
        System.err.println("accessToken id " + id);

        String sql = ("select * from access_token where id = ?  ;");
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        AccessToken accessToken = new AccessToken();

        accessToken.setUser_id((int) result.get("user_id"));
        accessToken.setToken((String) result.get("token"));
        accessToken.setCreate((Timestamp) result.get("create"));
        accessToken.setExpires((Timestamp) result.get("expires"));
        accessToken.setRefresh_token_id((int) result.get("refresh_token_id"));

        Integer accessToken_id = result.get("id") != null ? ((Long) result.get("id")).intValue() : null;
        accessToken.setId(accessToken_id);
        return accessToken;
    }

    @Override
    public int insert(AccessToken accessToken) throws SQLException {
        return jdbcTemplate.update(
                "insert into access_token (user_id, token, `create`, expires, refresh_token_id) values (?,?,?,?,?)",
                accessToken.getUser_id(),
                accessToken.getToken(),
                accessToken.getCreate(),
                accessToken.getExpires(),
                accessToken.getRefresh_token_id());

    }

    @Override
    public AccessToken delete(int accessToken_id) throws SQLException {
        System.err.println("accessToken_id  " + accessToken_id);

        return jdbcTemplate.query(
                "select * from access_token where accessToken_id = ?;",
                new ResultSetExtractor<AccessToken>() {
                    @Override
                    public AccessToken extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        if (rs.next()) {
                            AccessToken a = new AccessToken();

                            a.setId(rs.getInt("id"));
                            a.setUser_id(rs.getInt("user_id"));
                            a.setToken(rs.getString("token"));
                            a.setCreate(rs.getTimestamp("create"));
                            a.setExpires(rs.getTimestamp("expires"));
                            a.setRefresh_token_id(rs.getInt("refresh_token_id"));
                            return a;
                        }

                        return null;
                    }
                },
                accessToken_id);
    }

    @Override
    public int update(AccessToken accessToken) throws SQLException {
        String sql = "update access_token set user_id = ?, token = ?, create = ?, expires = ?, refresh_token_id = ? ,  where id = ?";
        System.err.println(accessToken.getUser_id() + ", " + accessToken.getToken() + ", " + accessToken.getCreate() + ", " + accessToken.getExpires() + ", " + accessToken.getRefresh_token_id() + ", " + accessToken.getId());
        int result = jdbcTemplate.update(sql, accessToken.getUser_id(), accessToken.getToken(), accessToken.getCreate(), accessToken.getExpires(), accessToken.getRefresh_token_id(), accessToken.getId());
        if (result > 0) {
            System.out.println("a new row has been update.");
            return accessToken.getId();
        }
        return accessToken.getId();

    }

    public int deleteByUserId(int user_id) throws SQLException {
        return jdbcTemplate.update(
                "delete  from access_token where user_id = ?",
                user_id);


    }

}







