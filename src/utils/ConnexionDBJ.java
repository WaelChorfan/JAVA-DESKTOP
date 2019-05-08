/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Salma
 */
public class ConnexionDBJ {
  String url="jdbc:mysql://localhost:3306/wasssim";
    String login="root";
    String pwd="";
    Connection cx; 
  static  ConnexionDBJ cBd;
    private ConnexionDBJ() {
      try {
          cx = DriverManager.getConnection(url, login, pwd);
          System.out.println("Connex etablie");
      } catch (SQLException ex) {
          Logger.getLogger(ConnexionDBJ.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    public static  ConnexionDBJ getInstance(){
        if(cBd == null)
            cBd = new ConnexionDBJ();
         return 
                 cBd;
    }
    
    public Connection getConnection(){
        return cx;
    }
    
}
