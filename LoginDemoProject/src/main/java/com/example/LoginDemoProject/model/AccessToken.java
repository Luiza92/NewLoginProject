package com.example.LoginDemoProject.model;

import java.sql.Date;
import java.util.List;

public class AccessToken {

    private int id;
    private int user_id;
    private String token;
    private Date create;
    private Date expires;
    private String refresh_token;
    private List<Integer> userId;


    public AccessToken(int id, int user_id, String token, Date create, Date expires, String refresh_token) {
        this.id = id;
        this.user_id = user_id;
        this.token = token;
        this.create = create;
        this.expires = expires;
        this.refresh_token = refresh_token;
    }

    public AccessToken() {

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

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
