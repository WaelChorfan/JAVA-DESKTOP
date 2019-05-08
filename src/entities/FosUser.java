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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "fos_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FosUser.findAll", query = "SELECT f FROM FosUser f")
    , @NamedQuery(name = "FosUser.findById", query = "SELECT f FROM FosUser f WHERE f.id = :id")
    , @NamedQuery(name = "FosUser.findByUsername", query = "SELECT f FROM FosUser f WHERE f.username = :username")
    , @NamedQuery(name = "FosUser.findByUsernameCanonical", query = "SELECT f FROM FosUser f WHERE f.usernameCanonical = :usernameCanonical")
    , @NamedQuery(name = "FosUser.findByEmail", query = "SELECT f FROM FosUser f WHERE f.email = :email")
    , @NamedQuery(name = "FosUser.findByEmailCanonical", query = "SELECT f FROM FosUser f WHERE f.emailCanonical = :emailCanonical")
    , @NamedQuery(name = "FosUser.findByEnabled", query = "SELECT f FROM FosUser f WHERE f.enabled = :enabled")
    , @NamedQuery(name = "FosUser.findBySalt", query = "SELECT f FROM FosUser f WHERE f.salt = :salt")
    , @NamedQuery(name = "FosUser.findByPassword", query = "SELECT f FROM FosUser f WHERE f.password = :password")
    , @NamedQuery(name = "FosUser.findByLastLogin", query = "SELECT f FROM FosUser f WHERE f.lastLogin = :lastLogin")
    , @NamedQuery(name = "FosUser.findByConfirmationToken", query = "SELECT f FROM FosUser f WHERE f.confirmationToken = :confirmationToken")
    , @NamedQuery(name = "FosUser.findByPasswordRequestedAt", query = "SELECT f FROM FosUser f WHERE f.passwordRequestedAt = :passwordRequestedAt")
    , @NamedQuery(name = "FosUser.findByImageName", query = "SELECT f FROM FosUser f WHERE f.imageName = :imageName")})
public class FosUser implements Serializable {

    @OneToMany(mappedBy = "userId")
    private Collection<Produit> produitCollection;

    @OneToMany(mappedBy = "recepteurId")
    private Collection<Message> messageCollection;
    @OneToMany(mappedBy = "userId")
    private Collection<Message> messageCollection1;
    @OneToMany(mappedBy = "emeteurId")
    private Collection<Message> messageCollection2;

    @Column(name = "role")
    private String role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Commentaire> commentaireCollection;
  

    @Column(name = "enabled")
    private Boolean enabled;
    

    @OneToMany(mappedBy = "recepteurId")
    private Collection<Ticket> ticketCollection;
    @OneToMany(mappedBy = "emeteurId")
    private Collection<Ticket> ticketCollection1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "username_canonical")
    private String usernameCanonical;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "email_canonical")
    private String emailCanonical;
    @Column(name = "salt")
    private String salt;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Column(name = "confirmation_token")
    private String confirmationToken;
    @Column(name = "password_requested_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordRequestedAt;
    @Basic(optional = false)
    @Lob
    @Column(name = "roles")
    private String roles;
    @Column(name = "image_name")
    private String imageName;

    public FosUser() {
    }

    public FosUser(Integer id) {
        this.id = id;
    }

    public FosUser(Integer id, String username, String usernameCanonical, String email, String emailCanonical, boolean enabled, String password, String roles) {
        this.id = id;
        this.username = username;
        this.usernameCanonical = usernameCanonical;
        this.email = email;
        this.emailCanonical = emailCanonical;
        this.enabled = enabled;
        this.password = password;
        this.roles = roles;
    }

    
     public FosUser(Integer id, String username, String email,  boolean enabled, String role,String image_name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
        this.imageName=image_name;
    }
    
      
     public FosUser(Integer id, String username, String email,  boolean enabled, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.role = role;
    }
      public FosUser(String _username, String _email, String _password, boolean _enabled, String _role,String image_name) {
        this.username = _username;
        this.email = _email;
        this.password=_password;
        this.enabled = _enabled;
        this.role = _role;
        this.imageName=image_name;
    }
     public FosUser(String _username, String _email, String _password,  String _role,String image_name) {
        this.username = _username;
        this.email = _email;
        this.password=_password;
        this.role = _role;
        this.imageName=image_name;
    }
      
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameCanonical() {
        return usernameCanonical;
    }

    public void setUsernameCanonical(String usernameCanonical) {
        this.usernameCanonical = usernameCanonical;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCanonical() {
        return emailCanonical;
    }

    public void setEmailCanonical(String emailCanonical) {
        this.emailCanonical = emailCanonical;
    }


    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getPasswordRequestedAt() {
        return passwordRequestedAt;
    }

    public void setPasswordRequestedAt(Date passwordRequestedAt) {
        this.passwordRequestedAt = passwordRequestedAt;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
        if (!(object instanceof FosUser)) {
            return false;
        }
        FosUser other = (FosUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FosUser[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Ticket> getTicketCollection() {
        return ticketCollection;
    }

    public void setTicketCollection(Collection<Ticket> ticketCollection) {
        this.ticketCollection = ticketCollection;
    }

    @XmlTransient
    public Collection<Ticket> getTicketCollection1() {
        return ticketCollection1;
    }

    public void setTicketCollection1(Collection<Ticket> ticketCollection1) {
        this.ticketCollection1 = ticketCollection1;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @XmlTransient
    public Collection<Commentaire> getCommentaireCollection() {
        return commentaireCollection;
    }

    public void setCommentaireCollection(Collection<Commentaire> commentaireCollection) {
        this.commentaireCollection = commentaireCollection;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection1() {
        return messageCollection1;
    }

    public void setMessageCollection1(Collection<Message> messageCollection1) {
        this.messageCollection1 = messageCollection1;
    }

    @XmlTransient
    public Collection<Message> getMessageCollection2() {
        return messageCollection2;
    }

    public void setMessageCollection2(Collection<Message> messageCollection2) {
        this.messageCollection2 = messageCollection2;
    }

    @XmlTransient
    public Collection<Produit> getProduitCollection() {
        return produitCollection;
    }

    public void setProduitCollection(Collection<Produit> produitCollection) {
        this.produitCollection = produitCollection;
    }

    
}
