/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package ui;

import connectDB.ConnectDB;
import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.NhanVien_DAO;
import dao.SanPham_DAO;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;

import entity.NhanVien;
import entity.SanPham;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nvmin
 */
public class Menu_2_TaiQuay_Internal_GUI extends javax.swing.JInternalFrame {

    private KhachHang_DAO khachHang_DAO;
    private SanPham_DAO sanPham_DAO;
    private HoaDon_DAO hoaDon_DAO;
    private ChiTietHoaDon_DAO chiTietHoaDon_DAO;
    private NhanVien_DAO nhanVien_DAO;
    // Tien Te Chung
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat numf = NumberFormat.getCurrencyInstance(localeVN);

    /**
     * Creates new form NewJInternalFrame_Menu_2
     */
    public Menu_2_TaiQuay_Internal_GUI() {
        initComponents();
        try {
            ConnectDB.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        khachHang_DAO = new KhachHang_DAO();
        sanPham_DAO = new SanPham_DAO();
        nhanVien_DAO = new NhanVien_DAO();
        hoaDon_DAO = new HoaDon_DAO();
        chiTietHoaDon_DAO = new ChiTietHoaDon_DAO();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);

        displayProducts();

        for (int i = 0; i < jTable_bill.getColumnCount(); i++) {
            this.jTable_bill.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
            });
        }

        DefaultTableModel model = (DefaultTableModel) jTable_bill.getModel();
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                calculateTotals();
            }
        });

        //internal
        jInternalFrame_bill.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BasicInternalFrameUI uiBill = (BasicInternalFrameUI) jInternalFrame_bill.getUI();
        uiBill.setNorthPane(null);

        //Modal Bill
        jDialog_bill.setLocationRelativeTo(null);
        jDialog_bill.setSize(400, 230);

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);
        jLabel_now.setText(formattedDate);

    }

    public double getTotal() {
        double total = Double.parseDouble(jLabel_totalValue.getText());
        return total;
    }

    private void calculateTotals() {
        int totalQuantity = 0;
        double totalPrice = 0.0;
        DefaultTableModel model = (DefaultTableModel) jTable_bill.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            int quantity = Integer.parseInt(model.getValueAt(i, 1).toString());
            double price = Double.parseDouble(model.getValueAt(i, 2).toString());
            totalQuantity += quantity;
            totalPrice += quantity * price;
        }
        jLabel_quantityValue.setText("" + totalQuantity);
        jLabel_totalValue.setText("" + totalPrice);
//        jLabel_totalValue.setText("" + numf.format(totalPrice));
    }

    public void updateBill() {
        jDesktopPane_bill.removeAll();
        if (jTable_bill.getModel().getRowCount() > 0) {
            jDesktopPane_bill.add(jInternalFrame_bill).setVisible(true);
            jInternalFrame_bill.setSize(jDesktopPane_bill.getSize());
            jButton_viewBill.setEnabled(true);
            jButton_Cancel.setEnabled(true);
            jButton_viewBill.setBackground(new Color(0, 102, 51));
            jButton_Cancel.setBackground(Color.RED);
        } else {
            jDesktopPane_bill.add(jPanel_empty).setVisible(true);
            jButton_viewBill.setEnabled(false);
            jButton_Cancel.setEnabled(false);
            jButton_viewBill.setBackground(new Color(204, 204, 204));
            jButton_Cancel.setBackground(new Color(204, 204, 204));
        }
        jDesktopPane_bill.revalidate();
        jDesktopPane_bill.repaint();
    }

    private void displayProducts() {
        ArrayList<SanPham> danhSachSanPham = sanPham_DAO.getAllTableSanPham();
        jPanel16.removeAll();
        for (SanPham sanPham : danhSachSanPham) {
            JPanel productPanel = createProductPanel(sanPham);
            productPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String tenSP = sanPham.getTenSP();

                    DefaultTableModel model = (DefaultTableModel) jTable_bill.getModel();
                    boolean existed = false;
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).equals(tenSP)) {
                            int currentQuantity = Integer.parseInt(model.getValueAt(i, 1).toString());
                            model.setValueAt(currentQuantity + 1, i, 1);
                            existed = true;
                            break;
                        }
                    }
                    if (!existed) {
                        double giaSP = sanPham.getGiaSP();
//                        String giaSPTxt = Double.toString(giaSP);
                        String giaSPTxt = Double.toString(giaSP);
                        model.addRow(new Object[]{tenSP, "1", giaSPTxt});
                    }
                    updateBill();
                }
            });

            jPanel16.add(productPanel);
        }

        jPanel16.revalidate();
        jPanel16.repaint();
    }

    private JPanel createProductPanel(SanPham sanPham) {
        JPanel productPanel = new JPanel(new BorderLayout());
        //Hình ảnh <3
        try {
            if (sanPham.getHinhAnhSP() != null) {
                ImageIcon imageIcon = new ImageIcon(sanPham.getHinhAnhSP());
                JLabel imgLabel = new JLabel() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(200, 200);
                    }

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        int labelWidth = getWidth();
                        int labelHeight = getHeight();
                        g.drawImage(imageIcon.getImage(), 0, 0, labelWidth, labelHeight, this);
                    }
                };
                imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
                productPanel.add(imgLabel, BorderLayout.CENTER);
            } else {
                JLabel errorLabel = new JLabel("Không có hình ảnh");
                errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
                productPanel.add(errorLabel, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Không thể hiển thị hình ảnh");
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            productPanel.add(errorLabel, BorderLayout.CENTER);
        }
        // Tên sản phẩm <3
        JLabel nameLabel = new JLabel(sanPham.getTenSP());
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // Giá <3 
//        JLabel priceLabel = new JLabel(String.valueOf(sanPham.getGiaSP()));
        JLabel priceLabel = new JLabel(numf.format(sanPham.getGiaSP()));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setForeground(new Color(0, 102, 51));
        priceLabel.setPreferredSize(new Dimension(productPanel.getPreferredSize().width, 30));
        //
        JPanel jPanel_content = new JPanel(new BorderLayout());
        jPanel_content.add(nameLabel, BorderLayout.CENTER);
        jPanel_content.add(priceLabel, BorderLayout.SOUTH);
        //
        productPanel.add(jPanel_content, BorderLayout.SOUTH);
        jPanel_content.setPreferredSize(new Dimension(productPanel.getPreferredSize().width, 50));
        productPanel.setPreferredSize(new Dimension(productPanel.getPreferredSize().width, 200));
        //
        return productPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame_bill = new javax.swing.JInternalFrame();
        jPanel_wrapper = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_bill = new javax.swing.JTable();
        jDialog_bill = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel_index = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel_nameProduct = new javax.swing.JLabel();
        jLabel_money = new javax.swing.JLabel();
        jPanel_trash = new javax.swing.JPanel();
        jButton_trash = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel_quantity = new javax.swing.JLabel();
        jButton_deincrease = new javax.swing.JButton();
        jButton_increase = new javax.swing.JButton();
        jButton_confirm = new javax.swing.JButton();
        jDialog_detailsBill = new javax.swing.JDialog();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel_nameTable_bill = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel_now = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_detail_bill = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel_nhanVien_name = new javax.swing.JLabel();
        jLabel_total_quantity = new javax.swing.JLabel();
        jLabel_money_total = new javax.swing.JLabel();
        jButton_payment = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel_sale = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_ban_name = new javax.swing.JLabel();
        jLabel_nhanVien_codes = new javax.swing.JLabel();
        jLabel_ban_codes = new javax.swing.JLabel();
        jDialog_member = new javax.swing.JDialog();
        jPanel22 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jTextField_phoneUuDai = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jButton_enterUuuDai = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel_container = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jDesktopPane_bill = new javax.swing.JDesktopPane();
        jPanel_empty = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel_totalText = new javax.swing.JLabel();
        jLabel_totalValue = new javax.swing.JLabel();
        jLabel_quantityText = new javax.swing.JLabel();
        jLabel_quantityValue = new javax.swing.JLabel();
        jButton_Cancel = new javax.swing.JButton();
        jButton_viewBill = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel_quantityValue1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel_table_name = new javax.swing.JLabel();
        jLabel_table_codes = new javax.swing.JLabel();
        jLabel_currentUS = new javax.swing.JLabel();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel_container1 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jDesktopPane_bill1 = new javax.swing.JDesktopPane();
        jPanel_empty1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel_totalText1 = new javax.swing.JLabel();
        jLabel_totalValue1 = new javax.swing.JLabel();
        jLabel_quantityText1 = new javax.swing.JLabel();
        jLabel_quantityValue2 = new javax.swing.JLabel();
        jButton_Cancel1 = new javax.swing.JButton();
        jButton_viewBill1 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel_quantityValue3 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel_table_name1 = new javax.swing.JLabel();
        jLabel_table_codes1 = new javax.swing.JLabel();
        jLabel_currentUS1 = new javax.swing.JLabel();

        jInternalFrame_bill.setMinimumSize(new java.awt.Dimension(410, 591));
        jInternalFrame_bill.setPreferredSize(new java.awt.Dimension(410, 591));
        jInternalFrame_bill.setVisible(true);

        jTable_bill.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jTable_bill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sản phẩm", "Số lượng", "Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_bill.setFocusable(false);
        jTable_bill.setGridColor(new java.awt.Color(255, 255, 255));
        jTable_bill.setRowHeight(70);
        jTable_bill.setSelectionBackground(new java.awt.Color(0, 102, 51));
        jTable_bill.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jTable_bill.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_bill.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_bill.setShowGrid(false);
        jTable_bill.getTableHeader().setReorderingAllowed(false);
        jTable_bill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_billMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_bill);
        if (jTable_bill.getColumnModel().getColumnCount() > 0) {
            jTable_bill.getColumnModel().getColumn(0).setPreferredWidth(250);
            jTable_bill.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel_wrapperLayout = new javax.swing.GroupLayout(jPanel_wrapper);
        jPanel_wrapper.setLayout(jPanel_wrapperLayout);
        jPanel_wrapperLayout.setHorizontalGroup(
            jPanel_wrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );
        jPanel_wrapperLayout.setVerticalGroup(
            jPanel_wrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
        );

        jInternalFrame_bill.getContentPane().add(jPanel_wrapper, java.awt.BorderLayout.CENTER);

        jDialog_bill.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog_bill.setTitle("Thông tin sản phẩm");
        jDialog_bill.setMinimumSize(new java.awt.Dimension(400, 270));
        jDialog_bill.setModal(true);
        jDialog_bill.setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 102, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thông tin sản phẩm");

        jLabel_index.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel_index.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_index.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel_index, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jLabel_index, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog_bill.getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel_nameProduct.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel_nameProduct.setText("Tên SP");

        jLabel_money.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_money.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_money.setText("0");

        jPanel_trash.setBackground(new java.awt.Color(255, 255, 255));

        jButton_trash.setBackground(new java.awt.Color(255, 255, 254));
        jButton_trash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/trash-36.png"))); // NOI18N
        jButton_trash.setBorder(null);
        jButton_trash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_trashActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_trashLayout = new javax.swing.GroupLayout(jPanel_trash);
        jPanel_trash.setLayout(jPanel_trashLayout);
        jPanel_trashLayout.setHorizontalGroup(
            jPanel_trashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_trashLayout.createSequentialGroup()
                .addComponent(jButton_trash, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_trashLayout.setVerticalGroup(
            jPanel_trashLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_trashLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton_trash, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
        );

        jLabel_quantity.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_quantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_quantity.setText("1");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_quantity, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel_quantity, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jButton_deincrease.setBackground(new java.awt.Color(204, 204, 204));
        jButton_deincrease.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/minus-28.png"))); // NOI18N
        jButton_deincrease.setBorder(null);
        jButton_deincrease.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_deincreaseActionPerformed(evt);
            }
        });

        jButton_increase.setBackground(new java.awt.Color(204, 204, 204));
        jButton_increase.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/plus-28.png"))); // NOI18N
        jButton_increase.setBorder(null);
        jButton_increase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_increaseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jButton_deincrease, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jButton_increase, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_deincrease, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton_increase, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jButton_confirm.setBackground(new java.awt.Color(0, 102, 51));
        jButton_confirm.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton_confirm.setForeground(new java.awt.Color(255, 255, 255));
        jButton_confirm.setText("Xác nhận");
        jButton_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_confirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel_money, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel_nameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel_trash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_nameProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel_trash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_money, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_confirm, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addGap(0, 53, Short.MAX_VALUE))
        );

        jDialog_bill.getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        jDialog_detailsBill.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog_detailsBill.setLocation(new java.awt.Point(500, 10));
        jDialog_detailsBill.setModal(true);
        jDialog_detailsBill.setResizable(false);
        jDialog_detailsBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jDialog_detailsBillMouseEntered(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Eleven Coffee Shop");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("12 Nguyễn Văn Bảo, Phường 4, Gò Vấp, Thành phố Hồ Chí Minh");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Hotline : 0353 999 798");

        jLabel_nameTable_bill.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_nameTable_bill.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_nameTable_bill.setText("Hoá đơn");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Ngày mua :");

        jLabel_now.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel_now.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jTable_detail_bill.setBackground(new java.awt.Color(255, 255, 254));
        jTable_detail_bill.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTable_detail_bill.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTable_detail_bill.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên sản phẩm", "Số lượng", "Giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_detail_bill.setEnabled(false);
        jTable_detail_bill.setRowHeight(40);
        jTable_detail_bill.setSelectionBackground(new java.awt.Color(255, 255, 255));
        jTable_detail_bill.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable_detail_bill.setShowGrid(false);
        jScrollPane3.setViewportView(jTable_detail_bill);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Thu ngân :");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Thành tiền");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel13.setText("Số lượng");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Cảm ơn quý khách đã sử dụng dịch vụ");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Hẹn gặp lại quý khách lần sau");

        jLabel_nhanVien_name.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel_nhanVien_name.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_nhanVien_name.setPreferredSize(new java.awt.Dimension(130, 25));

        jLabel_total_quantity.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel_total_quantity.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_total_quantity.setText("0");

        jLabel_money_total.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_money_total.setForeground(new java.awt.Color(255, 51, 51));
        jLabel_money_total.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_money_total.setText("0đ");

        jButton_payment.setBackground(new java.awt.Color(0, 102, 51));
        jButton_payment.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton_payment.setForeground(new java.awt.Color(255, 255, 255));
        jButton_payment.setText("Thanh toán");
        jButton_payment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton_paymentMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_paymentMouseEntered(evt);
            }
        });
        jButton_payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_paymentActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setText("Giảm giá");

        jLabel_sale.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel_sale.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_sale.setText("0");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Bàn :");

        jLabel_ban_name.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel_ban_name.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel_ban_name.setPreferredSize(new java.awt.Dimension(45, 25));

        jLabel_nhanVien_codes.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel_nhanVien_codes.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_nhanVien_codes.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jLabel_ban_codes.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel_ban_codes.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_ban_codes.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel_nameTable_bill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel_money_total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_sale, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel_total_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_now, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel_nhanVien_codes, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel_ban_codes, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel_ban_name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel_nhanVien_name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE))))))
                        .addGap(50, 50, 50))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel_nameTable_bill, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_now, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_nhanVien_codes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel_nhanVien_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_ban_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_ban_codes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_total_quantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_sale))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel_money_total, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_payment, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jDialog_detailsBill.getContentPane().add(jPanel11, java.awt.BorderLayout.CENTER);

        jDialog_member.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog_member.setTitle("Member");
        jDialog_member.setMinimumSize(new java.awt.Dimension(503, 600));
        jDialog_member.setPreferredSize(new java.awt.Dimension(503, 400));

        jPanel22.setBackground(new java.awt.Color(0, 102, 51));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Ưu đãi thành viên");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jDialog_member.getContentPane().add(jPanel22, java.awt.BorderLayout.PAGE_START);

        jPanel23.setBorder(javax.swing.BorderFactory.createEmptyBorder(40, 40, 40, 40));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Số điện thoại");

        jTextField_phoneUuDai.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 0, 0));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Không tìm thấy khách hàng thành viên");

        jButton_enterUuuDai.setBackground(new java.awt.Color(0, 102, 51));
        jButton_enterUuuDai.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton_enterUuuDai.setForeground(new java.awt.Color(255, 255, 255));
        jButton_enterUuuDai.setText("Enter");
        jButton_enterUuuDai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_enterUuuDaiActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setText("Tên khách hàng");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 102, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Xác nhận");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextField2)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jTextField_phoneUuDai, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jButton_enterUuuDai)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_enterUuuDai, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(jTextField_phoneUuDai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jDialog_member.getContentPane().add(jPanel23, java.awt.BorderLayout.LINE_START);

        setPreferredSize(new java.awt.Dimension(1100, 800));

        jPanel_container.setBackground(new java.awt.Color(204, 204, 204));
        jPanel_container.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bill-128.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Hiện chưa có dịch vụ nào");

        javax.swing.GroupLayout jPanel_emptyLayout = new javax.swing.GroupLayout(jPanel_empty);
        jPanel_empty.setLayout(jPanel_emptyLayout);
        jPanel_emptyLayout.setHorizontalGroup(
            jPanel_emptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_emptyLayout.setVerticalGroup(
            jPanel_emptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_emptyLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
        );

        jDesktopPane_bill.setLayer(jPanel_empty, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane_billLayout = new javax.swing.GroupLayout(jDesktopPane_bill);
        jDesktopPane_bill.setLayout(jDesktopPane_billLayout);
        jDesktopPane_billLayout.setHorizontalGroup(
            jDesktopPane_billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_empty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane_billLayout.setVerticalGroup(
            jDesktopPane_billLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_empty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel_totalText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_totalText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_totalText.setText("Tổng cộng  :");

        jLabel_totalValue.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_totalValue.setForeground(new java.awt.Color(255, 0, 0));
        jLabel_totalValue.setText("0");

        jLabel_quantityText.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        jLabel_quantityText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_quantityText.setText("Số lượng : ");

        jLabel_quantityValue.setBackground(new java.awt.Color(0, 0, 0));
        jLabel_quantityValue.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel_quantityValue.setText("0");

        jButton_Cancel.setBackground(new java.awt.Color(204, 204, 204));
        jButton_Cancel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton_Cancel.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Cancel.setText("Huỷ");
        jButton_Cancel.setEnabled(false);
        jButton_Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CancelActionPerformed(evt);
            }
        });

        jButton_viewBill.setBackground(new java.awt.Color(204, 204, 204));
        jButton_viewBill.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton_viewBill.setForeground(new java.awt.Color(255, 255, 255));
        jButton_viewBill.setText("Xem Bill");
        jButton_viewBill.setEnabled(false);
        jButton_viewBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_viewBillMouseEntered(evt);
            }
        });
        jButton_viewBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewBillActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel2.setText("Giảm giá");

        jLabel_quantityValue1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel_quantityValue1.setText("0");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/member36.png"))); // NOI18N
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 49, Short.MAX_VALUE))
        );

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(204, 0, 51));
        jLabel25.setText("0%");

        jLabel26.setText("=");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_quantityText, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel_quantityValue1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel26))
                                    .addComponent(jLabel_quantityValue, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel_totalText, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_totalValue, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 7, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_viewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_quantityText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_quantityValue, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_quantityValue1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel_totalText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_totalValue)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_viewBill, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDesktopPane_bill))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane_bill)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel_container.add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel16.setLayout(new java.awt.GridLayout(0, 4, 10, 10));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(588, 70));

        jLabel_table_name.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_table_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel_table_codes.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_table_codes.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_table_codes.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jLabel_currentUS.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel_table_name, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_table_codes, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                .addComponent(jLabel_currentUS, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_table_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_table_codes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_currentUS, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 708, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel15);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel_container.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel_container, java.awt.BorderLayout.CENTER);

        jInternalFrame1.setPreferredSize(new java.awt.Dimension(1100, 800));

        jPanel_container1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel_container1.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(204, 204, 204));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bill-128.png"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Hiện chưa có dịch vụ nào");

        javax.swing.GroupLayout jPanel_empty1Layout = new javax.swing.GroupLayout(jPanel_empty1);
        jPanel_empty1.setLayout(jPanel_empty1Layout);
        jPanel_empty1Layout.setHorizontalGroup(
            jPanel_empty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_empty1Layout.setVerticalGroup(
            jPanel_empty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_empty1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
        );

        jDesktopPane_bill1.setLayer(jPanel_empty1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane_bill1Layout = new javax.swing.GroupLayout(jDesktopPane_bill1);
        jDesktopPane_bill1.setLayout(jDesktopPane_bill1Layout);
        jDesktopPane_bill1Layout.setHorizontalGroup(
            jDesktopPane_bill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_empty1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane_bill1Layout.setVerticalGroup(
            jDesktopPane_bill1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_empty1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel_totalText1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_totalText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_totalText1.setText("Tổng cộng  :");

        jLabel_totalValue1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_totalValue1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel_totalValue1.setText("0");

        jLabel_quantityText1.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        jLabel_quantityText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_quantityText1.setText("Số lượng : ");

        jLabel_quantityValue2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel_quantityValue2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel_quantityValue2.setText("0");

        jButton_Cancel1.setBackground(new java.awt.Color(204, 204, 204));
        jButton_Cancel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton_Cancel1.setForeground(new java.awt.Color(255, 255, 255));
        jButton_Cancel1.setText("Huỷ");
        jButton_Cancel1.setEnabled(false);
        jButton_Cancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_Cancel1ActionPerformed(evt);
            }
        });

        jButton_viewBill1.setBackground(new java.awt.Color(204, 204, 204));
        jButton_viewBill1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jButton_viewBill1.setForeground(new java.awt.Color(255, 255, 255));
        jButton_viewBill1.setText("Xem Bill");
        jButton_viewBill1.setEnabled(false);
        jButton_viewBill1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_viewBill1MouseEntered(evt);
            }
        });
        jButton_viewBill1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_viewBill1ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel19.setText("Giảm giá");

        jLabel_quantityValue3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel_quantityValue3.setText("0");

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/member36.png"))); // NOI18N
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel_totalText1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel_totalValue1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_quantityText1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_quantityValue2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_quantityValue3, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jButton_Cancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_viewBill1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_quantityText1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_quantityValue2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_quantityValue3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel_totalText1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel_totalValue1)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_Cancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_viewBill1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDesktopPane_bill1))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane_bill1)
                .addGap(0, 0, 0)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel_container1.add(jPanel12, java.awt.BorderLayout.LINE_END);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel20.setLayout(new java.awt.GridLayout(0, 4, 10, 10));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setPreferredSize(new java.awt.Dimension(588, 70));

        jLabel_table_name1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_table_name1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel_table_codes1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel_table_codes1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_table_codes1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);

        jLabel_currentUS1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addComponent(jLabel_table_name1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel_table_codes1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                .addComponent(jLabel_currentUS1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_table_name1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_table_codes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_currentUS1, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 708, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(jPanel19);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel17.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel_container1.add(jPanel17, java.awt.BorderLayout.CENTER);

        jInternalFrame1.getContentPane().add(jPanel_container1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jInternalFrame1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CancelActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable_bill.getModel();
        model.setRowCount(0);
        updateBill();
    }//GEN-LAST:event_jButton_CancelActionPerformed

    public void updateBillDetail() {
        int rowCount = jTable_bill.getRowCount();
        int colCount = jTable_bill.getColumnCount();

        DefaultTableModel model_2 = (DefaultTableModel) jTable_detail_bill.getModel();
        model_2.setRowCount(0);
        for (int row = 0; row < rowCount; row++) {
            Object[] rowData = new Object[colCount];
            for (int col = 0; col < colCount; col++) {
                rowData[col] = jTable_bill.getValueAt(row, col);
            }
            model_2.addRow(rowData);
        }
        String maNV = jLabel_currentUS.getText();
        ArrayList<NhanVien> danhSachNhanVien = nhanVien_DAO.getNhanVienTheoMa(maNV);
        jLabel_nhanVien_codes.setText(maNV);
        for (NhanVien nhanVien : danhSachNhanVien) {
            jLabel_nhanVien_name.setText(nhanVien.getTenNV());
        }
        jLabel_sale.setText(jLabel_quantityValue1.getText());
        jLabel_ban_codes.setText(jLabel_table_codes.getText());
        jLabel_ban_name.setText(jLabel_table_name.getText());
        jLabel_money_total.setText(jLabel_totalValue.getText());
        jLabel_total_quantity.setText(jLabel_quantityValue.getText());
    }

    private void jButton_viewBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewBillActionPerformed
        jDialog_detailsBill.setSize(600, 850);
        updateBillDetail();
        jDialog_detailsBill.setVisible(true);

    }//GEN-LAST:event_jButton_viewBillActionPerformed

    private void jTable_billMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_billMouseClicked
        int row = jTable_bill.getSelectedRow();
        String nameProduct = jTable_bill.getValueAt(row, 0).toString();
        String quantityProduct = jTable_bill.getValueAt(row, 1).toString();
        String priceProduct = jTable_bill.getValueAt(row, 2).toString();
        int rowIndex = row + 1;
        jLabel_index.setText("" + rowIndex);
        jLabel_nameProduct.setText(nameProduct);
        jLabel_money.setText(priceProduct);
        jLabel_quantity.setText(quantityProduct);
        jDialog_bill.setVisible(true);
    }//GEN-LAST:event_jTable_billMouseClicked

    private void jButton_deincreaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_deincreaseActionPerformed
        int currentValue = Integer.parseInt(jLabel_quantity.getText());
        if (currentValue > 1) {
            jLabel_quantity.setText(String.valueOf(currentValue - 1));
        } else {
            jLabel_quantity.setText("1");
        }
    }//GEN-LAST:event_jButton_deincreaseActionPerformed

    private void jButton_increaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_increaseActionPerformed
        int currentValue = Integer.parseInt(jLabel_quantity.getText());
        currentValue++;
        jLabel_quantity.setText(String.valueOf(currentValue));
    }//GEN-LAST:event_jButton_increaseActionPerformed

    private void jButton_trashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_trashActionPerformed
        int indexLabel = Integer.parseInt(jLabel_index.getText());
        int indexTable = indexLabel - 1;
        DefaultTableModel model = (DefaultTableModel) jTable_bill.getModel();
        model.removeRow(indexTable);
        updateBill();
        jDialog_bill.dispose();
    }//GEN-LAST:event_jButton_trashActionPerformed

    private void jButton_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirmActionPerformed
        int indexLabel = Integer.parseInt(jLabel_index.getText());
        int indexTable = indexLabel - 1;
        String quantityProduct = jLabel_quantity.getText();
        DefaultTableModel model = (DefaultTableModel) jTable_bill.getModel();
        model.setValueAt(quantityProduct, indexTable, 1);
        updateBill();
        jDialog_bill.dispose();
    }//GEN-LAST:event_jButton_confirmActionPerformed

    public String generateHoaDonCode() {
        String newHoaDonCode = "";
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        try {
            String currentDate = dateFormat.format(new Date());
            ArrayList<String> hoaDonCodes = hoaDon_DAO.getHoaDonCodes();
            if (hoaDonCodes.isEmpty()) {
                newHoaDonCode = "HD" + currentDate + "0001";
            } else {
                String lastHoaDonCode = hoaDonCodes.get(hoaDonCodes.size() - 1);
                String lastDatePart = lastHoaDonCode.substring(2, 10);
                int lastNumber = Integer.parseInt(lastHoaDonCode.substring(10));
                if (lastDatePart.equals(currentDate)) {
                    int newNumber = lastNumber + 1;
                    newHoaDonCode = String.format("HD%s%04d", currentDate, newNumber);
                } else {
                    newHoaDonCode = "HD" + currentDate + "0001";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newHoaDonCode;
    }


    private void jButton_paymentMouseClicked(java.awt.event.MouseEvent evt){//GEN-FIRST:event_jButton_paymentMouseClicked
        HoaDon hd;
        String maHD = generateHoaDonCode();
//        try {
//            maHD = hoaDon_DAO.taoMaHoaDonTheoNgay().toString();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        String maBan;
        if (jLabel_ban_codes.getText().length() > 0) {
            maBan = jLabel_ban_codes.getText();
        } else {
            maBan = null;
        }
        String maNV = jLabel_nhanVien_codes.getText();
        String maKH = null;
        int tongSoSP = Integer.parseInt(jLabel_quantityValue.getText());
        double giamGiaHD = Double.parseDouble(jLabel_quantityValue1.getText());
        double tongThanhTien = Double.parseDouble(jLabel_totalValue.getText());
        java.sql.Date ngayTaoHoaDon = new java.sql.Date(System.currentTimeMillis());
        hd = new HoaDon(maHD, new Ban(maBan), new KhachHang(maKH), new NhanVien(maNV), tongSoSP, giamGiaHD, tongThanhTien, ngayTaoHoaDon);
        try {
            hoaDon_DAO.createHD(hd);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("error");
        }
        ArrayList<ChiTietHoaDon> arrcthd = new ArrayList<ChiTietHoaDon>();

        DefaultTableModel model = (DefaultTableModel) jTable_detail_bill.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String tenSP = model.getValueAt(i, 0).toString();
            String maSP = null;
            if (tenSP != null && !tenSP.isEmpty()) {
                try {
                    maSP = sanPham_DAO.getMaSPByTenSP(tenSP);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            int soLuongSP = Integer.parseInt(model.getValueAt(i, 1).toString());
            double giaSP = Double.parseDouble(model.getValueAt(i, 2).toString());

            ChiTietHoaDon cthd = new ChiTietHoaDon();
            cthd.setHoaDon(new HoaDon(maHD));
            cthd.setSanPham(new SanPham(maSP));
            cthd.setSoLuongSP(soLuongSP);
            cthd.setGiaSP(giaSP);
            arrcthd.add(cthd);
        }

        for (ChiTietHoaDon cthd : arrcthd) {
            chiTietHoaDon_DAO.createChiTietHD(cthd);
        }

        Menu_2_ThanhToan_Dialog_GUI payment_NewJDialog = new Menu_2_ThanhToan_Dialog_GUI((java.awt.Frame) SwingUtilities.getWindowAncestor(this), true);
        updateBill();
        payment_NewJDialog.jLabel_temporary.setText(jLabel_totalValue.getText());
        payment_NewJDialog.setVisible(true);

        //xoa table
        DefaultTableModel model_1 = (DefaultTableModel) jTable_bill.getModel();
        model_1.setRowCount(0);
        updateBill();

        jDialog_detailsBill.dispose();
    }//GEN-LAST:event_jButton_paymentMouseClicked

    private void jButton_paymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_paymentActionPerformed

    }//GEN-LAST:event_jButton_paymentActionPerformed

    private void jButton_paymentMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_paymentMouseEntered

    }//GEN-LAST:event_jButton_paymentMouseEntered

    private void jDialog_detailsBillMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDialog_detailsBillMouseEntered

    }//GEN-LAST:event_jDialog_detailsBillMouseEntered

    private void jButton_viewBillMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_viewBillMouseEntered

    }//GEN-LAST:event_jButton_viewBillMouseEntered

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        jDialog_member.setLocationRelativeTo(null);
        jDialog_member.setVisible(true);
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jButton_Cancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_Cancel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_Cancel1ActionPerformed

    private void jButton_viewBill1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_viewBill1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_viewBill1MouseEntered

    private void jButton_viewBill1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_viewBill1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_viewBill1ActionPerformed

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jButton_enterUuuDaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_enterUuuDaiActionPerformed
        String phoneNumber = jTextField_phoneUuDai.getText().trim();
        try {
            ArrayList<KhachHang> khachHangs = khachHang_DAO.searchByPhoneNumber(phoneNumber);
            if (!khachHangs.isEmpty()) {
                KhachHang khachHang = khachHangs.get(0); // Chỉ lấy thông tin của khách hàng đầu tiên trong danh sách
                jTextField2.setText(khachHang.getTenKH());
                jLabel23.setText("");
            } else {
                jTextField2.setText("");
                jLabel23.setText("Không tìm thấy thông tin khách hàng.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu_2_TaiQuay_Internal_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_enterUuuDaiActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jTextField2.getText().equals("")) {
            return; 
        } else {
            jLabel25.setText("5%"); 
            double total = Double.parseDouble(jLabel_totalValue.getText());
            double kmai = total * 0.05;
            total -= kmai;
            jLabel_quantityValue1.setText(String.valueOf(kmai));
            jLabel_totalValue.setText(String.valueOf(total));
        }
        jDialog_member.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Cancel;
    private javax.swing.JButton jButton_Cancel1;
    private javax.swing.JButton jButton_confirm;
    private javax.swing.JButton jButton_deincrease;
    private javax.swing.JButton jButton_enterUuuDai;
    private javax.swing.JButton jButton_increase;
    private javax.swing.JButton jButton_payment;
    private javax.swing.JButton jButton_trash;
    private javax.swing.JButton jButton_viewBill;
    private javax.swing.JButton jButton_viewBill1;
    private javax.swing.JDesktopPane jDesktopPane_bill;
    private javax.swing.JDesktopPane jDesktopPane_bill1;
    private javax.swing.JDialog jDialog_bill;
    private javax.swing.JDialog jDialog_detailsBill;
    private javax.swing.JDialog jDialog_member;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame_bill;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_ban_codes;
    private javax.swing.JLabel jLabel_ban_name;
    public javax.swing.JLabel jLabel_currentUS;
    public javax.swing.JLabel jLabel_currentUS1;
    private javax.swing.JLabel jLabel_index;
    private javax.swing.JLabel jLabel_money;
    private javax.swing.JLabel jLabel_money_total;
    private javax.swing.JLabel jLabel_nameProduct;
    private javax.swing.JLabel jLabel_nameTable_bill;
    private javax.swing.JLabel jLabel_nhanVien_codes;
    private javax.swing.JLabel jLabel_nhanVien_name;
    private javax.swing.JLabel jLabel_now;
    private javax.swing.JLabel jLabel_quantity;
    private javax.swing.JLabel jLabel_quantityText;
    private javax.swing.JLabel jLabel_quantityText1;
    private javax.swing.JLabel jLabel_quantityValue;
    private javax.swing.JLabel jLabel_quantityValue1;
    private javax.swing.JLabel jLabel_quantityValue2;
    private javax.swing.JLabel jLabel_quantityValue3;
    private javax.swing.JLabel jLabel_sale;
    public javax.swing.JLabel jLabel_table_codes;
    public javax.swing.JLabel jLabel_table_codes1;
    public javax.swing.JLabel jLabel_table_name;
    public javax.swing.JLabel jLabel_table_name1;
    private javax.swing.JLabel jLabel_totalText;
    private javax.swing.JLabel jLabel_totalText1;
    private javax.swing.JLabel jLabel_totalValue;
    private javax.swing.JLabel jLabel_totalValue1;
    private javax.swing.JLabel jLabel_total_quantity;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel_container;
    private javax.swing.JPanel jPanel_container1;
    private javax.swing.JPanel jPanel_empty;
    private javax.swing.JPanel jPanel_empty1;
    private javax.swing.JPanel jPanel_trash;
    private javax.swing.JPanel jPanel_wrapper;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JTable jTable_bill;
    public javax.swing.JTable jTable_detail_bill;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField_phoneUuDai;
    // End of variables declaration//GEN-END:variables
}
