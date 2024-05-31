/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author nvmin
 */
public class KhachHang {

    private String maKH;
    private String tenKH;
    private String soDienThoaiKH;
    private boolean gioiTinhKH;
    private String diaChiKH;
    private String loaiKH;

    public KhachHang() {
    }

    public KhachHang(String maKH) {
        this.maKH = maKH;
    }

    public KhachHang(String maKH, String tenKH, String soDienThoaiKH, boolean gioiTinhKH, String diaChiKH, String loaiKH) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.gioiTinhKH = gioiTinhKH;
        this.soDienThoaiKH = soDienThoaiKH;
        this.diaChiKH = diaChiKH;
        this.loaiKH = loaiKH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public boolean isGioiTinhKH() {
        return gioiTinhKH;
    }

    public void setGioiTinhKH(boolean gioiTinhKH) {
        this.gioiTinhKH = gioiTinhKH;
    }

    public String getSoDienThoaiKH() {
        return soDienThoaiKH;
    }

    public void setSoDienThoaiKH(String soDienThoaiKH) {
        this.soDienThoaiKH = soDienThoaiKH;
    }

    public String getDiaChiKH() {
        return diaChiKH;
    }

    public void setDiaChiKH(String diaChiKH) {
        this.diaChiKH = diaChiKH;
    }

    public String getLoaiKH() {
        return loaiKH;
    }

    public void setLoaiKH(String loaiKH) {
        this.loaiKH = loaiKH;
    }
}
