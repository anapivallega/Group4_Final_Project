/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package src;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static src.category.jTable6;

/**
 *
 * @author Student.Admin
 */
public class Suppliers extends javax.swing.JFrame {

    /**
     * Creates new form Suppliers
     */
    public Suppliers() {
        initComponents();
        Connect();
        dt();
        time();
        diabled();
        offeredproducts();

    }

    public Suppliers(String userad) {
        initComponents();
        Connect();
        jusername.setText(userad);
        dt();
        time();
        diabled();
        offeredproducts();

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

//       public String getstatus() {
//        return status;
//    }
//
//    public void setstatus(String status) {
//        this.status = status;
//    }
//    
    }

    public void dt() {

        Date d = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String dd = sdf.format(d);
        date1.setText(dd);
    }

    Timer t;
    SimpleDateFormat st;

    public void time() {
        t = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dt = new Date();
                st = new SimpleDateFormat("hh-mm-ss a");
                String tt = st.format(dt);
                time1.setText(tt);

            }
        });
        t.start();
    }

    Connection con;
    PreparedStatement pst;
    PreparedStatement pst1;
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

    public void product() {
        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);

        String timee = time1.getText();
        String bname = jbname.getText();
        String desc = jdescription.getText();
        String buyingprice = jprice.getText();

        String supp = jsupplier.getText();
        String img1 = jlink.getText();
        String tal = jtotalcost_amount.getText();
//   
        try {

            String query6 = "insert into `offered_products`(product_name,description,quantity,price,total_amount,date,time,product_image,supplier)values(?,?,?,?,?,?,?,?,?);";
            pst = con.prepareStatement(query6, Statement.RETURN_GENERATED_KEYS);
            pst1 = con.prepareStatement(query6);
            pst1.setString(1, bname);
            pst1.setString(2, desc);
            pst1.setInt(3, Integer.parseInt(jquantity.getText()));
            pst1.setString(4, buyingprice);
            pst1.setString(5, tal);
            pst1.setString(6, date);
            pst1.setString(7, timee);

            InputStream is = new FileInputStream(new File(photopath));
            pst1.setBlob(8, is);
//              pst1.setString(8, img1);
            pst1.setString(9, supp);
            pst1.executeUpdate();

//            fillTable();
//            rs = pst.getGeneratedKeys();
            JOptionPane.showMessageDialog(this, "Product Added");

        } catch (SQLException e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);
//      
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Suppliers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void diabled() {
        jtotalcost_amount.setEnabled(false);
        jdelete.setEnabled(false);
        jupdate.setEnabled(false);
        jlink.setEnabled(false);
    }

    public void displayImage(String imgPath, JLabel label) {
        ImageIcon imgicon = new ImageIcon(imgPath);
        Image img = imgicon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
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
        Image img1 = img.getScaledInstance(jpimage.getWidth(), jpimage.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon ph = new ImageIcon(img1);
        return ph;
    }

    public ArrayList<productstock> retrieveData() {
        ArrayList<productstock> al = null;
        al = new ArrayList<productstock>();

        try {

            String qry = "select * from offered_products";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(qry);
            productstock product;
            while (rs.next()) {
                product = new productstock(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getBytes(9), rs.getString(10));
                al.add(product);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return al;
    }
//    

    public void fillTable() {
        ArrayList<productstock> al = retrieveData();
        DefaultTableModel model = (DefaultTableModel) jTable30.getModel();
        model.setRowCount(0); // Empty/clear the table
        Object[] row = new Object[8];
        for (int i = 0; i < al.size(); i++) {
            row[0] = al.get(i).getCode();
            row[1] = al.get(i).getpName();
            row[2] = al.get(i).getdescrip();
            row[3] = al.get(i).getquantity();
            row[4] = al.get(i).getbprice();
            row[5] = al.get(i).gettcost();
            row[6] = al.get(i).getPhoto();
            row[7] = al.get(i).getsuppl();

            model.addRow(row);
        }
        //model.setRowCount(0);
    }

    public void offeredproducts() {

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

//                string array for store data into jtable..
                String tbData[] = {bid, bookn, des, qty, price1, tots, image, sup};
                DefaultTableModel tblModel = (DefaultTableModel) jTable30.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

//            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(adminpage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateproductsales() {
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

    public void salesupdate() {
        try {
            pst = con.prepareStatement("select * from `offered_products`");
            rs = pst.executeQuery();

            ResultSetMetaData rsd = rs.getMetaData();
            int c;

            c = rsd.getColumnCount();
            DefaultTableModel de = (DefaultTableModel) jTable30.getModel();
            de.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getString("product_id"));
                    v2.add(rs.getString("product_name"));
                    v2.add(rs.getString("description"));
                    v2.add(rs.getString("quantity"));
                    v2.add(rs.getString("price"));
                    v2.add(rs.getString("total_amount"));
//                   v2.add(rs.getString("date"));
//                   v2.add(rs.getString("time"));
                    v2.add(rs.getBytes("product_image"));
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
     *
     *
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jusername = new javax.swing.JLabel();
        Home = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jsearch = new javax.swing.JLabel();
        jinput = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jbname = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jquantity = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jprice = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jdescription = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jtotalcost_amount = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jsupplier = new javax.swing.JTextField();
        jlink = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jpimage = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable30 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        date1 = new javax.swing.JTextField();
        time1 = new javax.swing.JTextField();
        jadd = new javax.swing.JButton();
        jupdate = new javax.swing.JButton();
        jdelete = new javax.swing.JButton();
        jclear = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jshowadd = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 0), new java.awt.Color(0, 102, 0), new java.awt.Color(0, 51, 0), new java.awt.Color(0, 51, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(44, 116, 60));

        jPanel7.setBackground(new java.awt.Color(44, 116, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile.png"))); // NOI18N

        jusername.setForeground(new java.awt.Color(255, 255, 255));
        jusername.setText("Username");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jusername))
                    .addComponent(jLabel1))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jusername, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Home.setBackground(new java.awt.Color(44, 116, 60));
        Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                HomeMouseExited(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Home");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout HomeLayout = new javax.swing.GroupLayout(Home);
        Home.setLayout(HomeLayout);
        HomeLayout.setHorizontalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HomeLayout.setVerticalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jPanel10.setBackground(new java.awt.Color(44, 116, 60));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10MouseExited(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chat.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Messages");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        jLabel9.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Support");

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Home, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel9)))
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, -1, 610));

        jPanel3.setBackground(new java.awt.Color(44, 116, 60));

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

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Cooper Black", 0, 14)); // NOI18N
        jLabel14.setText("ADD PRODUCTS");
        jPanel15.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(254, 12, 133, 22));

        jLabel22.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel22.setText("Name of Product");
        jPanel15.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, 24));

        jbname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbnameActionPerformed(evt);
            }
        });
        jPanel15.add(jbname, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 160, 32));

        jLabel24.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel24.setText("Quantity");
        jPanel15.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 109, 24));

        jquantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jquantityActionPerformed(evt);
            }
        });
        jPanel15.add(jquantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 90, 32));

        jLabel25.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel25.setText("Price per Kilo");
        jPanel15.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, 109, 24));

        jprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpriceActionPerformed(evt);
            }
        });
        jPanel15.add(jprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 109, 32));

        jLabel26.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel26.setText("Product Description");
        jPanel15.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, -1, 24));

        jdescription.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdescriptionActionPerformed(evt);
            }
        });
        jPanel15.add(jdescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 260, 40));

        jLabel27.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 204, 51));
        jLabel27.setText("Show Total Amount");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });
        jPanel15.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 210, -1, 24));

        jtotalcost_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtotalcost_amountActionPerformed(evt);
            }
        });
        jPanel15.add(jtotalcost_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 240, 90, 40));

        jLabel29.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel29.setText("Product Image");
        jPanel15.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 107, 24));

        jLabel31.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel31.setText("Supplier");
        jPanel15.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 107, 24));

        jsupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jsupplierActionPerformed(evt);
            }
        });
        jPanel15.add(jsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 242, 120, 40));
        jPanel15.add(jlink, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, 260, 30));

        jButton1.setText("Upload Image");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel15.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 292, 120, 30));

        jpimage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jpimage.setOpaque(true);
        jPanel15.add(jpimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 140, 110));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("kg");
        jPanel15.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 30, 35));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setText("Php");
        jPanel15.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 30, 36));

        jTable30.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Product_Name", "Description", "Quantity", "Price", "Total amount", "Image", "Supplier"
            }
        ));
        jTable30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable30MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable30);

        jPanel8.setBackground(new java.awt.Color(0, 102, 51));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Date:");

        jLabel19.setBackground(new java.awt.Color(51, 51, 51));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Time:");

        date1.setBackground(new java.awt.Color(0, 102, 51));
        date1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        date1.setForeground(new java.awt.Color(204, 255, 204));
        date1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        date1.setText("0");
        date1.setBorder(null);
        date1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date1ActionPerformed(evt);
            }
        });

        time1.setBackground(new java.awt.Color(0, 102, 51));
        time1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        time1.setForeground(new java.awt.Color(204, 255, 204));
        time1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        time1.setText("0");
        time1.setBorder(null);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(time1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(time1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jadd.setBackground(new java.awt.Color(255, 204, 51));
        jadd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jadd.setText("ADD");
        jadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jaddActionPerformed(evt);
            }
        });

        jupdate.setBackground(new java.awt.Color(255, 204, 51));
        jupdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jupdate.setText("Update");
        jupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jupdateActionPerformed(evt);
            }
        });

        jdelete.setBackground(new java.awt.Color(255, 204, 51));
        jdelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jdelete.setText("Delete");
        jdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdeleteActionPerformed(evt);
            }
        });

        jclear.setBackground(new java.awt.Color(255, 204, 51));
        jclear.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jclear.setText("Clear");
        jclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jclearActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 204, 0));
        jLabel2.setText("Show ALL");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jshowadd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jshowadd.setForeground(new java.awt.Color(255, 204, 0));
        jshowadd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jshowaddMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Offered Products");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jclear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jupdate)
                        .addGap(37, 37, 37)
                        .addComponent(jadd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jshowadd, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jshowadd, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(50, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jadd, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jdelete, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jclear, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41))))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 1030, 490));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ImageIcon_2.png"))); // NOI18N
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 0, 120, 110));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbnameActionPerformed

    private void jquantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jquantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jquantityActionPerformed

    private void jpriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpriceActionPerformed

    private void jdescriptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdescriptionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jdescriptionActionPerformed

    private void jtotalcost_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtotalcost_amountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtotalcost_amountActionPerformed

    private void HomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseEntered
        // TODO add your handling code here:
        Home.setBackground(new Color(35, 77, 32));

    }//GEN-LAST:event_HomeMouseEntered

    private void HomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseExited
        // TODO add your handling code here:
        Home.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_HomeMouseExited

    private void jPanel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseEntered
        // TODO add your handling code here:
        jPanel10.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel10MouseEntered

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        // TODO add your handling code here:
        jPanel10.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel10MouseExited

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

    private void date1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_date1ActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        Supplier_Message main = new Supplier_Message(jusername.getText());
        main.setVisible(true);
        main.pack();

    }//GEN-LAST:event_jLabel6MouseClicked

    private void jaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jaddActionPerformed
        // TODO add your handling code here:

        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);

        String timee = time1.getText();
        String bname = jbname.getText();
        String desc = jdescription.getText();
        String buyingprice = jprice.getText();
        ImageIcon ph = (ImageIcon) jpimage.getIcon();
        String supp = jsupplier.getText();
        String img1 = jlink.getText();
        String tal = jtotalcost_amount.getText();
        String user = jusername.getText();
        if (bname.trim().equals("") || bname.trim().equals("")
                || desc.trim().equals("") || desc.trim().equals("")
                || buyingprice.trim().equals("") || buyingprice.trim().equals("")
                || supp.trim().equals("") || supp.trim().equals("")
                || //                        img1.trim().equals("") ||  img1.trim().equals("") ||
                tal.trim().equals("") || tal.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Other fields are empty!");
        }
        if (img1.trim().equals("") || img1.trim().equals("")) {

            JOptionPane.showMessageDialog(null, " Please provide image product!");

        }
        if (!user.trim().equals(supp)) {

            JOptionPane.showMessageDialog(null, "This" + supp + "is not the right supplier!");
        } else if (jbname.getText().length() <= 0
                || jdescription.getText().length() <= 0
                || jsupplier.getText().length() <= 0
                || jtotalcost_amount.getText().length() <= 0
                || jlink.getText().length() <= 0
                || jprice.getText().length() <= 0
                || jquantity.getText().length() <= 0) {

        } else {
            JOptionPane.showMessageDialog(null, "Product has been submitted!");
            product();

            offeredproducts();
            fillTable();

            jbname.setText("");
            jdescription.setText("");
            jlink.setText("");
            jsupplier.setText("");
            jprice.setText("");
            jquantity.setText("");
            jtotalcost_amount.setText("");

            jpimage.setIcon(null);

        }
    }//GEN-LAST:event_jaddActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter fnef = new FileNameExtensionFilter(".Product-Sales", "jpg", "png", "webp", "jpeg");
        chooser.addChoosableFileFilter(fnef);
        int ans = chooser.showSaveDialog(null);
        if (ans == JFileChooser.APPROVE_OPTION) {
            File selectedPhoto = chooser.getSelectedFile();

            String path = selectedPhoto.getAbsolutePath();
            jpimage.setIcon(resetImageSize(path, null));
            this.photopath = path;
            jlink.setText(path);
            //or...
//            displayImage(path, jproductimage);
            System.out.println(path);

        } else {
            System.out.println("no file selected");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        // TODO add your handling code here:
        String price1 = jprice.getText();
        String quan = jquantity.getText();

        if (price1.trim().equals("") || price1.trim().equals("")
                || quan.trim().equals("") || quan.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "please input price or quantity !");
        } else {
            int qty = Integer.parseInt(jquantity.getText());
            int price = Integer.parseInt(jprice.getText());

            int btot = price * qty;
            jtotalcost_amount.setText(String.valueOf(btot));
        }
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsearchMouseClicked
        // TODO add your handling code here:

        String st = jinput.getText();

        DefaultTableModel model = (DefaultTableModel) jTable30.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable30.setRowSorter(tr);
        tr.setRowFilter(RowFilter.regexFilter(st.trim()));
    }//GEN-LAST:event_jsearchMouseClicked

    private void jTable30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable30MouseClicked
        // TODO add your handling code here:

        DefaultTableModel model2 = (DefaultTableModel) jTable30.getModel();

        int Myindex = jTable30.getSelectedRow();
        int Mycolumn = jTable30.getSelectedColumn();

        String value = model2.getValueAt(Myindex, Mycolumn).toString();
        int id = Integer.parseInt(model2.getValueAt(Myindex, 0).toString());
        jbname.setText(model2.getValueAt(Myindex, 1).toString());
        jdescription.setText(model2.getValueAt(Myindex, 2).toString());
        jquantity.setText(model2.getValueAt(Myindex, 3).toString());
        jprice.setText(model2.getValueAt(Myindex, 4).toString());
        jtotalcost_amount.setText(model2.getValueAt(Myindex, 5).toString());

        jsupplier.setText(model2.getValueAt(Myindex, 7).toString());
//        jpimage.setIcon(model2.getValueAt(Myindex, 6).toString());
//        jlink.setText(model2.getValueAt(Myindex, 6).toString());
        jpimage.setIcon(resetImageSize(null, retrieveData().get(Myindex).getPhoto()));

        jadd.setEnabled(false);
        jdelete.setEnabled(true);
        jupdate.setEnabled(true);
        String text = "Return to Add";

        jshowadd.setText(text);
    }//GEN-LAST:event_jTable30MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable30.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable30.setRowSorter(tr);
        DefaultTableModel df = (DefaultTableModel) jTable30.getModel();
        df.setRowCount(0);
//               fillTable();

        offeredproducts();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jupdateActionPerformed

        if (jTable30.getSelectedRowCount() == 1) {
            DefaultTableModel model2 = (DefaultTableModel) jTable30.getModel();
            int Myindex = jTable30.getSelectedRow();
            int Mycolumn = jTable30.getSelectedColumn();

            String value = model2.getValueAt(Myindex, Mycolumn).toString();
            int id = Integer.parseInt(model2.getValueAt(Myindex, 0).toString());
//   
            String bname = jbname.getText();
            String qty = jquantity.getText();
            String bprice = jprice.getText();
            String amount = jtotalcost_amount.getText();
            String des = jdescription.getText();
            String imgl = jlink.getText();
            ImageIcon ph = (ImageIcon) jpimage.getIcon();

            String supp = jusername.getText();

            if (bname.trim().equals("") || bname.trim().equals("")
                    || qty.trim().equals("") || qty.trim().equals("")
                    || des.trim().equals("") || des.trim().equals("")
                    || supp.trim().equals("") || supp.trim().equals("")
                    || //                        imgl.trim().equals("") ||  imgl.trim().equals("") ||
                    amount.trim().equals("") || amount.trim().equals("")) {
                JOptionPane.showMessageDialog(null, " Other fields is empty!");
            } //            if (imgl.trim().equals("") || imgl.trim().equals("")) {
            //                JOptionPane.showMessageDialog(null, " please provide product image!");
            //            } 
            else if (photopath == null) {

                try {
                    pst = con.prepareStatement("UPDATE `offered_products` set product_name= ?,description=?, quantity= ?, price= ?,total_amount= ?, supplier=? where product_id= ?");
                    pst.setString(1, bname);
                    pst.setString(2, des);
                    pst.setString(3, qty);
                    pst.setString(4, bprice);
                    pst.setString(5, amount);
//               pst.setString(6,imgl);
//                pst.setBlob(6, is);
                    pst.setString(6, supp);
                    pst.setInt(7, id);
                    int k = pst.executeUpdate();
                    fillTable();
                    if (k == 1) {
                        JOptionPane.showMessageDialog(this, "Successfully Updated");
                        salesupdate();

                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

            } else {

                try {
                    InputStream is = new FileInputStream(new File(photopath));
                    pst = con.prepareStatement("UPDATE `offered_products` set product_name= ?,description=?, quantity= ?, price= ?,total_amount= ?, product_image= ?, supplier=? where product_id= ?");
                    pst.setString(1, bname);
                    pst.setString(2, des);
                    pst.setString(3, qty);
                    pst.setString(4, bprice);
                    pst.setString(5, amount);
//            pst.setString(6,imgl);
                    pst.setBlob(6, is);
                    pst.setString(7, supp);
                    pst.setInt(8, id);
                    int k = pst.executeUpdate();

                    if (k == 1) {
                        JOptionPane.showMessageDialog(this, "Successfully Updated");

                        salesupdate();
                        jbname.setText("");
//                    jsupplier.setText("");
                        jquantity.setText("");
                        jlink.setText("");
                        jpimage.setIcon(null);
                        jtotalcost_amount.setText("");
                        jdescription.setText("");
                        jprice.setText("");
                        jbname.requestFocus();

//               invetorytransacupdate();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Suppliers.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } else {

            if (jTable30.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Field is empty!");
            }
        }

        jadd.setEnabled(true);

        String text = "";
        jshowadd.setText(text);
    }//GEN-LAST:event_jupdateActionPerformed

    private void jdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdeleteActionPerformed
        // TODO add your handling code here:

        if (jTable30.getSelectedRowCount() == 1) {
            int row = jTable30.getSelectedRow();
            String cell = jTable30.getModel().getValueAt(row, 0).toString();
            String sql = "DELETE FROM `offered_products` where product_id= " + cell;

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

                DefaultTableModel model = (DefaultTableModel) jTable30.getModel();
                //delete row
                if (jTable30.getSelectedRowCount() == 1) {
                    //if single row is selected then delete
                    model.removeRow(jTable30.getSelectedRow());
                    jbname.setText("");
                    jquantity.setText("");
                    jprice.setText("");
                    jpimage.setIcon(null);
                    jlink.setText("");
                    jtotalcost_amount.setText("");
//                    jsupplier.setText("");
                    jdescription.setText("");

                }
            }
        } else {

            if (jTable30.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Field is empty!");
            } else {
                JOptionPane.showMessageDialog(null, "Please select a single row to delete!");
            }
        }
        jadd.setEnabled(true);

        String text = "";
        jshowadd.setText(text);
    }//GEN-LAST:event_jdeleteActionPerformed

    private void jclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jclearActionPerformed
        // TODO add your handling code here:
        jbname.setText("");
        jquantity.setText("");
        jprice.setText("");
        jlink.setText("");
        jtotalcost_amount.setText("");
//        jsupplier.setText("");
        jdescription.setText("");


    }//GEN-LAST:event_jclearActionPerformed

    private void jshowaddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jshowaddMouseClicked
        // TODO add your handling code here:
        String text = "";
        jshowadd.setText(text);

        jdelete.setEnabled(false);
        jupdate.setEnabled(false);
        jadd.setEnabled(true);

        jbname.setText("");
        jdescription.setText("");
        jlink.setText("");
        jsupplier.setText("");
        jprice.setText("");
        jquantity.setText("");
        jtotalcost_amount.setText("");
        jpimage.setIcon(null);

    }//GEN-LAST:event_jshowaddMouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:

        LoginUsers main = new LoginUsers();
        main.setVisible(true);
        main.pack();
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

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
            java.util.logging.Logger.getLogger(Suppliers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Suppliers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Suppliers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Suppliers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Suppliers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Home;
    private javax.swing.JTextField date1;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable30;
    private javax.swing.JButton jadd;
    private javax.swing.JTextField jbname;
    private javax.swing.JButton jclear;
    private javax.swing.JButton jdelete;
    private javax.swing.JTextField jdescription;
    private javax.swing.JTextField jinput;
    private javax.swing.JTextField jlink;
    private javax.swing.JLabel jpimage;
    private javax.swing.JTextField jprice;
    private javax.swing.JTextField jquantity;
    private javax.swing.JLabel jsearch;
    private javax.swing.JLabel jshowadd;
    private javax.swing.JTextField jsupplier;
    private javax.swing.JTextField jtotalcost_amount;
    private javax.swing.JButton jupdate;
    public javax.swing.JLabel jusername;
    private javax.swing.JTextField time1;
    // End of variables declaration//GEN-END:variables
}
