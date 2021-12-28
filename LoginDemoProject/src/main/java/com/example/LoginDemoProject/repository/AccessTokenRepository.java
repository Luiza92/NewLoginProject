package com.example.LoginDemoProject.repository;
import com.example.LoginDemoProject.model.AccessToken;
import java.sql.SQLException;


public interface AccessTokenRepository {


    AccessToken get(int id) throws SQLException;

    int insert(AccessToken accessToken) throws SQLException;

    AccessToken delete(int id) throws SQLException;

    int update(AccessToken accessToken) throws SQLException;

    int deleteByUserId(int user_id)throws SQLException;


}
