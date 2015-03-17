/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.onlineshop.integracion;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findByProductoIdProducto", query = "SELECT i FROM Item i WHERE i.itemPK.productoIdProducto = :productoIdProducto"),
    @NamedQuery(name = "Item.findByCantidad", query = "SELECT i FROM Item i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "Item.findByCostoUnitario", query = "SELECT i FROM Item i WHERE i.costoUnitario = :costoUnitario"),
    @NamedQuery(name = "Item.findByCostoTotal", query = "SELECT i FROM Item i WHERE i.costoTotal = :costoTotal"),
    @NamedQuery(name = "Item.findByPedidoFacturaIdFactura", query = "SELECT i FROM Item i WHERE i.itemPK.pedidoFacturaIdFactura = :pedidoFacturaIdFactura")})
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemPK itemPK;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Column(name = "COSTO_UNITARIO")
    private String costoUnitario;
    @Column(name = "COSTO_TOTAL")
    private String costoTotal;
    @JoinColumn(name = "PEDIDO_FACTURA_ID_FACTURA", referencedColumnName = "FACTURA_ID_FACTURA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pedido pedido;
    @JoinColumn(name = "PRODUCTO_ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public Item() {
    }

    public Item(ItemPK itemPK) {
        this.itemPK = itemPK;
    }

    public Item(String productoIdProducto, int pedidoFacturaIdFactura) {
        this.itemPK = new ItemPK(productoIdProducto, pedidoFacturaIdFactura);
    }

    public ItemPK getItemPK() {
        return itemPK;
    }

    public void setItemPK(ItemPK itemPK) {
        this.itemPK = itemPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(String costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public String getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(String costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemPK != null ? itemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.itemPK == null && other.itemPK != null) || (this.itemPK != null && !this.itemPK.equals(other.itemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sena.onlineshop.integracion.Item[ itemPK=" + itemPK + " ]";
    }
    
}
