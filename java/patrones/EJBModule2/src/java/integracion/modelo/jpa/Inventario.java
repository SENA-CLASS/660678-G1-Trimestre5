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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "inventario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i"),
    @NamedQuery(name = "Inventario.findByIdInventario", query = "SELECT i FROM Inventario i WHERE i.inventarioPK.idInventario = :idInventario"),
    @NamedQuery(name = "Inventario.findByCantidad", query = "SELECT i FROM Inventario i WHERE i.cantidad = :cantidad"),
    @NamedQuery(name = "Inventario.findByProductoIdProducto", query = "SELECT i FROM Inventario i WHERE i.inventarioPK.productoIdProducto = :productoIdProducto"),
    @NamedQuery(name = "Inventario.findByCanProComprado", query = "SELECT i FROM Inventario i WHERE i.canProComprado = :canProComprado"),
    @NamedQuery(name = "Inventario.findByFechaCompra", query = "SELECT i FROM Inventario i WHERE i.fechaCompra = :fechaCompra"),
    @NamedQuery(name = "Inventario.findByProveedorTipoDocumento", query = "SELECT i FROM Inventario i WHERE i.inventarioPK.proveedorTipoDocumento = :proveedorTipoDocumento"),
    @NamedQuery(name = "Inventario.findByProveedorNumDocumento", query = "SELECT i FROM Inventario i WHERE i.inventarioPK.proveedorNumDocumento = :proveedorNumDocumento")})
public class Inventario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InventarioPK inventarioPK;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    @Column(name = "CAN_PRO_COMPRADO")
    private Integer canProComprado;
    @Size(max = 45)
    @Column(name = "FECHA_COMPRA")
    private String fechaCompra;
    @JoinColumn(name = "PRODUCTO_ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @JoinColumns({
        @JoinColumn(name = "PROVEEDOR_TIPO_DOCUMENTO", referencedColumnName = "TIPO_DOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "PROVEEDOR_NUM_DOCUMENTO", referencedColumnName = "NUM_DOCUMENTO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Proveedor proveedor;

    public Inventario() {
    }

    public Inventario(InventarioPK inventarioPK) {
        this.inventarioPK = inventarioPK;
    }

    public Inventario(int idInventario, String productoIdProducto, String proveedorTipoDocumento, String proveedorNumDocumento) {
        this.inventarioPK = new InventarioPK(idInventario, productoIdProducto, proveedorTipoDocumento, proveedorNumDocumento);
    }

    public InventarioPK getInventarioPK() {
        return inventarioPK;
    }

    public void setInventarioPK(InventarioPK inventarioPK) {
        this.inventarioPK = inventarioPK;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCanProComprado() {
        return canProComprado;
    }

    public void setCanProComprado(Integer canProComprado) {
        this.canProComprado = canProComprado;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inventarioPK != null ? inventarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventario)) {
            return false;
        }
        Inventario other = (Inventario) object;
        if ((this.inventarioPK == null && other.inventarioPK != null) || (this.inventarioPK != null && !this.inventarioPK.equals(other.inventarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.Inventario[ inventarioPK=" + inventarioPK + " ]";
    }
    
}
