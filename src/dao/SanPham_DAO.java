/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectDB.ConnectDB;
import static connectDB.ConnectDB.connection;
import entity.KhachHang;
import entity.SanPham;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author nvmin
 */
public class SanPham_DAO {

    public SanPham_DAO() {
    }

    public ArrayList<SanPham> getAllTableSanPham() {
        ArrayList<SanPham> danhSachSP = new ArrayList<SanPham>();
        try {
            ConnectDB.getInstance().connect();
            Connection connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM SanPham";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String maSP = resultSet.getString("maSP");
                String tenSP = resultSet.getString("tenSP");
                double giaSP = resultSet.getDouble("giaSP");
                byte[] hinhAnhSP = resultSet.getBytes("hinhAnhSP");
                SanPham sanPham = new SanPham(maSP, tenSP, giaSP, hinhAnhSP);
                danhSachSP.add(sanPham);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachSP;
    }

    public boolean createProduct(SanPham sanPham) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        int n = 0;
        String sql = "INSERT INTO SanPham VALUES(?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sanPham.getMaSP());
            preparedStatement.setString(2, sanPham.getTenSP());
            preparedStatement.setDouble(3, sanPham.getGiaSP());
            preparedStatement.setBytes(4, sanPham.getHinhAnhSP());
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

    public ArrayList<SanPham> searchByID(String maSP) throws SQLException {
        ArrayList<SanPham> danhSachSanPham = new ArrayList<SanPham>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM SanPham WHERE maSP = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maSP);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String tenSP = resultSet.getString("tenSP");
                double giaSP = resultSet.getDouble("giaSP");
                byte[] hinhAnhSP = resultSet.getBytes("hinhAnhSP");
                SanPham sanPham = new SanPham(maSP, tenSP, giaSP, hinhAnhSP);
                danhSachSanPham.add(sanPham);
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
        return danhSachSanPham;
    }

    public boolean updateProduct(SanPham sanPham) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        int n = 0;
        String sql = "UPDATE SanPham set tenSP = ?, giaSP = ?, hinhAnhSP = ? WHERE maSP = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sanPham.getTenSP());
            preparedStatement.setDouble(2, sanPham.getGiaSP());
            preparedStatement.setBytes(3, sanPham.getHinhAnhSP());
            preparedStatement.setString(4, sanPham.getMaSP());
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

//    public boolean deleteProduct(String maSP) throws SQLException {
//        ConnectDB.getInstance().connect();
//        Connection connection = ConnectDB.getConnection();
//        PreparedStatement preparedStatement = null;
//        String sql = "DELETE FROM SanPham WHERE maSP = ?";
//        int n = 0;
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, maSP);
//            n = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return n > 0;
//    }
    public boolean deleteProduct(String maSP) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM SanPham WHERE maSP = ?";
        int n = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maSP);
            n = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Xử lý lỗi
//            e.printStackTrace(); // Bạn có thể thay thế bằng các hành động khác, ví dụ như ghi log, hiển thị cảnh báo cho người dùng, hoặc thực hiện các xử lý phù hợp khác.
            JOptionPane.showMessageDialog(null, "Không thể xoá sản phẩm này vì nó đã được sử dụng ở nơi khác!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Đóng tài nguyên
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            ConnectDB.getInstance().disconnect(); // Đảm bảo đóng kết nối sau khi thực hiện xong
        }
        return n > 0;
    }

    public ArrayList<String> getProductCodes() throws SQLException {
        ArrayList<String> productCodes = new ArrayList<>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectDB.getInstance().getConnection();
            String sql = "SELECT maSP FROM SanPham";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String maSP = resultSet.getString("maSP");
                productCodes.add(maSP);
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
        return productCodes;
    }

    public ArrayList<String> getProductNames() throws SQLException {
        ArrayList<String> productNames = new ArrayList<>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectDB.getInstance().getConnection();
            String sql = "SELECT tenSP FROM SanPham";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String tenSP = resultSet.getString("tenSP");
                productNames.add(tenSP);
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
        return productNames;
    }

    public String getMaSPByTenSP(String tenSP) throws SQLException {
        String maSP = null;
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT maSP FROM SanPham WHERE tenSP = ?");) {
            preparedStatement.setNString(1, tenSP);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    maSP = resultSet.getString(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maSP;
    }

}
