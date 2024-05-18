package com.example.btl.model;

import java.io.Serializable;

public class User implements Serializable {
    private int id;

    private byte[] hinhDaiDien;
    private String username;
    private String password;
    private String cookbookId;

    public User() {
    }

    public User(int id, String username, String password, String cookbookId, String email, String chiaSeChung, byte[] hinhDaiDien) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cookbookId = cookbookId;
        this.email = email;
        this.chiaSeChung = chiaSeChung;
        this.hinhDaiDien = hinhDaiDien;
    }

    public byte[] getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(byte[] hinhDaiDien) {
        this.hinhDaiDien = hinhDaiDien;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCookbookId() {
        return cookbookId;
    }

    public void setCookbookId(String cookbookId) {
        this.cookbookId = cookbookId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChiaSeChung() {
        return chiaSeChung;
    }

    public void setChiaSeChung(String chiaSeChung) {
        this.chiaSeChung = chiaSeChung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String email;
    private String chiaSeChung;

}
