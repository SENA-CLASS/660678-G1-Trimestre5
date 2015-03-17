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
import javax.persistence.Entity;
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
@Table(name = "departamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d"),
    @NamedQuery(name = "Departamento.findByIdDEPARTAMENTO", query = "SELECT d FROM Departamento d WHERE d.idDEPARTAMENTO = :idDEPARTAMENTO"),
    @NamedQuery(name = "Departamento.findByNombreDepartamento", query = "SELECT d FROM Departamento d WHERE d.nombreDepartamento = :nombreDepartamento")})
public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDEPARTAMENTO")
    private Integer idDEPARTAMENTO;
    @Size(max = 45)
    @Column(name = "NOMBRE_DEPARTAMENTO")
    private String nombreDepartamento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dEPARTAMENTOidDEPARTAMENTO")
    private List<Municipio> municipioList;

    public Departamento() {
    }

    public Departamento(Integer idDEPARTAMENTO) {
        this.idDEPARTAMENTO = idDEPARTAMENTO;
    }

    public Integer getIdDEPARTAMENTO() {
        return idDEPARTAMENTO;
    }

    public void setIdDEPARTAMENTO(Integer idDEPARTAMENTO) {
        this.idDEPARTAMENTO = idDEPARTAMENTO;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    @XmlTransient
    public List<Municipio> getMunicipioList() {
        return municipioList;
    }

    public void setMunicipioList(List<Municipio> municipioList) {
        this.municipioList = municipioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDEPARTAMENTO != null ? idDEPARTAMENTO.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.idDEPARTAMENTO == null && other.idDEPARTAMENTO != null) || (this.idDEPARTAMENTO != null && !this.idDEPARTAMENTO.equals(other.idDEPARTAMENTO))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.entities.Departamento[ idDEPARTAMENTO=" + idDEPARTAMENTO + " ]";
    }
    
}
