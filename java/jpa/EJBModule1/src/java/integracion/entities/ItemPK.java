/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.entities;

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
    @Column(name = "PEDIDO_FACTURA_ID_FACTURA")
    private int pedidoFacturaIdFactura;

    public ItemPK() {
    }

    public ItemPK(String productoIdProducto, int pedidoFacturaIdFactura) {
        this.productoIdProducto = productoIdProducto;
        this.pedidoFacturaIdFactura = pedidoFacturaIdFactura;
    }

    public String getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(String productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }

    public int getPedidoFacturaIdFactura() {
        return pedidoFacturaIdFactura;
    }

    public void setPedidoFacturaIdFactura(int pedidoFacturaIdFactura) {
        this.pedidoFacturaIdFactura = pedidoFacturaIdFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoIdProducto != null ? productoIdProducto.hashCode() : 0);
        hash += (int) pedidoFacturaIdFactura;
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
        if (this.pedidoFacturaIdFactura != other.pedidoFacturaIdFactura) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.entities.ItemPK[ productoIdProducto=" + productoIdProducto + ", pedidoFacturaIdFactura=" + pedidoFacturaIdFactura + " ]";
    }
    
}
