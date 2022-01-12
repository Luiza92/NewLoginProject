package com.example.LoginDemoProject.model;

import java.sql.Timestamp;

public class RefreshToken {

    private int id;
    private int user_id;
    private String token;
    private Timestamp create;
    private Timestamp expires;
    private int status;

    public RefreshToken(int id, int user_id, String token, Timestamp create, Timestamp expires, int status) {
        this.id = id;
        this.user_id = user_id;
        this.token = token;
        this.create = create;
        this.expires = expires;
        this.status = status;
    }

    public RefreshToken() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getCreate() {
        return create;
    }

    public void setCreate(Timestamp create) {
        this.create = create;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getExpires() {
        return expires;
    }

    public void setExpires(Timestamp expires) {
        this.expires = expires;
    }
}
