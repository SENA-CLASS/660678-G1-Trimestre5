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
import javax.persistence.JoinColumns;
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
@Table(name = "inventario_compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InventarioCompras.findAll", query = "SELECT i FROM InventarioCompras i"),
    @NamedQuery(name = "InventarioCompras.findByIdInventario", query = "SELECT i FROM InventarioCompras i WHERE i.inventarioComprasPK.idInventario = :idInventario"),
    @NamedQuery(name = "InventarioCompras.findByProductoIdProducto", query = "SELECT i FROM InventarioCompras i WHERE i.inventarioComprasPK.productoIdProducto = :productoIdProducto"),
    @NamedQuery(name = "InventarioCompras.findByCanProComprado", query = "SELECT i FROM InventarioCompras i WHERE i.canProComprado = :canProComprado"),
    @NamedQuery(name = "InventarioCompras.findByFechaCompra", query = "SELECT i FROM InventarioCompras i WHERE i.fechaCompra = :fechaCompra"),
    @NamedQuery(name = "InventarioCompras.findByProveedorTipoDocumento", query = "SELECT i FROM InventarioCompras i WHERE i.inventarioComprasPK.proveedorTipoDocumento = :proveedorTipoDocumento"),
    @NamedQuery(name = "InventarioCompras.findByProveedorNumDocumento", query = "SELECT i FROM InventarioCompras i WHERE i.inventarioComprasPK.proveedorNumDocumento = :proveedorNumDocumento")})
public class InventarioCompras implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InventarioComprasPK inventarioComprasPK;
    @Column(name = "CAN_PRO_COMPRADO")
    private Integer canProComprado;
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

    public InventarioCompras() {
    }

    public InventarioCompras(InventarioComprasPK inventarioComprasPK) {
        this.inventarioComprasPK = inventarioComprasPK;
    }

    public InventarioCompras(int idInventario, String productoIdProducto, String proveedorTipoDocumento, String proveedorNumDocumento) {
        this.inventarioComprasPK = new InventarioComprasPK(idInventario, productoIdProducto, proveedorTipoDocumento, proveedorNumDocumento);
    }

    public InventarioComprasPK getInventarioComprasPK() {
        return inventarioComprasPK;
    }

    public void setInventarioComprasPK(InventarioComprasPK inventarioComprasPK) {
        this.inventarioComprasPK = inventarioComprasPK;
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
        hash += (inventarioComprasPK != null ? inventarioComprasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InventarioCompras)) {
            return false;
        }
        InventarioCompras other = (InventarioCompras) object;
        if ((this.inventarioComprasPK == null && other.inventarioComprasPK != null) || (this.inventarioComprasPK != null && !this.inventarioComprasPK.equals(other.inventarioComprasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sena.onlineshop.integracion.InventarioCompras[ inventarioComprasPK=" + inventarioComprasPK + " ]";
    }
    
}
