package com.example.btl.model;

import java.io.Serializable;

public class Message implements Serializable {
    private int id;
    private User user;

    private MonAn monAn;
    String title, time;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Message(int id, User user, MonAn monAn, String title, String time) {
        this.id = id;
        this.user = user;
        this.monAn = monAn;
        this.title = title;
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public void setMonAn(MonAn monAn) {
        this.monAn = monAn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
