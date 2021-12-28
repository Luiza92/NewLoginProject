package com.example.LoginDemoProject.model;

import java.sql.Timestamp;

public class Approve {

    private int id;
    private int user_id;
    private String randomId;
    private Timestamp timeExpires;

    public Approve( int id, int user_id, String randomId, Timestamp timeExpires) {
        this.user_id = user_id;
        this.randomId = randomId;
        this.timeExpires = timeExpires;
    }

    public Approve() {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getRandomId() {
        return randomId;
    }

    public void setRandomId(String randomId) {
        this.randomId = randomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimeExpires() {
        return timeExpires;
    }

    public void setTimeExpires(Timestamp timeExpires) {
        this.timeExpires = timeExpires;
    }
}


