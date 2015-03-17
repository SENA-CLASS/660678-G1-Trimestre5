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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "domicilio_cuentas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DomicilioCuentas.findAll", query = "SELECT d FROM DomicilioCuentas d"),
    @NamedQuery(name = "DomicilioCuentas.findByDireccion", query = "SELECT d FROM DomicilioCuentas d WHERE d.direccion = :direccion"),
    @NamedQuery(name = "DomicilioCuentas.findByTelefono", query = "SELECT d FROM DomicilioCuentas d WHERE d.telefono = :telefono"),
    @NamedQuery(name = "DomicilioCuentas.findByBarrio", query = "SELECT d FROM DomicilioCuentas d WHERE d.barrio = :barrio"),
    @NamedQuery(name = "DomicilioCuentas.findByLocalidad", query = "SELECT d FROM DomicilioCuentas d WHERE d.localidad = :localidad"),
    @NamedQuery(name = "DomicilioCuentas.findByCorreoPostal", query = "SELECT d FROM DomicilioCuentas d WHERE d.correoPostal = :correoPostal"),
    @NamedQuery(name = "DomicilioCuentas.findByCuentaNumeroDocumento", query = "SELECT d FROM DomicilioCuentas d WHERE d.domicilioCuentasPK.cuentaNumeroDocumento = :cuentaNumeroDocumento"),
    @NamedQuery(name = "DomicilioCuentas.findByCuentaTipoDocumento", query = "SELECT d FROM DomicilioCuentas d WHERE d.domicilioCuentasPK.cuentaTipoDocumento = :cuentaTipoDocumento")})
public class DomicilioCuentas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DomicilioCuentasPK domicilioCuentasPK;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "barrio")
    private String barrio;
    @Column(name = "localidad")
    private String localidad;
    @Column(name = "CORREO_POSTAL")
    private String correoPostal;
    @JoinColumns({
        @JoinColumn(name = "CUENTA_NUMERO_DOCUMENTO", referencedColumnName = "NUMERO_DOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "CUENTA_TIPO_DOCUMENTO", referencedColumnName = "TIPO_DOCUMENTO", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Cuenta cuenta;
    @JoinColumn(name = "MUNICIPIO_idMUNICIPIO", referencedColumnName = "idMUNICIPIO")
    @ManyToOne(optional = false)
    private Municipio mUNICIPIOidMUNICIPIO;

    public DomicilioCuentas() {
    }

    public DomicilioCuentas(DomicilioCuentasPK domicilioCuentasPK) {
        this.domicilioCuentasPK = domicilioCuentasPK;
    }

    public DomicilioCuentas(String cuentaNumeroDocumento, String cuentaTipoDocumento) {
        this.domicilioCuentasPK = new DomicilioCuentasPK(cuentaNumeroDocumento, cuentaTipoDocumento);
    }

    public DomicilioCuentasPK getDomicilioCuentasPK() {
        return domicilioCuentasPK;
    }

    public void setDomicilioCuentasPK(DomicilioCuentasPK domicilioCuentasPK) {
        this.domicilioCuentasPK = domicilioCuentasPK;
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

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Municipio getMUNICIPIOidMUNICIPIO() {
        return mUNICIPIOidMUNICIPIO;
    }

    public void setMUNICIPIOidMUNICIPIO(Municipio mUNICIPIOidMUNICIPIO) {
        this.mUNICIPIOidMUNICIPIO = mUNICIPIOidMUNICIPIO;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (domicilioCuentasPK != null ? domicilioCuentasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DomicilioCuentas)) {
            return false;
        }
        DomicilioCuentas other = (DomicilioCuentas) object;
        if ((this.domicilioCuentasPK == null && other.domicilioCuentasPK != null) || (this.domicilioCuentasPK != null && !this.domicilioCuentasPK.equals(other.domicilioCuentasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sena.onlineshop.integracion.DomicilioCuentas[ domicilioCuentasPK=" + domicilioCuentasPK + " ]";
    }
    
}
