/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Objects;

/**
 *
 * @author nvmin
 */
public class NhanVien {

    private String maNV;
    private String tenNV;
    private String diaChiNV;
    private String soDienThoaiNV;
    private boolean gioiTinhNV;
    private String matKhauNV;

    public NhanVien() {
    }

    public NhanVien(String maNV) {
        this.maNV = maNV;
    }
    

    public NhanVien(String maNV, String tenNV, String diaChiNV, String soDienThoaiNV, boolean gioiTinhNV, String matKhauNV) {
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.diaChiNV = diaChiNV;
        this.soDienThoaiNV = soDienThoaiNV;
        this.gioiTinhNV = gioiTinhNV;
        this.matKhauNV = matKhauNV;
    }

    public NhanVien(String maNV, String matKhauNV) {
        this.maNV = maNV;
        this.matKhauNV = matKhauNV;
    }
    
    

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getDiaChiNV() {
        return diaChiNV;
    }

    public void setDiaChiNV(String diaChiNV) {
        this.diaChiNV = diaChiNV;
    }

    public String getSoDienThoaiNV() {
        return soDienThoaiNV;
    }

    public void setSoDienThoaiNV(String soDienThoaiNV) {
        this.soDienThoaiNV = soDienThoaiNV;
    }

    public boolean isGioiTinhNV() {
        return gioiTinhNV;
    }

    public void setGioiTinhNV(boolean gioiTinhNV) {
        this.gioiTinhNV = gioiTinhNV;
    }

    public String getMatKhauNV() {
        return matKhauNV;
    }

    public void setMatKhauNV(String matKhauNV) {
        this.matKhauNV = matKhauNV;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.maNV);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NhanVien other = (NhanVien) obj;
        return Objects.equals(this.maNV, other.maNV);
    }

}
