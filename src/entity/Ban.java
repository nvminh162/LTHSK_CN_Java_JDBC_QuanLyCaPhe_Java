/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author nvmin
 */
public class Ban {

    private String maBan;
    private String tenBan;
    private int soLuongNguoi;
    private String trangThaiBan;

    public Ban() {
    }

    public Ban(String maBan) {
        this.maBan = maBan;
    }

    public Ban(String maBan, String tenBan, int soLuongNguoi, String trangThaiBan) {
        this.maBan = maBan;
        this.tenBan = tenBan;
        this.soLuongNguoi = soLuongNguoi;
        this.trangThaiBan = trangThaiBan;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public int getSoLuongNguoi() {
        return soLuongNguoi;
    }

    public void setSoLuongNguoi(int soLuongNguoi) {
        this.soLuongNguoi = soLuongNguoi;
    }

    public String getTrangThaiBan() {
        return trangThaiBan;
    }

    public void setTrangThaiBan(String trangThaiBan) {
        this.trangThaiBan = trangThaiBan;
    }

}
