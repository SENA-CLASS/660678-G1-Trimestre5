/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.modelo.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p"),
    @NamedQuery(name = "Pago.findByIdpago", query = "SELECT p FROM Pago p WHERE p.idpago = :idpago"),
    @NamedQuery(name = "Pago.findByTipoPago", query = "SELECT p FROM Pago p WHERE p.tipoPago = :tipoPago"),
    @NamedQuery(name = "Pago.findByNumeCuenta", query = "SELECT p FROM Pago p WHERE p.numeCuenta = :numeCuenta"),
    @NamedQuery(name = "Pago.findByNumeroTargeta", query = "SELECT p FROM Pago p WHERE p.numeroTargeta = :numeroTargeta")})
public class Pago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpago")
    private Integer idpago;
    @Size(max = 45)
    @Column(name = "tipo pago")
    private String tipoPago;
    @Size(max = 45)
    @Column(name = "nume cuenta")
    private String numeCuenta;
    @Column(name = "numero targeta")
    private Integer numeroTargeta;

    public Pago() {
    }

    public Pago(Integer idpago) {
        this.idpago = idpago;
    }

    public Integer getIdpago() {
        return idpago;
    }

    public void setIdpago(Integer idpago) {
        this.idpago = idpago;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getNumeCuenta() {
        return numeCuenta;
    }

    public void setNumeCuenta(String numeCuenta) {
        this.numeCuenta = numeCuenta;
    }

    public Integer getNumeroTargeta() {
        return numeroTargeta;
    }

    public void setNumeroTargeta(Integer numeroTargeta) {
        this.numeroTargeta = numeroTargeta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpago != null ? idpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.idpago == null && other.idpago != null) || (this.idpago != null && !this.idpago.equals(other.idpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.Pago[ idpago=" + idpago + " ]";
    }
    
}
