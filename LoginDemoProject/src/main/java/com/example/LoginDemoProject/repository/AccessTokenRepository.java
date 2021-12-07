package com.example.LoginDemoProject.repository;
import com.example.LoginDemoProject.model.AccessToken;

import java.sql.Date;
import java.sql.SQLException;


public interface AccessTokenRepository {


    AccessToken get(int id) throws SQLException;

    int insert(AccessToken accessToken) throws SQLException;

    AccessToken delete(int id) throws SQLException;

    int update(AccessToken accessToken) throws SQLException;


    int insertAccess_token(int user_id, int token, Date create, Date expires, String refresh_token)
            throws SQLException ;
}
