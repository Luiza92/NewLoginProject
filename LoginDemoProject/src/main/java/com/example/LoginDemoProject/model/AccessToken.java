package com.example.LoginDemoProject.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


public class AccessToken {

    private int id;
    private int user_id;
    private String token;
    private Timestamp create;
    private Timestamp expires;
    private int refresh_token_id;
    private int status;
    private List<Integer> userId;


    public AccessToken(int id, int user_id, String token, Timestamp create, Timestamp expires, int refresh_token_id,int status) {
        this.id = id;
        this.user_id = user_id;
        this.token = token;
        this.create = create;
        this.expires = expires;
        this.refresh_token_id = refresh_token_id;
        this.status = status;
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

    public Timestamp getCreate() {
        return create;
    }

    public void setCreate(Timestamp create) {
        this.create = create;
    }

    public Timestamp getExpires() {
        return expires;
    }

    public void setExpires(Timestamp expires) {
        this.expires = expires;
    }

    public int getRefresh_token_id() {
        return refresh_token_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRefresh_token_id(int refresh_token_id) {
        this.refresh_token_id = refresh_token_id;
    }
}
