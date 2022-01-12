package com.example.LoginDemoProject.repository;

import com.example.LoginDemoProject.model.User;

import java.sql.SQLException;

public interface UserRepository {


    int insert(User user) throws SQLException;

    User get(int id) throws SQLException;

    User getByUsername(String username) throws SQLException;

    User getByUserId(int userId) throws SQLException;

    boolean delete(int id) throws SQLException;

    int update(User user) throws SQLException;
}
