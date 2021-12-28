package com.example.LoginDemoProject.service;

import com.example.LoginDemoProject.model.RefreshToken;
import com.example.LoginDemoProject.repository.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class RefreshTokenService extends RefreshTokenRepo {

    @Autowired
    RefreshTokenRepo refreshTokenRepo;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insert(RefreshToken refreshToken) throws SQLException {

        int refreshTokenID = this.refreshTokenRepo.insert(refreshToken);
        {
            System.err.println("add " + refreshTokenID);
        }
        return refreshTokenID;
    }

    public RefreshToken get(int id) throws SQLException {

        System.err.println("refreshToken_id " + id);

        String sql = ("select * from refresh_token where id = ?  ;");
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser_id((int) result.get("user_id"));
        refreshToken.setToken((String) result.get("token"));
        refreshToken.setCreate((Timestamp) result.get("create"));
        refreshToken.setExpires((Timestamp) result.get("expires"));

        refreshToken.setId((int) result.get("id"));
        return refreshToken;
    }

    public int update(RefreshToken refreshToken) throws SQLException {
        int refreshTokenID = this.refreshTokenRepo.update(refreshToken);
        {
            System.err.println("add " + refreshToken);
        }
        return refreshTokenID;
    }

    public RefreshToken delete(int id) throws SQLException {
        return this.refreshTokenRepo.delete(id);
    }

    public int deleteByUserId(int user_id) throws SQLException {
        return this.refreshTokenRepo.deleteByUserId(user_id);
    }

}
