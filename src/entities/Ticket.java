/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import services.UserService;

/**
 *
 * @author Wassim
 */
@Entity
@Table(name = "ticket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t")
    , @NamedQuery(name = "Ticket.findById", query = "SELECT t FROM Ticket t WHERE t.id = :id")
    , @NamedQuery(name = "Ticket.findByStatut", query = "SELECT t FROM Ticket t WHERE t.statut = :statut")
    , @NamedQuery(name = "Ticket.findBySujet", query = "SELECT t FROM Ticket t WHERE t.sujet = :sujet")
    , @NamedQuery(name = "Ticket.findByCreatedat", query = "SELECT t FROM Ticket t WHERE t.createdat = :createdat")
    , @NamedQuery(name = "Ticket.findByImageName", query = "SELECT t FROM Ticket t WHERE t.imageName = :imageName")
    , @NamedQuery(name = "Ticket.findByUpdatedAt", query = "SELECT t FROM Ticket t WHERE t.updatedAt = :updatedAt")})
public class Ticket implements Serializable {

    @Lob
    @Column(name = "image")
    private byte[] image;
    @OneToMany(mappedBy = "idTicket")
    private Collection<Commentaire> commentaireCollection;
 



    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "statut")
    private String statut;
    @Basic(optional = false)
    @Column(name = "sujet")
    private String sujet;
    @Basic(optional = false)
    @Column(name = "createdat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdat;
    @Basic(optional = false)
    @Column(name = "image_name")
    private String imageName;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "recepteur_id", referencedColumnName = "id")
    @ManyToOne
    private FosUser recepteurId;
    @JoinColumn(name = "emeteur_id", referencedColumnName = "id")
    @ManyToOne
    private FosUser emeteurId;
    @OneToMany(mappedBy = "ticketId")
    private Collection<Message> messageCollection;



    public String getEmeteurEmail() {
        return emeteurEmail;
    }

    public void setEmeteurEmail(String emeteurEmail) {
        this.emeteurEmail = emeteurEmail;
    }

    public String getRecepteurEmail() {
        return recepteurEmail;
    }

    //added custom columns
    public void setRecepteurEmail(String recepteurEmail) {
        this.recepteurEmail = recepteurEmail;
    }
    private String emeteurEmail;
    private String recepteurEmail;


    public Ticket() {
    }

    public Ticket(Integer id) {
        this.id = id;
    }

    public Ticket(Integer id, String description, String statut, String sujet, Date createdat, String imageName, Date updatedAt) {
        this.id = id;
        this.description = description;
        this.statut = statut;
        this.sujet = sujet;
        this.createdat = createdat;
        this.imageName = imageName;
        this.updatedAt = updatedAt;
    }

     public Ticket(Integer id, String sujet, String statut, String description, Date createdat) 
        {
        this.id = id;
        this.sujet = sujet;
        this.statut = statut;
        this.description = description;
        this.createdat = createdat;
       
    }
    public Ticket(Integer id, String sujet, String statut, String description, Date createdat,String em, String rec) 
        {
        this.id = id;
        this.sujet = sujet;
        this.statut = statut;
        this.description = description;
        this.createdat = createdat;
        this.emeteurEmail = em;
        this.recepteurEmail =rec ;
    }
     public Ticket(Integer id, String sujet, String statut, String description, Date createdat,String em, String rec,String imgName) 
        {
        this.id = id;
        this.sujet = sujet;
        this.statut = statut;
        this.description = description;
        this.createdat = createdat;
        this.emeteurEmail = em;
        this.recepteurEmail =rec ;
        this.imageName=imgName;
    }

    UserService us = new UserService();
    
    public Ticket( String sujet, String statut, String description,int em, int rec) throws SQLException 
        {
        this.id = id;
        this.sujet = sujet;
        this.statut = statut;
        this.description = description;
        this.createdat = createdat;
        this.emeteurId = us.getUserById(em);
        this.recepteurId =us.getUserById(rec) ;
    }
    
    public Ticket( String sujet, String statut, String description,int em, int rec,String imgName) throws SQLException 
        {
        this.id = id;
        this.sujet = sujet;
        this.statut = statut;
        this.description = description;
        this.createdat = createdat;
        this.emeteurId = us.getUserById(em);
        this.recepteurId =us.getUserById(rec) ;
        this.imageName=imgName;
    }
  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FosUser getRecepteurId() {
        return recepteurId;
    }

    public void setRecepteurId(FosUser recepteurId) {
        this.recepteurId = recepteurId;
    }

    public FosUser getEmeteurId() {
        return emeteurId;
    }

    public void setEmeteurId(FosUser emeteurId) {
        this.emeteurId = emeteurId;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
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
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ticket[ id=" + id + "desc" + description + "statut" + statut + " ]";
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @XmlTransient
    public Collection<Commentaire> getCommentaireCollection() {
        return commentaireCollection;
    }

    public void setCommentaireCollection(Collection<Commentaire> commentaireCollection) {
        this.commentaireCollection = commentaireCollection;
    }

    

  

}
