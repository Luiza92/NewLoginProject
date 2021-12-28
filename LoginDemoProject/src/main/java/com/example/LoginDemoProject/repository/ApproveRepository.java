package com.example.LoginDemoProject.repository;

import com.example.LoginDemoProject.model.Approve;

import java.sql.SQLException;

public interface ApproveRepository {

    Approve get(int id) throws SQLException;

    int insert(Approve approve) throws SQLException;

    Approve delete(int id) throws SQLException;

    int update(Approve approve) throws SQLException;



}
