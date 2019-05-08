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
import services.UserService;

/**
 *
 * @author WaelChorfan
 */
@Entity
@Table(name = "produit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produit.findAll", query = "SELECT p FROM Produit p")
    , @NamedQuery(name = "Produit.findById", query = "SELECT p FROM Produit p WHERE p.id = :id")
    , @NamedQuery(name = "Produit.findByNom", query = "SELECT p FROM Produit p WHERE p.nom = :nom")
    , @NamedQuery(name = "Produit.findByDate", query = "SELECT p FROM Produit p WHERE p.date = :date")
    , @NamedQuery(name = "Produit.findByPrix", query = "SELECT p FROM Produit p WHERE p.prix = :prix")
    , @NamedQuery(name = "Produit.findByImageName", query = "SELECT p FROM Produit p WHERE p.imageName = :imageName")
    , @NamedQuery(name = "Produit.findByUpdatedAt", query = "SELECT p FROM Produit p WHERE p.updatedAt = :updatedAt")
    , @NamedQuery(name = "Produit.findByTypeProd", query = "SELECT p FROM Produit p WHERE p.typeProd = :typeProd")
    , @NamedQuery(name = "Produit.findByCatProd", query = "SELECT p FROM Produit p WHERE p.catProd = :catProd")})
public class Produit implements Serializable {

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
    @Lob
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prix")
    private Double prix;
    @Basic(optional = false)
    @Column(name = "image_name")
    private String imageName;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "type_prod")
    private String typeProd;
    @Column(name = "cat_prod")
    private String catProd;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private FosUser userId;
    @JoinColumn(name = "categorie_id", referencedColumnName = "id")
    @ManyToOne
    private Categorie categorieId;
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne
    private Typeproduit typeId;
    
    
    
    
    private String userEmail ;
    private String type;
    private String categorie;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    

    public Produit() {
    }

    public Produit(Integer id) {
        this.id = id;
    }

    public Produit(Integer id, String nom, String description, Date date, String imageName, Date updatedAt,String type,String categorie) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date = date;
        this.imageName = imageName;
        this.updatedAt = updatedAt;
        this.type=type;
        this.categorie=categorie;
    }
   
    
    
    
        
    UserService us = new UserService();
      public Produit(Integer id,String nom, String description, Double prix,FosUser u ,String imageName,String type,String categorie) throws SQLException {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.imageName = imageName;
        this.userEmail=us.getUserById(u.getId()).getEmail();
        this.type=type;
        this.categorie=categorie;
        this.userId=u;
    }
    
 
    
      public Produit(String nom, String description, Double prix,FosUser u ,String imageName,String type,String categorie) throws SQLException {
        
          this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.imageName = imageName;
        this.userEmail=us.getUserById(u.getId()).getEmail();
        this.type=type;
        this.categorie=categorie;
        this.userId=u;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
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

    public String getTypeProd() {
        return typeProd;
    }

    public void setTypeProd(String typeProd) {
        this.typeProd = typeProd;
    }

    public String getCatProd() {
        return catProd;
    }

    public void setCatProd(String catProd) {
        this.catProd = catProd;
    }

    public FosUser getUserId() {
        return userId;
    }

    public void setUserId(FosUser userId) {
        this.userId = userId;
    }

    public Categorie getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Categorie categorieId) {
        this.categorieId = categorieId;
    }

    public Typeproduit getTypeId() {
        return typeId;
    }

    public void setTypeId(Typeproduit typeId) {
        this.typeId = typeId;
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
        if (!(object instanceof Produit)) {
            return false;
        }
        Produit other = (Produit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Produit[ id=" + id + " ]";
    }
    
}
