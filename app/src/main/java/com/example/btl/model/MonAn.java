package com.example.btl.model;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

public class MonAn implements Serializable {

    private int id;

    private byte[] hinhDaiDien;
    private String ten, moTa;
    private int khauPhan, thoiGianNau;

    private String nguyenLieu;

    private String cachLam;

    private String huongDan;

    private String ngayTao;

    private User nguoiViet;


    public MonAn() {
    }

    public MonAn(int id, byte[] hinhDaiDien, String ten, String moTa, int khauPhan, int thoiGianNau, String nguyenLieu, String cachLam, String ngayTao, User nguoiViet, String huongDan) {
        this.id = id;
        this.hinhDaiDien = hinhDaiDien;
        this.ten = ten;
        this.moTa = moTa;
        this.khauPhan = khauPhan;
        this.thoiGianNau = thoiGianNau;
        this.nguyenLieu = nguyenLieu;
        this.cachLam = cachLam;
        this.ngayTao = ngayTao;
        this.nguoiViet = nguoiViet;
        this.huongDan = huongDan;
    }

    public void setNguyenLieu(String nguyenLieu) {
        this.nguyenLieu = nguyenLieu;
    }

    public void setCachLam(String cachLam) {
        this.cachLam = cachLam;
    }

    public String getCachLam() {
        return cachLam;
    }

    public void setHuongDan(String huongDan) {
        this.huongDan = huongDan;
    }

    public String getHuongDan() {
        return huongDan;
    }

    public String getNguyenLieu() {
        return nguyenLieu;
    }

    public byte[] getHinhDaiDien() {
        return hinhDaiDien;
    }

    public void setHinhDaiDien(byte[] hinhDaiDien) {
        this.hinhDaiDien = hinhDaiDien;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getKhauPhan() {
        return khauPhan;
    }

    public void setKhauPhan(int khauPhan) {
        this.khauPhan = khauPhan;
    }

    public int getThoiGianNau() {
        return thoiGianNau;
    }

    public void setThoiGianNau(int thoiGianNau) {
        this.thoiGianNau = thoiGianNau;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public User getNguoiViet() {
        return nguoiViet;
    }

    public void setNguoiViet(User nguoiViet) {
        this.nguoiViet = nguoiViet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
