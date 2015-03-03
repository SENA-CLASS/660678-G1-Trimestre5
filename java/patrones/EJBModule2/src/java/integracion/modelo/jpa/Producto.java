/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.modelo.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.productoPK.idProducto = :idProducto"),
    @NamedQuery(name = "Producto.findByNombreProducto", query = "SELECT p FROM Producto p WHERE p.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "Producto.findByPrecio", query = "SELECT p FROM Producto p WHERE p.precio = :precio"),
    @NamedQuery(name = "Producto.findByCategoriaIdCategoria", query = "SELECT p FROM Producto p WHERE p.productoPK.categoriaIdCategoria = :categoriaIdCategoria"),
    @NamedQuery(name = "Producto.findByCatalogoIdCatalogo", query = "SELECT p FROM Producto p WHERE p.productoPK.catalogoIdCatalogo = :catalogoIdCatalogo"),
    @NamedQuery(name = "Producto.findByCantidad", query = "SELECT p FROM Producto p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "Producto.findByActivo", query = "SELECT p FROM Producto p WHERE p.activo = :activo"),
    @NamedQuery(name = "Producto.findByFechaCreacion", query = "SELECT p FROM Producto p WHERE p.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Producto.findByUsuarioCreacion", query = "SELECT p FROM Producto p WHERE p.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "Producto.findByFechaUltimaModificacion", query = "SELECT p FROM Producto p WHERE p.fechaUltimaModificacion = :fechaUltimaModificacion"),
    @NamedQuery(name = "Producto.findByUsuarioUltimaModificacion", query = "SELECT p FROM Producto p WHERE p.usuarioUltimaModificacion = :usuarioUltimaModificacion")})
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoPK productoPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NOMBRE_PRODUCTO")
    private String nombreProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRECIO")
    private double precio;
    @Lob
    @Column(name = "IMAGEN")
    private byte[] imagen;
    @Size(max = 45)
    @Column(name = "CANTIDAD")
    private String cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVO")
    private boolean activo;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Size(max = 45)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_ULTIMA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUltimaModificacion;
    @Size(max = 45)
    @Column(name = "USUARIO_ULTIMA_MODIFICACION")
    private String usuarioUltimaModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<Item> itemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<CarritoComprasHasProducto> carritoComprasHasProductoCollection;
    @JoinColumn(name = "CATALOGO_ID_CATALOGO", referencedColumnName = "ID_CATALOGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Catalogo catalogo;
    @JoinColumn(name = "CATEGORIA_ID_CATEGORIA", referencedColumnName = "ID_CATEGORIA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Categoria categoria;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto")
    private Collection<Inventario> inventarioCollection;

    public Producto() {
    }

    public Producto(ProductoPK productoPK) {
        this.productoPK = productoPK;
    }

    public Producto(ProductoPK productoPK, String nombreProducto, double precio, boolean activo) {
        this.productoPK = productoPK;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.activo = activo;
    }

    public Producto(String idProducto, int categoriaIdCategoria, int catalogoIdCatalogo) {
        this.productoPK = new ProductoPK(idProducto, categoriaIdCategoria, catalogoIdCatalogo);
    }

    public ProductoPK getProductoPK() {
        return productoPK;
    }

    public void setProductoPK(ProductoPK productoPK) {
        this.productoPK = productoPK;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
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

    @XmlTransient
    public Collection<CarritoComprasHasProducto> getCarritoComprasHasProductoCollection() {
        return carritoComprasHasProductoCollection;
    }

    public void setCarritoComprasHasProductoCollection(Collection<CarritoComprasHasProducto> carritoComprasHasProductoCollection) {
        this.carritoComprasHasProductoCollection = carritoComprasHasProductoCollection;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @XmlTransient
    public Collection<Inventario> getInventarioCollection() {
        return inventarioCollection;
    }

    public void setInventarioCollection(Collection<Inventario> inventarioCollection) {
        this.inventarioCollection = inventarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoPK != null ? productoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.productoPK == null && other.productoPK != null) || (this.productoPK != null && !this.productoPK.equals(other.productoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.Producto[ productoPK=" + productoPK + " ]";
    }
    
}
