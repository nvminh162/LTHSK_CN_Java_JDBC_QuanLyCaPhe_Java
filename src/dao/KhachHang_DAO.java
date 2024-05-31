/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectDB.ConnectDB;
import entity.Ban;
import entity.KhachHang;
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
public class KhachHang_DAO {

    public KhachHang_DAO() {

    }

    public ArrayList<KhachHang> getAllTableKhachHang() {
        ArrayList<KhachHang> danhSachKH = new ArrayList<KhachHang>();
        try {
            ConnectDB.getInstance().connect();
            Connection connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM KhachHang";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String maKH = resultSet.getString(1);
                String tenKH = resultSet.getString(2);
                String soDienThoaiKH = resultSet.getString(3);
                boolean gioiTinhKH = resultSet.getBoolean(4);
                String diaChiKH = resultSet.getString(5);
                String loaiKH = resultSet.getString(6);
                KhachHang khachHang = new KhachHang(maKH, tenKH, soDienThoaiKH, gioiTinhKH, diaChiKH, loaiKH);
                danhSachKH.add(khachHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachKH;
    }

    public boolean createCustomer(KhachHang khachHang) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        int n = 0;
        String sql = "INSERT INTO KhachHang VALUES(?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, khachHang.getMaKH());
            preparedStatement.setString(2, khachHang.getTenKH());
            preparedStatement.setString(3, khachHang.getSoDienThoaiKH());
            preparedStatement.setBoolean(4, khachHang.isGioiTinhKH());
            preparedStatement.setString(5, khachHang.getDiaChiKH());
            preparedStatement.setString(6, khachHang.getLoaiKH());
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

    public boolean deleteCustomer(String maKH) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM KhachHang WHERE maKH = ?";
        int n = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maKH);
            n = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public boolean updateCustomer(KhachHang khachHang) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        int n = 0;
        String sql = "UPDATE KhachHang set tenKH = ?, gioiTinhKH = ?, soDienThoaiKH = ?, diaChiKH = ?, loaiKH = ? WHERE maKH = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, khachHang.getTenKH());
            preparedStatement.setBoolean(2, khachHang.isGioiTinhKH());
            preparedStatement.setString(3, khachHang.getSoDienThoaiKH());
            preparedStatement.setString(4, khachHang.getDiaChiKH());
            preparedStatement.setString(5, khachHang.getLoaiKH());
            preparedStatement.setString(6, khachHang.getMaKH());
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

    public ArrayList<KhachHang> searchByIDCustomer(String maKH) throws SQLException {
        ArrayList<KhachHang> danhSachKH = new ArrayList<KhachHang>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM KhachHang WHERE maKH = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maKH);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String tenKH = resultSet.getString("tenKH");
                String soDienThoaiKH = resultSet.getString("soDienThoaiKH");
                boolean gioiTinhKH = resultSet.getBoolean("gioiTinhKH");
                String diaChiKH = resultSet.getString("diaChiKH");
                String loaiKH = resultSet.getString("loaiKH");
                KhachHang khachHang = new KhachHang(maKH, tenKH, soDienThoaiKH, gioiTinhKH, diaChiKH, loaiKH);
                danhSachKH.add(khachHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachKH;
    }

    public ArrayList<KhachHang> searchByPhoneNumber(String soDienThoai) throws SQLException {
        ArrayList<KhachHang> danhSachKH = new ArrayList<KhachHang>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM KhachHang WHERE soDienThoaiKH = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, soDienThoai);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String maKH = resultSet.getString("maKH");
                String tenKH = resultSet.getString("tenKH");
                boolean gioiTinhKH = resultSet.getBoolean("gioiTinhKH");
                String diaChiKH = resultSet.getString("diaChiKH");
                String loaiKH = resultSet.getString("loaiKH");
                KhachHang khachHang = new KhachHang(maKH, tenKH, soDienThoai, gioiTinhKH, diaChiKH, loaiKH);
                danhSachKH.add(khachHang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return danhSachKH;
    }

    public ArrayList<String> getCustomerCodes() throws SQLException {
        ArrayList<String> customerCodes = new ArrayList<>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectDB.getInstance().getConnection();
            String sql = "SELECT maKH FROM KhachHang";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String maKH = resultSet.getString("maKH");
                customerCodes.add(maKH);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customerCodes;
    }

    public ArrayList<String> getCustomerNames() throws SQLException {
        ArrayList<String> customerNames = new ArrayList<>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectDB.getInstance().getConnection();
            String sql = "SELECT tenKH FROM KhachHang";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String tenKH = resultSet.getString("tenKH");
                customerNames.add(tenKH);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customerNames;
    }
}
