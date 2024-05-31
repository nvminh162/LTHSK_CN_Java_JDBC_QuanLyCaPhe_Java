/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Image;
import java.text.DecimalFormat;

/**
 *
 * @author nvmin
 */
public class SanPham {

    private String maSP;
    private String tenSP;
    private double giaSP;
    private byte[] hinhAnhSP;

    public SanPham() {
    }

    public SanPham(String maSP) {
        this.maSP = maSP;
    }

    public SanPham(String maSP, String tenSP, double giaSP, byte[] hinhAnhSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhAnhSP = hinhAnhSP;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(double giaSP) {
        this.giaSP = giaSP;
    }

    public byte[] getHinhAnhSP() {
        return hinhAnhSP;
    }

    public void setHinhAnhSP(byte[] hinhAnhSP) {
        this.hinhAnhSP = hinhAnhSP;
    }

}
