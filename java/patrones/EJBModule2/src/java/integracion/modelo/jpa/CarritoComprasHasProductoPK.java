/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.modelo.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Enrique Moreno
 */
@Embeddable
public class CarritoComprasHasProductoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "CARRITO_COMPRAS_ID_CARRITO")
    private int carritoComprasIdCarrito;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PRODUCTO_ID_PRODUCTO")
    private String productoIdProducto;

    public CarritoComprasHasProductoPK() {
    }

    public CarritoComprasHasProductoPK(int carritoComprasIdCarrito, String productoIdProducto) {
        this.carritoComprasIdCarrito = carritoComprasIdCarrito;
        this.productoIdProducto = productoIdProducto;
    }

    public int getCarritoComprasIdCarrito() {
        return carritoComprasIdCarrito;
    }

    public void setCarritoComprasIdCarrito(int carritoComprasIdCarrito) {
        this.carritoComprasIdCarrito = carritoComprasIdCarrito;
    }

    public String getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(String productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) carritoComprasIdCarrito;
        hash += (productoIdProducto != null ? productoIdProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CarritoComprasHasProductoPK)) {
            return false;
        }
        CarritoComprasHasProductoPK other = (CarritoComprasHasProductoPK) object;
        if (this.carritoComprasIdCarrito != other.carritoComprasIdCarrito) {
            return false;
        }
        if ((this.productoIdProducto == null && other.productoIdProducto != null) || (this.productoIdProducto != null && !this.productoIdProducto.equals(other.productoIdProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.CarritoComprasHasProductoPK[ carritoComprasIdCarrito=" + carritoComprasIdCarrito + ", productoIdProducto=" + productoIdProducto + " ]";
    }
    
}
