/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.modelo.jpa;

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
@Table(name = "carrito_compras_has_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarritoComprasHasProducto.findAll", query = "SELECT c FROM CarritoComprasHasProducto c"),
    @NamedQuery(name = "CarritoComprasHasProducto.findByCarritoComprasIdCarrito", query = "SELECT c FROM CarritoComprasHasProducto c WHERE c.carritoComprasHasProductoPK.carritoComprasIdCarrito = :carritoComprasIdCarrito"),
    @NamedQuery(name = "CarritoComprasHasProducto.findByProductoIdProducto", query = "SELECT c FROM CarritoComprasHasProducto c WHERE c.carritoComprasHasProductoPK.productoIdProducto = :productoIdProducto"),
    @NamedQuery(name = "CarritoComprasHasProducto.findByCantidad", query = "SELECT c FROM CarritoComprasHasProducto c WHERE c.cantidad = :cantidad"),
    @NamedQuery(name = "CarritoComprasHasProducto.findByValorUnitario", query = "SELECT c FROM CarritoComprasHasProducto c WHERE c.valorUnitario = :valorUnitario"),
    @NamedQuery(name = "CarritoComprasHasProducto.findByValorTotal", query = "SELECT c FROM CarritoComprasHasProducto c WHERE c.valorTotal = :valorTotal")})
public class CarritoComprasHasProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CarritoComprasHasProductoPK carritoComprasHasProductoPK;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALOR_UNITARIO")
    private Double valorUnitario;
    @Column(name = "VALOR_TOTAL")
    private Double valorTotal;
    @JoinColumn(name = "CARRITO_COMPRAS_ID_CARRITO", referencedColumnName = "ID_CARRITO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CarritoCompras carritoCompras;
    @JoinColumn(name = "PRODUCTO_ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public CarritoComprasHasProducto() {
    }

    public CarritoComprasHasProducto(CarritoComprasHasProductoPK carritoComprasHasProductoPK) {
        this.carritoComprasHasProductoPK = carritoComprasHasProductoPK;
    }

    public CarritoComprasHasProducto(int carritoComprasIdCarrito, String productoIdProducto) {
        this.carritoComprasHasProductoPK = new CarritoComprasHasProductoPK(carritoComprasIdCarrito, productoIdProducto);
    }

    public CarritoComprasHasProductoPK getCarritoComprasHasProductoPK() {
        return carritoComprasHasProductoPK;
    }

    public void setCarritoComprasHasProductoPK(CarritoComprasHasProductoPK carritoComprasHasProductoPK) {
        this.carritoComprasHasProductoPK = carritoComprasHasProductoPK;
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

    public CarritoCompras getCarritoCompras() {
        return carritoCompras;
    }

    public void setCarritoCompras(CarritoCompras carritoCompras) {
        this.carritoCompras = carritoCompras;
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
        hash += (carritoComprasHasProductoPK != null ? carritoComprasHasProductoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarritoComprasHasProducto)) {
            return false;
        }
        CarritoComprasHasProducto other = (CarritoComprasHasProducto) object;
        if ((this.carritoComprasHasProductoPK == null && other.carritoComprasHasProductoPK != null) || (this.carritoComprasHasProductoPK != null && !this.carritoComprasHasProductoPK.equals(other.carritoComprasHasProductoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.CarritoComprasHasProducto[ carritoComprasHasProductoPK=" + carritoComprasHasProductoPK + " ]";
    }
    
}
