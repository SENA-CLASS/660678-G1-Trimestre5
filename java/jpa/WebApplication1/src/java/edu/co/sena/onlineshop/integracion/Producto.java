/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.onlineshop.integracion;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto"),
    @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByCantidad", query = "SELECT p FROM Producto p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "Producto.findByActivo", query = "SELECT p FROM Producto p WHERE p.activo = :activo"),
    @NamedQuery(name = "Producto.findByFechaCreacion", query = "SELECT p FROM Producto p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Producto.findByUsuarioCreacion", query = "SELECT p FROM Producto p WHERE p.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "Producto.findByFechaUltimaModificacion", query = "SELECT p FROM Producto p WHERE p.fechaUltimaModificacion = :fechaUltimaModificacion"),
    @NamedQuery(name = "Producto.findByUsuarioUltimaModificacion", query = "SELECT p FROM Producto p WHERE p.usuarioUltimaModificacion = :usuarioUltimaModificacion")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PRODUCTO")
    private String idProducto;
    @Basic(optional = false)
    @Column(name = "NOMBRE_PRODUCTO")
    private String nombreProducto;
    @Basic(optional = false)
    @Column(name = "PRECIO")
    private double precio;
    @Lob
    @Column(name = "IMAGEN")
    private byte[] imagen;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CANTIDAD")
    private Float cantidad;
    @Basic(optional = false)
    @Column(name = "ACTIVO")
    private boolean activo;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_ULTIMA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaModificacion;
    @Column(name = "USUARIO_ULTIMA_MODIFICACION")
    private String usuarioUltimaModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<Item> itemCollection;
    @JoinColumn(name = "CATALOGO_ID_CATALOGO", referencedColumnName = "ID_CATALOGO")
    @ManyToOne(optional = false)
    private Catalogo catalogoIdCatalogo;
    @JoinColumn(name = "CATEGORIA_ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA")
    @ManyToOne(optional = false)
    private Categoria categoriaIdCategoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<ItemCarrito> itemCarritoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<InventarioCompras> inventarioComprasCollection;

    public Producto() {
    }

    public Producto(String idProducto) {
        this.idProducto = idProducto;
    }

    public Producto(String idProducto, String nombreProducto, double precio, boolean activo) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.activo = activo;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(Date fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public String getUsuarioUltimaModificacion() {
        return usuarioUltimaModificacion;
    }

    public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
        this.usuarioUltimaModificacion = usuarioUltimaModificacion;
    }

    @XmlTransient
    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    public Catalogo getCatalogoIdCatalogo() {
        return catalogoIdCatalogo;
    }

    public void setCatalogoIdCatalogo(Catalogo catalogoIdCatalogo) {
        this.catalogoIdCatalogo = catalogoIdCatalogo;
    }

    public Categoria getCategoriaIdCategoria() {
        return categoriaIdCategoria;
    }

    public void setCategoriaIdCategoria(Categoria categoriaIdCategoria) {
        this.categoriaIdCategoria = categoriaIdCategoria;
    }

    @XmlTransient
    public Collection<ItemCarrito> getItemCarritoCollection() {
        return itemCarritoCollection;
    }

    public void setItemCarritoCollection(Collection<ItemCarrito> itemCarritoCollection) {
        this.itemCarritoCollection = itemCarritoCollection;
    }

    @XmlTransient
    public Collection<InventarioCompras> getInventarioComprasCollection() {
        return inventarioComprasCollection;
    }

    public void setInventarioComprasCollection(Collection<InventarioCompras> inventarioComprasCollection) {
        this.inventarioComprasCollection = inventarioComprasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sena.onlineshop.integracion.Producto[ idProducto=" + idProducto + " ]";
    }
    
}
