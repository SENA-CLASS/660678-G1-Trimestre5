/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.modelo.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c"),
    @NamedQuery(name = "Categoria.findByIdCategoria", query = "SELECT c FROM Categoria c WHERE c.idCategoria = :idCategoria"),
    @NamedQuery(name = "Categoria.findByNombre", query = "SELECT c FROM Categoria c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Categoria.findByActiva", query = "SELECT c FROM Categoria c WHERE c.activa = :activa"),
    @NamedQuery(name = "Categoria.findByFechaCreacion", query = "SELECT c FROM Categoria c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Categoria.findByUsuarioCreacion", query = "SELECT c FROM Categoria c WHERE c.usuarioCreacion = :usuarioCreacion"),
    @NamedQuery(name = "Categoria.findByFechaUltimaModificacion", query = "SELECT c FROM Categoria c WHERE c.fechaUltimaModificacion = :fechaUltimaModificacion"),
    @NamedQuery(name = "Categoria.findByUsuarioUltimaModificacion", query = "SELECT c FROM Categoria c WHERE c.usuarioUltimaModificacion = :usuarioUltimaModificacion"),
    @NamedQuery(name = "Categoria.findByPariente", query = "SELECT c FROM Categoria c WHERE c.pariente = :pariente")})
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CATEGORIA")
    private Integer idCategoria;
    @Size(max = 45)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ACTIVA")
    private Boolean activa;
    @Size(max = 45)
    @Column(name = "FECHA_CREACION")
    private String fechaCreacion;
    @Size(max = 45)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Size(max = 45)
    @Column(name = "FECHA_ULTIMA_MODIFICACION")
    private String fechaUltimaModificacion;
    @Size(max = 45)
    @Column(name = "USUARIO_ULTIMA_MODIFICACION")
    private String usuarioUltimaModificacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PARIENTE")
    private int pariente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoria")
    private Collection<Producto> productoCollection;

    public Categoria() {
    }

    public Categoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Categoria(Integer idCategoria, int pariente) {
        this.idCategoria = idCategoria;
        this.pariente = pariente;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public String getUsuarioUltimaModificacion() {
        return usuarioUltimaModificacion;
    }

    public void setUsuarioUltimaModificacion(String usuarioUltimaModificacion) {
        this.usuarioUltimaModificacion = usuarioUltimaModificacion;
    }

    public int getPariente() {
        return pariente;
    }

    public void setPariente(int pariente) {
        this.pariente = pariente;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCategoria != null ? idCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.idCategoria == null && other.idCategoria != null) || (this.idCategoria != null && !this.idCategoria.equals(other.idCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.Categoria[ idCategoria=" + idCategoria + " ]";
    }
    
}
