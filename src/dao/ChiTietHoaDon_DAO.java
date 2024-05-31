/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author nvmin
 */
public class ChiTietHoaDon_DAO {
	
	public boolean createChiTietHD(ChiTietHoaDon chiTietHD) {
        Connection con = ConnectDB.getInstance().getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        try {
            stmt = con.prepareStatement("INSERT INTO ChiTietHoaDon VALUES (?, ?, ?, ?)");
            stmt.setString(1, chiTietHD.getHoaDon().getMaHD());
            stmt.setString(2, chiTietHD.getSanPham().getMaSP());
            stmt.setInt(3, chiTietHD.getSoLuongSP());
            stmt.setDouble(4, chiTietHD.getGiaSP());

            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

	
}
