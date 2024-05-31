/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author nvmin
 */
public class ChiTietHoaDon {
	private HoaDon hoaDon;
	private SanPham sanPham;
	private int soLuongSP;
	private double giaSP;
	/**
	 * @param hoaDon
	 * @param sanPham
	 * @param soLuongSP
	 * @param giaSP
	 */
	public ChiTietHoaDon(HoaDon hoaDon, SanPham sanPham, int soLuongSP, double giaSP) {
		super();
		this.hoaDon = hoaDon;
		this.sanPham = sanPham;
		this.soLuongSP = soLuongSP;
		this.giaSP = giaSP;
	}
	/**
	 * 
	 */
	public ChiTietHoaDon() {
		
	}
	
	public HoaDon getHoaDon() {
		return hoaDon;
	}
	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}
	public SanPham getSanPham() {
		return sanPham;
	}
	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	public int getSoLuongSP() {
		return soLuongSP;
	}
	public void setSoLuongSP(int soLuongSP) {
		this.soLuongSP = soLuongSP;
	}
	public double getGiaSP() {
		return giaSP;
	}
	public void setGiaSP(double giaSP) {
		this.giaSP = giaSP;
	}
	@Override
	public String toString() {
		return "ChiTietHoaDon [hoaDon=" + hoaDon + ", sanPham=" + sanPham + ", soLuongSP=" + soLuongSP + ", giaSP="
				+ giaSP + "]";
	}

	
}
