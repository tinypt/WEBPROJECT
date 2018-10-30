/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author tinypt
 */
@Entity
@Table(name = "HISTORY_ORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoryOrder.findAll", query = "SELECT h FROM HistoryOrder h")
    , @NamedQuery(name = "HistoryOrder.findByHistoryId", query = "SELECT h FROM HistoryOrder h WHERE h.historyId = :historyId")
    , @NamedQuery(name = "HistoryOrder.findByTime", query = "SELECT h FROM HistoryOrder h WHERE h.time = :time")
    , @NamedQuery(name = "HistoryOrder.findByTotalprice", query = "SELECT h FROM HistoryOrder h WHERE h.totalprice = :totalprice")})
public class HistoryOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "HISTORY_ID")
    private Integer historyId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTALPRICE")
    private int totalprice;
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")
    @ManyToOne(optional = false)
    private Account accountId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historyId")
    private List<OrderDetail> orderDetailList;

    public HistoryOrder() {
    }

    public HistoryOrder(Integer historyId) {
        this.historyId = historyId;
    }

    public HistoryOrder(Integer historyId, Date time, int totalprice) {
        this.historyId = historyId;
        this.time = time;
        this.totalprice = totalprice;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    @XmlTransient
    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historyId != null ? historyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoryOrder)) {
            return false;
        }
        HistoryOrder other = (HistoryOrder) object;
        if ((this.historyId == null && other.historyId != null) || (this.historyId != null && !this.historyId.equals(other.historyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.model.HistoryOrder[ historyId=" + historyId + " ]";
    }
    
}
