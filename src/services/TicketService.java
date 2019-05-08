/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.FosUser;
import entities.Ticket;
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
public class TicketService implements ITicketService {

    //cnx using  ctor
    Connection cx;
    Statement ste;
    ConnexionDBJ myconx;
    private PreparedStatement pste;

    public TicketService() {
        cx = ConnexionDBJ.getInstance().getConnection();
        try {
            ste = cx.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void ajouterTicket(Ticket t) {

        try {
            String req
                    = "INSERT INTO Ticket(description,statut,sujet,recepteur_id,emeteur_id,image_name)"
                    + " values (?,?,?,?,?,?)";
            pste = this.cx.prepareStatement(req);
            pste.setString(1, t.getDescription());
            pste.setString(2, t.getStatut());
            pste.setString(3, t.getSujet());
            pste.setInt(4, t.getRecepteurId().getId());
            pste.setInt(5, t.getEmeteurId().getId());
            pste.setString(6, t.getImageName());
            pste.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void supprTicket(Ticket t) {
        String req = "DELETE FROM Ticket WHERE id=" + t.getId();
        try {
            ste.executeUpdate(req);
            System.out.println("supprission c bon ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Ticket> afficherTickets() {
        List<Ticket> list = new ArrayList<Ticket>();
        try {
            //get the real tickets !! from ticket
            String req = "select * from ticket";
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                list.add(new Ticket(
                        res.getInt("id"),
                        res.getString("description"),
                        res.getString("statut"),
                        res.getString("sujet"),
                        res.getDate("createdat")
                ));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public List<Ticket> afficherTicketsWithUsernames() throws SQLException {

        List<Ticket> list = new ArrayList<>();
        UserService us = new UserService();
        String req = "select * from ticket";
        ResultSet res = ste.executeQuery(req);
        int i = 0;
        while (res.next()) {

            list.add(
                    new Ticket(
                            res.getInt("id"),
                            res.getString("description"),
                            res.getString("statut"),
                            res.getString("sujet"),
                            res.getDate("createdat"),
                            us.getUserById((Integer) res.getObject("emeteur_id")).getEmail(),
                            us.getUserById((Integer) res.getObject("recepteur_id")).getEmail()
                    )
            );
            i++;
        }
        return list;

    }

    public List<Ticket> afficherTicketsWithUsernamesForCurrentUser(FosUser fu) throws SQLException {

        System.out.println("services.TicketService.afficherTicketsWithUsernamesForCurrentUser()");
        List<Ticket> list = new ArrayList<>();
        UserService us = new UserService();
        String req = "SELECT * FROM ticket where emeteur_id=" + fu.getId() + " or recepteur_id=" + fu.getId();

        ResultSet res = ste.executeQuery(req);

        int i = 0;
        while (res.next()) {

            System.out.println("tickets for " + fu.getEmail() + "are loaded");
            list.add(
                    new Ticket(
                            res.getInt("id"),
                            res.getString("description"),
                            res.getString("statut"),
                            res.getString("sujet"),
                            res.getDate("createdat"),
                            us.getUserById((Integer) res.getObject("emeteur_id")).getEmail(),
                            us.getUserById((Integer) res.getObject("recepteur_id")).getEmail()
                    )
            );
            i++;
        }
        return list;

    }

    private ResultSet rs;
    private Ticket t;
    UserService us = new UserService();

    @Override
    public Ticket getTicketById(int id) {
        try {
            String req = "SELECT * FROM ticket WHERE id = ?";
            pste = cx.prepareStatement(req);

            pste.setInt(1, id);
            rs = pste.executeQuery();

            while (rs.next()) {
                t = new Ticket(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getString("statut"),
                        rs.getString("sujet"),
                        rs.getDate("createdat"),
                        us.getUserById((Integer) rs.getObject("emeteur_id")).getEmail(),
                        us.getUserById((Integer) rs.getObject("recepteur_id")).getEmail(),
                        rs.getString("image_name")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(TicketService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;

    }

    @Override
    public void modifTicket(Ticket t, String newSujet, String newDescription, String newStatut) {

        try {
            String description = (newDescription.equals(null) || newDescription.equals("")) ? t.getDescription() : newDescription;
            String statut = (newStatut.equals(null) || newStatut.equals("")) ? t.getStatut() : newStatut;
            String sujet = (newSujet.equals(null) || newSujet.equals("")) ? t.getSujet() : newSujet;

            String req = "Update ticket  set description='" + description + "',"
                    + "Statut='" + statut + "',Sujet='" + sujet + "' where id=" + t.getId();
            System.err.println(req);
            ste.executeUpdate(req);
            System.out.println("ticket with id =" + t.getId() + " has been successfully updated  ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *
     * @param t
     * @param newSujet
     * @param newDescription
     * @param newStatut
     * @param newImageName
     */
    @Override
    public void modifTicket(Ticket t, String newSujet, String newDescription, String newStatut, String newImageName) {

        try {
            String description = (newDescription.equals(null) || newDescription.equals("")) ? t.getDescription() : newDescription;
            String statut = (newStatut.equals(null) || newStatut.equals("")) ? t.getStatut() : newStatut;
            String sujet = (newDescription.equals(null) || newSujet.equals("")) ? t.getSujet() : newSujet;
            String imgName = (newImageName.equals(null) || newImageName.equals("")) ? t.getImageName() : newImageName;

            String req = "Update ticket  set description='"
                    + description + "',"
                    + "Statut='" + statut + "',Sujet='" + sujet
                    + "' , image_name='" + imgName
                    + "' where id=" + t.getId();
            System.err.println(req);
            ste.executeUpdate(req);
            System.out.println("ticket with id =" + t.getId() + " was successfully updated  ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
