/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

//import com.ibm.icu.util.Calendar;
//import com.ibm.icu.util.GregorianCalendar;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import static src.LoginUsers.id;
import static src.category.jTable6;
import static src.soldadmin.jTable9;
import static src.soldproducts.jTable5;

/**
 *
 * @author 1styrGroupB
 */
public class inventorypage extends javax.swing.JFrame {

    String filename = null;
    byte[] product_image = null;

    public inventorypage() {

        initComponents();

        Connect();

        choice();
        dt();
        time();

    }

    public inventorypage(String usernamee) {
        initComponents();
        jusernamee.setText(usernamee);
        Connect();
        dt();
        time();
        choice();

    }

    public class flowers {

        int id;
        String name;

        public flowers(int id, String name) {
            this.id = id;
            this.name = name;

        }

        public String toString() {
            return name;
        }
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

//     
//       public int getsprice() {
//        return sprice;
//    }
//    
//     public void setsprice(int sprice) {
//        this.sprice = sprice;
//    }
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

    public void toadd() {
        try {
            Statement st = con.createStatement();
            String query1 = "select * from `approved_products`";
            ResultSet rs1 = st.executeQuery(query1);
//         
//
            while (rs1.next()) {
                //data wil added until finished..
                String id = rs1.getString("id");
                String hh = rs1.getString("product_name");
                String des1 = rs1.getString("description");
                String trr = rs1.getString("quantity");
                String buy = rs1.getString("buyingprice");
                String sell1 = rs1.getString("sellingprice");
                String talm = rs1.getString("totalamount_cost");
                String pro = rs1.getString("product_image");
                String sup = rs1.getString("supplier");

                //string array for store data into jtable..
                String tbData[] = {id, hh, des1, trr, buy, sell1, talm, pro, sup};
                DefaultTableModel tblModel = (DefaultTableModel) jTable4.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);
//
            }

        } catch (SQLException ex) {
            Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
        }
//
    }
//

//     
    public void salesupdate() {
        try {
            pst = con.prepareStatement("select * from `products`");
            rs = pst.executeQuery();

            ResultSetMetaData rsd = rs.getMetaData();
            int c;

            c = rsd.getColumnCount();
            DefaultTableModel de = (DefaultTableModel) jTable4.getModel();
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
                    v2.add(rs.getString("status"));

                }
                de.addRow(v2);

            }
        } catch (SQLException ex) {
            Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void AddRowToJcategoryTable(Object[] dataRow) {
        DefaultTableModel model6 = (DefaultTableModel) jTable6.getModel();
        model6.addRow(dataRow);
    }

    public static void AddRowToJsoldproductTable(Object[] dataRow) {
        DefaultTableModel model5 = (DefaultTableModel) jTable5.getModel();
        model5.addRow(dataRow);
    }

    Connection con;
    PreparedStatement pst;
    PreparedStatement pst9;
    PreparedStatement pst1;
    ResultSet rs;
    DefaultTableModel df;

//    inventorypage() {
//       inventorypage in = new inventorypage();
//       
//    }
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

    String photopath = "";

    public ImageIcon resetImageSize(String photopath, byte[] photo) {
        ImageIcon myPhoto = null;
        if (photopath != null) {
            myPhoto = new ImageIcon(photopath);

        } else {
            myPhoto = new ImageIcon(photo);
        }
        Image img = myPhoto.getImage();
        Image img1 = img.getScaledInstance(jproductimage.getWidth(), jproductimage.getHeight(),
                Image.SCALE_SMOOTH);
        ImageIcon ph = new ImageIcon(img1);
        return ph;
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

    public void dt() {

        Date d = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String dd = sdf.format(d);
        date.setText(dd);
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
                time.setText(tt);

            }
        });
        t.start();
    }

    public void choice() {
        jbname.setEnabled(false);
        jquantity.setEnabled(false);
        jbuyprice.setEnabled(false);
        jsellprice.setEnabled(false);
        jdescription.setEnabled(false);
        jlinkimage.setEnabled(false);
        jsupplier.setEnabled(false);
        jtotalamount.setEnabled(false);
        jid.setEnabled(false);
        jimageproduct.setEnabled(false);

    }

    public void choice1() {
        jbname.setEnabled(true);
        jquantity.setEnabled(true);
        jbuyprice.setEnabled(true);
        jsellprice.setEnabled(true);
        jdescription.setEnabled(true);
        jlinkimage.setEnabled(true);
        jsupplier.setEnabled(true);
        jtotalamount.setEnabled(true);
        jid.setEnabled(true);
        jimageproduct.setEnabled(true);

    }
//       

    public void product() {
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
        String tamount = jtotalamount.getText();
        String supp = jsupplier.getText();
        String img1 = jlinkimage.getText();
        String stats = "Not yet displayed";
//   
        try {

            String query6 = "insert into `products`(product_name,description,quantity,buyingprice,sellingprice,totalamount_cost,productImage,supplier,status)values(?,?,?,?,?,?,?,?,?);";
            pst9 = con.prepareStatement(query6, Statement.RETURN_GENERATED_KEYS);
            pst9 = con.prepareStatement(query6);
            pst9.setString(1, bname);
            pst9.setString(2, desc);
            pst9.setInt(3, Integer.parseInt(jquantity.getText()));
            pst9.setString(4, buyingprice);
            pst9.setString(5, sellingprice);
            pst9.setString(6, tamount);
            pst9.setString(7, img1);
            pst9.setString(8, supp);
            pst9.setString(9, stats);
            pst9.executeUpdate();
//            rs = pst.getGeneratedKeys();

            JOptionPane.showMessageDialog(this, "Product Added");

        } catch (SQLException e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);
//      
        }
    }

    public void productdispaly() {
//
        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);

        String timee = time.getText();
        String bname = jbname.getText();
        String desc = jdescription.getText();
//        String pid = jid.getText();
        String tamount = jtotalamount.getText();
        String sellingprice = jsellprice.getText();
        String supp = jsupplier.getText();
        String img1 = jlinkimage.getText();

        try {

            String query6 = "insert into `product_display`(barcode, product_name,description,quantity,sellingprice,totalamount_cost,productImage,supplier)values(?,?,?,?,?,?,?,?);";
            pst = con.prepareStatement(query6, Statement.RETURN_GENERATED_KEYS);
            pst9 = con.prepareStatement(query6);
            pst9.setInt(1, Integer.parseInt(jid.getText()));
            pst9.setString(2, bname);
            pst9.setString(3, desc);
            pst9.setInt(4, Integer.parseInt(jquantity.getText()));
            pst9.setString(5, sellingprice);
            pst9.setString(6, tamount);
            pst9.setString(7, img1);
            pst9.setString(8, supp);
            pst9.executeUpdate();
//            rs = pst.getGeneratedKeys();

            JOptionPane.showMessageDialog(this, "Product displayed");

        } catch (SQLException e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);
//      
        }
    }

    //create a fuction to display image into jtable
    public void displayImage(String imgPath, JLabel label) {
        ImageIcon imgicon = new ImageIcon(imgPath);
        Image img = imgicon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }

    //insert into staff transaction.......
    public void invetorytransac() {

        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);
        String timee = time.getText();
        String prod = jbname.getText();
        String username = jusernamee.getText();
        String types = "Added Products";
        String supp = jsupplier.getText();
        String rol = jposition.getText();
//           double d = Double.parseDouble(qty);

        int damm = 0;

        String id = jTable4.getModel().getValueAt(jTable4.getModel().getRowCount() - 1, 0).toString();
        try {

            String query = "insert into transactions(InventoryID,quantity,type_transaction,UserID,date,time,username,role)values(?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(id));//inventory id
//            pst.setInt(2, Integer.parseInt(jquantity.getText()));
            pst.setDouble(2, Double.parseDouble(jquantity.getText()));
//            pst.setDouble(2, d);
            pst.setString(3, types);
//          pst.setString(4, new LoginUsers().id);//username id...
            pst.setInt(4, new LoginUsers().ID());
            pst.setString(5, date);
            pst.setString(6, timee);
            pst.setString(7, username);
            pst.setString(8, rol);
            pst.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void getproduct() {

        DefaultTableModel df = (DefaultTableModel) jTable4.getModel();
        df.setRowCount(0);

        try {
            Statement st7 = con.createStatement();
            String query1 = "select * from `products`";
            ResultSet rs1 = st7.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..
                String id = rs1.getString("barcode");
                String hh = rs1.getString("product_name");
                String des1 = rs1.getString("description");
                String trr = rs1.getString("quantity");
                String buy = rs1.getString("buyingprice");
                String sell1 = rs1.getString("sellingprice");
                String tcost = rs1.getString("totalamount_cost");
                String pro = rs1.getString("productImage");
                String supppl = rs1.getString("supplier");
                String sat = rs1.getString("status");

                //string array for store data into jtable..
                String tbData[] = {id, hh, des1, trr, buy, sell1, tcost, pro, supppl, sat};
                DefaultTableModel tblModel = (DefaultTableModel) jTable4.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

        } catch (SQLException ex) {
            Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //insert delete type of transaction..
    public void invetorytransacdelete() {

        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);
        String timee = time.getText();
        String prod = jbname.getText();
        String username = jusernamee.getText();
        String types = "Deleted Products";
        String supp = jsupplier.getText();
        String rol = jposition.getText();

        String id = jTable4.getModel().getValueAt(jTable4.getModel().getRowCount() - 1, 0).toString();
        try {

            String query = "insert into transactions(barcode,quantity,type_transaction,UserID,date,time,username,role)values(?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(id));//inventory id
            pst.setDouble(2, Double.parseDouble(jquantity.getText()));
            pst.setString(3, types);
//          pst.setString(4, new LoginUsers().id);//username id...
            pst.setInt(4, new LoginUsers().ID());
            pst.setString(5, date);
            pst.setString(6, timee);
            pst.setString(7, username);
            pst.setString(8, rol);
            pst.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);
//      
        }
    }

    public void invetorytransacupdate() {

        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);
        String timee = time.getText();
        String prod = jbname.getText();
        String username = jusernamee.getText();
        String supp = jsupplier.getText();
//            String tots = jtotalamount.getText();
        String types = "Updated Products";
        String rol = jposition.getText();

        String id = jTable4.getModel().getValueAt(jTable4.getModel().getRowCount() - 1, 0).toString();
        try {

            String query = "insert into transactions(barcode,quantity,type_transaction,UserID,date,time,username,role)values(?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(id));//inventory id
            pst.setDouble(2, Double.parseDouble(jquantity.getText()));
            pst.setString(3, types);
//          pst.setString(4, new LoginUsers().id);//username id...
            pst.setInt(4, new LoginUsers().ID());
            pst.setString(5, date);
            pst.setString(6, timee);
            pst.setString(7, username);
            pst.setString(8, rol);
            pst.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void invetorytransacdisplay() {

        Calendar cal = new GregorianCalendar();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dt.format(now);
        String timee = time.getText();
        String prod = jbname.getText();
        String username = jusernamee.getText();
        String supp = jsupplier.getText();
//            String tots = jtotalamount.getText();
        String types = "display Products";

        String rol = jposition.getText();
//      

        String id = jTable4.getModel().getValueAt(jTable4.getModel().getRowCount() - 1, 0).toString();
        try {

            String query = "insert into transactions(barcode,quantity,type_transaction,UserID,date,time,username,role)values(?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(id));//inventory id
            pst.setDouble(2, Double.parseDouble(jquantity.getText()));
            pst.setString(3, types);
//          pst.setString(4, new LoginUsers().id);//username id...
            pst.setInt(4, new LoginUsers().ID());
            pst.setString(5, date);
            pst.setString(6, timee);
            pst.setString(7, username);
            pst.setString(8, rol);
            pst.executeUpdate();

        } catch (Exception e) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public void updateproduct() {
        String sql = "select from `approved_products`";
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

    public void getproductdisplay() {

        DefaultTableModel df = (DefaultTableModel) jTable4.getModel();
        df.setRowCount(0);
        String sta = "Not yet displayed";

        try {
            Statement st7 = con.createStatement();
            String query1 = "select * from `products`";
            ResultSet rs1 = st7.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..
                String id = rs1.getString("barcode");
                String hh = rs1.getString("product_name");
                String des1 = rs1.getString("description");
                String trr = rs1.getString("quantity");
                String buy = rs1.getString("buyingprice");
                String sell1 = rs1.getString("sellingprice");
                String tcost = rs1.getString("totalamount_cost");
                String pro = rs1.getString("productImage");
                String supppl = rs1.getString("supplier");
                String stat = rs1.getString("status");

                //string array for store data into jtable..
                String tbData[] = {id, hh, des1, trr, buy, sell1, tcost, pro, supppl, stat};
                DefaultTableModel tblModel = (DefaultTableModel) jTable4.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

        } catch (SQLException ex) {
            Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updatestat() {

        if (jTable4.getSelectedRowCount() == 1) {
            DefaultTableModel model2 = (DefaultTableModel) jTable4.getModel();
            int Myindex = jTable4.getSelectedRow();
            int Mycolumn = jTable4.getSelectedColumn();

            String value = model2.getValueAt(Myindex, Mycolumn).toString();
            int id = Integer.parseInt(model2.getValueAt(Myindex, 0).toString());
            String stats = "displayed";

            invetorytransacupdate();

            try {
                pst = con.prepareStatement("UPDATE `products` set status=?  where barcode= ?");
                //
                pst.setString(1, stats);
                pst.setInt(2, id);

                int k = pst.executeUpdate();

                if (k == 1) {
                    jbname.setText("");
                    jquantity.setText("");
                    jbuyprice.setText("");
                    jsellprice.setText("");
                    jdescription.setText("");
                    jlinkimage.setText("");
                    jsupplier.setText("");
                    jtotalamount.setText("");
                    jid.setText("");
                    jproductimage.setIcon(null);
                    jbname.requestFocus();

                    salesupdate();

                }

            } catch (SQLException ex) {
                Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            if (jTable4.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Field is empty!");
            }
        }

    }

    public ArrayList<inventorypage.productstock> retrieveData() {
        ArrayList<inventorypage.productstock> al = null;
        al = new ArrayList<inventorypage.productstock>();

        try {

            String qry = "select * from offered_products";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(qry);
            inventorypage.productstock product;
            while (rs.next()) {
                product = new inventorypage.productstock(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getBytes(9), rs.getString(10));
                al.add(product);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return al;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jusernamee = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        Home4 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jposition = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jclear = new javax.swing.JButton();
        jdelete = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jupdate = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jchoose = new javax.swing.JComboBox<>();
        jshow = new javax.swing.JButton();
        jdisplay = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jbuyprice = new javax.swing.JTextField();
        jquantity = new javax.swing.JTextField();
        jbname = new javax.swing.JTextField();
        jproductimage = new javax.swing.JLabel();
        jsellprice = new javax.swing.JTextField();
        jdescription = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jlinkimage = new javax.swing.JTextField();
        jimageproduct = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jtotalamount = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jid = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jsupplier = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jadd = new javax.swing.JButton();
        jPanel67 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        time = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(227, 225, 225));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 51, 0), new java.awt.Color(0, 102, 0), new java.awt.Color(0, 51, 0), new java.awt.Color(0, 51, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel19.setBackground(new java.awt.Color(44, 116, 60));

        jPanel18.setBackground(new java.awt.Color(44, 116, 60));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile.png"))); // NOI18N

        jusernamee.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jusernamee.setForeground(new java.awt.Color(255, 255, 255));
        jusernamee.setText("Username");

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
        jPanel20.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 34, -1));

        jLabel21.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Sold Products");
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });
        jPanel20.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, 40));

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

        jposition.setForeground(new java.awt.Color(255, 255, 255));
        jposition.setText("Staff");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jusernamee)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(28, 28, 28)
                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Home4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jposition, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Home4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jusernamee)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jposition)
                .addContainerGap())
        );

        jPanel1.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, 130));

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
        jPanel3.add(jclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 117, 38));

        jdelete.setBackground(new java.awt.Color(255, 204, 51));
        jdelete.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jdelete.setText("Delete");
        jdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdeleteActionPerformed(evt);
            }
        });
        jPanel3.add(jdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 360, 117, 38));

        jButton3.setBackground(new java.awt.Color(255, 204, 51));
        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jButton3.setText("Exit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 360, 117, 37));

        jupdate.setBackground(new java.awt.Color(255, 204, 51));
        jupdate.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jupdate.setText("Update");
        jupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jupdateActionPerformed(evt);
            }
        });
        jPanel3.add(jupdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 360, 117, 35));

        jTable4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Product Name", "Description", "Quatity", "Buying Price", "Sellintg Price", "Total cost", "Image", "Supplier", "Status"
            }
        ));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable4);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 720, 249));

        jchoose.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jchoose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Products to add", "Available products", "Products to display" }));
        jPanel3.add(jchoose, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 200, 40));

        jshow.setText("Show");
        jshow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jshowActionPerformed(evt);
            }
        });
        jPanel3.add(jshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, 70, 40));

        jdisplay.setBackground(new java.awt.Color(255, 204, 51));
        jdisplay.setText("Display");
        jdisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdisplayActionPerformed(evt);
            }
        });
        jPanel3.add(jdisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 360, 90, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 180, 770, 430));

        jPanel2.setBackground(new java.awt.Color(44, 116, 60));

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
                .addContainerGap(40, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(267, Short.MAX_VALUE)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 2, 600, 120));

        jPanel5.setBackground(new java.awt.Color(227, 225, 225));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 0), 5, true));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ImageIcon_1_1.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 0, -1, -1));

        jPanel8.setBackground(new java.awt.Color(44, 116, 60));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel4.add(jbuyprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 160, 34));
        jPanel4.add(jquantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 120, 33));
        jPanel4.add(jbname, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 160, 34));

        jproductimage.setBackground(new java.awt.Color(255, 255, 255));
        jproductimage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jproductimage.setOpaque(true);
        jPanel4.add(jproductimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 210, 150));
        jPanel4.add(jsellprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 160, 34));

        jdescription.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jdescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 160, 32));

        jLabel31.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel31.setText("Product Name");
        jPanel4.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 24));

        jLabel33.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel33.setText("Buying Price");
        jPanel4.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, 24));

        jLabel36.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel36.setText("Total Cost:");
        jPanel4.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, -1, 30));

        jLabel37.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel37.setText("Quantity");
        jPanel4.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 24));

        jLabel38.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel38.setText("Selling Price");
        jPanel4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, 24));
        jPanel4.add(jlinkimage, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 210, 34));

        jimageproduct.setBackground(new java.awt.Color(255, 255, 204));
        jimageproduct.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jimageproduct.setText("Choose Product");
        jimageproduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jimageproductActionPerformed(evt);
            }
        });
        jPanel4.add(jimageproduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, 41));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("kg");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 40, 35));

        jLabel39.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel39.setText("ID");
        jPanel4.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, 24));

        jtotalamount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtotalamountActionPerformed(evt);
            }
        });
        jPanel4.add(jtotalamount, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, 100, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Php");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 230, 30, 30));
        jPanel4.add(jid, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 160, 30));

        jLabel40.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel40.setText("Supplier");
        jPanel4.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, -1, 24));

        jsupplier.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 160, 35));

        jLabel41.setFont(new java.awt.Font("Segoe UI Historic", 0, 14)); // NOI18N
        jLabel41.setText("Description");
        jPanel4.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, 24));

        jadd.setBackground(new java.awt.Color(255, 204, 51));
        jadd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jadd.setText("Add");
        jadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jaddActionPerformed(evt);
            }
        });
        jPanel4.add(jadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, 120, 38));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 174, 530, 440));

        jPanel67.setBackground(new java.awt.Color(204, 204, 204));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Date:");

        jLabel17.setBackground(new java.awt.Color(51, 51, 51));
        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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
                .addComponent(jLabel15)
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
                .addContainerGap()
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(date, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                    .addComponent(time)
                    .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(jLabel17)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 130, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                .addContainerGap())
        );

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

    private void jclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jclearActionPerformed
        // TODO add your handling code here:
        jbname.setText("");
        jbuyprice.setText("");
        jquantity.setText("");
        jsellprice.setText("");
        jsupplier.setText("");
        jtotalamount.setText("");
        jdescription.setText("");
        jlinkimage.setText("");
        jproductimage.setIcon(null);
        jbname.setText("");
        jadd.setEnabled(true);
    }//GEN-LAST:event_jclearActionPerformed

    private void jdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdeleteActionPerformed
        if (jTable4.getSelectedRowCount() == 1) {
            invetorytransacdelete();
            int row = jTable4.getSelectedRow();
            String cell = jTable4.getModel().getValueAt(row, 0).toString();
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

                DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
                //delete row
                if (jTable4.getSelectedRowCount() == 1) {
                    //if single row is selected then delete
                    model.removeRow(jTable4.getSelectedRow());
                    jbname.setText("");
                    jbuyprice.setText("");
                    jquantity.setText("");
                    jsellprice.setText("");
                    jsupplier.setText("");
                    jtotalamount.setText("");
                    jdescription.setText("");
                    jlinkimage.setText("");
                    jproductimage.setIcon(null);
                    jbname.setText("");

                }

            }
        } else {

            if (jTable4.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Field is empty!");
            }

        }


    }//GEN-LAST:event_jdeleteActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jupdateActionPerformed
        // TODO add your handling code here:

        if (jTable4.getSelectedRowCount() == 1) {
            DefaultTableModel model2 = (DefaultTableModel) jTable4.getModel();
            int Myindex = jTable4.getSelectedRow();
            int Mycolumn = jTable4.getSelectedColumn();

            String value = model2.getValueAt(Myindex, Mycolumn).toString();
            int id = Integer.parseInt(model2.getValueAt(Myindex, 0).toString());
            String bname = jbname.getText();
            String qty = jquantity.getText();
            String bprice = jbuyprice.getText();
            String sprice = jsellprice.getText();
            String des = jdescription.getText();
            String imgl = jlinkimage.getText();
            String ts = jtotalamount.getText();
            String supp = jsupplier.getText();

            invetorytransacupdate();

            try {
                pst = con.prepareStatement("UPDATE `products` set product_name= ?,description=?, quantity= ?, buyingprice= ?,sellingprice= ?,totalamount_cost=?, productImage= ?, supplier=?  where barcode= ?");
                //
                pst.setString(1, bname);
                pst.setString(2, des);
                pst.setString(3, qty);
                pst.setString(4, bprice);
                pst.setString(5, sprice);
                pst.setString(6, ts);
                pst.setString(7, imgl);

                pst.setString(8, supp);

                pst.setInt(9, id);

                int k = pst.executeUpdate();

                if (k == 1) {
                    JOptionPane.showMessageDialog(this, "Successfully Updated");
                    jbname.setText("");
                    jquantity.setText("");
                    jbuyprice.setText("");
                    jsellprice.setText("");
                    jdescription.setText("");
                    jlinkimage.setText("");
                    jsupplier.setText("");
                    jtotalamount.setText("");
                    jid.setText("");
                    jproductimage.setIcon(null);
                    jbname.requestFocus();

                    salesupdate();

                }

            } catch (SQLException ex) {
                Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            if (jTable4.getSelectedRowCount() == 0) {
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

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:

        DefaultTableModel model2 = (DefaultTableModel) jTable4.getModel();
        //          InputStream is=new FileInputStream(new File(photopath));
        //          pst.setBlob(5, is);
        int Myindex = jTable4.getSelectedRow();
        int Mycolumn = jTable4.getSelectedColumn();

        String value = model2.getValueAt(Myindex, Mycolumn).toString();
        int id = Integer.parseInt(model2.getValueAt(Myindex, 0).toString());
        //        jbname.setSelectedItem(model2.getValueAt(Myindex, 1).toString());
        jid.setText(model2.getValueAt(Myindex, 0).toString());
        jdescription.setText(model2.getValueAt(Myindex, 2).toString());
        jquantity.setText(model2.getValueAt(Myindex, 3).toString());
        jbname.setText(model2.getValueAt(Myindex, 1).toString());

        jbuyprice.setText(model2.getValueAt(Myindex, 4).toString());
        jsellprice.setText(model2.getValueAt(Myindex, 5).toString());

        jlinkimage.setText(model2.getValueAt(Myindex, 7).toString());
        jproductimage.setIcon(resetImageSize(null, retrieveData().get(Myindex).getPhoto()));

        jsupplier.setText(model2.getValueAt(Myindex, 8).toString());
        jtotalamount.setText(model2.getValueAt(Myindex, 6).toString());


    }//GEN-LAST:event_jTable4MouseClicked

    private void jPanel20MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseEntered
        // TODO add your handling code here:

        jPanel20.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel20MouseEntered

    private void jPanel20MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel20MouseExited
        // TODO add your handling code here:
        jPanel20.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel20MouseExited

    private void dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateActionPerformed

    private void jimageproductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jimageproductActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter fnef = new FileNameExtensionFilter(".Flower-Sales", "jpg", "png", "webp", "jpeg");
        chooser.addChoosableFileFilter(fnef);
        int ans = chooser.showSaveDialog(null);
        if (ans == JFileChooser.APPROVE_OPTION) {
            File selectedPhoto = chooser.getSelectedFile();
            String path = selectedPhoto.getAbsolutePath();
            jproductimage.setIcon(resetImageSize(path, null));
            this.photopath = path;
            jlinkimage.setText(path);
            //or...
            //            displayImage(path, jproductimage);
            System.out.println(path);

        } else {
            System.out.println("no file selected");
        }
    }//GEN-LAST:event_jimageproductActionPerformed

    private void jtotalamountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtotalamountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtotalamountActionPerformed

    private void jaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jaddActionPerformed

        DefaultTableModel tbmodel = (DefaultTableModel) jTable4.getModel();
        if (jTable4.getSelectedRowCount() == 1) {
            product();
//            JOptionPane.showMessageDialog(null, "Approved!");

            int row = jTable4.getSelectedRow();

            String cell = jTable4.getModel().getValueAt(row, 0).toString();

            String sql = "DELETE FROM `approved_products` where id= " + cell;

            try {
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, " New product Added!");
                updateproduct();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();

                } catch (Exception e) {
                }

                DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
                //delete row
                if (jTable4.getSelectedRowCount() == 1) {
                    //if single row is selected then delete
                    model.removeRow(jTable4.getSelectedRow());
                    jbname.setText("");
                    jbuyprice.setText("");
                    jquantity.setText("");
                    jsellprice.setText("");
                    jsupplier.setText("");
                    jtotalamount.setText("");
                    jdescription.setText("");
                    jlinkimage.setText("");
                    jproductimage.setIcon(null);
                    jbname.setText("");
                    //
                    //              jstatus.setSelectedIndex(0);
                }
            }

        } else {
            if (jTable4.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Table is empty!");
            } else {
                JOptionPane.showMessageDialog(null, "Please select a single row!");
            }
        }

    }//GEN-LAST:event_jaddActionPerformed

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
        soldproducts sold = new soldproducts(jusernamee.getText());
        sold.show();
        dispose();

        try {
            Statement st = con.createStatement();
            String query1 = "select * from `sales_products` ";
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
                String date2 = rs1.getString("date");
                String userID = rs1.getString("userid");
                String supp = rs1.getString("supplier");
                String use = rs1.getString("username");

                //string array for store data into jtable..
                String tbData[] = {bid2, salesid2, bookn2, buying, price2, qty2, totl2, date2, userID, supp, use};
                DefaultTableModel tblModel = (DefaultTableModel) jTable5.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jshowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jshowActionPerformed
        // TODO add your handling code here:
        DefaultTableModel df = (DefaultTableModel) jTable4.getModel();
        df.setRowCount(0);

        if (jchoose.getSelectedIndex() == 0) {
            toadd();
            jupdate.setEnabled(false);
            jdelete.setEnabled(false);
            jadd.setEnabled(true);
            jdisplay.setEnabled(false);
            choice();
        } else if (jchoose.getSelectedIndex() == 1) {
            getproduct();
            jadd.setEnabled(false);
            jupdate.setEnabled(true);
            jdelete.setEnabled(true);
            jdisplay.setEnabled(false);

            choice1();
        } else if (jchoose.getSelectedIndex() == 2) {
            getproductdisplay();
            jadd.setEnabled(false);
            jupdate.setEnabled(true);
            jdisplay.setEnabled(true);
            jdelete.setEnabled(true);
            choice1();
        }
        DefaultTableModel model = (DefaultTableModel) jTable4.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable4.setRowSorter(tr);
    }//GEN-LAST:event_jshowActionPerformed

    private void jdisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdisplayActionPerformed
        // TODO add your handling code here:
        productdispaly();
        invetorytransacdisplay();
        updatestat();

        jbname.setText("");
        jbuyprice.setText("");
        jquantity.setText("");
        jsellprice.setText("");
        jsupplier.setText("");
        jtotalamount.setText("");
        jdescription.setText("");
        jlinkimage.setText("");
        jproductimage.setIcon(null);
        jbname.setText("");

    }//GEN-LAST:event_jdisplayActionPerformed

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
            java.util.logging.Logger.getLogger(inventorypage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(inventorypage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(inventorypage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(inventorypage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new inventorypage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Home4;
    private javax.swing.JTextField date;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTable4;
    private javax.swing.JButton jadd;
    private javax.swing.JTextField jbname;
    private javax.swing.JTextField jbuyprice;
    private javax.swing.JComboBox<String> jchoose;
    private javax.swing.JButton jclear;
    private javax.swing.JButton jdelete;
    private javax.swing.JTextField jdescription;
    private javax.swing.JButton jdisplay;
    private javax.swing.JTextField jid;
    private javax.swing.JButton jimageproduct;
    private javax.swing.JTextField jlinkimage;
    private javax.swing.JLabel jposition;
    private javax.swing.JLabel jproductimage;
    private javax.swing.JTextField jquantity;
    private javax.swing.JTextField jsellprice;
    private javax.swing.JButton jshow;
    private javax.swing.JTextField jsupplier;
    private javax.swing.JTextField jtotalamount;
    private javax.swing.JButton jupdate;
    private javax.swing.JLabel jusernamee;
    private javax.swing.JTextField time;
    // End of variables declaration//GEN-END:variables
}
