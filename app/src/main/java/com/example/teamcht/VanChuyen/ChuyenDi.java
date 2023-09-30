package com.example.teamcht.VanChuyen;

public class ChuyenDi {
    private String phuongTien, DiemKhoiHanh, DiemDen, NgayDi, SoHanhKhach, GiaVe;
    private Integer id;

    public ChuyenDi(String phuongTien, String diemKhoiHanh, String diemDen, String ngayDi, String soHanhKhach, String giaVe) {
        this.phuongTien = phuongTien;
        DiemKhoiHanh = diemKhoiHanh;
        DiemDen = diemDen;
        NgayDi = ngayDi;
        SoHanhKhach = soHanhKhach;
        GiaVe = giaVe;
    }

    public ChuyenDi() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhuongTien() {
        return phuongTien;
    }

    public void setPhuongTien(String phuongTien) {
        this.phuongTien = phuongTien;
    }

    public String getDiemKhoiHanh() {
        return DiemKhoiHanh;
    }

    public void setDiemKhoiHanh(String diemKhoiHanh) {
        DiemKhoiHanh = diemKhoiHanh;
    }

    public String getDiemDen() {
        return DiemDen;
    }

    public void setDiemDen(String diemDen) {
        DiemDen = diemDen;
    }

    public String getNgayDi() {
        return NgayDi;
    }

    public void setNgayDi(String ngayDi) {
        NgayDi = ngayDi;
    }

    public String getSoHanhKhach() {
        return SoHanhKhach;
    }

    public void setSoHanhKhach(String soHanhKhach) {
        SoHanhKhach = soHanhKhach;
    }

    public String getGiaVe() {
        return GiaVe;
    }

    public void setGiaVe(String giaVe) {
        GiaVe = giaVe;
    }
}
