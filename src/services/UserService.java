package services;

import entities.FosUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnexionDBJ;

public class UserService implements IUserService {

    //cnx using  ctor
    Connection cx;
    Statement ste;
    private PreparedStatement pste;
    ConnexionDBJ myconx;

    public UserService() {
        cx = ConnexionDBJ.getInstance().getConnection();
        try {
            ste = cx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ajouterUser(FosUser u) {
        try {
            String requete = "INSERT INTO `fos_user` (username,email,password,role,image_name) values (?,?,?,?,?)";
            pste = this.cx.prepareStatement(requete);
            pste.setString(1, u.getUsername());
            pste.setString(2, u.getEmail());
            pste.setString(3, u.getPassword());
            pste.setString(4, u.getRole());
            pste.setString(5, u.getImageName());
            pste.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifUser(FosUser u, String newUserName, String newEmail, String newPassword, String newImageName) {

    }

    @Override
    public void supprUser(FosUser u) {
    }

    @Override
    public List<FosUser> afficherUsers() {
        List<FosUser> list = new ArrayList<FosUser>();
        try {
            String req = "select * from fos_user";

            ResultSet res = ste.executeQuery(req);
            while (res.next()) {

                FosUser fu = new FosUser(
                        res.getInt("id"),
                        res.getString("username"),
                        res.getString("email"),
                        res.getBoolean("enabled"),
                        res.getString("role")
                );
                list.add(fu);

//                System.out.println(res.getInt("id") + "-- " + res.getString("username") + "--  " + res.getString("email"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(list.toString());
        return list;

    }

    @Override
    public FosUser afficherUser(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private ResultSet rs;
    private FosUser u;

    public FosUser getUserById(int id) throws SQLException {
        String req = "SELECT * FROM `fos_user` WHERE id = ?";
        PreparedStatement pre = cx.prepareStatement(req);
        pre.setInt(1, id);
        rs = pre.executeQuery();
        while (rs.next()) {

            u = new FosUser(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getBoolean("enabled"),
                    rs.getString("role"),
                    rs.getString("image_name")
            );
        }

        return u;
    }

    public FosUser checkUser(String mail, String pwd) throws SQLException {

        String req = "SELECT * FROM `fos_user` WHERE email= ? or username=? And password = ?";
        pste = cx.prepareStatement(req);
        pste.setString(1, mail);
        pste.setString(2, mail);
        pste.setString(3, pwd);
        rs = pste.executeQuery();
        if (rs.next()) {
            rs.getInt("id");
            u = UserService.this.getUserById(rs.getInt("id"));
        } else {
            return null;
        }
        return u;
    }

    @Override
    public FosUser getUserByEmail(String email) {
        String req = "SELECT * FROM `fos_user` WHERE email = ?";
        PreparedStatement pre = null;
        try {
            pre = cx.prepareStatement(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pre.setString(1 ,email);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rs = pre.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("getting UserBy Email "+email);
        try {
            while (rs.next()) {
                
                u = new FosUser(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getBoolean("enabled"),
                        rs.getString("role"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return u;
    
    }

    

}
