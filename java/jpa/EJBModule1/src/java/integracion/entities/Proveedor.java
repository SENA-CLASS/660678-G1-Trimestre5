/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p"),
    @NamedQuery(name = "Proveedor.findByTipoDocumento", query = "SELECT p FROM Proveedor p WHERE p.proveedorPK.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "Proveedor.findByNumDocumento", query = "SELECT p FROM Proveedor p WHERE p.proveedorPK.numDocumento = :numDocumento"),
    @NamedQuery(name = "Proveedor.findByNombreProveedor", query = "SELECT p FROM Proveedor p WHERE p.nombreProveedor = :nombreProveedor"),
    @NamedQuery(name = "Proveedor.findByTelefono", query = "SELECT p FROM Proveedor p WHERE p.telefono = :telefono"),
    @NamedQuery(name = "Proveedor.findByDireccion", query = "SELECT p FROM Proveedor p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Proveedor.findByCorreo", query = "SELECT p FROM Proveedor p WHERE p.correo = :correo"),
    @NamedQuery(name = "Proveedor.findByDomicilioIddomicilio", query = "SELECT p FROM Proveedor p WHERE p.domicilioIddomicilio = :domicilioIddomicilio")})
public class Proveedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProveedorPK proveedorPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NOMBRE_PROVEEDOR")
    private String nombreProveedor;
    @Size(max = 45)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 45)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 45)
    @Column(name = "CORREO")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "domicilio_iddomicilio")
    private int domicilioIddomicilio;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "proveedor")
    private DomicilioProvee domicilioProvee;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedor")
    private List<InventarioCompras> inventarioComprasList;

    public Proveedor() {
    }

    public Proveedor(ProveedorPK proveedorPK) {
        this.proveedorPK = proveedorPK;
    }

    public Proveedor(ProveedorPK proveedorPK, String nombreProveedor, int domicilioIddomicilio) {
        this.proveedorPK = proveedorPK;
        this.nombreProveedor = nombreProveedor;
        this.domicilioIddomicilio = domicilioIddomicilio;
    }

    public Proveedor(String tipoDocumento, String numDocumento) {
        this.proveedorPK = new ProveedorPK(tipoDocumento, numDocumento);
    }

    public ProveedorPK getProveedorPK() {
        return proveedorPK;
    }

    public void setProveedorPK(ProveedorPK proveedorPK) {
        this.proveedorPK = proveedorPK;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getDomicilioIddomicilio() {
        return domicilioIddomicilio;
    }

    public void setDomicilioIddomicilio(int domicilioIddomicilio) {
        this.domicilioIddomicilio = domicilioIddomicilio;
    }

    public DomicilioProvee getDomicilioProvee() {
        return domicilioProvee;
    }

    public void setDomicilioProvee(DomicilioProvee domicilioProvee) {
        this.domicilioProvee = domicilioProvee;
    }

    @XmlTransient
    public List<InventarioCompras> getInventarioComprasList() {
        return inventarioComprasList;
    }

    public void setInventarioComprasList(List<InventarioCompras> inventarioComprasList) {
        this.inventarioComprasList = inventarioComprasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proveedorPK != null ? proveedorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.proveedorPK == null && other.proveedorPK != null) || (this.proveedorPK != null && !this.proveedorPK.equals(other.proveedorPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.entities.Proveedor[ proveedorPK=" + proveedorPK + " ]";
    }
    
}
