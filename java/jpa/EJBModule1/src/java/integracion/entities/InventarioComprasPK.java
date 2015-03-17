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
public class InventarioComprasPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_INVENTARIO")
    private int idInventario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PRODUCTO_ID_PRODUCTO")
    private String productoIdProducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PROVEEDOR_TIPO_DOCUMENTO")
    private String proveedorTipoDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PROVEEDOR_NUM_DOCUMENTO")
    private String proveedorNumDocumento;

    public InventarioComprasPK() {
    }

    public InventarioComprasPK(int idInventario, String productoIdProducto, String proveedorTipoDocumento, String proveedorNumDocumento) {
        this.idInventario = idInventario;
        this.productoIdProducto = productoIdProducto;
        this.proveedorTipoDocumento = proveedorTipoDocumento;
        this.proveedorNumDocumento = proveedorNumDocumento;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public String getProductoIdProducto() {
        return productoIdProducto;
    }

    public void setProductoIdProducto(String productoIdProducto) {
        this.productoIdProducto = productoIdProducto;
    }

    public String getProveedorTipoDocumento() {
        return proveedorTipoDocumento;
    }

    public void setProveedorTipoDocumento(String proveedorTipoDocumento) {
        this.proveedorTipoDocumento = proveedorTipoDocumento;
    }

    public String getProveedorNumDocumento() {
        return proveedorNumDocumento;
    }

    public void setProveedorNumDocumento(String proveedorNumDocumento) {
        this.proveedorNumDocumento = proveedorNumDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idInventario;
        hash += (productoIdProducto != null ? productoIdProducto.hashCode() : 0);
        hash += (proveedorTipoDocumento != null ? proveedorTipoDocumento.hashCode() : 0);
        hash += (proveedorNumDocumento != null ? proveedorNumDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventarioComprasPK)) {
            return false;
        }
        InventarioComprasPK other = (InventarioComprasPK) object;
        if (this.idInventario != other.idInventario) {
            return false;
        }
        if ((this.productoIdProducto == null && other.productoIdProducto != null) || (this.productoIdProducto != null && !this.productoIdProducto.equals(other.productoIdProducto))) {
            return false;
        }
        if ((this.proveedorTipoDocumento == null && other.proveedorTipoDocumento != null) || (this.proveedorTipoDocumento != null && !this.proveedorTipoDocumento.equals(other.proveedorTipoDocumento))) {
            return false;
        }
        if ((this.proveedorNumDocumento == null && other.proveedorNumDocumento != null) || (this.proveedorNumDocumento != null && !this.proveedorNumDocumento.equals(other.proveedorNumDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.entities.InventarioComprasPK[ idInventario=" + idInventario + ", productoIdProducto=" + productoIdProducto + ", proveedorTipoDocumento=" + proveedorTipoDocumento + ", proveedorNumDocumento=" + proveedorNumDocumento + " ]";
    }
    
}
