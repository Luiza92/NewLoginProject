package com.example.LoginDemoProject.service;

import com.example.LoginDemoProject.model.AccessToken;
import com.example.LoginDemoProject.repository.AccessTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

@Service
public class AccessTokenService extends AccessTokenRepo {

    @Autowired
    AccessTokenRepo accessTokenRepo;

    @Autowired
    JdbcTemplate jdbcTemplate;


    public int insert(AccessToken accessToken) throws SQLException {

        int accessTokenID = this.accessTokenRepo.insert(accessToken);
        {
            System.err.println("add " + accessTokenID);
        }
        return accessTokenID;
    }

    public AccessToken get(int id) throws SQLException {

        System.err.println("accessToken_id " + id);

        String sql = ("select * from access_token where id = ?  ;");
        Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        AccessToken accessToken = new AccessToken();

        accessToken.setUser_id((int) result.get("user_id"));
        accessToken.setToken((String) result.get("token"));
        accessToken.setCreate((Timestamp) result.get("create"));
        accessToken.setExpires((Timestamp) result.get("expires"));
        accessToken.setRefresh_token_id((int) result.get("refresh_token_id"));


        accessToken.setId((int)result.get("id"));
        return accessToken;
    }

    public int update(AccessToken accessToken) throws SQLException {
        int accessTokenID = this.accessTokenRepo.update(accessToken);
        {
            System.err.println("add " + accessTokenID);
        }
        return accessTokenID;
    }


    public AccessToken delete(int id) throws SQLException {
        return this.accessTokenRepo.delete(id);

    }

    public int deleteByUserId(int user_id) throws SQLException {
        return this.accessTokenRepo.deleteByUserId(user_id);
    }

}
