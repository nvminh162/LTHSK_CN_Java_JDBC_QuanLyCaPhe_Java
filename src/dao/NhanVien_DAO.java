/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectDB.ConnectDB;
import entity.KhachHang;
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
public class NhanVien_DAO {

    public NhanVien_DAO() {
    }

    public ArrayList<NhanVien> getAllTableNhanVien() {
        ArrayList<NhanVien> danhSachNV = new ArrayList<NhanVien>();
        try {
            ConnectDB.getInstance().connect();
            Connection connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM NhanVien";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String maNV = resultSet.getString(1);
                String tenNV = resultSet.getString(2);
                String diaChiNV = resultSet.getString(3);
                String soDienThoaiNV = resultSet.getString(4);
                boolean gioiTinhNV = resultSet.getBoolean(5);
                String matKhauNV = resultSet.getString(6);
                NhanVien nhanVien = new NhanVien(maNV, tenNV, diaChiNV, soDienThoaiNV, gioiTinhNV, matKhauNV);
                danhSachNV.add(nhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNV;
    }

    public boolean updateNhanVienPwd(NhanVien nhanVien) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        int n = 0;
        String sql = "UPDATE NhanVien set matKhauNV WHERE maNV = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nhanVien.getMatKhauNV());
            preparedStatement.setString(2, nhanVien.getMaNV());
            n = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

    public ArrayList<NhanVien> getNhanVienTheoMa(String maNV) {
        ArrayList<NhanVien> danhSachNhanVien = new ArrayList<NhanVien>();
        ConnectDB.getInstance();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM NhanVien where maNV = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maNV);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String maNVien = resultSet.getString(1);
                String tenNV = resultSet.getString(2);
                String diaChiNV = resultSet.getString(3);
                String soDienThoaiNV = resultSet.getString(4);
                boolean gioiTinhNV = resultSet.getBoolean(5);
                String matKhauNV = resultSet.getString(6);
                NhanVien nhanVien = new NhanVien(maNVien, tenNV, diaChiNV, soDienThoaiNV, gioiTinhNV, matKhauNV);
                danhSachNhanVien.add(nhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachNhanVien;
    }
}
