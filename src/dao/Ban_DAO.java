/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectDB.ConnectDB;
import entity.Ban;
import entity.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author nvmin
 */
public class Ban_DAO {

    public Ban_DAO() {

    }

    public ArrayList<Ban> getAllTableBan() {
        ArrayList<Ban> danhSachBan = new ArrayList<Ban>();
        try {
            ConnectDB.getInstance().connect();
            Connection connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM Ban";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String maBan = resultSet.getString(1);
                String tenBan = resultSet.getString(2);
                int soLuong = resultSet.getInt(3);
                String trangThai = resultSet.getString(4);
                Ban ban = new Ban(maBan, tenBan, soLuong, trangThai);
                danhSachBan.add(ban);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachBan;
    }

    public boolean createTable(Ban ban) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        int n = 0;
        String sql = "INSERT INTO Ban VALUES(?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ban.getMaBan());
            preparedStatement.setString(2, ban.getTenBan());
            preparedStatement.setInt(3, ban.getSoLuongNguoi());
            preparedStatement.setString(4, ban.getTrangThaiBan());
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

//    public boolean deleteTable(String maBan) throws SQLException {
//        ConnectDB.getInstance().connect();
//        Connection connection = ConnectDB.getConnection();
//        PreparedStatement preparedStatement = null;
//        String sql = "DELETE FROM Ban WHERE maBan = ?";
//        int n = 0;
//        try {
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, maBan);
//            n = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return n > 0;
//    }
    public boolean deleteTable(String maBan) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM Ban WHERE maBan = ?";
        int n = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maBan);
            n = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Xử lý ngoại lệ ở đây
//            if (e.getSQLState().equals("23503")) { // Là mã lỗi của ràng buộc khóa ngoại
////                System.out.println("Không thể xóa bàn vì có hóa đơn liên quan đến bàn này.");
//                JOptionPane.showMessageDialog(null, "Không thể xóa bàn vì có hóa đơn liên quan đến bàn này.", "Error", JOptionPane.ERROR_MESSAGE);
//            } else {
//                e.printStackTrace();
//            }
            JOptionPane.showMessageDialog(null, "Không thể xóa bàn vì có hóa đơn liên quan đến bàn này.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Đóng preparedStatement và connection
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return n > 0;
    }

    public boolean updateTable(Ban ban) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        int n = 0;
        String sql = "UPDATE Ban set tenBan = ?, soLuongNguoi = ?, trangThaiBan = ? WHERE maBan = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ban.getTenBan());
            preparedStatement.setInt(2, ban.getSoLuongNguoi());
            preparedStatement.setString(3, ban.getTrangThaiBan());
            preparedStatement.setString(4, ban.getMaBan());
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

    public boolean updateStatus(String trangThai, String tenBan) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        int n = 0;
        String sql = "UPDATE Ban SET trangThaiBan = ? WHERE tenBan = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, trangThai);
            preparedStatement.setString(2, tenBan);
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

    public ArrayList<Ban> searchByIDTable(String maBan) throws SQLException {
        ArrayList<Ban> danhSachBan = new ArrayList<Ban>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM Ban WHERE maBan = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, maBan);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String tenSP = resultSet.getString("tenBan");
                int soLuong = resultSet.getInt("soLuongNguoi");
                String trangThai = resultSet.getString("trangThaiBan");
                Ban ban = new Ban(maBan, tenSP, soLuong, trangThai);
                danhSachBan.add(ban);
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
        return danhSachBan;
    }

    public ArrayList<String> getTableCodes() throws SQLException {
        ArrayList<String> tableCodes = new ArrayList<>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectDB.getInstance().getConnection();
            String sql = "SELECT maBan FROM Ban";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String maBan = resultSet.getString("maBan");
                tableCodes.add(maBan);
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
        return tableCodes;
    }

    public ArrayList<String> getTableNames() throws SQLException {
        ArrayList<String> tableNames = new ArrayList<>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectDB.getInstance().getConnection();
            String sql = "SELECT tenBan FROM Ban";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String tenBan = resultSet.getString("tenBan");
                tableNames.add(tenBan);
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
        return tableNames;
    }
}
