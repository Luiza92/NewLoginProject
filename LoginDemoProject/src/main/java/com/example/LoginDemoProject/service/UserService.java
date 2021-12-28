package com.example.LoginDemoProject.service;


import com.example.LoginDemoProject.model.User;
import com.example.LoginDemoProject.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public int add(User user) throws SQLException {
        System.err.println("Error add  ");

        int userId = this.userRepo.insert(user);
        {
            System.err.println("add " + userId);
        }
        return userId;
    }

    public User get(int id) throws SQLException {

        return this.userRepo.get(id);
    }

    public User getByUsername(String username) throws SQLException {
        return this.userRepo.getByUsername(username);
    }

    public User getByUserId(String user_id) throws SQLException {
        return this.userRepo.getByUserId(user_id);
    }

    public boolean delete(int id) throws SQLException {
        return this.userRepo.delete(id);

    }

    public int update(User user) throws SQLException {
        int userId = this.userRepo.update(user);
        {
            System.err.println("add " + userId);
        }
        return userId;
    }





}
