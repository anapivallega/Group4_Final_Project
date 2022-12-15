/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package src;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
//import static src.cashierregistrants.jTable2;
import javax.swing.table.TableRowSorter;
import static src.Messages.jTable81;
import static src.adminpage.jTable20;
import static src.category.jTable6;
//import static src.categorycashier.jTable56;
import static src.inventorypage.jTable4;
import static src.soldproducts.jTable5;

/**
 *
 * @author Student.Admin
 */
public class cashierpage extends javax.swing.JFrame {

    /**
     * Creates new form cashierpage
     */
    public cashierpage() {
        initComponents();
        Connect();
//        sales();
        dt();
        time();
        able();
//      
    }

    public cashierpage(String usernamee) {
        initComponents();
        Connect();

        jusername.setText(usernamee);
        dt();
        time();
        able();
//     

    }

    public static void AddRowToJcategoryTable(Object[] dataRow) {
        DefaultTableModel model6 = (DefaultTableModel) jTable6.getModel();
        model6.addRow(dataRow);
    }

    URL ic = getClass().getResource("/images/ImageIcon_1.png");
    Icon ci = new ImageIcon(ic);

    Connection con;
    PreparedStatement pst;
    PreparedStatement pst1;
    PreparedStatement pst2;
    ResultSet rs;
    DefaultTableModel df;

    Connection con1 = null;
    Statement st = null;
    ResultSet RS = null;

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/marketsystem", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void invoiceinsert() {
        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);

        String paymet;
        String cid;
        String timee = time.getText();
        String rece = jreciept.getText();

        try {

            for (int i = 0; i < jTable2.getRowCount(); i++) {

                paymet = (String) jTable2.getValueAt(i, 6);
                cid = (String) jTable2.getValueAt(i, 7);

                String query = "insert into invoice(receipt,date,time,payment_description,customer_id)values(?,?,?,?,?)";
                pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                pst = con.prepareStatement(query);
                pst.setString(1, rece);
                pst.setString(2, date);
                pst.setString(3, timee);
                pst.setString(4, paymet);
                pst.setString(5, cid);

                pst.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Invoice printed");
        } catch (Exception e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void able() {
        jtot.setEnabled(false);
        jsupplier.setEnabled(false);
        jcid.setEnabled(false);
        jpayment.setEnabled(false);
    }

    public void soldp() {
        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date1 = dt.format(now);
        String pbuying = jbuyp.getText();
        String totalcost = jtcost.getText();
        String pay1 = jpay.getText();
        int quans = Integer.parseInt(quantity.getValue().toString());
        String user = jusername.getText();
        String ply = jsupplier.getText();
//         int damm =0;

        try {
            String query1 = "insert into `sales_products`(barcode, product_name,buyingprice,price, quantity, total, date, userid,supplier,username) values (?,?,?,?,?,?,?,?,?,?);";

            pst1 = con.prepareStatement(query1);

            String bname = "";

            String price;
            String costbuying;
            int qty;
            String id;
            int total = 0;

            for (int i = 0; i < jTable2.getRowCount(); i++) {
                id = (String) jTable2.getValueAt(i, 0);
                bname = (String) jTable2.getValueAt(i, 1);
//               costbuying = (String)jTable2.getValueAt(i, 2);
                price = (String) jTable2.getValueAt(i, 3);
                qty = (int) jTable2.getValueAt(i, 4);
                total = (int) jTable2.getValueAt(i, 5);

                pst1.setString(1, id);
//                pst1.setInt(1, id);
                pst1.setString(2, bname);
                pst1.setString(3, pbuying);
                pst1.setString(4, price);
                pst1.setInt(5, qty);
                pst1.setInt(6, total);
                pst1.setString(7, date1);
                pst1.setInt(8, new LoginUsers().ID());
                pst1.setString(9, ply);
                pst1.setString(10, user);
                pst1.executeUpdate();

            }

        } catch (SQLException ex) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pos() {
        String nme = jcode.getText();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/marketsystem", "root", "");
            pst = con.prepareStatement("select * from products where barcode=?");
            pst.setString(1, nme);
            rs = pst.executeQuery();

            while (rs.next()) {
                int currentquanti;
                currentquanti = rs.getInt("quantity");
//              int minimumquan;
//              minimumquan = rs.getInt("quantity");

                int price2 = Integer.parseInt(jbuyp.getText());
                int price = Integer.parseInt(jprice.getText());
                int qtynew = Integer.parseInt(quantity.getValue().toString());

                int tot = price * qtynew;
                int btot = price2 * qtynew;
                jtot.setText(String.valueOf(btot));
                if (qtynew >= currentquanti) {
                    JOptionPane.showMessageDialog(this, "Available Products" + " =" + currentquanti);
                    JOptionPane.showMessageDialog(this, "Quantity is not enough!!");
                } else {
                    String na = jbname.getText();
                    String pric = jprice.getText();
                    String quan = jtquantity.getText();
                    int value = (Integer) quantity.getValue();

                    Date da = new Date();
                    String datet = da.toString();
                    jreciept.setText(jreciept.getText() + "\n______________________________________\n\n");
                    jreciept.setText(jreciept.getText() + "Product Name  --------------- : " + na + "\n\n");
                    jreciept.setText(jreciept.getText() + "Price        --------------- : " + pric + "\n\n");
                    jreciept.setText(jreciept.getText() + "Quantity      --------------- : " + value + "\n\n");

                    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                    model.addRow(new Object[]{
                        jcode.getText(),
                        jbname.getText(),
                        jbuyp.getText(),
                        jprice.getText(),
                        quantity.getValue(),
                        tot,
                        jpayment.getText(),
                        jcid.getText(),});
                    jcode.setText("");
                    jbname.setText("");
                    jprice.setText("");
                    quantity.setValue(0);
                    jcode.requestFocus();

                    int sum = 0;
                    int sum1 = 0;
                    int rows = jTable2.getRowCount();

                    for (int i = 0; i < jTable2.getRowCount(); i++) {
                        sum1 = sum1 + Integer.parseInt(jTable2.getValueAt(i, 4).toString());
                        sum = sum + Integer.parseInt(jTable2.getValueAt(i, 5).toString());

                    }
                    jtcost.setText(String.valueOf(sum));
                    jtquantity.setText(String.valueOf(rows));
                }

            }

//       
        } catch (SQLException ex) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cashiertransac() {

        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);
        String timee = time.getText();
        String prod = jbname.getText();
        String rolee = jposition.getText();

        String username = jusername.getText();
//        double qty = Double.parseDouble(quantity.getValue().toString()); 
        int quan = Integer.parseInt(quantity.getValue().toString());
        String codee = jcode.getText();
//        Double qty = quantity.getValue().toString();
        String type = "Get products";

        try {

            String query = "insert into transactions(barcode,quantity,type_transaction,UserID,date,time,username,role)values(?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst = con.prepareStatement(query);
            pst.setString(1, codee);
//            pst.setInt(1, Integer.parseInt(jcode.getText().toString()));
            pst.setInt(2, quan);
            pst.setString(3, type);
            pst.setInt(4, new LoginUsers().ID());//username id...
            pst.setString(5, date);
            pst.setString(6, timee);
            pst.setString(7, username);
            pst.setString(8, rolee);
            pst.executeUpdate();
        } catch (Exception e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void cashiertransaccancel() {

        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);
        String timee = time.getText();
        String prod = jbname.getText();
        String rolee = jposition.getText();
        String username = jusername.getText();
        int quan = Integer.parseInt(quantity.getValue().toString());
        String type = "returned sales";
        String codee = jcode.getText();
        try {

            String query = "insert into transactions(barcode,quantity,type_transaction,UserID,date,time,username,role)values(?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst = con.prepareStatement(query);
            pst.setString(1, codee);
//            pst.setInt(1, Integer.parseInt(jcode.getText()));
            pst.setInt(2, quan);
            pst.setString(3, type);
            pst.setInt(4, new LoginUsers().ID());//username id...
            pst.setString(5, date);
            pst.setString(6, timee);
            pst.setString(7, username);
            pst.setString(8, rolee);
            pst.executeUpdate();
        } catch (Exception e) {

            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void cashiertransacsold() {

        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);
        String timee = time.getText();
        String prod = jbname.getText();

        String rolee = jposition.getText();
        String username = jusername.getText();
        String codee = jcode.getText();
        int quan = Integer.parseInt(quantity.getValue().toString());
//          String codee = jcode.getText();
        String type = "sold products";
        try {
            String query = "insert into transactions(barcode,quantity,type_transaction,UserID,date,time,username,role)values(?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst = con.prepareStatement(query);
            pst.setString(1, codee);
//            pst.setInt(1, Integer.parseInt(jcode.getText()));
            pst.setInt(2, quan);
            pst.setString(3, type);
            pst.setInt(4, new LoginUsers().ID());//username id...
            pst.setString(5, date);
            pst.setString(6, timee);
            pst.setString(7, username);
            pst.setString(8, rolee);
            pst.executeUpdate();

        } catch (Exception e) {

            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void sales() {
//      int bup = Integer.parseInt(jbuyp.getText());
        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date1 = dt.format(now);
        String totalcostbuy = jtot.getText();
        String totalcost = jtcost.getText();
        String pay1 = jpay.getText();
//       int bal = Integer.parseInt(jbalance.getText().toString());
        String bal = jbalance.getText();
        String user = jusername.getText();
        String ply = jsupplier.getText();

        int damm = 0;

        try {
            String query = "insert into sales(subtotal,pay,balance) values (?,?,?)";
            pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, totalcost);
//          pst.setInt(2, pay);
            pst.setString(2, pay1);
            pst.setString(3, bal);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();

            if (rs.next()) {
                damm = rs.getInt(1);

            }
            int qty;
            String id;

            String query4 = "update products set quantity= quantity-? where barcode =?";
            pst2 = con.prepareStatement(query4);

            for (int i = 0; i < jTable2.getRowCount(); i++) {
                id = (String) jTable2.getValueAt(i, 0);
                qty = (int) jTable2.getValueAt(i, 4);

                pst2.setInt(1, qty);
                pst2.setString(2, id);
                pst2.execute();
            }
            JOptionPane.showMessageDialog(this, "Sales completed");

        } catch (SQLException ex) {

            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dt() {

        Date d = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String dd = sdf.format(d);
        date.setText(dd);
    }
    Timer t;
    SimpleDateFormat stt;

    public void time() {
        t = new Timer(0, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Date dt = new Date();
                stt = new SimpleDateFormat("hh-mm-ss a");
                String tt = stt.format(dt);
                time.setText(tt);

            }
        });
        t.start();
    }

    public void updateproductsales() {
        String sql = "select from `orders`";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
//            jTable3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
//           JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();

            } catch (Exception e) {

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

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jusername = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jposition = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jreciept = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable7 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        quantity = new javax.swing.JSpinner();
        jcode = new javax.swing.JTextField();
        jbname = new javax.swing.JTextField();
        jprice = new javax.swing.JTextField();
        jtcost = new javax.swing.JTextField();
        jpay = new javax.swing.JTextField();
        jbalance = new javax.swing.JTextField();
        jtquantity = new javax.swing.JTextField();
        jadd = new javax.swing.JButton();
        jButtondelete = new javax.swing.JButton();
        jclear = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jadd2 = new javax.swing.JButton();
        jadd3 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jbuyp = new javax.swing.JTextField();
        jtot = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jsearch = new javax.swing.JLabel();
        jinput = new javax.swing.JTextField();
        jsupplier = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jpayment = new javax.swing.JTextField();
        jcid = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        time = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jsearch1 = new javax.swing.JLabel();
        jinput1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(227, 225, 225));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 0), new java.awt.Color(0, 102, 0), new java.awt.Color(0, 51, 0), new java.awt.Color(0, 51, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(44, 116, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile.png"))); // NOI18N

        jusername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jusername.setForeground(new java.awt.Color(255, 255, 255));
        jusername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jusername.setText("Cashier User");

        jLabel7.setFont(new java.awt.Font("Palatino Linotype", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Cashier Management");

        jposition.setForeground(new java.awt.Color(255, 255, 255));
        jposition.setText("Cashier");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jusername)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jposition, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel7))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jusername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jposition)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 130));

        jPanel3.setBackground(new java.awt.Color(44, 116, 60));

        jreciept.setColumns(20);
        jreciept.setRows(5);
        jScrollPane3.setViewportView(jreciept);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(864, 343, 420, 330));

        jPanel2.setBackground(new java.awt.Color(44, 116, 60));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(44, 116, 60));
        jPanel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel12MouseExited(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/settings.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Settings");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jPanel2.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 21, -1, 44));

        jPanel13.setBackground(new java.awt.Color(44, 116, 60));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel13MouseExited(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Logout");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jPanel2.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(375, 21, -1, 44));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Exit");
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(549, 39, 53, -1));

        jPanel17.setBackground(new java.awt.Color(44, 116, 60));
        jPanel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel17MouseExited(evt);
            }
        });

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chat.png"))); // NOI18N

        jLabel39.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Messages");
        jLabel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel39MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jPanel2.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, -2, 650, 130));

        jPanel5.setBackground(new java.awt.Color(227, 225, 225));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 0), 5, true));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ImageIcon_1_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 0, 240, 140));

        jPanel8.setBackground(new java.awt.Color(44, 116, 60));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable7.setBackground(new java.awt.Color(245, 244, 244));
        jTable7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "order id", "cus-ID", "CUS-name", "barcode", "productname", "description", "qty", "price", "payment", "Supplier"
            }
        ));
        jTable7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable7MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable7);

        jPanel8.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 650, 118));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                jPanel7ComponentHidden(evt);
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Quantity");
        jPanel7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 28));

        quantity.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel7.add(quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 155, 29));

        jcode.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcodeActionPerformed(evt);
            }
        });
        jcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcodeKeyPressed(evt);
            }
        });
        jPanel7.add(jcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 155, 36));

        jbname.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel7.add(jbname, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 155, 35));

        jprice.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel7.add(jprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 155, 31));

        jtcost.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jtcost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtcostMouseClicked(evt);
            }
        });
        jPanel7.add(jtcost, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 114, 30));

        jpay.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jpay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpayActionPerformed(evt);
            }
        });
        jPanel7.add(jpay, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 111, 114, 30));

        jbalance.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbalanceActionPerformed(evt);
            }
        });
        jPanel7.add(jbalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 150, 114, 40));

        jtquantity.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel7.add(jtquantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 114, 36));

        jadd.setBackground(new java.awt.Color(255, 204, 51));
        jadd.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jadd.setText("Add");
        jadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jaddActionPerformed(evt);
            }
        });
        jPanel7.add(jadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 97, 31));

        jButtondelete.setBackground(new java.awt.Color(255, 204, 51));
        jButtondelete.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButtondelete.setText("Cancel");
        jButtondelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtondeleteActionPerformed(evt);
            }
        });
        jPanel7.add(jButtondelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 97, 31));

        jclear.setBackground(new java.awt.Color(255, 204, 51));
        jclear.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jclear.setText("Clear");
        jclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jclearActionPerformed(evt);
            }
        });
        jPanel7.add(jclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, 97, 31));

        jLabel31.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel31.setText("Product Code");
        jPanel7.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 24));

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("Price");
        jPanel7.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 28));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setText("Product Name");
        jPanel7.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 28));

        jLabel32.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel32.setText("Total Item");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, -1, 24));

        jLabel33.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel33.setText("Total Cost");
        jPanel7.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, -1, 24));

        jLabel34.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel34.setText("Pay");
        jPanel7.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, -1, 24));

        jLabel35.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel35.setText("Balance");
        jPanel7.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, -1, 24));

        jPanel8.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 220, 650, 200));

        jTable2.setBackground(new java.awt.Color(245, 244, 244));
        jTable2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "barcode", "Product_Name", "Buying price", "Price", "Quantity", "Total", "paymentmethod", "cus-ID"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel8.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 430, 650, 129));

        jadd2.setBackground(new java.awt.Color(255, 204, 51));
        jadd2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jadd2.setText("Show Orders");
        jadd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jadd2ActionPerformed(evt);
            }
        });
        jPanel8.add(jadd2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 120, 31));

        jadd3.setBackground(new java.awt.Color(255, 204, 51));
        jadd3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jadd3.setText("Hide Orders");
        jadd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jadd3ActionPerformed(evt);
            }
        });
        jPanel8.add(jadd3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 20, 120, 31));

        jLabel29.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Buying Price");
        jPanel8.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 28));

        jbuyp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel8.add(jbuyp, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 120, 35));

        jtot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel8.add(jtot, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 120, 35));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 204, 51));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel14MouseExited(evt);
            }
        });

        jsearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/magnifying-glass.png"))); // NOI18N
        jsearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jsearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsearch, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsearch, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jinput.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jinput.setText("Type here");
        jinput.setBorder(null);
        jinput.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jinputFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jinputFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jinput, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jinput)
        );

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        jsupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsupplierActionPerformed(evt);
            }
        });
        jPanel8.add(jsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 120, 40));

        jLabel36.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Total Buying price");
        jPanel8.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 120, 28));

        jLabel37.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Payment Method");
        jPanel8.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, 100, 28));
        jPanel8.add(jpayment, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 120, 40));
        jPanel8.add(jcid, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 120, 40));

        jLabel40.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(255, 255, 255));
        jLabel40.setText("Customer ID");
        jPanel8.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 100, 28));

        jLabel41.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Supplier");
        jPanel8.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 100, 28));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 810, 570));

        jPanel10.setBackground(new java.awt.Color(227, 225, 225));

        jLabel18.setBackground(new java.awt.Color(0, 51, 0));
        jLabel18.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 51, 0));
        jLabel18.setText("Date:");

        jLabel19.setBackground(new java.awt.Color(51, 51, 51));
        jLabel19.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 51, 0));
        jLabel19.setText("Time:");

        date.setBackground(new java.awt.Color(227, 225, 225));
        date.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        date.setForeground(new java.awt.Color(0, 51, 0));
        date.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        date.setText("0");
        date.setBorder(null);
        date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateActionPerformed(evt);
            }
        });

        time.setBackground(new java.awt.Color(227, 225, 225));
        time.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        time.setForeground(new java.awt.Color(0, 51, 0));
        time.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        time.setText("0");
        time.setBorder(null);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 137, -1, -1));

        jButton1.setBackground(new java.awt.Color(255, 204, 51));
        jButton1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 0));
        jButton1.setText("Print Invoice");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 690, 141, 47));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "barcode", "Product name", "description", "Supplier", "Buying price"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 240, 460, 90));

        jButton3.setText("Product List");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 190, -1, 40));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 204, 51));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel15MouseExited(evt);
            }
        });

        jsearch1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/magnifying-glass.png"))); // NOI18N
        jsearch1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jsearch1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsearch1, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsearch1, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                .addContainerGap())
        );

        jinput1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jinput1.setText("Type here");
        jinput1.setBorder(null);
        jinput1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jinput1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jinput1FocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jinput1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jinput1)
        );

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 190, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1362, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String payment = jpay.getText();

        if (payment.trim().equals("") || payment.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter Payment!");

        } else {

            invoiceinsert();

            cashiertransacsold();

            int pay = Integer.parseInt(jpay.getText());
            int totalcost = Integer.parseInt(jtcost.getText());
            sales();
            int bal = pay - totalcost;
            jbalance.setText(String.valueOf(bal));
            String na = jbname.getText();
            String pric = jprice.getText();
            String quan = jtquantity.getText();
            String cost = jtcost.getText();
            String balanc = jbalance.getText();
            String cash = jpay.getText();
            String paym;
            Date da = new Date();
            String datet = da.toString();

            for (int i = 0; i < jTable2.getRowCount(); i++) {
                paym = (String) jTable2.getValueAt(i, 6);

                jreciept.setText(jreciept.getText() + "\n______________________________________\n\n");
                jreciept.setText(jreciept.getText() + "+++++++++FaToTab Market+++++++++++++\n");
                jreciept.setText(jreciept.getText() + "Total Item   --------------- : " + quan + "\n\n");
                jreciept.setText(jreciept.getText() + "Total Cost   --------------- : " + cost + "\n\n");
                jreciept.setText(jreciept.getText() + "Payment Method --------------- : " + paym + "\n\n");
                jreciept.setText(jreciept.getText() + "Cash         --------------- : " + cash + "\n\n");
                jreciept.setText(jreciept.getText() + "-------------------------------------------------\n\n");
                jreciept.setText(jreciept.getText() + "Change       --------------- : " + balanc + "\n\n");
                jreciept.setText(jreciept.getText() + "______________________________________________\n\n");
                jreciept.setText(jreciept.getText() + "Save and Purchase\n\n");
                jreciept.setText(jreciept.getText() + "Thank you Come again!!\n\n");
            }

            jcode.setText("");
            jbname.setText("");
            jprice.setText("");
            quantity.setValue(0);
            jtcost.setText("");
            jpay.setText("");
            jtquantity.setText("");

            DefaultTableModel df = (DefaultTableModel) jTable2.getModel();
            df.setRowCount(0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateActionPerformed

    private void jadd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jadd3ActionPerformed
        // TODO add your handling code here:

        DefaultTableModel df = (DefaultTableModel) jTable7.getModel();
        df.setRowCount(0);
    }//GEN-LAST:event_jadd3ActionPerformed

    private void jadd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jadd2ActionPerformed
        // TODO add your handling code here:

        DefaultTableModel df = (DefaultTableModel) jTable7.getModel();
        df.setRowCount(0);
        try {
            Statement st = con.createStatement();
            String query1 = "select * from `orders`";
            ResultSet rs1 = st.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..

                String bid = rs1.getString("order_id");
                String custid = rs1.getString("customer_id");
                String cusname = rs1.getString("customername");
                String pid = rs1.getString("product_id");
                String pname = rs1.getString("product_name");

                String des = rs1.getString("product_description");
                String qtyy = rs1.getString("qty");
                String pric = rs1.getString("price");
                String paydes = rs1.getString("payment_description");
                String supp = rs1.getString("supplier");

                //string array for store data into jtable..
                String tbData[] = {bid, custid, cusname, pid, pname, des, qtyy, pric, paydes, supp};
                DefaultTableModel tblModel = (DefaultTableModel) jTable7.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

//            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(adminpage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jadd2ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        DefaultTableModel model1 = (DefaultTableModel) jTable2.getModel();
        int Myindex = jTable2.getSelectedRow();
        int Mycolumn = jTable2.getSelectedColumn();

        String value = model1.getValueAt(Myindex, Mycolumn).toString();
        jcode.setText(model1.getValueAt(Myindex, 0).toString());
        jbname.setText(model1.getValueAt(Myindex, 1).toString());
        jbuyp.setText(model1.getValueAt(Myindex, 2).toString());
        jprice.setText(model1.getValueAt(Myindex, 3).toString());
        int quantity_ = Integer.parseInt(model1.getValueAt(Myindex, 4).toString());
        quantity.setValue((int) quantity_);

        //          jtquantity.setText(model1.getValueAt(Myindex, 2).toString());
    }//GEN-LAST:event_jTable2MouseClicked

    private void jPanel7ComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel7ComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel7ComponentHidden

    private void jclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jclearActionPerformed
        // TODO add your handling code here:
        jcode.setText("");
        jbname.setText("");
        jprice.setText("");
        quantity.setValue(0);
    }//GEN-LAST:event_jclearActionPerformed

    private void jButtondeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtondeleteActionPerformed
        // TODO add your handling code here:
        cashiertransaccancel();
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        //delete row
        if (jTable2.getSelectedRowCount() == 1) {
            //if single row is selected then delete
            model.removeRow(jTable2.getSelectedRow());
            jcode.setText("");
            jbname.setText("");
            jprice.setText("");
            quantity.setValue(0);

            //to subtract the total cost from the deleted sales
            int numrow = jTable2.getRowCount();
            double tot = 0;
            double tot1 = 0;

            for (int i = 0; i < numrow; i++) {
                double val = Double.valueOf(jTable2.getValueAt(i, 3).toString());
                double val1 = Double.valueOf(jTable2.getValueAt(i, 2).toString());
                tot += val;
                tot1 += val1;
            }
            jtcost.setText(Double.toString(tot));
            jtquantity.setText(Double.toString(tot1));

            JOptionPane.showMessageDialog(this, "Order Cancelled!!");

        } else {
            if (jTable2.getRowCount() == 0) {
                //if table is not empty(no data) than display message
                JOptionPane.showMessageDialog(this, "Table is empty.");
            } else {
                //if table is not empty but row is not selected or multiple row is selected

                JOptionPane.showMessageDialog(this, "Please select a single row to delete.");

            }
        }
    }//GEN-LAST:event_jButtondeleteActionPerformed

    private void jaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jaddActionPerformed
        String buying = jbuyp.getText();
        String suppl = jsupplier.getText();
        String cost = jcode.getText();
        if (buying.trim().equals("") || buying.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter Buying Price!");

        } else if (suppl.trim().equals("") || suppl.trim().equals("")) {

            JOptionPane.showMessageDialog(null, "Input The SupplierI!");
        } else {
            pos();
            cashiertransac();

            soldp();
        }
        jbuyp.setText("");

        if (jTable7.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "select the process order from the table!");
        } else {

            if (jTable7.getSelectedRowCount() == 1) {
//             if select from the table proceed

                int row = jTable7.getSelectedRow();
                String cell = jTable7.getModel().getValueAt(row, 3).toString();
                String sql = "DELETE FROM `orders` where product_id= " + cell;

                try {

                    pst = con.prepareStatement(sql);
                    pst.execute();
                    updateproductsales();
                    JOptionPane.showMessageDialog(null, "order " + cost + " processed!");

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(null, e);

                } finally {
                    try {
                        pst.close();
                        rs.close();

                    } catch (Exception e) {
                    }

                    DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
                    //delete row
                    if (jTable7.getSelectedRowCount() == 1) {
                        //if single row is selected then delete
                        model.removeRow(jTable7.getSelectedRow());

                    }

                }
            }
        }
    }//GEN-LAST:event_jaddActionPerformed

    private void jtcostMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtcostMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jtcostMouseClicked

    private void jcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcodeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String bcode = jcode.getText();
                pst = (PreparedStatement) con.prepareStatement("select * from products where barcode = ?");
                pst.setString(1, bcode);
                rs = pst.executeQuery();

                if (rs.next() == false) {
                    JOptionPane.showMessageDialog(this, "Product not Found");
                } else {
                    String bname = rs.getString("product_name");
                    jbname.setText(bname.trim());

                    String price = rs.getString("sellingprice");
                    jprice.setText(price.trim());

                    quantity.requestFocus();
                }
            } catch (SQLException ex) {
                Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        }
    }//GEN-LAST:event_jcodeKeyPressed

    private void jcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcodeActionPerformed

    private void jTable7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable7MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model2 = (DefaultTableModel) jTable7.getModel();
        int Myindex = jTable7.getSelectedRow();
        TableModel model3 = (TableModel) jTable7.getModel();

        int Mycolumn = jTable7.getSelectedColumn();

        String value = model2.getValueAt(Myindex, Mycolumn).toString();

        //        int id = Integer.parseInt(model2.getValueAt(Myindex, 0).toString());
        jcid.setText(model2.getValueAt(Myindex, 1).toString());
        jsupplier.setText(model2.getValueAt(Myindex, 9).toString());
        jpayment.setText(model2.getValueAt(Myindex, 8).toString());

    }//GEN-LAST:event_jTable7MouseClicked

    private void jPanel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseExited
        // TODO add your handling code here:
        jPanel13.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel13MouseExited

    private void jPanel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseEntered
        // TODO add your handling code here:
        jPanel13.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel13MouseEntered

    private void jPanel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseExited
        // TODO add your handling code here:
        jPanel12.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel12MouseExited

    private void jPanel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseEntered
        // TODO add your handling code here:
        jPanel12.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel12MouseEntered

    private void jbalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbalanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbalanceActionPerformed

    private void jsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsearchMouseClicked
        // TODO add your handling code here:

        String st = jinput.getText();

        DefaultTableModel model = (DefaultTableModel) jTable7.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable7.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(st.trim()));
    }//GEN-LAST:event_jsearchMouseClicked

    private void jPanel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseEntered
        // TODO add your handling code here:
        jPanel14.setBackground(new Color(249, 233, 9));
    }//GEN-LAST:event_jPanel14MouseEntered

    private void jPanel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseExited
        // TODO add your handling code here:
        jPanel14.setBackground(new Color(255, 204, 51));
    }//GEN-LAST:event_jPanel14MouseExited

    private void jinputFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jinputFocusGained
        // TODO add your handling code here:
        jinput.setText("");
    }//GEN-LAST:event_jinputFocusGained

    private void jinputFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jinputFocusLost
        // TODO add your handling code here:
        jinput.setText("Type Here");
    }//GEN-LAST:event_jinputFocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable3.setRowSorter(tr);
        DefaultTableModel df = (DefaultTableModel) jTable3.getModel();
        df.setRowCount(0);
        try {
            Statement st7 = con.createStatement();
            String query1 = "select *  from `products`";
            ResultSet rs1 = st7.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..
                String id = rs1.getString("barcode");
                String hh = rs1.getString("product_name");
                String des1 = rs1.getString("description");
                String supppl = rs1.getString("supplier");
                String buy = rs1.getString("buyingprice");

                //string array for store data into jtable..
                String tbData[] = {id, hh, des1, supppl, buy};
                DefaultTableModel tblModel = (DefaultTableModel) jTable3.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

        } catch (SQLException ex) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jsearch1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsearch1MouseClicked
        // TODO add your handling code here:
        String st = jinput1.getText();

        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable3.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(st.trim()));

    }//GEN-LAST:event_jsearch1MouseClicked

    private void jPanel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel15MouseEntered

    private void jPanel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel15MouseExited

    private void jinput1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jinput1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jinput1FocusGained

    private void jinput1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jinput1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jinput1FocusLost

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:

        LoginUsers main = new LoginUsers();
        main.setVisible(true);
        main.pack();
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jpayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpayActionPerformed

    private void jLabel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel39MouseClicked
        // TODO add your handling code here:
        jcashier_message main = new jcashier_message(jusername.getText());
        main.setVisible(true);

        main.pack();
        this.dispose();

    }//GEN-LAST:event_jLabel39MouseClicked

    private void jPanel17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseEntered
        // TODO add your handling code here:
        jPanel10.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel17MouseEntered

    private void jPanel17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel17MouseExited
        // TODO add your handling code here:
        jPanel10.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel17MouseExited

    private void jsupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jsupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jsupplierActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(cashierpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cashierpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cashierpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cashierpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new cashierpage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtondelete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    public javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable7;
    private javax.swing.JButton jadd;
    private javax.swing.JButton jadd2;
    private javax.swing.JButton jadd3;
    private javax.swing.JTextField jbalance;
    private javax.swing.JTextField jbname;
    private javax.swing.JTextField jbuyp;
    private javax.swing.JTextField jcid;
    private javax.swing.JButton jclear;
    private javax.swing.JTextField jcode;
    private javax.swing.JTextField jinput;
    private javax.swing.JTextField jinput1;
    private javax.swing.JTextField jpay;
    private javax.swing.JTextField jpayment;
    private javax.swing.JLabel jposition;
    private javax.swing.JTextField jprice;
    private javax.swing.JTextArea jreciept;
    private javax.swing.JLabel jsearch;
    private javax.swing.JLabel jsearch1;
    private javax.swing.JTextField jsupplier;
    private javax.swing.JTextField jtcost;
    private javax.swing.JTextField jtot;
    private javax.swing.JTextField jtquantity;
    private javax.swing.JLabel jusername;
    private javax.swing.JSpinner quantity;
    private javax.swing.JTextField time;
    // End of variables declaration//GEN-END:variables
}
