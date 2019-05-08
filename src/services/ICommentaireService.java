/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.FosUser;
import java.util.List;
import entities.Commentaire;
import entities.Ticket;

public interface ICommentaireService {

    public void ajouterCommentaire(Commentaire c);

    public void modifCommentaire(Commentaire c, String contenu);

    public void supprCommentaire(Commentaire c);

    public List<Commentaire> afficherCommentaires();

    public List<Commentaire> afficherCommentairesByTicket(Ticket t);
    
    public Commentaire getCommentaireById(int id);

}
