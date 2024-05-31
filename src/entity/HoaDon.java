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
public class HoaDon {

    private String maHD;
    private Ban ban;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private int tongSoSP;
    private double giamGiaHD;
    private double tongThanhTienHD;
    private Date ngayTaoHoaDon;
	/**
	 * @param maHD
	 * @param ban
	 * @param khachHang
	 * @param nhanVien
	 * @param tongSoSP
	 * @param giamGiaHD
	 * @param tongThanhTienHD
	 * @param ngayTaoHoaDon
	 */
	public HoaDon(String maHD, Ban ban, KhachHang khachHang, NhanVien nhanVien, int tongSoSP, double giamGiaHD,
			double tongThanhTienHD, Date ngayTaoHoaDon) {
		super();
		this.maHD = maHD;
		this.ban = ban;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
		this.tongSoSP = tongSoSP;
		this.giamGiaHD = giamGiaHD;
		this.tongThanhTienHD = tongThanhTienHD;
		this.ngayTaoHoaDon = ngayTaoHoaDon;
	}
	/**
	 * 
	 */
	public HoaDon() {
		
	}
	
	/**
	 * @param maHD
	 */
	public HoaDon(String maHD) {
		super();
		this.maHD = maHD;
	}
	public String getMaHD() {
		return maHD;
	}
	public void setMaHD(String maHD) {
		this.maHD = maHD;
	}
	public Ban getBan() {
		return ban;
	}
	public void setBan(Ban ban) {
		this.ban = ban;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public int getTongSoSP() {
		return tongSoSP;
	}
	public void setTongSoSP(int tongSoSP) {
		this.tongSoSP = tongSoSP;
	}
	public double getGiamGiaHD() {
		return giamGiaHD;
	}
	public void setGiamGiaHD(double giamGiaHD) {
		this.giamGiaHD = giamGiaHD;
	}
	public double getTongThanhTienHD() {
		return tongThanhTienHD;
	}
	public void setTongThanhTienHD(double tongThanhTienHD) {
		this.tongThanhTienHD = tongThanhTienHD;
	}
	public Date getNgayTaoHoaDon() {
		return ngayTaoHoaDon;
	}
	public void setNgayTaoHoaDon(Date ngayTaoHoaDon) {
		this.ngayTaoHoaDon = ngayTaoHoaDon;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maHD);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoaDon other = (HoaDon) obj;
		return Objects.equals(maHD, other.maHD);
	}
	@Override
	public String toString() {
		return "HoaDon [maHD=" + maHD + ", ban=" + ban + ", khachHang=" + khachHang + ", nhanVien=" + nhanVien
				+ ", tongSoSP=" + tongSoSP + ", giamGiaHD=" + giamGiaHD + ", tongThanhTienHD=" + tongThanhTienHD
				+ ", ngayTaoHoaDon=" + ngayTaoHoaDon + "]";
	}
    
	
    

}
