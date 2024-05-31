/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ui;

import connectDB.ConnectDB;
import dao.Ban_DAO;
import dao.HoaDon_DAO;
import dao.NhanVien_DAO;
import dao.SanPham_DAO;
import entity.Ban;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nvmin
 */
public class Menu_5__QuanLy_Internal_GUI extends javax.swing.JInternalFrame {

    private final SanPham_DAO sanPham_DAO;
    private final Ban_DAO ban_DAO;
    private final NhanVien_DAO nhanVien_DAO;
    private final HoaDon_DAO hoaDon_DAO;
    private static String FILENAME = null;
    public byte[] image_product = null;

    /**
     * Creates new form NewJInternalFrame_Menu_5
     */
    public Menu_5__QuanLy_Internal_GUI() throws SQLException {
        initComponents();
        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sanPham_DAO = new SanPham_DAO();
        ban_DAO = new Ban_DAO();
        nhanVien_DAO = new NhanVien_DAO();
        hoaDon_DAO = new HoaDon_DAO();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        jInternalFrame_product.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI uiProduct = (BasicInternalFrameUI) jInternalFrame_product.getUI();
        uiProduct.setNorthPane(null);
        jInternalFrame_product.setSize(jPanel_container.getSize());

        jInternalFrame_table.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI uiTable = (BasicInternalFrameUI) jInternalFrame_table.getUI();
        uiTable.setNorthPane(null);
        jInternalFrame_table.setSize(jPanel_container.getSize());

        jInternalFrame_history.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI uiHis = (BasicInternalFrameUI) jInternalFrame_history.getUI();
        uiHis.setNorthPane(null);
        jInternalFrame_history.setSize(jPanel_container.getSize());

        displayListProduct();
//        insertID_update();
        insertID_product();

        //table
        displayListTable();
        insertID_table();

        jInternalFrame_employee.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI uiEm = (BasicInternalFrameUI) jInternalFrame_employee.getUI();
        uiEm.setNorthPane(null);

        displayListHistory();
    }

    public void displayListProduct() {
        DefaultTableModel model = (DefaultTableModel) jTable_product.getModel();
        ArrayList<SanPham> danhSachSanPham = sanPham_DAO.getAllTableSanPham();
        for (SanPham sanPham : danhSachSanPham) {
            model.addRow(new Object[]{sanPham.getMaSP(), sanPham.getTenSP(), sanPham.getGiaSP()});
        }

        //render
        for (int i = 0; i < jTable_product.getColumnCount(); i++) {
            this.jTable_product.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
            });
        }
    }

    public void displayListHistory() {
        DefaultTableModel model = (DefaultTableModel) jTable_history.getModel();
        ArrayList<HoaDon> danhSachHD = hoaDon_DAO.getAllTableHD();
        for (HoaDon hoaDon : danhSachHD) {
            model.addRow(new Object[]{hoaDon.getMaHD(), hoaDon.getBan().getMaBan(), "Khách vãng lai", hoaDon.getNhanVien().getMaNV(), hoaDon.getTongSoSP(), hoaDon.getGiamGiaHD(), hoaDon.getTongThanhTienHD(), hoaDon.getNgayTaoHoaDon()});
        }

        for (int i = 0; i < jTable_history.getColumnCount(); i++) {
            this.jTable_history.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
            });
        }
    }

//    public void insertID_update() throws SQLException {
//        jComboBox_maSP_update.removeAllItems();
//        ArrayList<String> productCodes = sanPham_DAO.getProductCodes();
//        for (String productCode : productCodes) {
//            jComboBox_maSP_update.addItem(productCode);
//        }
//    }
    public void insertID_product() throws SQLException {
        jComboBox_maSP_update.removeAllItems();
        ArrayList<String> productCodes = sanPham_DAO.getProductCodes();
        if (productCodes != null) { // Kiểm tra xem ArrayList không phải là null
            for (String productCode : productCodes) {
                if (productCode != null) {
                    jComboBox_maSP_update.addItem(productCode);
                }

            }
        }
    }

    //************************************************************************//
    public void displayListTable() {
        DefaultTableModel model = (DefaultTableModel) jTable_table.getModel();
        ArrayList<Ban> danhSachBan = ban_DAO.getAllTableBan();
        for (Ban ban : danhSachBan) {
            model.addRow(new Object[]{ban.getMaBan(), ban.getTenBan(), ban.getSoLuongNguoi(), ban.getTrangThaiBan()});
        }

        //render
        for (int i = 0; i < jTable_table.getColumnCount(); i++) {
            this.jTable_table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
            });
        }
    }

    public void insertID_table() throws SQLException {
        jComboBox_maSP_update_table.removeAllItems();
        ArrayList<String> tableCodes = ban_DAO.getTableCodes();
        if (tableCodes != null) { // Kiểm tra xem ArrayList không phải là null
            for (String tableCode : tableCodes) {
                if (tableCode != null) {
                    jComboBox_maSP_update_table.addItem(tableCode);
                }

            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame_product = new javax.swing.JInternalFrame();
        jPanel_wrapper = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_product = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField_tenSP = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField_giaSP = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel_hinhAnhSP = new javax.swing.JLabel();
        jButton_add = new javax.swing.JButton();
        jButton_update = new javax.swing.JButton();
        jButton_choose = new javax.swing.JButton();
        jDialog_update = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel_container_dialog = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jComboBox_maSP_update = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jTextField_tenSP_update = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField_giaSP_update = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel_hinhAnhSP_update = new javax.swing.JLabel();
        jButton_confirm = new javax.swing.JButton();
        jButton_choose_update = new javax.swing.JButton();
        jButton_delete = new javax.swing.JButton();
        jInternalFrame_table = new javax.swing.JInternalFrame();
        jPanel_wrapper_table = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_table = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTextField_tenBan = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jButton_add_table = new javax.swing.JButton();
        jButton_update_table = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList_soLuong = new javax.swing.JList<>();
        jDialog_table = new javax.swing.JDialog();
        jPanel19 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel_container_dialog1 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jComboBox_maSP_update_table = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jTextField_tenSP_update_table = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jButton_confirm_table = new javax.swing.JButton();
        jButton_delete1 = new javax.swing.JButton();
        jComboBox_soLuong_update_table = new javax.swing.JComboBox<>();
        jComboBox_trangThai_update_table = new javax.swing.JComboBox<>();
        jInternalFrame_employee = new javax.swing.JInternalFrame();
        jPanel_container_em = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPanel23 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jPanel24 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPasswordField3 = new javax.swing.JPasswordField();
        jPanel25 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jInternalFrame_history = new javax.swing.JInternalFrame();
        jPanel27 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable_history = new javax.swing.JTable();
        jPanel_container = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel_product = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel_table = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel_customer = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel_chart = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel_chart1 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();

        jInternalFrame_product.setVisible(true);

        jPanel_wrapper.setLayout(new java.awt.GridLayout(0, 2));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setPreferredSize(new java.awt.Dimension(662, 50));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Thông tin sản phẩm");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jTable_product.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTable_product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_product.setRowHeight(50);
        jTable_product.setSelectionBackground(new java.awt.Color(0, 102, 51));
        jTable_product.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jTable_product.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_productMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_product);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
        );

        jPanel5.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel_wrapper.add(jPanel5);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setPreferredSize(new java.awt.Dimension(662, 50));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Quản lý sản phẩm");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Tên sản phẩm");

        jTextField_tenSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Giá");

        jTextField_giaSP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField_giaSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_giaSPKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Hình ảnh");

        jLabel_hinhAnhSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel_hinhAnhSP, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_hinhAnhSP, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        jButton_add.setBackground(new java.awt.Color(0, 102, 51));
        jButton_add.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_add.setForeground(new java.awt.Color(255, 255, 255));
        jButton_add.setText("Thêm");
        jButton_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_addActionPerformed(evt);
            }
        });

        jButton_update.setBackground(new java.awt.Color(0, 51, 255));
        jButton_update.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_update.setForeground(new java.awt.Color(255, 255, 255));
        jButton_update.setText("Cập nhật");
        jButton_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_updateActionPerformed(evt);
            }
        });

        jButton_choose.setBackground(new java.awt.Color(255, 255, 254));
        jButton_choose.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton_choose.setText("Choose");
        jButton_choose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_chooseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_choose)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField_tenSP, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_giaSP, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addComponent(jButton_add, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(20, 20, 20))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_tenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTextField_giaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_choose, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_add, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_update, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel_wrapper.add(jPanel7);

        jInternalFrame_product.getContentPane().add(jPanel_wrapper, java.awt.BorderLayout.CENTER);

        jDialog_update.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog_update.setModal(true);

        jPanel12.setBackground(new java.awt.Color(0, 102, 51));
        jPanel12.setPreferredSize(new java.awt.Dimension(600, 80));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Cập nhật sản phẩm");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jDialog_update.getContentPane().add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel_container_dialog.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_container_dialog.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 30, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Mã sản phẩm");

        jComboBox_maSP_update.setBackground(new java.awt.Color(255, 255, 254));
        jComboBox_maSP_update.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox_maSP_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_maSP_updateMouseClicked(evt);
            }
        });
        jComboBox_maSP_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_maSP_updateActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Tên sản phẩm");

        jTextField_tenSP_update.setBackground(new java.awt.Color(255, 255, 254));
        jTextField_tenSP_update.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Giá sản phẩm");

        jTextField_giaSP_update.setBackground(new java.awt.Color(255, 255, 254));
        jTextField_giaSP_update.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField_giaSP_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_giaSP_updateActionPerformed(evt);
            }
        });
        jTextField_giaSP_update.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_giaSP_updateKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Hình ảnh");

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel_hinhAnhSP_update.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_hinhAnhSP_update.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel_hinhAnhSP_update.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_hinhAnhSP_update, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_hinhAnhSP_update, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
        );

        jButton_confirm.setBackground(new java.awt.Color(0, 102, 51));
        jButton_confirm.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_confirm.setForeground(new java.awt.Color(255, 255, 255));
        jButton_confirm.setText("Xác nhận");
        jButton_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirmActionPerformed(evt);
            }
        });

        jButton_choose_update.setBackground(new java.awt.Color(255, 255, 254));
        jButton_choose_update.setText("Choose");
        jButton_choose_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_choose_updateActionPerformed(evt);
            }
        });

        jButton_delete.setBackground(new java.awt.Color(255, 0, 51));
        jButton_delete.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton_delete.setForeground(new java.awt.Color(255, 255, 255));
        jButton_delete.setText("Xoá");
        jButton_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_container_dialogLayout = new javax.swing.GroupLayout(jPanel_container_dialog);
        jPanel_container_dialog.setLayout(jPanel_container_dialogLayout);
        jPanel_container_dialogLayout.setHorizontalGroup(
            jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_container_dialogLayout.createSequentialGroup()
                .addGroup(jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_container_dialogLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_maSP_update, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField_tenSP_update)
                            .addComponent(jTextField_giaSP_update)))
                    .addGroup(jPanel_container_dialogLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_choose_update)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel_container_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_confirm, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jButton_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel_container_dialogLayout.setVerticalGroup(
            jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_container_dialogLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox_maSP_update, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_tenSP_update, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_giaSP_update, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addGroup(jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_container_dialogLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_container_dialogLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_container_dialogLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_choose_update, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel_container_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_confirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_delete, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jDialog_update.getContentPane().add(jPanel_container_dialog, java.awt.BorderLayout.CENTER);

        jInternalFrame_table.setVisible(true);

        jPanel_wrapper_table.setLayout(new java.awt.GridLayout(0, 2));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel13.setPreferredSize(new java.awt.Dimension(662, 50));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Thông tin bàn");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel13, java.awt.BorderLayout.PAGE_START);

        jTable_table.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTable_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã bàn", "Tên bàn", "Số lượng", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_table.setRowHeight(50);
        jTable_table.setSelectionBackground(new java.awt.Color(0, 102, 51));
        jTable_table.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jTable_table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_tableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable_table);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 662, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
        );

        jPanel10.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel_wrapper_table.add(jPanel10);

        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel17.setPreferredSize(new java.awt.Dimension(662, 50));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Quản lý bàn");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 660, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel17, java.awt.BorderLayout.PAGE_START);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setText("Tên bàn");

        jTextField_tenBan.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("Số lượng người");

        jButton_add_table.setBackground(new java.awt.Color(0, 102, 51));
        jButton_add_table.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_add_table.setForeground(new java.awt.Color(255, 255, 255));
        jButton_add_table.setText("Thêm");
        jButton_add_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_add_tableActionPerformed(evt);
            }
        });

        jButton_update_table.setBackground(new java.awt.Color(0, 51, 255));
        jButton_update_table.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_update_table.setForeground(new java.awt.Color(255, 255, 255));
        jButton_update_table.setText("Cập nhật");
        jButton_update_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_update_tableActionPerformed(evt);
            }
        });

        jList_soLuong.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jList_soLuong.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "2", "4", "8", "12", "" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList_soLuong.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList_soLuong.setSelectionBackground(new java.awt.Color(0, 102, 51));
        jList_soLuong.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane4.setViewportView(jList_soLuong);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addComponent(jTextField_tenBan, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                        .addComponent(jButton_add_table, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_update_table, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 482, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_tenBan, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 275, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_add_table, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_update_table, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel16.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel_wrapper_table.add(jPanel16);

        jInternalFrame_table.getContentPane().add(jPanel_wrapper_table, java.awt.BorderLayout.CENTER);

        jDialog_table.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog_table.setModal(true);

        jPanel19.setBackground(new java.awt.Color(0, 102, 51));
        jPanel19.setPreferredSize(new java.awt.Dimension(600, 80));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Cập nhật bàn");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jDialog_table.getContentPane().add(jPanel19, java.awt.BorderLayout.PAGE_START);

        jPanel_container_dialog1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_container_dialog1.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 30, 30));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Mã bàn");

        jComboBox_maSP_update_table.setBackground(new java.awt.Color(255, 255, 254));
        jComboBox_maSP_update_table.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox_maSP_update_table.setToolTipText("");
        jComboBox_maSP_update_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_maSP_update_tableMouseClicked(evt);
            }
        });
        jComboBox_maSP_update_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_maSP_update_tableActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Tên bàn");

        jTextField_tenSP_update_table.setBackground(new java.awt.Color(255, 255, 254));
        jTextField_tenSP_update_table.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Số lượng người");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Trạng thái");

        jButton_confirm_table.setBackground(new java.awt.Color(0, 102, 51));
        jButton_confirm_table.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_confirm_table.setForeground(new java.awt.Color(255, 255, 255));
        jButton_confirm_table.setText("Xác nhận");
        jButton_confirm_table.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirm_tableActionPerformed(evt);
            }
        });

        jButton_delete1.setBackground(new java.awt.Color(255, 0, 51));
        jButton_delete1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton_delete1.setForeground(new java.awt.Color(255, 255, 255));
        jButton_delete1.setText("Xoá");
        jButton_delete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_delete1ActionPerformed(evt);
            }
        });

        jComboBox_soLuong_update_table.setBackground(new java.awt.Color(255, 255, 254));
        jComboBox_soLuong_update_table.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox_soLuong_update_table.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "4", "8", "12" }));

        jComboBox_trangThai_update_table.setBackground(new java.awt.Color(255, 255, 254));
        jComboBox_trangThai_update_table.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox_trangThai_update_table.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trống", "Hoạt động", "Đặt trước" }));

        javax.swing.GroupLayout jPanel_container_dialog1Layout = new javax.swing.GroupLayout(jPanel_container_dialog1);
        jPanel_container_dialog1.setLayout(jPanel_container_dialog1Layout);
        jPanel_container_dialog1Layout.setHorizontalGroup(
            jPanel_container_dialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_container_dialog1Layout.createSequentialGroup()
                .addGroup(jPanel_container_dialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel_container_dialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_maSP_update_table, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_tenSP_update_table)
                    .addComponent(jComboBox_soLuong_update_table, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox_trangThai_update_table, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addGroup(jPanel_container_dialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_confirm_table, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jButton_delete1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel_container_dialog1Layout.setVerticalGroup(
            jPanel_container_dialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_container_dialog1Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel_container_dialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox_maSP_update_table, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel_container_dialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_tenSP_update_table, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(jPanel_container_dialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jComboBox_soLuong_update_table))
                .addGap(20, 20, 20)
                .addGroup(jPanel_container_dialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jComboBox_trangThai_update_table))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                .addGroup(jPanel_container_dialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_confirm_table, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_delete1, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jDialog_table.getContentPane().add(jPanel_container_dialog1, java.awt.BorderLayout.CENTER);

        jInternalFrame_employee.setVisible(true);
        jInternalFrame_employee.getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel_container_em.setLayout(new java.awt.BorderLayout());

        jPanel20.setBackground(new java.awt.Color(0, 102, 51));
        jPanel20.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jPanel20.setPreferredSize(new java.awt.Dimension(1055, 80));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Tài khoản nhân viên");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 1053, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
        );

        jPanel_container_em.add(jPanel20, java.awt.BorderLayout.PAGE_START);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 300, 0, 300));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel33.setText("Mật khẩu cũ");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPasswordField1))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel34.setText("Mật khẩu mới");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel35.setText("Nhập lại mật khẩu");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPasswordField3)
                .addGap(0, 0, 0))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPasswordField3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 51, 51));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Đổi mật khẩu");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 102, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Xác nhận");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel_container_em.add(jPanel21, java.awt.BorderLayout.CENTER);

        jInternalFrame_employee.getContentPane().add(jPanel_container_em);

        jInternalFrame_history.setVisible(true);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Lịch sử hoá đơn");

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jInternalFrame_history.getContentPane().add(jPanel27, java.awt.BorderLayout.PAGE_START);

        jTable_history.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTable_history.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HD", "Mã Bàn", "Mã KH", "Mã NV", "Số lượng", "Giảm giá", "Thành tiền HD", "Ngày tạo HD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_history.setRowHeight(50);
        jTable_history.setSelectionBackground(new java.awt.Color(0, 102, 51));
        jTable_history.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jTable_history.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(jTable_history);
        if (jTable_history.getColumnModel().getColumnCount() > 0) {
            jTable_history.getColumnModel().getColumn(6).setResizable(false);
            jTable_history.getColumnModel().getColumn(7).setResizable(false);
        }

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        jInternalFrame_history.getContentPane().add(jPanel28, java.awt.BorderLayout.CENTER);

        setPreferredSize(new java.awt.Dimension(1274, 538));

        jPanel_container.setBackground(new java.awt.Color(204, 204, 204));
        jPanel_container.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 30, 30, 30));
        jPanel3.setLayout(new java.awt.GridLayout(0, 4, 30, 30));

        jPanel_product.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_productMouseClicked(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/product-128.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sản phẩm");

        javax.swing.GroupLayout jPanel_productLayout = new javax.swing.GroupLayout(jPanel_product);
        jPanel_product.setLayout(jPanel_productLayout);
        jPanel_productLayout.setHorizontalGroup(
            jPanel_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_productLayout.setVerticalGroup(
            jPanel_productLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_productLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel_product);

        jPanel_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_tableMouseClicked(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/table-128.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Bàn");

        javax.swing.GroupLayout jPanel_tableLayout = new javax.swing.GroupLayout(jPanel_table);
        jPanel_table.setLayout(jPanel_tableLayout);
        jPanel_tableLayout.setHorizontalGroup(
            jPanel_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_tableLayout.setVerticalGroup(
            jPanel_tableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_tableLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        jPanel3.add(jPanel_table);

        jPanel_customer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_customerMouseClicked(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/customer-128.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Khách hàng");

        javax.swing.GroupLayout jPanel_customerLayout = new javax.swing.GroupLayout(jPanel_customer);
        jPanel_customer.setLayout(jPanel_customerLayout);
        jPanel_customerLayout.setHorizontalGroup(
            jPanel_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_customerLayout.setVerticalGroup(
            jPanel_customerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_customerLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel_customer);

        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/employee-128.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Nhân viên");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel6);

        jPanel_chart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_chartMouseClicked(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/chart-128.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Thống kê");

        javax.swing.GroupLayout jPanel_chartLayout = new javax.swing.GroupLayout(jPanel_chart);
        jPanel_chart.setLayout(jPanel_chartLayout);
        jPanel_chartLayout.setHorizontalGroup(
            jPanel_chartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_chartLayout.setVerticalGroup(
            jPanel_chartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_chartLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel_chart);

        jPanel_chart1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel_chart1MouseClicked(evt);
            }
        });

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/chart-128.png"))); // NOI18N

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Lịch sử");

        javax.swing.GroupLayout jPanel_chart1Layout = new javax.swing.GroupLayout(jPanel_chart1);
        jPanel_chart1.setLayout(jPanel_chart1Layout);
        jPanel_chart1Layout.setHorizontalGroup(
            jPanel_chart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_chart1Layout.setVerticalGroup(
            jPanel_chart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_chart1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel_chart1);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(jPanel2);

        jPanel_container.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel_container, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel_customerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_customerMouseClicked
        jPanel_container.removeAll();
        Menu_4_KhachHang_Internal_GUI menu_4 = new Menu_4_KhachHang_Internal_GUI();
        jPanel_container.add(menu_4).setVisible(true);
        jPanel_container.revalidate();
        jPanel_container.repaint();
    }//GEN-LAST:event_jPanel_customerMouseClicked

    private void jPanel_chartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_chartMouseClicked
        jPanel_container.removeAll();
        Menu_3__ThongKe_Internal_GUI menu_3 = new Menu_3__ThongKe_Internal_GUI();
        jPanel_container.add(menu_3).setVisible(true);

        jPanel_container.revalidate();
        jPanel_container.repaint();
    }//GEN-LAST:event_jPanel_chartMouseClicked

    private void jPanel_productMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_productMouseClicked
        jPanel_container.removeAll();
        jPanel_container.add(jInternalFrame_product).setVisible(true);
        jPanel_container.revalidate();
        jPanel_container.repaint();
    }//GEN-LAST:event_jPanel_productMouseClicked

    private void jTable_productMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_productMouseClicked
        int row = jTable_product.getSelectedRow();
        String maSP = jTable_product.getValueAt(row, 0).toString();
        jTextField_tenSP.setText(jTable_product.getValueAt(row, 1).toString());
        jTextField_giaSP.setText(jTable_product.getValueAt(row, 2).toString());

        try {
            ArrayList<SanPham> danhSachSanPham = sanPham_DAO.searchByID(maSP);
            if (!danhSachSanPham.isEmpty()) {
                byte[] hinhAnhSP = danhSachSanPham.get(0).getHinhAnhSP();

                if (hinhAnhSP == null) {
                    jLabel_hinhAnhSP.setText("Không có hình ảnh");
                    jLabel_hinhAnhSP.setIcon(null);
                    return;
                } else {
                    image_product = hinhAnhSP;
                }

                ImageIcon imageIcon = new ImageIcon(hinhAnhSP);

                // Lấy kích thước hiện tại của jLabel_hinhAnhSP
                int width = jLabel_hinhAnhSP.getWidth();
                int height = jLabel_hinhAnhSP.getHeight();

                // Điều chỉnh kích thước của ảnh
                Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

                // Tạo ImageIcon mới với ảnh đã điều chỉnh kích thước
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                // Gắn ảnh đã điều chỉnh vào jLabel_hinhAnhSP
                jLabel_hinhAnhSP.setIcon(scaledIcon);
            }
        } catch (SQLException ex) {
//            Logger.getLogger(Menu_5__QuanLy_Internal_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTable_productMouseClicked

    private void jButton_chooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_chooseActionPerformed
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showOpenDialog(null);
        File file = jFileChooser.getSelectedFile();
        if (file != null) {
            FILENAME = file.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(FILENAME).getImage().getScaledInstance(jLabel_hinhAnhSP.getWidth(), jLabel_hinhAnhSP.getHeight(), Image.SCALE_SMOOTH));
            jLabel_hinhAnhSP.setIcon(imageIcon);
            try {
                File image = new File(FILENAME);
                FileInputStream fis = new FileInputStream(image);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
                image_product = bos.toByteArray();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh", "Selection", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton_chooseActionPerformed

    private void jButton_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_addActionPerformed
        String maSP = generateProductCode();
        String tenSP = jTextField_tenSP.getText().trim();
        double giaSP = Double.parseDouble(jTextField_giaSP.getText().trim());
        byte[] hinhAnhSP = image_product;

        if (isProductNameUnique(tenSP)) {
            SanPham sanPham = new SanPham(maSP, tenSP, giaSP, hinhAnhSP);
            try {
                if (sanPham_DAO.createProduct(sanPham)) {
                    JOptionPane.showMessageDialog(this, "Thêm " + tenSP + " vào danh mục sản phẩm", "Success", JOptionPane.WARNING_MESSAGE);
                    DocDuLieuDatabaseVaoTable();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tên sản phẩm đã tồn tại!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton_addActionPerformed

    private void jButton_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_updateActionPerformed
        try {
            insertID_product();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Menu_5__QuanLy_Internal_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        jDialog_update.setSize(650, 750);
        jDialog_update.setLocationRelativeTo(null);
        jDialog_update.setVisible(true);
    }//GEN-LAST:event_jButton_updateActionPerformed

    private void jTextField_giaSPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_giaSPKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_giaSPKeyTyped

    private void jComboBox_maSP_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_maSP_updateActionPerformed
        jLabel_hinhAnhSP_update.setText("");
        String maSP = (String) jComboBox_maSP_update.getSelectedItem();
        try {
            ArrayList<SanPham> danhSachSanPham = sanPham_DAO.searchByID(maSP);
            if (!danhSachSanPham.isEmpty()) {
                SanPham sanPham = danhSachSanPham.get(0);
                jTextField_tenSP_update.setText(sanPham.getTenSP());
                jTextField_giaSP_update.setText(String.valueOf(sanPham.getGiaSP()));
                byte[] hinhAnhSP = sanPham.getHinhAnhSP();

                if (hinhAnhSP == null) {
                    jLabel_hinhAnhSP_update.setText("Không có hình ảnh");
                    jLabel_hinhAnhSP_update.setIcon(null);
                    return;
                } else {
                    image_product = hinhAnhSP;
                }

                int width = jLabel_hinhAnhSP_update.getWidth();
                int height = jLabel_hinhAnhSP_update.getHeight();
                if (width == 0 || height == 0) {
                    return;
                }

                ImageIcon imageIcon = new ImageIcon(hinhAnhSP);
                Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                jLabel_hinhAnhSP_update.setText("");
                jLabel_hinhAnhSP_update.setIcon(scaledIcon);
            } else {
                jTextField_tenSP_update.setText("");
                jTextField_giaSP_update.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jComboBox_maSP_updateActionPerformed

    private void jButton_choose_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_choose_updateActionPerformed
        jLabel_hinhAnhSP_update.setText("");
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.showOpenDialog(null);
        File file = jFileChooser.getSelectedFile();
        if (file != null) {
            FILENAME = file.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(FILENAME).getImage().getScaledInstance(jLabel_hinhAnhSP_update.getWidth(), jLabel_hinhAnhSP.getHeight(), Image.SCALE_SMOOTH));
            jLabel_hinhAnhSP_update.setIcon(imageIcon);
            try {
                File image = new File(FILENAME);
                FileInputStream fis = new FileInputStream(image);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
                image_product = bos.toByteArray();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh", "Selection", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton_choose_updateActionPerformed

    private void jTextField_giaSP_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_giaSP_updateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_giaSP_updateActionPerformed

    private void jComboBox_maSP_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_maSP_updateMouseClicked
        jLabel_hinhAnhSP_update.setText("");
        String maSP = jComboBox_maSP_update.getSelectedItem().toString();
        try {
            ArrayList<SanPham> danhSachSanPham = sanPham_DAO.searchByID(maSP);
            if (!danhSachSanPham.isEmpty()) {
                SanPham sanPham = danhSachSanPham.get(0);
                jTextField_tenSP_update.setText(sanPham.getTenSP());
                jTextField_giaSP_update.setText(String.valueOf(sanPham.getGiaSP()));
            } else {
                jTextField_tenSP_update.setText("");
                jTextField_giaSP_update.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jComboBox_maSP_updateMouseClicked

    private void jButton_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirmActionPerformed
        String maSP = jComboBox_maSP_update.getSelectedItem().toString();
        String tenSP = jTextField_tenSP_update.getText();
        String giaSPText = jTextField_giaSP_update.getText();
        if (giaSPText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập giá sản phẩm", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Chuyển đổi giá trị từ chuỗi thành số thực
        double giaSP = Double.parseDouble(giaSPText);
        byte[] hinhAnhSP = image_product;

        SanPham sanPham = new SanPham(maSP, tenSP, giaSP, hinhAnhSP);

        try {
            if (sanPham_DAO.updateProduct(sanPham)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin sản phẩm thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
                DocDuLieuDatabaseVaoTable();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin sản phẩm thất bại", "Fail", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi cập nhật thông tin sản phẩm", "Fail", JOptionPane.ERROR_MESSAGE);
        }
        jDialog_update.dispose();
    }//GEN-LAST:event_jButton_confirmActionPerformed

    private void jTextField_giaSP_updateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_giaSP_updateKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_giaSP_updateKeyTyped

    private void jButton_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deleteActionPerformed
        String maSP = jComboBox_maSP_update.getSelectedItem().toString();
        String tenSP = jTextField_tenSP_update.getText().trim();
        try {
            sanPham_DAO.deleteProduct(maSP);
//            JOptionPane.showMessageDialog(this, "Đã xoá " + tenSP + " ra danh mục sản phẩm", "Success", JOptionPane.WARNING_MESSAGE);
            DocDuLieuDatabaseVaoTable();
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(this, "Xoá sản phẩm không thành công!", "Fail", JOptionPane.WARNING_MESSAGE);
        }
        jDialog_update.dispose();
    }//GEN-LAST:event_jButton_deleteActionPerformed

    private void jPanel_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_tableMouseClicked
        jPanel_container.removeAll();
        jPanel_container.add(jInternalFrame_table).setVisible(true);
        jPanel_container.revalidate();
        jPanel_container.repaint();
    }//GEN-LAST:event_jPanel_tableMouseClicked

    private void jTable_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_tableMouseClicked
        int row = jTable_table.getSelectedRow();
        String maSP = jTable_table.getValueAt(row, 0).toString();
        jTextField_tenBan.setText(jTable_table.getValueAt(row, 1).toString());
        jList_soLuong.setSelectedValue(jTable_table.getValueAt(row, 2).toString(), true);
    }//GEN-LAST:event_jTable_tableMouseClicked

    private void jButton_add_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_add_tableActionPerformed
        String maBan = generateTableCode();
        String tenBan = jTextField_tenBan.getText().trim();
        int soLuongNguoi = Integer.parseInt(jList_soLuong.getSelectedValue().toString());
        String trangThai = "Trống";

        if (isTableNameUnique(tenBan)) {
            Ban ban = new Ban(maBan, tenBan, soLuongNguoi, trangThai);
            try {
                if (ban_DAO.createTable(ban)) {
                    JOptionPane.showMessageDialog(this, "Thêm " + tenBan + " vào danh mục bàn", "Success", JOptionPane.PLAIN_MESSAGE);
                    DocDuLieuDatabaseVaoTableBan();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tên bàn đã tồn tại!", "Fail", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton_add_tableActionPerformed

    private void jButton_update_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_update_tableActionPerformed
        jDialog_table.setSize(650, 750);
        jDialog_table.setLocationRelativeTo(null);
        try {
            insertID_table();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Menu_5__QuanLy_Internal_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        jDialog_table.setVisible(true);
    }//GEN-LAST:event_jButton_update_tableActionPerformed

    private void jComboBox_maSP_update_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_maSP_update_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_maSP_update_tableMouseClicked

    private void jComboBox_maSP_update_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_maSP_update_tableActionPerformed
        String maBan = (String) jComboBox_maSP_update_table.getSelectedItem();
        ArrayList<Ban> danhSachBan = null;
        try {
            danhSachBan = ban_DAO.searchByIDTable(maBan);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Menu_5__QuanLy_Internal_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        if (!danhSachBan.isEmpty()) {
            Ban ban = danhSachBan.get(0);
            jTextField_tenSP_update_table.setText(ban.getTenBan());
            jComboBox_soLuong_update_table.setSelectedItem(String.valueOf(ban.getSoLuongNguoi()));
            jComboBox_trangThai_update_table.setSelectedItem(String.valueOf(ban.getTrangThaiBan()));
        } else {
            jTextField_tenSP_update_table.setText("");
            jComboBox_soLuong_update_table.setSelectedIndex(0);
            jComboBox_trangThai_update_table.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jComboBox_maSP_update_tableActionPerformed

    private void jButton_confirm_tableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirm_tableActionPerformed
        String maBan = jComboBox_maSP_update_table.getSelectedItem().toString();
        String tenBan = jTextField_tenSP_update_table.getText();
        int soLuongNguoi = Integer.parseInt(jComboBox_soLuong_update_table.getSelectedItem().toString());
        String trangThaiNguoi = jComboBox_trangThai_update_table.getSelectedItem().toString();

        Ban ban = new Ban(maBan, tenBan, soLuongNguoi, trangThaiNguoi);

        try {
            if (ban_DAO.updateTable(ban)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin bàn thành công", "Success", JOptionPane.INFORMATION_MESSAGE);
                DocDuLieuDatabaseVaoTableBan();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin bàn thất bại", "Fail", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi cập nhật thông tin bàn", "Fail", JOptionPane.ERROR_MESSAGE);
        }
        jDialog_table.dispose();
    }//GEN-LAST:event_jButton_confirm_tableActionPerformed

    private void jButton_delete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_delete1ActionPerformed
        String maBan = jComboBox_maSP_update_table.getSelectedItem().toString();
        String tenSP = jTextField_tenSP_update_table.getText().trim();
        try {
            ban_DAO.deleteTable(maBan);
//            JOptionPane.showMessageDialog(this, "Đã xoá " + tenSP + " vào danh mục bàn", "Success", JOptionPane.WARNING_MESSAGE);
            DocDuLieuDatabaseVaoTableBan();
            try {
                insertID_table();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(Menu_5__QuanLy_Internal_GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(this, "Xoá bàn không thành công!", "Fail", JOptionPane.WARNING_MESSAGE);
        }
        jDialog_table.dispose();
    }//GEN-LAST:event_jButton_delete1ActionPerformed

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        jPanel_container.removeAll();
        jPanel_container.add(jInternalFrame_employee).setVisible(true);
        jPanel_container.revalidate();
        jPanel_container.repaint();
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jPanel_chart1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel_chart1MouseClicked
        jPanel_container.removeAll();
        jPanel_container.add(jInternalFrame_history).setVisible(true);
        jPanel_container.revalidate();
        jPanel_container.repaint();
    }//GEN-LAST:event_jPanel_chart1MouseClicked

    public String generateProductCode() {
        String newProductCode = "";
        try {
            ArrayList<String> productCodes = sanPham_DAO.getProductCodes();
            if (productCodes.isEmpty()) {
                newProductCode = "CF0001";
            } else {
                String lastProductCode = productCodes.get(productCodes.size() - 1);
                String lastNumberPart = lastProductCode.substring(2);
                int lastNumber = Integer.parseInt(lastNumberPart);
                int newNumber = lastNumber + 1;
                newProductCode = String.format("CF%04d", newNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newProductCode;
    }

    public String generateTableCode() {
        String newTableCode = "";
        ArrayList<Ban> tableList = ban_DAO.getAllTableBan();
        if (tableList.isEmpty()) {
            newTableCode = "TAB0001";
        } else {
            Ban lastTable = tableList.get(tableList.size() - 1);
            String lastTableCode = lastTable.getMaBan();
            String lastNumberPart = lastTableCode.substring(3);
            int lastNumber = Integer.parseInt(lastNumberPart);
            int newNumber = lastNumber + 1;
            newTableCode = String.format("TAB%04d", newNumber);
        }
        return newTableCode;
    }

    private boolean isProductNameUnique(String productName) {
        try {
            ArrayList<String> productNames = sanPham_DAO.getProductNames();
            return !productNames.contains(productName);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isTableNameUnique(String tableName) {
        try {
            ArrayList<String> tableNames = ban_DAO.getTableNames();
            return !tableNames.contains(tableName);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void DocDuLieuDatabaseVaoTable() {
        List<SanPham> list = sanPham_DAO.getAllTableSanPham();
        DefaultTableModel model = (DefaultTableModel) jTable_product.getModel();
        model.setRowCount(0);
        for (SanPham sanPham : list) {
            model.addRow(new Object[]{sanPham.getMaSP(), sanPham.getTenSP(),
                sanPham.getGiaSP()});
        }
    }

    public void DocDuLieuDatabaseVaoTableBan() {
        List<Ban> list = ban_DAO.getAllTableBan();
        DefaultTableModel model = (DefaultTableModel) jTable_table.getModel();
        model.setRowCount(0);
        for (Ban ban : list) {
            model.addRow(new Object[]{ban.getMaBan(), ban.getTenBan(),
                ban.getSoLuongNguoi(), ban.getTrangThaiBan()});
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_add;
    private javax.swing.JButton jButton_add_table;
    private javax.swing.JButton jButton_choose;
    private javax.swing.JButton jButton_choose_update;
    private javax.swing.JButton jButton_confirm;
    private javax.swing.JButton jButton_confirm_table;
    private javax.swing.JButton jButton_delete;
    private javax.swing.JButton jButton_delete1;
    private javax.swing.JButton jButton_update;
    private javax.swing.JButton jButton_update_table;
    private javax.swing.JComboBox<String> jComboBox_maSP_update;
    private javax.swing.JComboBox<String> jComboBox_maSP_update_table;
    private javax.swing.JComboBox<String> jComboBox_soLuong_update_table;
    private javax.swing.JComboBox<String> jComboBox_trangThai_update_table;
    private javax.swing.JDialog jDialog_table;
    private javax.swing.JDialog jDialog_update;
    private javax.swing.JInternalFrame jInternalFrame_employee;
    private javax.swing.JInternalFrame jInternalFrame_history;
    private javax.swing.JInternalFrame jInternalFrame_product;
    private javax.swing.JInternalFrame jInternalFrame_table;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public javax.swing.JLabel jLabel_hinhAnhSP;
    private javax.swing.JLabel jLabel_hinhAnhSP_update;
    private javax.swing.JList<String> jList_soLuong;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_chart;
    private javax.swing.JPanel jPanel_chart1;
    private javax.swing.JPanel jPanel_container;
    private javax.swing.JPanel jPanel_container_dialog;
    private javax.swing.JPanel jPanel_container_dialog1;
    private javax.swing.JPanel jPanel_container_em;
    private javax.swing.JPanel jPanel_customer;
    private javax.swing.JPanel jPanel_product;
    private javax.swing.JPanel jPanel_table;
    private javax.swing.JPanel jPanel_wrapper;
    private javax.swing.JPanel jPanel_wrapper_table;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JPasswordField jPasswordField3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable_history;
    private javax.swing.JTable jTable_product;
    private javax.swing.JTable jTable_table;
    private javax.swing.JTextField jTextField_giaSP;
    private javax.swing.JTextField jTextField_giaSP_update;
    private javax.swing.JTextField jTextField_tenBan;
    private javax.swing.JTextField jTextField_tenSP;
    private javax.swing.JTextField jTextField_tenSP_update;
    private javax.swing.JTextField jTextField_tenSP_update_table;
    // End of variables declaration//GEN-END:variables
}
