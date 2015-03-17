/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.onlineshop.integracion;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p"),
    @NamedQuery(name = "Pedido.findByFacturaIdFactura", query = "SELECT p FROM Pedido p WHERE p.facturaIdFactura = :facturaIdFactura"),
    @NamedQuery(name = "Pedido.findByTotal", query = "SELECT p FROM Pedido p WHERE p.total = :total"),
    @NamedQuery(name = "Pedido.findBySubtotal", query = "SELECT p FROM Pedido p WHERE p.subtotal = :subtotal"),
    @NamedQuery(name = "Pedido.findByImpuestos", query = "SELECT p FROM Pedido p WHERE p.impuestos = :impuestos")})
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "FACTURA_ID_FACTURA")
    private Integer facturaIdFactura;
    @Basic(optional = false)
    @Column(name = "TOTAL")
    private double total;
    @Basic(optional = false)
    @Column(name = "SUBTOTAL")
    private double subtotal;
    @Basic(optional = false)
    @Column(name = "IMPUESTOS")
    private double impuestos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Collection<Item> itemCollection;
    @JoinColumn(name = "FACTURA_ID_FACTURA", referencedColumnName = "ID_FACTURA", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Factura factura;

    public Pedido() {
    }

    public Pedido(Integer facturaIdFactura) {
        this.facturaIdFactura = facturaIdFactura;
    }

    public Pedido(Integer facturaIdFactura, double total, double subtotal, double impuestos) {
        this.facturaIdFactura = facturaIdFactura;
        this.total = total;
        this.subtotal = subtotal;
        this.impuestos = impuestos;
    }

    public Integer getFacturaIdFactura() {
        return facturaIdFactura;
    }

    public void setFacturaIdFactura(Integer facturaIdFactura) {
        this.facturaIdFactura = facturaIdFactura;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(double impuestos) {
        this.impuestos = impuestos;
    }

    @XmlTransient
    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facturaIdFactura != null ? facturaIdFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.facturaIdFactura == null && other.facturaIdFactura != null) || (this.facturaIdFactura != null && !this.facturaIdFactura.equals(other.facturaIdFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sena.onlineshop.integracion.Pedido[ facturaIdFactura=" + facturaIdFactura + " ]";
    }
    
}
