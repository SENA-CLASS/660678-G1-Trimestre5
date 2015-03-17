/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Enrique Moreno
 */
@Embeddable
public class DomicilioCuentasPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CUENTA_NUMERO_DOCUMENTO")
    private String cuentaNumeroDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CUENTA_TIPO_DOCUMENTO")
    private String cuentaTipoDocumento;

    public DomicilioCuentasPK() {
    }

    public DomicilioCuentasPK(String cuentaNumeroDocumento, String cuentaTipoDocumento) {
        this.cuentaNumeroDocumento = cuentaNumeroDocumento;
        this.cuentaTipoDocumento = cuentaTipoDocumento;
    }

    public String getCuentaNumeroDocumento() {
        return cuentaNumeroDocumento;
    }

    public void setCuentaNumeroDocumento(String cuentaNumeroDocumento) {
        this.cuentaNumeroDocumento = cuentaNumeroDocumento;
    }

    public String getCuentaTipoDocumento() {
        return cuentaTipoDocumento;
    }

    public void setCuentaTipoDocumento(String cuentaTipoDocumento) {
        this.cuentaTipoDocumento = cuentaTipoDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuentaNumeroDocumento != null ? cuentaNumeroDocumento.hashCode() : 0);
        hash += (cuentaTipoDocumento != null ? cuentaTipoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DomicilioCuentasPK)) {
            return false;
        }
        DomicilioCuentasPK other = (DomicilioCuentasPK) object;
        if ((this.cuentaNumeroDocumento == null && other.cuentaNumeroDocumento != null) || (this.cuentaNumeroDocumento != null && !this.cuentaNumeroDocumento.equals(other.cuentaNumeroDocumento))) {
            return false;
        }
        if ((this.cuentaTipoDocumento == null && other.cuentaTipoDocumento != null) || (this.cuentaTipoDocumento != null && !this.cuentaTipoDocumento.equals(other.cuentaTipoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.entities.DomicilioCuentasPK[ cuentaNumeroDocumento=" + cuentaNumeroDocumento + ", cuentaTipoDocumento=" + cuentaTipoDocumento + " ]";
    }
    
}
