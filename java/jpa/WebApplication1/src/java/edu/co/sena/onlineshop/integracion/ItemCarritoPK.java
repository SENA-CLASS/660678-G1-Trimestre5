/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.onlineshop.integracion;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Enrique Moreno
 */
@Embeddable
public class ItemCarritoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "CARRITO_COMPRAS_ID_CARRITO")
    private String carritoComprasIdCarrito;
    @Basic(optional = false)
    @Column(name = "PRODUCTO_ID_PRODUCTO")
    private String productoIdProducto;

    public ItemCarritoPK() {
    }

    public ItemCarritoPK(String carritoComprasIdCarrito, String productoIdProducto) {
        this.carritoComprasIdCarrito = carritoComprasIdCarrito;
        this.productoIdProducto = productoIdProducto;
    }

    public String getCarritoComprasIdCarrito() {
        return carritoComprasIdCarrito;
    }

    public void setCarritoComprasIdCarrito(String carritoComprasIdCarrito) {
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
        hash += (carritoComprasIdCarrito != null ? carritoComprasIdCarrito.hashCode() : 0);
        hash += (productoIdProducto != null ? productoIdProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemCarritoPK)) {
            return false;
        }
        ItemCarritoPK other = (ItemCarritoPK) object;
        if ((this.carritoComprasIdCarrito == null && other.carritoComprasIdCarrito != null) || (this.carritoComprasIdCarrito != null && !this.carritoComprasIdCarrito.equals(other.carritoComprasIdCarrito))) {
            return false;
        }
        if ((this.productoIdProducto == null && other.productoIdProducto != null) || (this.productoIdProducto != null && !this.productoIdProducto.equals(other.productoIdProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sena.onlineshop.integracion.ItemCarritoPK[ carritoComprasIdCarrito=" + carritoComprasIdCarrito + ", productoIdProducto=" + productoIdProducto + " ]";
    }
    
}
