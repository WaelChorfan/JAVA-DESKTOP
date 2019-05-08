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
 * @author Wassim
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
    , @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id")
    , @NamedQuery(name = "Message.findByCreatedAt", query = "SELECT m FROM Message m WHERE m.createdAt = :createdAt")})
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "text")
    private String text;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "recepteur_id", referencedColumnName = "id")
    @ManyToOne
    private FosUser recepteurId;
    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    @ManyToOne
    private Ticket ticketId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private FosUser userId;
    @JoinColumn(name = "emeteur_id", referencedColumnName = "id")
    @ManyToOne
    private FosUser emeteurId;

    
    
    public String userName;
    public String email;
    public Message() {
    }

    public Message(Integer id) {
        this.id = id;
    }

    public Message(Integer id, String text, Date createdAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
    }
    
     UserService us = new UserService();
 TicketService ts = new TicketService();
 
   public  Message(int _userid ,int _ticketid ,String _contenu) throws SQLException{
       this.userId=us.getUserById(_userid) ;
       this.userName=us.getUserById(_userid).getUsername();
       this.email=us.getUserById(_userid).getEmail() ;
       this.ticketId=ts.getTicketById(_ticketid);
       this.text=_contenu;
   
   }

    public Message(int _id, Integer _userId, String _text, java.sql.Date _date, Integer _ticket_id, String _email, String _username) throws SQLException {
       this.id = _id;
        this.userId = us.getUserById(_userId);
        this.text = _text;
        this.createdAt = _date;
        this.ticketId = ts.getTicketById(_ticket_id);
        this.userName=_username;
        this.email=_email;
   
        
        
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public FosUser getRecepteurId() {
        return recepteurId;
    }

    public void setRecepteurId(FosUser recepteurId) {
        this.recepteurId = recepteurId;
    }

    public Ticket getTicketId() {
        return ticketId;
    }

    public void setTicketId(Ticket ticketId) {
        this.ticketId = ticketId;
    }

    public FosUser getUserId() {
        return userId;
    }

    public void setUserId(FosUser userId) {
        this.userId = userId;
    }

    public FosUser getEmeteurId() {
        return emeteurId;
    }

    public void setEmeteurId(FosUser emeteurId) {
        this.emeteurId = emeteurId;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Message[ id=" + id + " ]";
    }
    
}
