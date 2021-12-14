package com.example.LoginDemoProject.model;

import java.sql.Date;

public class RefreshToken {

    private int id;
    private int user_id;
    private String token;
    private Date create;
    private Date expires;

    public RefreshToken(int id, int user_id, String token, Date create, Date expires) {
        this.id = id;
        this.user_id = user_id;
        this.token = token;
        this.create = create;
        this.expires = expires;
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

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}
