/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "archive", catalog = "kerimb", schema = "")
@NamedQueries({
    @NamedQuery(name = "Archive.findAll", query = "SELECT a FROM Archive a")
    , @NamedQuery(name = "Archive.findById", query = "SELECT a FROM Archive a WHERE a.id = :id")
    , @NamedQuery(name = "Archive.findByUserId", query = "SELECT a FROM Archive a WHERE a.userId = :userId")
    , @NamedQuery(name = "Archive.findByNomEvent", query = "SELECT a FROM Archive a WHERE a.nomEvent = :nomEvent")
    , @NamedQuery(name = "Archive.findByAjouterPar", query = "SELECT a FROM Archive a WHERE a.ajouterPar = :ajouterPar")
    , @NamedQuery(name = "Archive.findByDate", query = "SELECT a FROM Archive a WHERE a.date = :date")
    , @NamedQuery(name = "Archive.findByAdresse", query = "SELECT a FROM Archive a WHERE a.adresse = :adresse")
    , @NamedQuery(name = "Archive.findByCategorie", query = "SELECT a FROM Archive a WHERE a.categorie = :categorie")
    , @NamedQuery(name = "Archive.findByNumTel", query = "SELECT a FROM Archive a WHERE a.numTel = :numTel")
    , @NamedQuery(name = "Archive.findByEmail", query = "SELECT a FROM Archive a WHERE a.email = :email")})
public class Archive implements Serializable {

    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "nom_event")
    private String nomEvent;
    @Basic(optional = false)
    @Column(name = "ajouter_par")
    private int ajouterPar;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @Column(name = "adresse")
    private String adresse;
    @Basic(optional = false)
    @Column(name = "categorie")
    private String categorie;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "num_tel")
    private String numTel;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;

    public Archive() {
    }

    public Archive(Integer id) {
        this.id = id;
    }

    public Archive(Integer id, String nomEvent, int ajouterPar, Date date, String adresse, String categorie, String description, String numTel, String email) {
        this.id = id;
        this.nomEvent = nomEvent;
        this.ajouterPar = ajouterPar;
        this.date = date;
        this.adresse = adresse;
        this.categorie = categorie;
        this.description = description;
        this.numTel = numTel;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        Integer oldId = this.id;
        this.id = id;
        changeSupport.firePropertyChange("id", oldId, id);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        Integer oldUserId = this.userId;
        this.userId = userId;
        changeSupport.firePropertyChange("userId", oldUserId, userId);
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        String oldNomEvent = this.nomEvent;
        this.nomEvent = nomEvent;
        changeSupport.firePropertyChange("nomEvent", oldNomEvent, nomEvent);
    }

    public int getAjouterPar() {
        return ajouterPar;
    }

    public void setAjouterPar(int ajouterPar) {
        int oldAjouterPar = this.ajouterPar;
        this.ajouterPar = ajouterPar;
        changeSupport.firePropertyChange("ajouterPar", oldAjouterPar, ajouterPar);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        Date oldDate = this.date;
        this.date = date;
        changeSupport.firePropertyChange("date", oldDate, date);
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        String oldAdresse = this.adresse;
        this.adresse = adresse;
        changeSupport.firePropertyChange("adresse", oldAdresse, adresse);
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        String oldCategorie = this.categorie;
        this.categorie = categorie;
        changeSupport.firePropertyChange("categorie", oldCategorie, categorie);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        changeSupport.firePropertyChange("description", oldDescription, description);
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        String oldNumTel = this.numTel;
        this.numTel = numTel;
        changeSupport.firePropertyChange("numTel", oldNumTel, numTel);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String oldEmail = this.email;
        this.email = email;
        changeSupport.firePropertyChange("email", oldEmail, email);
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
        if (!(object instanceof Archive)) {
            return false;
        }
        Archive other = (Archive) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "utils.Archive[ id=" + id + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
