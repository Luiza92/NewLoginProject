package com.example.LoginDemoProject.repository;

import com.example.LoginDemoProject.model.AccessToken;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Map;

@Repository
public class AccessTokenRepo implements AccessTokenRepository {

    private JdbcTemplate jdbcTemplate;
    private AccessToken accessToken;


    @Override
    public AccessToken get(int id) throws SQLException {
        System.err.println("accessToken id " + id);

        String sql = ("select * from access_token where id = ?  ;");
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        AccessToken accessToken = new AccessToken();

        accessToken.setUser_id((int) result.get("user_id"));
        accessToken.setToken((String) result.get("token"));
        accessToken.setCreate((Date) result.get("create"));
        accessToken.setExpires((Date) result.get("expires"));
        accessToken.setRefresh_token((String) result.get("refresh_token"));


        Integer accessToken_id = result.get("id") != null ? ((Long) result.get("id")).intValue() : null;
        accessToken.setId(accessToken_id);
        return accessToken;
    }

    @Override
    public int insert(AccessToken accessToken) throws SQLException {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("insert into access_token (user_id, token, create,expires,refresh_token) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, accessToken.getUser_id());
                statement.setString(2, accessToken.getToken());
                statement.setDate(3, accessToken.getCreate());
                statement.setDate(4, accessToken.getExpires());
                statement.setString(5, accessToken.getRefresh_token());
                return statement;
            }
        }, holder);

        return ((Long) holder.getKey().longValue()).intValue();
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
                            a.setCreate(rs.getDate("create"));
                            a.setExpires(rs.getDate("expires"));
                            a.setRefresh_token(rs.getString("refresh_token"));
                            return a;
                        }

                        return null;
                    }
                },
                accessToken_id);
    }

    @Override
    public int update(AccessToken accessToken) throws SQLException {
        String sql = "update access_token set user_id = ?, token = ?, create = ?, expires = ?, refresh_token = ? ,  where id = ?";
        System.err.println(accessToken.getUser_id() + ", " + accessToken.getToken() + ", " + accessToken.getCreate() + ", " + accessToken.getExpires() + ", " + accessToken.getRefresh_token() + ", " + accessToken.getId());
        int result = jdbcTemplate.update(sql, accessToken.getUser_id(), accessToken.getToken(), accessToken.getCreate(), accessToken.getExpires(), accessToken.getRefresh_token(), accessToken.getId());
        if (result > 0) {
            System.out.println("a new row has been update.");
            return accessToken.getId();
        }
        return accessToken.getId();

    }

    @Override
    public int insertAccess_token(int user_id, int token, Date create, Date expires, String refresh_token) throws SQLException {

        System.err.println(user_id);
        System.err.println(token);
        System.err.println(create);
        System.err.println(expires);
        System.err.println(refresh_token);
        GeneratedKeyHolder holder = new GeneratedKeyHolder();


        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement("insert into access_token ( user_id , token, create, expires, refresh_token ) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                statement.setInt(1, user_id);
                statement.setInt(2, token);
                statement.setDate(3, create);
                statement.setDate(4, expires);
                statement.setString(5, refresh_token);
                return statement;
            }
        }, holder);
        return ((Long) holder.getKey().longValue()).intValue();
    }


}
