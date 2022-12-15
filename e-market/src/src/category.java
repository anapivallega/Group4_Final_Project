/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static src.soldadmin.jTable9;
import static src.soldproducts.jTable5;

/**
 *
 * @author 1styrGroupB
 */
public class category extends javax.swing.JFrame {

    /**
     * Creates new form category
     */
    public category() {
        initComponents();

        Connect();
        availproducts();
        able();
    }

    public category(String username) {
        initComponents();
        Connect();
        availproducts();
        able();
        jusername.setText(username);
    }

    public class productstock {

        private int code;
        private String pname;
        private String descrip;
        private int quantity;
        private int bprice;
//    private int sprice;
        private int tcost;
//       private int tcost;
        private byte[] photo;
        private String suppl;
//     private String status;

//    public productstock(int code,String pname,String descrip,int quantity,int bprice,int sprice,int tcost,String dob,byte[] photo,String suppl, String status){
        public productstock(int code, String pname, String descrip, int quantity, int bprice, int tcost, byte[] photo, String suppl) {
            this.code = code;
            this.pname = pname;
            this.descrip = descrip;
            this.quantity = quantity;
            this.bprice = bprice;
//        this.sprice = sprice;
            this.tcost = tcost;
            this.photo = photo;
            this.suppl = suppl;
//        this.status = status;

            this.photo = photo;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getpName() {
            return pname;
        }

        public void setpName(String pname) {
            this.pname = pname;
        }

        public String getdescrip() {
            return descrip;
        }

        public void setdescrip(String descrip) {
            this.descrip = descrip;
        }

        public int getquantity() {
            return quantity;
        }

        public void setquantity(int quantity) {
            this.quantity = quantity;
        }

        public int getbprice() {
            return bprice;
        }

        public void setbprice(int bprice) {
            this.bprice = bprice;
        }

        public int gettcost() {
            return tcost;
        }

        public void settcost(int tcost) {
            this.tcost = tcost;
        }

        public byte[] getPhoto() {
            return photo;
        }

        public void setPhoto(byte[] photo) {
            this.photo = photo;
        }

        public String getsuppl() {
            return suppl;
        }

        public void setsuppl(String suppl) {
            this.suppl = suppl;
        }

    }

    String photopath = "";

    public ImageIcon resetImageSize(String photopath, byte[] photo) {
        ImageIcon myPhoto = null;
        if (photopath != null) {
            myPhoto = new ImageIcon(photopath);

        } else {
            myPhoto = new ImageIcon(photo);
        }
        Image img = myPhoto.getImage();
        Image img1 = img.getScaledInstance(jphoto1.getWidth(), jphoto1.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon ph = new ImageIcon(img1);
        return ph;
    }

    public ArrayList<category.productstock> retrieveData() {
        ArrayList<category.productstock> al = null;
        al = new ArrayList<category.productstock>();

        try {

            String qry = "select * from offered_products";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(qry);
            category.productstock product;
            while (rs.next()) {
                product = new category.productstock(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getBytes(9), rs.getString(10));
                al.add(product);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return al;
    }
//    

    public void fillTable() {
        ArrayList<category.productstock> al = retrieveData();
        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        model.setRowCount(0); // Empty/clear the table
        Object[] row = new Object[8];
        for (int i = 0; i < al.size(); i++) {
            row[0] = al.get(i).getCode();
            row[1] = al.get(i).getpName();
            row[2] = al.get(i).getdescrip();
            row[4] = al.get(i).getquantity();
            row[5] = al.get(i).getbprice();
            row[6] = al.get(i).gettcost();
            row[7] = al.get(i).getPhoto();
            row[8] = al.get(i).getsuppl();

            model.addRow(row);
        }
        //model.setRowCount(0);
    }

    public static void AddRowToJinventorypageTable(Object[] dataRow) {
        DefaultTableModel model5 = (DefaultTableModel) jTable6.getModel();
        model5.addRow(dataRow);
    }

    public static void AddRowToJcategoryTable(Object[] dataRow) {
        DefaultTableModel model6 = (DefaultTableModel) jTable6.getModel();
        model6.addRow(dataRow);
    }
    Connection con;
    PreparedStatement pst;
    PreparedStatement pst1;
    PreparedStatement pst9;
    ResultSet rs;
    DefaultTableModel df;

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

    public void updateproductsales() {
        String sql = "select from `products`";
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

    public void offeredproducts() {

        String price2 = "";
        try {
            Statement st = con.createStatement();
            String query1 = "select * from `offered_products`";
            ResultSet rs1 = st.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..

                String bid = rs1.getString("product_id");
                String bookn = rs1.getString("product_name");
                String des = rs1.getString("description");
                String qty = rs1.getString("quantity");
                String price1 = rs1.getString("price");
                String tots = rs1.getString("total_amount");
                String image = rs1.getString("product_image");
                String sup = rs1.getString("supplier");

                //string array for store data into jtable..
                String tbData[] = {bid, bookn, des, qty, price1, price2, tots, image, sup};
                DefaultTableModel tblModel = (DefaultTableModel) jTable6.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

//            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void able() {

        String val = jchoose.getSelectedItem().toString();
        jdecline.setEnabled(false);
        jadd.setEnabled(false);

    }

    public void productapp() {
//
        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);

        String timee = time.getText();
        String bname = jbname.getText();
        String desc = jdescription.getText();
        String buyingprice = jbuyprice.getText();
        String sellingprice = jsellprice.getText();
        String supp = jsupplier.getText();
        String img1 = jproduct1.getText();
        String cost = jtotalamountcost.getText();
        try {

            String query6 = "insert into `approved_products` (product_name,description,quantity,buyingprice,sellingprice,totalamount_cost,product_image,supplier)values(?,?,?,?,?,?,?,?);";
            pst = con.prepareStatement(query6, Statement.RETURN_GENERATED_KEYS);
            pst9 = con.prepareStatement(query6);
            pst9.setString(1, bname);
            pst9.setString(2, desc);
            pst9.setInt(3, Integer.parseInt(jquantity.getText()));
            pst9.setString(4, buyingprice);
            pst9.setString(5, sellingprice);
            pst9.setString(6, cost);

//            InputStream is = new FileInputStream(new File(photopath));
            pst9.setString(7, img1);
//            pst9.setString(7, img1);
            pst9.setString(8, supp);
            pst9.executeUpdate();
//            rs = pst.getGeneratedKeys();

            JOptionPane.showMessageDialog(this, "Offered product approve!");

        } catch (SQLException e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void productdec() {
//
        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);

        String timee = time.getText();
        String bname = jbname.getText();
        String desc = jdescription.getText();
        String buyingprice = jbuyprice.getText();
        String sellingprice = jsellprice.getText();
        String supp = jsupplier.getText();
        String img1 = jproduct1.getText();
        String cost = jtotalamountcost.getText();
//   
        try {

            String query6 = "insert into `decli` (product_name,description,quantity,price,totalamount_cost,product_image,supplier)values(?,?,?,?,?,?,?);";
            pst = con.prepareStatement(query6, Statement.RETURN_GENERATED_KEYS);
            pst9 = con.prepareStatement(query6);
            pst9.setString(1, bname);
            pst9.setString(2, desc);
            pst9.setInt(3, Integer.parseInt(jquantity.getText()));
            pst9.setString(4, buyingprice);

            pst9.setString(5, cost);
            pst9.setString(6, img1);
            pst9.setString(7, supp);
            pst9.executeUpdate();
//            rs = pst.getGeneratedKeys();

            JOptionPane.showMessageDialog(this, "Offered product declined!");

        } catch (SQLException e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);
//      
        }
    }

    public void availproducts() {
        try {
            Statement st = con.createStatement();
            String query1 = "select * from `products`";
            ResultSet rs1 = st.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..

                String bid = rs1.getString("barcode");
                String bookn = rs1.getString("product_name");
                String des = rs1.getString("description");
                String qty = rs1.getString("quantity");
                String price1 = rs1.getString("buyingprice");
                String price2 = rs1.getString("sellingprice");
                String tots = rs1.getString("totalamount_cost");
                String image = rs1.getString("productImage");
                String sup = rs1.getString("supplier");

                //string array for store data into jtable..
                String tbData[] = {bid, bookn, des, qty, price1, price2, tots, image, sup};
                DefaultTableModel tblModel = (DefaultTableModel) jTable6.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

//            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void products() {
        try {
            Statement st = con.createStatement();
            String query1 = "select * from `products`";
            ResultSet rs1 = st.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..

                String bid = rs1.getString("barcode");
                String bookn = rs1.getString("product_name");
                String des = rs1.getString("description");
                String qty = rs1.getString("quantity");
                String price1 = rs1.getString("buyingprice");
                String price2 = rs1.getString("sellingprice");
                String tots = rs1.getString("totalamount_cost");
                String image = rs1.getString("productImage");
                String sup = rs1.getString("supplier");

                //string array for store data into jtable..
                String tbData[] = {bid, bookn, des, qty, price1, price2, tots, image, sup};
                DefaultTableModel tblModel = (DefaultTableModel) jTable6.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

//            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salesupdate() {
        try {
            pst = con.prepareStatement("select * from `products`");
            rs = pst.executeQuery();

            ResultSetMetaData rsd = rs.getMetaData();
            int c;

            c = rsd.getColumnCount();
            DefaultTableModel de = (DefaultTableModel) jTable6.getModel();
            de.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getString("barcode"));
                    v2.add(rs.getString("product_name"));
                    v2.add(rs.getString("description"));
                    v2.add(rs.getString("quantity"));
                    v2.add(rs.getString("buyingprice"));
                    v2.add(rs.getString("sellingprice"));

                    v2.add(rs.getString("totalamount_cost"));
                    v2.add(rs.getString("productImage"));
                    v2.add(rs.getString("supplier"));

                }
                de.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateproduct() {
        String sql = "select from `offered_products`";
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
        jPanel19 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        Home4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jusername = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jclear = new javax.swing.JButton();
        jdelete = new javax.swing.JButton();
        jupdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jchoose = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jsearch = new javax.swing.JLabel();
        jinput = new javax.swing.JTextField();
        jdecline = new javax.swing.JButton();
        jadd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jbuyprice = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jquantity = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jbname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jproduct1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jphoto1 = new javax.swing.JLabel();
        jtotalamountcost = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jdescription = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jsupplier = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jsellprice = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel67 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        time = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(227, 225, 225));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 0), new java.awt.Color(0, 102, 0), new java.awt.Color(0, 51, 0), new java.awt.Color(0, 51, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel19.setBackground(new java.awt.Color(44, 116, 60));

        jPanel18.setBackground(new java.awt.Color(44, 116, 60));

        Home4.setBackground(new java.awt.Color(44, 116, 60));
        Home4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Home4HomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Home4HomeMouseExited(evt);
            }
        });

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Home");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Home4Layout = new javax.swing.GroupLayout(Home4);
        Home4.setLayout(Home4Layout);
        Home4Layout.setHorizontalGroup(
            Home4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Home4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        Home4Layout.setVerticalGroup(
            Home4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Home4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Home4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(Home4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Home4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile.png"))); // NOI18N

        jusername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jusername.setForeground(new java.awt.Color(255, 255, 255));
        jusername.setText("Username");

        jPanel20.setBackground(new java.awt.Color(44, 116, 60));
        jPanel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel20MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel20MouseExited(evt);
            }
        });
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/rating.png"))); // NOI18N
        jPanel20.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 34, -1));

        jLabel21.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Sold Products");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        jPanel20.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 14, -1, 24));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jusername))
                .addGap(28, 28, 28)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jusername))
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 100));

        jPanel3.setBackground(new java.awt.Color(44, 116, 60));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jclear.setBackground(new java.awt.Color(255, 204, 51));
        jclear.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jclear.setText("Clear");
        jclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jclearActionPerformed(evt);
            }
        });
        jPanel3.add(jclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 117, 40));

        jdelete.setBackground(new java.awt.Color(255, 204, 51));
        jdelete.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jdelete.setText("Delete");
        jdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdeleteActionPerformed(evt);
            }
        });
        jPanel3.add(jdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 117, 38));

        jupdate.setBackground(new java.awt.Color(255, 204, 51));
        jupdate.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jupdate.setText("Update");
        jupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jupdateActionPerformed(evt);
            }
        });
        jPanel3.add(jupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, 117, 35));

        jTable6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name", "Description", "Quatity", "Buying Price", "Sellintg Price", "TotalamountCost", "Image", "Supplier"
            }
        ));
        jTable6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable6MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable6);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 750, 260));

        jchoose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available Products", "Offered Products" }));
        jPanel3.add(jchoose, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 190, 40));

        jButton4.setText("View");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, 40));

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

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, -1));

        jdecline.setBackground(new java.awt.Color(255, 204, 51));
        jdecline.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jdecline.setText("Decline");
        jdecline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdeclineActionPerformed(evt);
            }
        });
        jPanel3.add(jdecline, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 360, 110, 37));

        jadd.setBackground(new java.awt.Color(255, 204, 51));
        jadd.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jadd.setText("Approve");
        jadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jaddActionPerformed(evt);
            }
        });
        jPanel3.add(jadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 360, 110, 37));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 220, 780, 410));

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
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11))
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

        jPanel2.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, 44));

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

        jPanel10.setBackground(new java.awt.Color(44, 116, 60));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10MouseExited(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/choose.png"))); // NOI18N

        jLabel23.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Exit");
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(14, 14, 14))
        );

        jPanel2.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 25, -1, 44));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 0, 600, 100));

        jPanel6.setBackground(new java.awt.Color(227, 225, 225));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 0), 5, true));

        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ImageIcon_1_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 240, -1));

        jPanel8.setBackground(new java.awt.Color(44, 116, 60));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(jbuyprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 150, 34));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Buying Price");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 91, -1));
        jPanel4.add(jquantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 150, 33));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Quantity");
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 91, 33));
        jPanel4.add(jbname, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 150, 34));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Booke Name");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 14, -1, -1));
        jPanel4.add(jproduct1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 250, 190, 34));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Product Overview");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 140, -1));

        jphoto1.setBackground(new java.awt.Color(255, 255, 255));
        jphoto1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jphoto1.setOpaque(true);
        jPanel4.add(jphoto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 200, 150));

        jtotalamountcost.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jPanel4.add(jtotalamountcost, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 80, 34));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Total  Cost:");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, 90, -1));

        jdescription.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jdescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 150, 32));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Description");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 88, -1));

        jsupplier.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 150, 32));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Suppier");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 80, 25));
        jPanel4.add(jsellprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 150, 34));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Selling Price");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 100, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Php");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 30, 36));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 510, 390));

        jPanel67.setBackground(new java.awt.Color(0, 102, 51));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Date:");

        jLabel17.setBackground(new java.awt.Color(51, 51, 51));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Time:");

        date.setBackground(new java.awt.Color(0, 102, 51));
        date.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        date.setForeground(new java.awt.Color(204, 255, 204));
        date.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        date.setText("0");
        date.setBorder(null);
        date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateActionPerformed(evt);
            }
        });

        time.setBackground(new java.awt.Color(0, 102, 51));
        time.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        time.setForeground(new java.awt.Color(204, 255, 204));
        time.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        time.setText("0");
        time.setBorder(null);

        javax.swing.GroupLayout jPanel67Layout = new javax.swing.GroupLayout(jPanel67);
        jPanel67.setLayout(jPanel67Layout);
        jPanel67Layout.setHorizontalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel17)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 110, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1350, 670));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Home4HomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Home4HomeMouseEntered
        // TODO add your handling code here:
        Home4.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_Home4HomeMouseEntered

    private void Home4HomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Home4HomeMouseExited
        // TODO add your handling code here:
        Home4.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_Home4HomeMouseExited

    private void jPanel20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseEntered
        // TODO add your handling code here:
        jPanel20.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel20MouseEntered

    private void jPanel20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseExited
        // TODO add your handling code here:
        jPanel20.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel20MouseExited

    private void jdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdeleteActionPerformed
        if (jTable6.getSelectedRowCount() == 1) {
            int row = jTable6.getSelectedRow();
            String cell = jTable6.getModel().getValueAt(row, 0).toString();
            String sql = "DELETE FROM `products` where barcode= " + cell;

            try {

                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Successfully Deleted!");
                updateproductsales();

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, e);

            } finally {
                try {
                    pst.close();
                    rs.close();

                } catch (Exception e) {
                }

                DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
                //delete row
                if (jTable6.getSelectedRowCount() == 1) {
                    //if single row is selected then delete
                    model.removeRow(jTable6.getSelectedRow());
                    jbname.setText("");
                    jquantity.setText("");
                    jbuyprice.setText("");

                    jsupplier.setText("");

                    jphoto1.setText("");
                    jbuyprice.setText("");
                    jtotalamountcost.setText("");
                    jdescription.setText("");
                    jproduct1.setText("");
                    jphoto1.setIcon(null);

                }
            }
        } else {

            if (jTable6.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Field is empty!");
            } else {
                JOptionPane.showMessageDialog(null, "Please select a single row to delete!");
            }
        }


    }//GEN-LAST:event_jdeleteActionPerformed

    private void jupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jupdateActionPerformed
        // TODO add your handling code here:
        if (jTable6.getSelectedRowCount() == 1) {
            DefaultTableModel model2 = (DefaultTableModel) jTable6.getModel();
            int Myindex = jTable6.getSelectedRow();
            int Mycolumn = jTable6.getSelectedColumn();

            String value = model2.getValueAt(Myindex, Mycolumn).toString();
            int id = Integer.parseInt(model2.getValueAt(Myindex, 0).toString());
            String bname = jbname.getText();
            String qty = jquantity.getText();
            String bprice = jbuyprice.getText();
            String sprice = jtotalamountcost.getText();
            String des = jdescription.getText();
            String imgl = jproduct1.getText();

            try {
                pst = con.prepareStatement("UPDATE `products` set product_name= ?, quantity= ?, buyingprice= ?,sellingprice= ?,description= ?,productImage= ? where barcode= ?");
                //
                pst.setString(1, bname);
                pst.setString(2, qty);
                pst.setString(3, bprice);
                pst.setString(4, sprice);
                pst.setString(5, des);
                pst.setString(6, imgl);
                pst.setInt(7, id);

                int k = pst.executeUpdate();

                if (k == 1) {
                    JOptionPane.showMessageDialog(this, "Successfully Updated");
                    jbname.setText("");
                    jsupplier.setText("");
                    jquantity.setText("");
                    jphoto1.setText("");
                    jbuyprice.setText("");
                    jtotalamountcost.setText("");
                    jdescription.setText("");
                    jproduct1.setText("");
                    jphoto1.setIcon(null);
                    jbname.requestFocus();

                    salesupdate();
//               invetorytransacupdate();
                }

            } catch (SQLException ex) {
                Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            if (jTable6.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Field is empty!");
            }
        }

    }//GEN-LAST:event_jupdateActionPerformed

    private void jPanel12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseEntered
        // TODO add your handling code here:
        jPanel12.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel12MouseEntered

    private void jPanel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel12MouseExited
        // TODO add your handling code here:
        jPanel12.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel12MouseExited

    private void jPanel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseEntered
        // TODO add your handling code here:
        jPanel13.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel13MouseEntered

    private void jPanel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseExited
        // TODO add your handling code here:
        jPanel13.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel13MouseExited

    private void jTable6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable6MouseClicked
        // TODO add your handling code here:

        DefaultTableModel model2 = (DefaultTableModel) jTable6.getModel();
        //          InputStream is=new FileInputStream(new File(photopath));
        //          pst.setBlob(5, is);
        int Myindex = jTable6.getSelectedRow();
        int Mycolumn = jTable6.getSelectedColumn();

        String value = model2.getValueAt(Myindex, Mycolumn).toString();
        int id = Integer.parseInt(model2.getValueAt(Myindex, 0).toString());
        jbname.setText(model2.getValueAt(Myindex, 1).toString());
        jdescription.setText(model2.getValueAt(Myindex, 2).toString());
        jquantity.setText(model2.getValueAt(Myindex, 3).toString());
        jbuyprice.setText(model2.getValueAt(Myindex, 4).toString());
        jtotalamountcost.setText(model2.getValueAt(Myindex, 6).toString());
        jsellprice.setText(model2.getValueAt(Myindex, 5).toString());
        jphoto1.setIcon(resetImageSize(null, retrieveData().get(Myindex).getPhoto()));
        jproduct1.setText(model2.getValueAt(Myindex, 7).toString());

        jsupplier.setText(model2.getValueAt(Myindex, 8).toString());

        //        byte[] img =
    }//GEN-LAST:event_jTable6MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

        DefaultTableModel df = (DefaultTableModel) jTable6.getModel();
        df.setRowCount(0);
        try {

            //             String st = jchoose.getSelectedItem().toString();
            if (jchoose.getSelectedIndex() == 0) {
                availproducts();
                jadd.setEnabled(false);
                jupdate.setEnabled(true);
                jdelete.setEnabled(true);
                jdecline.setEnabled(false);

            } else if (jchoose.getSelectedIndex() == 1) {
                offeredproducts();
                jadd.setEnabled(true);
                jdecline.setEnabled(true);
                jupdate.setEnabled(false);
                jdelete.setEnabled(false);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsearchMouseClicked
        // TODO add your handling code here:

        String st = jinput.getText();

        DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable6.setRowSorter(tr);
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

    private void jdeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdeclineActionPerformed
        // TODO add your handling code here:
        productdec();

        int row = jTable6.getSelectedRow();

        String cell = jTable6.getModel().getValueAt(row, 0).toString();

        String sql = "DELETE FROM `offered_products` where product_id= " + cell;

        try {
            pst = con.prepareStatement(sql);
            pst.execute();

            updateproduct();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                pst.close();
                rs.close();

            } catch (Exception e) {
            }

            DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
            //delete row
            if (jTable6.getSelectedRowCount() == 1) {
                //if single row is selected then delete
                model.removeRow(jTable6.getSelectedRow());
                jbname.setText("");
                jbuyprice.setText("");
                jquantity.setText("");
                jsellprice.setText("");
                jproduct1.setText("");
                jdescription.setText("");
                jsupplier.setText("");
                jtotalamountcost.setText("");
                jphoto1.setIcon(null);
            } else {
                if (jTable6.getSelectedRowCount() == 0) {
                    //if table is empty np data the show message..
                    JOptionPane.showMessageDialog(null, "Table is empty!");
                } else {
                    JOptionPane.showMessageDialog(null, "No row selected!");

                }
            }
        }

    }//GEN-LAST:event_jdeclineActionPerformed

    private void jaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jaddActionPerformed

        // TODO add your handling code here:
        String sellingprice = jsellprice.getText();

        if (sellingprice.equals("")) {
            JOptionPane.showMessageDialog(null, "You forgot to put selling price!");
        } else {

            productapp();

            int row = jTable6.getSelectedRow();

            String cell = jTable6.getModel().getValueAt(row, 0).toString();

            String sql = "DELETE FROM `offered_products` where product_id= " + cell;

            try {
                pst = con.prepareStatement(sql);
                pst.execute();

                updateproduct();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();

                } catch (Exception e) {
                }

                DefaultTableModel model = (DefaultTableModel) jTable6.getModel();
                //delete row
                if (jTable6.getSelectedRowCount() == 1) {
                    //if single row is selected then delete
                    model.removeRow(jTable6.getSelectedRow());
                    jbname.setText("");
                    jbuyprice.setText("");
                    jquantity.setText("");
                    jsellprice.setText("");
                    jproduct1.setText("");
                    jphoto1.setIcon(null);
                    jdescription.setText("");
                    jsupplier.setText("");
                    jtotalamountcost.setText("");
                } else {
                    if (jTable6.getSelectedRowCount() == 0) {
                        //if table is empty np data the show message..
                        JOptionPane.showMessageDialog(null, "Table is empty!");
                    } else {
                        JOptionPane.showMessageDialog(null, "No row selected!");

                    }
                }
            }
        }

    }//GEN-LAST:event_jaddActionPerformed

    private void jclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jclearActionPerformed
        // TODO add your handling code here:
        jbname.setText("");
        jquantity.setText("");
        jtotalamountcost.setText("");
        jbuyprice.setText("");
        jdescription.setText("");
        jproduct1.setText("");
        jphoto1.setIcon(null);
        jsupplier.setText("");
    }//GEN-LAST:event_jclearActionPerformed

    private void dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateActionPerformed

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
        soldadmin admin = new soldadmin(jusername.getText());
        admin.show();
        dispose();

        try {
            Statement st = con.createStatement();
            String query1 = "select * from `sales_products`";
            ResultSet rs1 = st.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..
                String bid2 = rs1.getString("salesid");
                String salesid2 = rs1.getString("barcode");
                String bookn2 = rs1.getString("product_name");
                String buying = rs1.getString("buyingprice");
                String price2 = rs1.getString("price");
                String qty2 = rs1.getString("quantity");
                String totl2 = rs1.getString("total");
                String dae = rs1.getString("date");
                String usr2 = rs1.getString("userid");
                String sup = rs1.getString("supplier");
                //string array for store data into jtable..
                String tbData[] = {bid2, salesid2, bookn2, buying, price2, qty2, totl2, dae, usr2, sup};
                DefaultTableModel tabledata = (DefaultTableModel) jTable9.getModel();

                //add string array data into jtable..
                tabledata.addRow(tbData);

            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(category.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        LoginUsers main = new LoginUsers();
        main.setVisible(true);
        main.pack();
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        // TODO add your handling code here:

        adminpage admin = new adminpage();
        admin.pack();
        admin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        // TODO add your handling code here:
        jPanel10.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel10MouseExited

    private void jPanel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseEntered
        // TODO add your handling code here:
        jPanel10.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel10MouseEntered

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel23MouseClicked

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
            java.util.logging.Logger.getLogger(category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new category().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Home4;
    private javax.swing.JTextField date;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable6;
    private javax.swing.JButton jadd;
    private javax.swing.JTextField jbname;
    private javax.swing.JTextField jbuyprice;
    private javax.swing.JComboBox<String> jchoose;
    private javax.swing.JButton jclear;
    private javax.swing.JButton jdecline;
    private javax.swing.JButton jdelete;
    private javax.swing.JTextField jdescription;
    private javax.swing.JTextField jinput;
    private javax.swing.JLabel jphoto1;
    private javax.swing.JTextField jproduct1;
    private javax.swing.JTextField jquantity;
    private javax.swing.JLabel jsearch;
    private javax.swing.JTextField jsellprice;
    private javax.swing.JTextField jsupplier;
    private javax.swing.JTextField jtotalamountcost;
    private javax.swing.JButton jupdate;
    private javax.swing.JLabel jusername;
    private javax.swing.JTextField time;
    // End of variables declaration//GEN-END:variables
}
