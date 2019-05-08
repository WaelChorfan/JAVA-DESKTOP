/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import services.TicketService;
import services.UserService;

/**
 *
 * @author WaelChorfan
 */
@Entity
@Table(name = "commentaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commentaire.findAll", query = "SELECT c FROM Commentaire c")
    , @NamedQuery(name = "Commentaire.findById", query = "SELECT c FROM Commentaire c WHERE c.id = :id")
    , @NamedQuery(name = "Commentaire.findByAuteur", query = "SELECT c FROM Commentaire c WHERE c.auteur = :auteur")
    , @NamedQuery(name = "Commentaire.findByDate", query = "SELECT c FROM Commentaire c WHERE c.date = :date")})
public class Commentaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "auteur")
    private String auteur;
    @Basic(optional = false)
    @Lob
    @Column(name = "contenu")
    private String contenu;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FosUser userId;
    @JoinColumn(name = "id_ticket", referencedColumnName = "id")
    @ManyToOne
    private Ticket idTicket;

    //added custom columns 
    private String username;
    private String email;

   
 public Commentaire(int id_, Integer user_id_, String contenu_, java.sql.Date date_, Integer ticket_id_, String email_, String username_) throws SQLException 
 {
        this.id = id_;
        this.userId = us.getUserById(user_id_);
        this.contenu = contenu_;
        this.date = date_;
        this.idTicket = ts.getTicketById(ticket_id_);
        this.username=username_;
        this.email=email_;
 
 }
 UserService us = new UserService();
 TicketService ts = new TicketService();
 
   public  Commentaire(int _userid ,int _ticketid ,String _contenu) throws SQLException{
       this.userId=us.getUserById(_userid) ;
       this.username=us.getUserById(_userid).getUsername();
       this.email=us.getUserById(_userid).getEmail() ;
       this.idTicket=ts.getTicketById(_ticketid);
       this.contenu=_contenu;
   }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public Commentaire() {
    }

    public Commentaire(Integer id) {
        this.id = id;
    }

    public Commentaire(Integer id, String contenu, Date date) {
        this.id = id;
        this.contenu = contenu;
        this.date = date;
    }


    public Commentaire(int id_, Integer user_id_, String contenu_, java.sql.Date date_, Integer ticket_id_) throws SQLException {
        this.id = id_;
        this.userId = us.getUserById(user_id_);
        this.contenu = contenu_;
        this.date = date_;
        this.idTicket = ts.getTicketById(ticket_id_);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public FosUser getUserId() {
        return userId;
    }

    public void setUserId(FosUser userId) {
        this.userId = userId;
    }

    public Ticket getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Ticket idTicket) {
        this.idTicket = idTicket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commentaire)) {
            return false;
        }
        Commentaire other = (Commentaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Commentaire[ id=" + id + " ]";
    }

}
