/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hong
 */
@Entity
@Table(name = "FAVOURITE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Favourite.findAll", query = "SELECT f FROM Favourite f")
    , @NamedQuery(name = "Favourite.findByFavouriteId", query = "SELECT f FROM Favourite f WHERE f.favouriteId = :favouriteId")
    , @NamedQuery(name = "Favourite.findByProductId", query = "SELECT f FROM Favourite f WHERE f.productId = :productId and f.accountId = :accountId")
})
public class Favourite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FAVOURITE_ID")
    private Integer favouriteId;
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")
    @ManyToOne(optional = false)
    private Account accountId;
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    @ManyToOne(optional = false)
    private Product productId;

    public Favourite() {
    }

    public Favourite(Integer favouriteId) {
        this.favouriteId = favouriteId;
    }

    public Integer getFavouriteId() {
        return favouriteId;
    }

    public void setFavouriteId(Integer favouriteId) {
        this.favouriteId = favouriteId;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (favouriteId != null ? favouriteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Favourite)) {
            return false;
        }
        Favourite other = (Favourite) object;
        if ((this.favouriteId == null && other.favouriteId != null) || (this.favouriteId != null && !this.favouriteId.equals(other.favouriteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.Favourite[ favouriteId=" + favouriteId + " ]";
    }
    
}
