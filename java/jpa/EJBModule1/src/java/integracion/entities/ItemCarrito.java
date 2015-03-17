/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.entities;

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
@Table(name = "item_carrito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemCarrito.findAll", query = "SELECT i FROM ItemCarrito i"),
    @NamedQuery(name = "ItemCarrito.findByCarritoComprasIdCarrito", query = "SELECT i FROM ItemCarrito i WHERE i.itemCarritoPK.carritoComprasIdCarrito = :carritoComprasIdCarrito"),
    @NamedQuery(name = "ItemCarrito.findByProductoIdProducto", query = "SELECT i FROM ItemCarrito i WHERE i.itemCarritoPK.productoIdProducto = :productoIdProducto"),
    @NamedQuery(name = "ItemCarrito.findByCantidad", query = "SELECT i FROM ItemCarrito i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "ItemCarrito.findByValorUnitario", query = "SELECT i FROM ItemCarrito i WHERE i.valorUnitario = :valorUnitario"),
    @NamedQuery(name = "ItemCarrito.findByValorTotal", query = "SELECT i FROM ItemCarrito i WHERE i.valorTotal = :valorTotal")})
public class ItemCarrito implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemCarritoPK itemCarritoPK;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_UNITARIO")
    private Double valorUnitario;
    @Column(name = "VALOR_TOTAL")
    private Double valorTotal;
    @JoinColumn(name = "PRODUCTO_ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumn(name = "CARRITO_COMPRAS_ID_CARRITO", referencedColumnName = "ID_CARRITO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CarritoCompras carritoCompras;

    public ItemCarrito() {
    }

    public ItemCarrito(ItemCarritoPK itemCarritoPK) {
        this.itemCarritoPK = itemCarritoPK;
    }

    public ItemCarrito(String carritoComprasIdCarrito, String productoIdProducto) {
        this.itemCarritoPK = new ItemCarritoPK(carritoComprasIdCarrito, productoIdProducto);
    }

    public ItemCarritoPK getItemCarritoPK() {
        return itemCarritoPK;
    }

    public void setItemCarritoPK(ItemCarritoPK itemCarritoPK) {
        this.itemCarritoPK = itemCarritoPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public CarritoCompras getCarritoCompras() {
        return carritoCompras;
    }

    public void setCarritoCompras(CarritoCompras carritoCompras) {
        this.carritoCompras = carritoCompras;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemCarritoPK != null ? itemCarritoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemCarrito)) {
            return false;
        }
        ItemCarrito other = (ItemCarrito) object;
        if ((this.itemCarritoPK == null && other.itemCarritoPK != null) || (this.itemCarritoPK != null && !this.itemCarritoPK.equals(other.itemCarritoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.entities.ItemCarrito[ itemCarritoPK=" + itemCarritoPK + " ]";
    }
    
}
