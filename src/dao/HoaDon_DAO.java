package dao;

import connectDB.ConnectDB;
import entity.Ban;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class HoaDon_DAO {

    //thong ke
    public int soLuongHoaDonTrongNgay() throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        String sql = "SELECT COUNT(maHD) FROM HoaDon "
                + "WHERE DAY(ngayTaoHD) = DAY(GETDATE()) AND "
                + "MONTH(ngayTaoHD) = MONTH(GETDATE()) AND "
                + "YEAR(ngayTaoHD) = YEAR(GETDATE())";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        try {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

    public String taoMaHoaDonTheoNgay() throws SQLException {
        Date currentDate = new Date();
        String dateString = dateFormat.format(currentDate);
        int soLuongHoaDon = soLuongHoaDonTrongNgay();
        return dateString + String.format("%05d", soLuongHoaDon + 1);
    }

    public boolean createHD(HoaDon hd) throws SQLException {
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        String sql = "INSERT INTO HoaDon VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, hd.getMaHD());
            stmt.setString(2, hd.getBan().getMaBan());
            stmt.setString(3, hd.getKhachHang().getMaKH());
            stmt.setString(4, hd.getNhanVien().getMaNV());
            stmt.setInt(5, hd.getTongSoSP());
            stmt.setDouble(6, hd.getGiamGiaHD());
            stmt.setDouble(7, hd.getTongThanhTienHD());
            stmt.setDate(8, (java.sql.Date) hd.getNgayTaoHoaDon());

            n = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public ArrayList<HoaDon> getAllTableHD() {
        ArrayList<HoaDon> danhSachHD = new ArrayList<HoaDon>();
        try {
            ConnectDB.getInstance().connect();
            Connection connection = ConnectDB.getConnection();
            String sql = "SELECT * FROM HoaDon";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String maHD = resultSet.getString(1);
                String maBan = resultSet.getString(2);
                String maKH = resultSet.getString(3);
                String maNV = resultSet.getString(4);
                int soLuongSP = resultSet.getInt(5);
                double giamGiaHD = resultSet.getDouble(6);
                double thanhTienHD = resultSet.getDouble(7);
                Date ngayTaoHD = resultSet.getDate(8);
                HoaDon hoaDon = new HoaDon(maHD, new Ban(maBan), new KhachHang(maKH), new NhanVien(maNV), soLuongSP, giamGiaHD, thanhTienHD, ngayTaoHD);
                danhSachHD.add(hoaDon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachHD;
    }

    public double calculateTotalRevenue() {
        double totalRevenue = 0.0;
        try {
            ConnectDB.getInstance().connect();
            Connection connection = ConnectDB.getConnection();
            String sql = "SELECT SUM(tongThanhTienHD) AS totalRevenue FROM HoaDon";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble("totalRevenue");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalRevenue;
    }

    public ArrayList<String> getHoaDonCodes() throws SQLException {
        ArrayList<String> hoaDonCodes = new ArrayList<>();
        ConnectDB.getInstance().connect();
        Connection connection = ConnectDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectDB.getInstance().getConnection();
            String sql = "SELECT maHD FROM HoaDon";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String maHD = resultSet.getString("maHD");
                hoaDonCodes.add(maHD);
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
        return hoaDonCodes;
    }

}
