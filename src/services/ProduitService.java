/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.FosUser;
import entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnexionDBJ;

/**
 *
 * @author Wassim
 */
public class ProduitService implements IProduitService {

    //cnx using  ctor
    Connection cx;
    Statement ste;
    ConnexionDBJ myconx;
    private PreparedStatement pste;

    public ProduitService() {
        cx = ConnexionDBJ.getInstance().getConnection();
        try {
            ste = cx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ajouterProduit(Produit t) {

        System.out.println("adding a new product");
        try {
            String req
                    = "INSERT INTO Produit(nom,description,prix,image_name,user_id,type_prod,cat_prod)"
                    + " values (?,?,?,?,?,?,?)";
            pste = this.cx.prepareStatement(req);
            pste.setString(1, t.getNom());
            pste.setString(2, t.getDescription());
            pste.setDouble(3, t.getPrix());
            pste.setString(4, t.getImageName());
            pste.setInt(5, t.getUserId().getId());
            pste.setString(6, t.getTypeProd());
            pste.setString(7, t.getCategorie());
            pste.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void supprProduit(Produit t) {
        String req = "DELETE FROM Produit WHERE id=" + t.getId();
        try {
            ste.executeUpdate(req);
            System.out.println("supprission c bon ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Produit> afficherProduitsWithUsernames() throws SQLException {

        List<Produit> list = new ArrayList<>();
        UserService us = new UserService();
        String req = "select * from produit";
        ResultSet res = ste.executeQuery(req);
        int i = 0;
        while (res.next()) {

            list.add(
                    new Produit(
                            res.getInt("id"),
                            res.getString("nom"),
                            res.getString("description"),
                            res.getDouble("prix"),
                            us.getUserById((Integer) res.getObject("user_id")),
                            res.getString("image_name"),
                            res.getString("type_prod"),
                            res.getString("cat_prod")
                    )
            );
            i++;
        }
        return list;

    }

    public List<Produit> afficherProduitsWithUsernamesForCurrentUser(FosUser fu) throws SQLException {

        List<Produit> list = new ArrayList<>();
        UserService us = new UserService();
        String req = "SELECT * FROM produit where user_id=" + fu.getId();

        ResultSet res = ste.executeQuery(req);

        int i = 0;
        while (res.next()) {

            System.out.println("tickets for " + fu.getEmail() + "are loaded");
            list.add(
                    new Produit(
                            res.getInt("id"),
                            res.getString("nom"),
                            res.getString("description"),
                            res.getDouble("prix"),
                            us.getUserById((Integer) res.getObject("user_id")),
                            res.getString("image_name"),
                            res.getString("type_prod"),
                            res.getString("cat_prod")
                    )
            );
            i++;
        }
        return list;

    }

    private ResultSet rs;
    private Produit t;
    UserService us = new UserService();

    public Produit getProduitById(int id) throws SQLException {

        String req = "SELECT * FROM `produit` WHERE id = ?";
        PreparedStatement pre = cx.prepareStatement(req);
        pre.setInt(1, id);
        rs = pre.executeQuery();
        while (rs.next()) {

            t = new Produit(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("description"),
                    rs.getDouble("prix"),
                    us.getUserById((Integer) rs.getObject("user_id")),
                    rs.getString("image_name"),
                    rs.getString("type_prod"),
                    rs.getString("cat_prod")
            );
        }

        return t;

    }

    @Override
    public void modifProduit(Produit t, String newNom, String newDescription, Double newPrix) {

        try {
            String nom = (newNom.equals(null) || newNom.equals("")) ? t.getNom() : newNom;
            String description = (newDescription.equals(null) || newDescription.equals("")) ? t.getDescription() : newDescription;
            Double prix = (newPrix == null) ? t.getPrix() : newPrix;

            String req = "Update produit  set description='" + description + "',"
                    + "nom='" + nom + "',prix=" + prix + " where id=" + t.getId();
            System.err.println(req);
            ste.executeUpdate(req);
            System.out.println("produit with id =" + t.getId() + " has been successfully updated  ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifProduit(Produit t, String newNom, String newDescription, Double newPrix, String newImageName) {

        try {
            String nom = (newNom.equals(null) || newNom.equals("")) ? t.getNom() : newNom;
            String description = (newDescription.equals(null) || newDescription.equals("")) ? t.getDescription() : newDescription;
            Double prix = (newPrix == null) ? t.getPrix() : newPrix;

            String imageName = (newImageName.equals(null) || newImageName.equals("")) ? t.getImageName() : newImageName;

            String req = "Update produit  set description='" + description + "',"
                    + "nom='" + nom + "',prix=" + prix + ", image_name='" + imageName + "' where id=" + t.getId();
            System.err.println(req);
            ste.executeUpdate(req);
            System.out.println("ticket with id =" + t.getId() + " has been successfully updated  ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Produit getProduitByUser(FosUser u) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Produit> afficherProduits() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Produit> afficherProduitsParType(String s) {
        List<Produit> list = new ArrayList<>();
        UserService us = new UserService();
        String req = (s == null) ? "SELECT * FROM produit " : "SELECT * FROM produit where type_prod='" + s + "'";

        ResultSet res = null;
        try {
            res = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = 0;
        try {
            while (res.next()) {

                try {
                    list.add(
                            new Produit(
                                    res.getInt("id"),
                                    res.getString("nom"),
                                    res.getString("description"),
                                    res.getDouble("prix"),
                                    us.getUserById((Integer) res.getObject("user_id")),
                                    res.getString("image_name"),
                                    res.getString("type_prod"),
                                    res.getString("cat_prod")
                            )
                    );
                } catch (SQLException ex) {
                    Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    @Override
    public List<Produit> afficherProduitsParCategorie(String s) {
        List<Produit> list = new ArrayList<>();
        UserService us = new UserService();
        String req = (s == null) ? "SELECT * FROM produit " : "SELECT * FROM produit where cat_prod='" + s + "'";

        ResultSet res = null;
        try {
            res = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = 0;
        try {
            while (res.next()) {

                try {
                    list.add(
                            new Produit(
                                    res.getInt("id"),
                                    res.getString("nom"),
                                    res.getString("description"),
                                    res.getDouble("prix"),
                                    us.getUserById((Integer) res.getObject("user_id")),
                                    res.getString("image_name"),
                                    res.getString("type_prod"),
                                    res.getString("cat_prod")
                            )
                    );
                } catch (SQLException ex) {
                    Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    @Override
    public List<Produit> afficherProduitsParTypeEtUser(String s, FosUser u) {

        List<Produit> list = new ArrayList<>();
        UserService us = new UserService();
        String req = "SELECT * FROM produit where type_prod='" + s + "' and user_id=" + u.getId();

        ResultSet res = null;
        try {
            res = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = 0;
        try {
            while (res.next()) {

                try {
                    list.add(
                            new Produit(
                                    res.getInt("id"),
                                    res.getString("nom"),
                                    res.getString("description"),
                                    res.getDouble("prix"),
                                    us.getUserById((Integer) res.getObject("user_id")),
                                    res.getString("image_name"),
                                    res.getString("type_prod"),
                                    res.getString("cat_prod")
                            )
                    );
                } catch (SQLException ex) {
                    Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    @Override
    public List<Produit> afficherProduitsParCategorieEtUser(String s, FosUser u) {
        List<Produit> list = new ArrayList<>();
        UserService us = new UserService();
        String req = "SELECT * FROM produit where cat_prod='" + s + "' and user_id=" + u.getId();

        ResultSet res = null;
        try {
            res = ste.executeQuery(req);
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }

        int i = 0;
        try {
            while (res.next()) {

                try {
                    list.add(
                            new Produit(
                                    res.getInt("id"),
                                    res.getString("nom"),
                                    res.getString("description"),
                                    res.getDouble("prix"),
                                    us.getUserById((Integer) res.getObject("user_id")),
                                    res.getString("image_name"),
                                    res.getString("type_prod"),
                                    res.getString("cat_prod")
                            )
                    );
                } catch (SQLException ex) {
                    Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
                }
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

}
