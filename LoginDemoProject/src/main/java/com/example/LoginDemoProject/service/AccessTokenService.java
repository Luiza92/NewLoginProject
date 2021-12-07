package com.example.LoginDemoProject.service;

import com.example.LoginDemoProject.model.AccessToken;
import com.example.LoginDemoProject.repository.AccessTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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


    public int insert(AccessToken accessToken, List<Integer> userId, int token, Date create, Date expires,
                      String refresh_token) throws SQLException {
        int accessTokenId = this.accessTokenRepo.insert(accessToken);
        for (int user_id : userId){
            this.accessTokenRepo.insertAccess_token( user_id, token, create,  expires, refresh_token);
        }
        System.err.println("add " + accessTokenId);
        return accessTokenId;
    }

    public AccessToken get(int id) throws SQLException {

        System.err.println("accessToken_id " + id);

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

}
