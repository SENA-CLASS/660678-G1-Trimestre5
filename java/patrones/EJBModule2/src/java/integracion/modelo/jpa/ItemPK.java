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
public class ItemPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PRODUCTO_ID_PRODUCTO")
    private String productoIdProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PEDIDO_ID_PEDIDO")
    private int pedidoIdPedido;

    public ItemPK() {
    }

    public ItemPK(String productoIdProducto, int pedidoIdPedido) {
        this.productoIdProducto = productoIdProducto;
        this.pedidoIdPedido = pedidoIdPedido;
    }

    public String getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(String productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }

    public int getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(int pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoIdProducto != null ? productoIdProducto.hashCode() : 0);
        hash += (int) pedidoIdPedido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemPK)) {
            return false;
        }
        ItemPK other = (ItemPK) object;
        if ((this.productoIdProducto == null && other.productoIdProducto != null) || (this.productoIdProducto != null && !this.productoIdProducto.equals(other.productoIdProducto))) {
            return false;
        }
        if (this.pedidoIdPedido != other.pedidoIdPedido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.ItemPK[ productoIdProducto=" + productoIdProducto + ", pedidoIdPedido=" + pedidoIdPedido + " ]";
    }
    
}
