/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Message;
import entities.Ticket;
import java.util.List;

/**
 *
 * @author WaelChorfan
 */
public interface IMessageService {
    
    
     public void ajouterMessage(Message m);

//    public void modifMessage(Message m, String contenu);
//
    public void supprMessage(Message m);
//    public List<Message> afficherMessages();

    public List<Message> afficherMessagesByTicket(Ticket t);
    
    public Message getMessageById(int id);
}
