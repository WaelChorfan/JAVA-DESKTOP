/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author WaelChorfan
 */
@Entity
@Table(name = "typeproduit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Typeproduit.findAll", query = "SELECT t FROM Typeproduit t")
    , @NamedQuery(name = "Typeproduit.findById", query = "SELECT t FROM Typeproduit t WHERE t.id = :id")
    , @NamedQuery(name = "Typeproduit.findByNom", query = "SELECT t FROM Typeproduit t WHERE t.nom = :nom")
    , @NamedQuery(name = "Typeproduit.findByCreateat", query = "SELECT t FROM Typeproduit t WHERE t.createat = :createat")})
public class Typeproduit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @Column(name = "createat")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createat;
    @OneToMany(mappedBy = "typeId")
    private Collection<Produit> produitCollection;

    public Typeproduit() {
    }

    public Typeproduit(Integer id) {
        this.id = id;
    }

    public Typeproduit(Integer id, String nom, Date createat) {
        this.id = id;
        this.nom = nom;
        this.createat = createat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getCreateat() {
        return createat;
    }

    public void setCreateat(Date createat) {
        this.createat = createat;
    }

    @XmlTransient
    public Collection<Produit> getProduitCollection() {
        return produitCollection;
    }

    public void setProduitCollection(Collection<Produit> produitCollection) {
        this.produitCollection = produitCollection;
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
        if (!(object instanceof Typeproduit)) {
            return false;
        }
        Typeproduit other = (Typeproduit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Typeproduit[ id=" + id + " ]";
    }
    
}
