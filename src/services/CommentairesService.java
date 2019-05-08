/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Commentaire;
import entities.Ticket;
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

/**
 *
 * @author WaelChorfan
 */
public class CommentairesService implements ICommentaireService {

    //cnx using  ctor
    Connection cx;
    Statement ste;
    ConnexionDBJ myconx;
    private PreparedStatement pste;

    public CommentairesService() {
        cx = ConnexionDBJ.getInstance().getConnection();
        try {
            ste = cx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    UserService us = new UserService();
    TicketService ts = new TicketService();

    @Override
    public void ajouterCommentaire(Commentaire c) {
        
    
       try {
            String req
                    = "INSERT INTO commentaire(user_id, id_ticket,contenu) "
                    + " values (?,?,?)";
            pste = this.cx.prepareStatement(req);
            pste.setInt(1, c.getUserId().getId());
            pste.setInt(2, c.getIdTicket().getId());
            pste.setString(3, c.getContenu());  
            pste.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }

    @Override
    public void modifCommentaire(Commentaire c, String contenu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprCommentaire(Commentaire c) {
               String req = "DELETE FROM Commentaire WHERE id=" + c.getId();
        try {
            ste.executeUpdate(req);
            System.out.println("commentaire supprimé ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
            
    }

    @Override
    public List<Commentaire> afficherCommentairesByTicket(Ticket t) {
        
        int id_ticket = t.getId();
        List<Commentaire> list = new ArrayList<Commentaire>();
        String req = "SELECT * FROM commentaire where id_ticket= "+id_ticket;
        ResultSet res;
        try {
            res = ste.executeQuery(req);
            while (res.next()) {
                try {
                    list.add(new Commentaire(
                            res.getInt("id"),
                            us.getUserById((Integer) res.getObject("user_id")).getId(),
                            res.getString("contenu"),
                            res.getDate("date"),
                            ts.getTicketById((Integer) res.getObject("id_ticket")).getId(),
                            us.getUserById((Integer) res.getObject("user_id")).getEmail(),
                            us.getUserById((Integer) res.getObject("user_id")).getUsername()
                            
                    ));

                    System.out.println( res.getInt("id")+"---"+ res.getString("contenu")+
                            us.getUserById((Integer) res.getObject("user_id")).getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(CommentairesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentairesService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;

    }


    @Override
    public Commentaire getCommentaireById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Commentaire> afficherCommentaires() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
 

}
