/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.onlineshop.integracion;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "municipio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM Municipio m"),
    @NamedQuery(name = "Municipio.findByIdMUNICIPIO", query = "SELECT m FROM Municipio m WHERE m.idMUNICIPIO = :idMUNICIPIO"),
    @NamedQuery(name = "Municipio.findByNombreMunicipio", query = "SELECT m FROM Municipio m WHERE m.nombreMunicipio = :nombreMunicipio")})
public class Municipio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idMUNICIPIO")
    private Integer idMUNICIPIO;
    @Column(name = "NOMBRE_MUNICIPIO")
    private String nombreMunicipio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mUNICIPIOidMUNICIPIO")
    private Collection<DomicilioProvee> domicilioProveeCollection;
    @JoinColumn(name = "DEPARTAMENTO_idDEPARTAMENTO", referencedColumnName = "idDEPARTAMENTO")
    @ManyToOne(optional = false)
    private Departamento dEPARTAMENTOidDEPARTAMENTO;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mUNICIPIOidMUNICIPIO")
    private Collection<DomicilioCuentas> domicilioCuentasCollection;

    public Municipio() {
    }

    public Municipio(Integer idMUNICIPIO) {
        this.idMUNICIPIO = idMUNICIPIO;
    }

    public Integer getIdMUNICIPIO() {
        return idMUNICIPIO;
    }

    public void setIdMUNICIPIO(Integer idMUNICIPIO) {
        this.idMUNICIPIO = idMUNICIPIO;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    @XmlTransient
    public Collection<DomicilioProvee> getDomicilioProveeCollection() {
        return domicilioProveeCollection;
    }

    public void setDomicilioProveeCollection(Collection<DomicilioProvee> domicilioProveeCollection) {
        this.domicilioProveeCollection = domicilioProveeCollection;
    }

    public Departamento getDEPARTAMENTOidDEPARTAMENTO() {
        return dEPARTAMENTOidDEPARTAMENTO;
    }

    public void setDEPARTAMENTOidDEPARTAMENTO(Departamento dEPARTAMENTOidDEPARTAMENTO) {
        this.dEPARTAMENTOidDEPARTAMENTO = dEPARTAMENTOidDEPARTAMENTO;
    }

    @XmlTransient
    public Collection<DomicilioCuentas> getDomicilioCuentasCollection() {
        return domicilioCuentasCollection;
    }

    public void setDomicilioCuentasCollection(Collection<DomicilioCuentas> domicilioCuentasCollection) {
        this.domicilioCuentasCollection = domicilioCuentasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMUNICIPIO != null ? idMUNICIPIO.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipio)) {
            return false;
        }
        Municipio other = (Municipio) object;
        if ((this.idMUNICIPIO == null && other.idMUNICIPIO != null) || (this.idMUNICIPIO != null && !this.idMUNICIPIO.equals(other.idMUNICIPIO))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.co.sena.onlineshop.integracion.Municipio[ idMUNICIPIO=" + idMUNICIPIO + " ]";
    }
    
}
