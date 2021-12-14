package com.example.LoginDemoProject.repository;

import com.example.LoginDemoProject.model.RefreshToken;
import java.sql.SQLException;


public interface RefreshTokenRepository {

  RefreshToken get(int id) throws SQLException;

    int insert(RefreshToken refreshToken) throws SQLException;

    RefreshToken delete(int id) throws SQLException;

    int update(RefreshToken refreshToken) throws SQLException;


}
