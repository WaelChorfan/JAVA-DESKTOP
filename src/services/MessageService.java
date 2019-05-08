/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Message;
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
public class MessageService implements IMessageService {

    //cnx using  ctor
    Connection cx;
    Statement ste;
    ConnexionDBJ myconx;
    private PreparedStatement pste;

    public MessageService() {
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
    public void ajouterMessage(Message m) {
        
    
       try {
            String req
                    = "INSERT INTO Message(user_id, ticket_id,text) "
                    + " values (?,?,?)";    
            pste = this.cx.prepareStatement(req);
            pste.setInt(1, m.getUserId().getId());
            pste.setInt(2, m.getTicketId().getId());
            pste.setString(3, m.getText());
            pste.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }

    @Override
    public void supprMessage(Message m) {
               String req = "DELETE FROM Message WHERE id=" + m.getId();
        try {
            ste.executeUpdate(req);
            System.out.println("Message supprimé ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
            
    }

    @Override
    public List<Message> afficherMessagesByTicket(Ticket t) {
        
        int id_ticket = t.getId();
        List<Message> list = new ArrayList<Message>();
        String req = "SELECT * FROM Message where ticket_id= "+id_ticket;
        ResultSet res;
        try {
            res = ste.executeQuery(req);
            while (res.next()) {
                try {
                    list.add(new Message(
                            res.getInt("id"),
                            us.getUserById((Integer) res.getObject("user_id")).getId(),
                            res.getString("text"),
                            res.getDate("created_at"),
                            ts.getTicketById((Integer) res.getObject("ticket_id")).getId(),
                            us.getUserById((Integer) res.getObject("user_id")).getEmail(),
                            us.getUserById((Integer) res.getObject("user_id")).getUsername()
                            
                    ));

                    System.out.println( res.getInt("id")+"---"+ res.getString("contenu")+
                            us.getUserById((Integer) res.getObject("user_id")).getEmail());
                } catch (SQLException ex) {
                    Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;

    }


    @Override
    public Message getMessageById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


 
 

}
