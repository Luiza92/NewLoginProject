package com.example.LoginDemoProject.repository;

import com.example.LoginDemoProject.model.RefreshToken;
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
public class RefreshTokenRepo implements RefreshTokenRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public RefreshToken get(int id) throws SQLException {
        System.err.println("refreshToken id " + id);

        String sql = ("select * from refresh_token where id = ?  ;");
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser_id((int) result.get("user_id"));
        refreshToken.setToken((String) result.get("token"));
        refreshToken.setCreate((Timestamp) result.get("create"));
        refreshToken.setExpires((Timestamp) result.get("expires"));
        refreshToken.setStatus((int)result.get("status"));


        Integer refreshToken_id = result.get("id") != null ? ((Long) result.get("id")).intValue() : null;
        refreshToken.setId(refreshToken_id);
        return refreshToken;
    }

    @Override
    public int insert(RefreshToken refreshToken) throws SQLException {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(" insert into refresh_token (user_id, token, `create`,expires,status) values (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1,  refreshToken.getUser_id());
                statement.setString(2,  refreshToken.getToken());
                statement.setTimestamp(3, refreshToken.getCreate());
                statement.setTimestamp(4,  refreshToken.getExpires());
                statement.setInt(5,refreshToken.getStatus());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public RefreshToken delete(int refreshToken_id) throws SQLException {
        System.err.println("refreshToken_id  " + refreshToken_id);

        return jdbcTemplate.query(
                "select * from refresh_token where refreshToken_id = ?;",
                new ResultSetExtractor<RefreshToken>() {
                    @Override
                    public RefreshToken extractData(ResultSet rs) throws SQLException,
                            DataAccessException {
                        if (rs.next()) {
                            RefreshToken refreshToken = new RefreshToken();

                            refreshToken.setId(rs.getInt("id"));
                            refreshToken.setUser_id(rs.getInt("user_id"));
                            refreshToken.setToken(rs.getString("token"));
                            refreshToken.setCreate(rs.getTimestamp("create"));
                            refreshToken.setExpires(rs.getTimestamp("expires"));
                            refreshToken.setStatus(rs.getInt("status"));
                            return refreshToken;
                        }

                        return null;
                    }
                },

                refreshToken_id);
    }

    @Override
    public int update(RefreshToken refreshToken) throws SQLException {
        String sql = "update refresh_token set user_id = ?, token = ?, create = ?, expires = ?, status=?,    where id = ?";
        System.err.println(refreshToken.getUser_id() + ", " + refreshToken.getToken() + ", " + refreshToken.getCreate() + ", " + refreshToken.getExpires() + ", " + refreshToken.getStatus() + ", "+  refreshToken.getId());
        int result = jdbcTemplate.update(sql, refreshToken.getUser_id(), refreshToken.getToken(),  refreshToken.getCreate(), refreshToken.getExpires(), refreshToken.getStatus(), refreshToken.getId());
        if (result > 0) {
            System.out.println("a new refreshToken has been update.");
            return refreshToken.getId();
        }
        return refreshToken.getId();

    }

    @Override
    public int deleteByUserId(int user_id) throws SQLException {
        return jdbcTemplate.update(
                "delete  from refresh_token where user_id = ?",
                user_id);

    }


}
