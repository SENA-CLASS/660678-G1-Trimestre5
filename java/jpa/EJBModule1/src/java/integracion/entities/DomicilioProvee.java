/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "domicilio_provee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DomicilioProvee.findAll", query = "SELECT d FROM DomicilioProvee d"),
    @NamedQuery(name = "DomicilioProvee.findByDireccion", query = "SELECT d FROM DomicilioProvee d WHERE d.direccion = :direccion"),
    @NamedQuery(name = "DomicilioProvee.findByTelefono", query = "SELECT d FROM DomicilioProvee d WHERE d.telefono = :telefono"),
    @NamedQuery(name = "DomicilioProvee.findByBarrio", query = "SELECT d FROM DomicilioProvee d WHERE d.barrio = :barrio"),
    @NamedQuery(name = "DomicilioProvee.findByLocalidad", query = "SELECT d FROM DomicilioProvee d WHERE d.localidad = :localidad"),
    @NamedQuery(name = "DomicilioProvee.findByCorreoPostal", query = "SELECT d FROM DomicilioProvee d WHERE d.correoPostal = :correoPostal"),
    @NamedQuery(name = "DomicilioProvee.findByProveedorTipoDocumento", query = "SELECT d FROM DomicilioProvee d WHERE d.domicilioProveePK.proveedorTipoDocumento = :proveedorTipoDocumento"),
    @NamedQuery(name = "DomicilioProvee.findByProveedorNumDocumento", query = "SELECT d FROM DomicilioProvee d WHERE d.domicilioProveePK.proveedorNumDocumento = :proveedorNumDocumento")})
public class DomicilioProvee implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DomicilioProveePK domicilioProveePK;
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 45)
    @Column(name = "barrio")
    private String barrio;
    @Size(max = 45)
    @Column(name = "localidad")
    private String localidad;
    @Size(max = 45)
    @Column(name = "CORREO_POSTAL")
    private String correoPostal;
    @JoinColumn(name = "MUNICIPIO_idMUNICIPIO", referencedColumnName = "idMUNICIPIO")
    @ManyToOne(optional = false)
    private Municipio mUNICIPIOidMUNICIPIO;
    @JoinColumns({
        @JoinColumn(name = "PROVEEDOR_TIPO_DOCUMENTO", referencedColumnName = "TIPO_DOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "PROVEEDOR_NUM_DOCUMENTO", referencedColumnName = "NUM_DOCUMENTO", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Proveedor proveedor;

    public DomicilioProvee() {
    }

    public DomicilioProvee(DomicilioProveePK domicilioProveePK) {
        this.domicilioProveePK = domicilioProveePK;
    }

    public DomicilioProvee(String proveedorTipoDocumento, String proveedorNumDocumento) {
        this.domicilioProveePK = new DomicilioProveePK(proveedorTipoDocumento, proveedorNumDocumento);
    }

    public DomicilioProveePK getDomicilioProveePK() {
        return domicilioProveePK;
    }

    public void setDomicilioProveePK(DomicilioProveePK domicilioProveePK) {
        this.domicilioProveePK = domicilioProveePK;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCorreoPostal() {
        return correoPostal;
    }

    public void setCorreoPostal(String correoPostal) {
        this.correoPostal = correoPostal;
    }

    public Municipio getMUNICIPIOidMUNICIPIO() {
        return mUNICIPIOidMUNICIPIO;
    }

    public void setMUNICIPIOidMUNICIPIO(Municipio mUNICIPIOidMUNICIPIO) {
        this.mUNICIPIOidMUNICIPIO = mUNICIPIOidMUNICIPIO;
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
        hash += (domicilioProveePK != null ? domicilioProveePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DomicilioProvee)) {
            return false;
        }
        DomicilioProvee other = (DomicilioProvee) object;
        if ((this.domicilioProveePK == null && other.domicilioProveePK != null) || (this.domicilioProveePK != null && !this.domicilioProveePK.equals(other.domicilioProveePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.entities.DomicilioProvee[ domicilioProveePK=" + domicilioProveePK + " ]";
    }
    
}
