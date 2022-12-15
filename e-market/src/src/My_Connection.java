/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author 1styrGroupB
 */
public class My_Connection {

    
    Connection conn;
 
    public Connection getConn(){
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/marketsystem","root","");
//             JOptionPane.showMessageDialog(null, "Database Connection Successful MySQLConnect getconns method");
             return conn;
        } 
           
       
        catch (Exception e) {
           JOptionPane.showMessageDialog(null, "Error in MySQLConnect getconns method"+e);
         
        }
           return conn;
    
  }
}

