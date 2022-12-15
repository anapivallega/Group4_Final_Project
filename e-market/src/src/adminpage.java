/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//this is updated yow
package src;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import static src.soldadmin.jTable9;
import static src.soldproducts.jTable5;

/**
 *
 * @author 1styrGroupB
 */
public class adminpage extends javax.swing.JFrame {

    /**
     * Creates new form adminpage
     */
    public adminpage() {
        initComponents();
        Connect();
        dt();
        time();
//        displayall();
        blockapprovebtns1();
    }

    public adminpage(String userad) {
        initComponents();
        jusernamee.setText(userad);
        Connect();
        dt();
        time();
//        displayall();
        blockapprovebtns1();
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

    public void blockfield() {
        jname.setEnabled(false);
        jpassword.setEnabled(false);
        jemailid.setEnabled(false);
        jgender.setEnabled(false);
        jage.setEnabled(false);
        jphone.setEnabled(false);
        jrole.setEnabled(false);
        jstatus.setEnabled(false);
    }

    public void blockapprovebtns() {
        jDelete.setEnabled(false);
        jUpdate.setEnabled(false);
        jadd.setEnabled(false);
        jrole.setEnabled(false);

    }

    public void blockapprovebtns1() {
        japprove.setEnabled(false);
        jremove.setEnabled(false);
        jDelete.setEnabled(true);
        jUpdate.setEnabled(true);

    }

    public void displayall() {
        try {
            Statement st = con.createStatement();
            String query1 = "select * from `registered_user`";
            ResultSet rs1 = st.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..
                String Id1 = rs1.getString("user_id");
                String username1 = rs1.getString("username");
                String password1 = rs1.getString("password");
                String email_id1 = rs1.getString("email");
                String phone = rs1.getString("phone_number");
                String dat = rs1.getString("date");
                String gender1 = rs1.getString("gender");
                String age1 = rs1.getString("age");
                String rolr = rs1.getString("role");

                String status1 = rs1.getString("status");

                //string array for store data into jtable..
                String tbData[] = {Id1, username1, password1, email_id1, phone, dat, gender1, age1, rolr, status1};
                DefaultTableModel tblModel = (DefaultTableModel) jTable20.getModel();

                //add string array data into jtable..
                tblModel.addRow(tbData);

            }

//            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(adminpage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateuserapplicant() {
        String sql = "select from `user_applicant`";
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

    public void usersupdate() {
        try {
            pst = con.prepareStatement("select * from `registered_user`");
            rs = pst.executeQuery();

            ResultSetMetaData rsd = rs.getMetaData();
            int c;

            c = rsd.getColumnCount();
            DefaultTableModel de = (DefaultTableModel) jTable20.getModel();
            de.setRowCount(0);

            while (rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= c; i++) {
                    v2.add(rs.getString("user_id"));
                    v2.add(rs.getString("username"));
                    v2.add(rs.getString("password"));
                    v2.add(rs.getString("email"));
                    v2.add(rs.getString("phone_number"));
                    v2.add(rs.getString("date"));
                    v2.add(rs.getString("gender"));
                    v2.add(rs.getString("age"));
                    v2.add(rs.getString("role"));
                    v2.add(rs.getString("status"));

                }
                de.addRow(v2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteusers() {
        String sql = "select from `registered_users`";
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

    public void toapproveuser() {

        String username = jname.getText();
        String password = jpassword.getText();
        String email_id = jemailid.getText();
        String gender = jgender.getText();
        String age = jage.getText();
        String phone = jphone.getText();
        String dta = date.getText();

        String role1;
        role1 = jrole.getSelectedItem().toString();

//       String status;
//       status=jstatus.getSelectedItem().toString();
        try {

            String query8 = "insert into `registered_user`(username,password,email,phone_number,date,gender,age,role,status)values(?,?,?,?,?,?,?,?,?);";
            pst = con.prepareStatement(query8);

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, email_id);
            pst.setString(4, phone);
            pst.setString(5, dta);
            pst.setString(6, gender);
            pst.setString(7, age);
            pst.setString(8, role1);

            pst.setString(9, "Active");
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//    

    public void todecline() {

        String username = jname.getText();
        String password = jpassword.getText();
        String email_id = jemailid.getText();
        String gender = jgender.getText();
        String age = jage.getText();
        String phone = jphone.getText();
        String dta = date.getText();

        String role1;
        role1 = jrole.getSelectedItem().toString();

//       String status;
//       status=jstatus.getSelectedItem().toString();
        try {

            String query = "insert into `declined_registrants`(username,password,email,phone_number,date,gender,age,role,status)values(?,?,?,?,?,?,?,?,?);";
            pst = con.prepareStatement(query);

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, email_id);
            pst.setString(4, phone);
            pst.setString(5, dta);
            pst.setString(6, gender);

            pst.setString(7, age);
//          pst.setInt(5, Integer.valueOf(jage.getText()));

            pst.setString(8, role1);

            pst.setString(9, "Active");
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void todeclineusers() {
        String sql = "select from `user_applicant`";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
//            jTable3.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
//           JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
//                rs.close();
//                pst.close();

            } catch (Exception e) {

            }
        }
    }

    public void registrants() {

        try {
            Statement st = con.createStatement();
            String query1 = "select * from `user_applicant`";
            ResultSet rs1 = st.executeQuery(query1);

            while (rs1.next()) {
                //data wil added until finished..
                String bid = rs1.getString("user_id");
                String username1 = rs1.getString("username");
                String password1 = rs1.getString("password");
                String email_id1 = rs1.getString("email");
                String phone = rs1.getString("phone_number");
                String dts = rs1.getString("date");
                String gen = rs1.getString("gender");
                String ag = rs1.getString("age");
                String ro = rs1.getString("role");
                String sta = rs1.getString("status");

//                String sts = rs1.getString("status");
                //string array for store data into jtable..
                String tbData[] = {bid, username1, password1, email_id1, phone, dts, gen, ag, ro, sta};

                DefaultTableModel modelu = (DefaultTableModel) jTable20.getModel();

                //add string array data into jtable..
                modelu.addRow(tbData);

            }

//            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(adminpage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//      public static void AddRowToallusersable(Object[] dataRow)   
//     {
//       DefaultTableModel modelu = (DefaultTableModel)jTable14.getModel(); 
//       modelu.addRow(dataRow);
//     }
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
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jusernamee = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable20 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jsearch = new javax.swing.JLabel();
        jinput = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jemailid = new javax.swing.JTextField();
        jage = new javax.swing.JTextField();
        jgender = new javax.swing.JTextField();
        jname = new javax.swing.JTextField();
        jpassword = new javax.swing.JPasswordField();
        jrole = new javax.swing.JComboBox<>();
        jstatus = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jphone = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jUpdate = new javax.swing.JButton();
        jDelete = new javax.swing.JButton();
        jclear = new javax.swing.JButton();
        jadd = new javax.swing.JButton();
        jchoose = new javax.swing.JComboBox<>();
        jshow = new javax.swing.JButton();
        japprove = new javax.swing.JButton();
        jremove = new javax.swing.JButton();
        jradd = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel67 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        date = new javax.swing.JTextField();
        time = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 0), new java.awt.Color(0, 51, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(44, 116, 60));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(44, 116, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/profile.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 25, -1, -1));

        jusernamee.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jusernamee.setForeground(new java.awt.Color(255, 255, 255));
        jusernamee.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jusernamee.setText("Username");
        jPanel6.add(jusernamee, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 125, -1, 22));

        jPanel8.setBackground(new java.awt.Color(44, 116, 60));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel8MouseExited(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Home");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jPanel6.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 165, 193, 44));

        jPanel10.setBackground(new java.awt.Color(44, 116, 60));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10MouseExited(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/choose.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Products");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jPanel6.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 227, 193, 44));

        jPanel11.setBackground(new java.awt.Color(44, 116, 60));
        jPanel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel11MouseExited(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Transactions");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jPanel6.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 289, 193, 44));

        jLabel9.setFont(new java.awt.Font("Sylfaen", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Support");
        jPanel6.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 447, -1, 24));

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

        jPanel6.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 477, 193, 44));

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanel6.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 533, 193, 44));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 2, -1, 630));

        jPanel3.setBackground(new java.awt.Color(44, 116, 60));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable20.setBackground(new java.awt.Color(245, 244, 244));
        jTable20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTable20.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Username", "Password", "Email_id", "Phone", "Date", "Gender", "Age", "Role", "Status"
            }
        ));
        jTable20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable20MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable20);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, 670, 320));

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
                .addComponent(jsearch, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jsearch, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
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
                .addComponent(jinput, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jinput)
        );

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, 260, 40));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Username");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 53, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Password");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 101, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Email ");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 150, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Gender");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 242, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Age");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 292, -1, -1));

        jemailid.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jemailid, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 144, 161, 31));

        jage.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jage, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 286, 161, 32));

        jgender.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jgender, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 236, 161, 32));

        jname.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jname, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 161, 30));

        jpassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 96, 161, 30));

        jrole.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jrole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Cashier", "Staff", "Supplier", "Buyer", " " }));
        jPanel4.add(jrole, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 335, 161, 32));

        jstatus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));
        jPanel4.add(jstatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 385, 161, 32));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Role");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 342, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Status");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 400, -1, -1));

        jphone.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel4.add(jphone, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 193, 161, 31));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setText("Phone");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 199, -1, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 300, 440));

        jUpdate.setBackground(new java.awt.Color(204, 255, 102));
        jUpdate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jUpdate.setText("Update");
        jUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jUpdateActionPerformed(evt);
            }
        });
        jPanel3.add(jUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 410, 105, 43));

        jDelete.setBackground(new java.awt.Color(204, 255, 102));
        jDelete.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jDelete.setText("Delete");
        jDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDeleteActionPerformed(evt);
            }
        });
        jPanel3.add(jDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 410, 105, 43));

        jclear.setBackground(new java.awt.Color(204, 255, 102));
        jclear.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jclear.setText("Clear");
        jclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jclearActionPerformed(evt);
            }
        });
        jPanel3.add(jclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, 97, 43));

        jadd.setBackground(new java.awt.Color(204, 255, 102));
        jadd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jadd.setText("Add");
        jadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jaddActionPerformed(evt);
            }
        });
        jPanel3.add(jadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 410, -1, 40));

        jchoose.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jchoose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Users", "Unapprove Users" }));
        jchoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchooseActionPerformed(evt);
            }
        });
        jPanel3.add(jchoose, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 200, 40));

        jshow.setText("Show");
        jshow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jshowActionPerformed(evt);
            }
        });
        jPanel3.add(jshow, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 20, 70, 40));

        japprove.setBackground(new java.awt.Color(204, 255, 102));
        japprove.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        japprove.setText("Approve");
        japprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                japproveActionPerformed(evt);
            }
        });
        jPanel3.add(japprove, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 413, -1, 40));

        jremove.setBackground(new java.awt.Color(204, 255, 102));
        jremove.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jremove.setText("Decline");
        jremove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jremoveActionPerformed(evt);
            }
        });
        jPanel3.add(jremove, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 413, -1, 40));

        jradd.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        jradd.setForeground(new java.awt.Color(255, 204, 0));
        jradd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jradd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jraddMouseClicked(evt);
            }
        });
        jPanel3.add(jradd, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, 120, 40));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 1060, 470));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ImageIcon_2.png"))); // NOI18N
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 165, 120));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel15.setText("Admin Management");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        jPanel67.setBackground(new java.awt.Color(0, 102, 51));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Date:");

        jLabel16.setBackground(new java.awt.Color(51, 51, 51));
        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Time:");

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
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel67Layout.setVerticalGroup(
            jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel67Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel67Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel16)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1338, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 637, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseEntered
        // TODO add your handling code here:
        jPanel8.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel8MouseEntered

    private void jPanel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseExited
        // TODO add your handling code here:
        jPanel8.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel8MouseExited

    private void jPanel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseEntered
        // TODO add your handling code here:
        jPanel10.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel10MouseEntered

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        // TODO add your handling code here:
        jPanel10.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel10MouseExited

    private void jPanel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseEntered
        // TODO add your handling code here:
        jPanel11.setBackground(new Color(35, 77, 32));
    }//GEN-LAST:event_jPanel11MouseEntered

    private void jPanel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel11MouseExited
        // TODO add your handling code here:
        jPanel11.setBackground(new Color(44, 116, 60));
    }//GEN-LAST:event_jPanel11MouseExited

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

    private void jTable20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable20MouseClicked

        DefaultTableModel model2 = (DefaultTableModel) jTable20.getModel();
        int Myindex = jTable20.getSelectedRow();
        TableModel model3 = (TableModel) jTable20.getModel();

        int Mycolumn = jTable20.getSelectedColumn();

        String value = model2.getValueAt(Myindex, Mycolumn).toString();

        //        int id = Integer.parseInt(model2.getValueAt(Myindex, 0).toString());
        jname.setText(model2.getValueAt(Myindex, 1).toString());
        jpassword.setText(model2.getValueAt(Myindex, 2).toString());
        jemailid.setText(model2.getValueAt(Myindex, 3).toString());
        jphone.setText(model2.getValueAt(Myindex, 4).toString());
        jgender.setText(model2.getValueAt(Myindex, 6).toString());
        jage.setText(model2.getValueAt(Myindex, 7).toString());

        String rolea = model2.getValueAt(Myindex, 8).toString();

        switch (rolea) {
            case "Admin":
                jrole.setSelectedIndex(0);
                break;
            case "Cashier":
                jrole.setSelectedIndex(1);
                break;
            case "Staff":
                jrole.setSelectedIndex(2);
                break;

            case "Supplier":
                jrole.setSelectedIndex(3);
                break;

            case "Buyer":
                jrole.setSelectedIndex(4);
                break;
        }

        String statusa = model2.getValueAt(Myindex, 9).toString();
        switch (statusa) {
            case "Active":
                jstatus.setSelectedIndex(0);
                break;
            case "Inactive":
                jstatus.setSelectedIndex(1);
                break;
        }

        jadd.setEnabled(false);
        jrole.setEnabled(true);
        String retrn = "Return to add";

        jradd.setText(retrn);

    }//GEN-LAST:event_jTable20MouseClicked

    private void dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        category admin = new category(jusernamee.getText());
        admin.show();
        dispose();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        transaction trans = new transaction(jusernamee.getText());
        trans.show();
        dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
        LoginUsers main = new LoginUsers();
        main.setVisible(true);
        main.pack();
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jsearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsearchMouseClicked
        // TODO add your handling code here:

        String st = jinput.getText();

        DefaultTableModel model = (DefaultTableModel) jTable20.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable20.setRowSorter(tr);
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

    private void jUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jUpdateActionPerformed
        // TODO add your handling code here:
        if (jTable20.getSelectedRowCount() == 1) {
            DefaultTableModel model3 = (DefaultTableModel) jTable20.getModel();
            int Myindex = jTable20.getSelectedRow();
            int Mycolumn = jTable20.getSelectedColumn();

            String value = model3.getValueAt(Myindex, Mycolumn).toString();
            int id = Integer.parseInt(model3.getValueAt(Myindex, 0).toString());
            String uname = jname.getText();
            String pass = jpassword.getText();
            String emai1 = jemailid.getText();
            String phn = jphone.getText();
            String gend = jgender.getText();
            String dt = date.getText();
            String agedd = jage.getText();
            String role;
            role = jrole.getSelectedItem().toString();
            String status;
            status = jstatus.getSelectedItem().toString();

            try {
                pst = con.prepareStatement("UPDATE registered_user set username= ?, password= ?, email= ?,phone_number=?,date=?, gender= ?, age= ?, role= ?, status= ? where user_id= ?");
                //
                pst.setString(1, uname);
                pst.setString(2, pass);
                pst.setString(3, emai1);
                pst.setString(4, phn);
                pst.setString(5, dt);
                pst.setString(6, gend);
                pst.setString(7, agedd);
                pst.setString(8, role);
                pst.setString(9, status);
                pst.setInt(10, id);

                int k = pst.executeUpdate();

                if (k == 1) {
                    JOptionPane.showMessageDialog(this, "User Updated");
                    jname.setText("");
                    jpassword.setText("");
                    jemailid.setText("");
                    jgender.setText("");
                    jphone.setText("");
                    jage.setText("");
                    jrole.setSelectedIndex(0);
                    jstatus.setSelectedIndex(0);
                    jname.requestFocus();

                    usersupdate();

                    //                jadd.setEnabled(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(inventorypage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            if (jTable20.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Field is empty!");
            }
        }
    }//GEN-LAST:event_jUpdateActionPerformed

    private void jDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDeleteActionPerformed
        // TODO add your handling code here:
        if (jTable20.getSelectedRowCount() == 1) {
            int row = jTable20.getSelectedRow();
            String cell = jTable20.getModel().getValueAt(row, 0).toString();
            String sql = "DELETE FROM `registered_user` where user_id= " + cell;

            try {
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Successfully Deleted!");
                deleteusers();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();

                } catch (Exception e) {
                }

                DefaultTableModel model = (DefaultTableModel) jTable20.getModel();
                //delete row
                if (jTable20.getSelectedRowCount() == 1) {
                    //if single row is selected then delete
                    model.removeRow(jTable20.getSelectedRow());
                    jname.setText("");
                    jpassword.setText("");
                    jemailid.setText("");
                    jgender.setText("");
                    jage.setText("");
                    jphone.setText("");
                    jrole.setSelectedIndex(0);
                    jstatus.setSelectedIndex(0);
                }
            }
        } else {

            if (jTable20.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Field is empty!");
            }
        }
        //        jadd.setEnabled(true);
    }//GEN-LAST:event_jDeleteActionPerformed

    private void jclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jclearActionPerformed
        // TODO add your handling code here:
        jname.setText("");
        jpassword.setText("");
        jphone.setText("");
        jemailid.setText("");
        jgender.setText("");
        jage.setText("");
        jrole.setSelectedIndex(0);
        jstatus.setSelectedIndex(0);
    }//GEN-LAST:event_jclearActionPerformed

    private void jaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jaddActionPerformed

        String username = jname.getText();
        String password = jpassword.getText();
        String email_id = jemailid.getText();
        String gender = jgender.getText();
        String da = date.getText();
        String ph = jphone.getText();
        String age = jage.getText();

        String role1 = "Admin";

        String status = "Active";

        if (username.trim().equals("") || username.trim().equals("")
                || password.trim().equals("") || password.trim().equals("")
                || email_id.trim().equals("") || email_id.trim().equals("")
                || gender.trim().equals("") || gender.trim().equals("")
                || ph.trim().equals("") || ph.trim().equals("")
                || age.trim().equals("") || age.trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Other fields are empty!");
        }

        if (jname.getText().length() <= 0
                || jpassword.getText().length() <= 0
                || jemailid.getText().length() <= 0
                || jgender.getText().length() <= 0
                || jage.getText().length() <= 0) {

        } //add user
        else {
            JOptionPane.showMessageDialog(null, "User Added!");
            jname.setText("");
            jpassword.setText("");
            jemailid.setText("");
            jgender.setText("");
            jphone.setText("");
            jage.setText("");
            jage.setText("");
            //

            try {

                String query = "insert into `registered_user`(username,password,email,phone_number,date,gender,age,role,status)values(?,?,?,?,?,?,?,?,?);";
                pst = con.prepareStatement(query);

                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, email_id);
                pst.setString(4, ph);
                pst.setString(5, da);
                pst.setString(6, gender);
                pst.setString(7, age);
                //          pst.setInt(5, Integer.valueOf(jage.getText()));

                pst.setString(8, role1);

                pst.setString(9, status);
                pst.executeUpdate();

            } catch (SQLException ex) {
                Logger.getLogger(cashierpage.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_jaddActionPerformed

    private void jshowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jshowActionPerformed
        // TODO add your handling code here:
        DefaultTableModel df = (DefaultTableModel) jTable20.getModel();
        df.setRowCount(0);

        if (jchoose.getSelectedIndex() == 0) {
            displayall();
            blockapprovebtns1();
            jDelete.setEnabled(true);
            jUpdate.setEnabled(true);
            jrole.setEnabled(false);

            jadd.setEnabled(true);

        } else if (jchoose.getSelectedIndex() == 1) {
            registrants();
            blockapprovebtns();
            japprove.setEnabled(true);
            jremove.setEnabled(true);
            jadd.setEnabled(false);

        }

        jname.setText("");
        jpassword.setText("");
        jemailid.setText("");
        jgender.setText("");
        jphone.setText("");
        jage.setText("");
        jrole.setSelectedIndex(0);
        jstatus.setSelectedIndex(0);

        DefaultTableModel model = (DefaultTableModel) jTable20.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
        jTable20.setRowSorter(tr);
    }//GEN-LAST:event_jshowActionPerformed

    private void japproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_japproveActionPerformed

        DefaultTableModel tbmodel = (DefaultTableModel) jTable20.getModel();
        if (jTable20.getSelectedRowCount() == 1) {
            toapproveuser();
            JOptionPane.showMessageDialog(null, "Approved!");

            //      to remove approved applicants/registrants..
            String role1;
            role1 = jrole.getSelectedItem().toString();

            int row = jTable20.getSelectedRow();

            String cell = jTable20.getModel().getValueAt(row, 0).toString();

            String sql = "DELETE FROM `user_applicant` where user_id= " + cell;

            try {
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, " New " + role1 + " Added!");
                updateuserapplicant();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();

                } catch (Exception e) {
                }

                DefaultTableModel model = (DefaultTableModel) jTable20.getModel();
                //delete row
                if (jTable20.getSelectedRowCount() == 1) {
                    //if single row is selected then delete
                    model.removeRow(jTable20.getSelectedRow());
                    jname.setText("");
                    jpassword.setText("");
                    jemailid.setText("");
//                    jdate.setText("");
                    jphone.setText("");
                    jgender.setText("");
                    jage.setText("");
                    jrole.setSelectedIndex(0);
                    //              jstatus.setSelectedIndex(0);
                }
            }

        } else {
            if (jTable20.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Table is empty!");
            } else {
                JOptionPane.showMessageDialog(null, "Please select a single row to delete!");
            }
        }

    }//GEN-LAST:event_japproveActionPerformed

    private void jremoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jremoveActionPerformed
        // TODO add your handling code here:

        DefaultTableModel tbmodel = (DefaultTableModel) jTable20.getModel();
        if (jTable20.getSelectedRowCount() == 1) {
            todecline();
            String username = jname.getText();
            String role1;
            role1 = jrole.getSelectedItem().toString();
            int row = jTable20.getSelectedRow();
            String cell = jTable20.getModel().getValueAt(row, 0).toString();
            String sql = "DELETE FROM `user_applicant` where user_id= " + cell;

            try {
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "You declined this user registration as  " + role1 + "   named    " + username + "!");
                todeclineusers();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                try {
                    pst.close();
                    rs.close();

                } catch (Exception e) {
                }

                DefaultTableModel model = (DefaultTableModel) jTable20.getModel();
                //delete row
                if (jTable20.getSelectedRowCount() == 1) {
                    //if single row is selected then delete
                    model.removeRow(jTable20.getSelectedRow());
                    jname.setText("");
                    jpassword.setText("");
                    jemailid.setText("");
                    jgender.setText("");
                    date.setText("");
                    jage.setText("");
                    jphone.setText("");
                    jrole.setSelectedIndex(0);
                    jstatus.setSelectedIndex(0);
                }
            }

        } else {
            if (jTable20.getSelectedRowCount() == 0) {
                //if table is empty np data the show message..
                JOptionPane.showMessageDialog(null, "Table is empty!");
            } else {
                JOptionPane.showMessageDialog(null, "Please select a single row to delete!");
            }
        }

    }//GEN-LAST:event_jremoveActionPerformed

    private void jraddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jraddMouseClicked
        // TODO add your handling code here:
        jadd.setEnabled(true);
        jrole.setEnabled(false);
        jUpdate.setEnabled(false);
        jDelete.setEnabled(false);

        japprove.setEnabled(false);
        jremove.setEnabled(false);

        jradd.setText("");

        jname.setText("");
        jpassword.setText("");
        jemailid.setText("");
        jgender.setText("");
        jphone.setText("");
        jage.setText("");
        jrole.setSelectedIndex(0);
        jstatus.setSelectedIndex(0);
    }//GEN-LAST:event_jraddMouseClicked

    private void jchooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchooseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jchooseActionPerformed

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
            java.util.logging.Logger.getLogger(adminpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(adminpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(adminpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(adminpage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new adminpage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField date;
    private javax.swing.JButton jDelete;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JTable jTable20;
    private javax.swing.JButton jUpdate;
    private javax.swing.JButton jadd;
    public javax.swing.JTextField jage;
    private javax.swing.JButton japprove;
    private javax.swing.JComboBox<String> jchoose;
    private javax.swing.JButton jclear;
    public javax.swing.JTextField jemailid;
    public javax.swing.JTextField jgender;
    private javax.swing.JTextField jinput;
    public javax.swing.JTextField jname;
    public javax.swing.JPasswordField jpassword;
    public javax.swing.JTextField jphone;
    private javax.swing.JLabel jradd;
    private javax.swing.JButton jremove;
    private javax.swing.JComboBox<String> jrole;
    private javax.swing.JLabel jsearch;
    private javax.swing.JButton jshow;
    private javax.swing.JComboBox<String> jstatus;
    private javax.swing.JLabel jusernamee;
    private javax.swing.JTextField time;
    // End of variables declaration//GEN-END:variables
}
