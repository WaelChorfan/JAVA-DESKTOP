/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.FosUser;
import java.util.List;
import entities.Ticket;

/**
 *
 * @author Wassim
 */
public interface ITicketService {

    public void ajouterTicket(Ticket t);

    public void modifTicket(Ticket t, String newSujet, String newDesccription, String newStatut);

    public void modifTicket(Ticket t, String newSujet, String newDesccription, String newStatut, String newImageName);

    public void supprTicket(Ticket t);

    public List<Ticket> afficherTickets();

    public Ticket getTicketById(int id);
    
 

}
