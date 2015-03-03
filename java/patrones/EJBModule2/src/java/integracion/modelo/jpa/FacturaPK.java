/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.modelo.jpa;

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
public class FacturaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_FACTURA")
    private int idFactura;
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "PEDIDO_ID_PEDIDO")
    private int pedidoIdPedido;

    public FacturaPK() {
    }

    public FacturaPK(int idFactura, String cuentaNumeroDocumento, String cuentaTipoDocumento, int pedidoIdPedido) {
        this.idFactura = idFactura;
        this.cuentaNumeroDocumento = cuentaNumeroDocumento;
        this.cuentaTipoDocumento = cuentaTipoDocumento;
        this.pedidoIdPedido = pedidoIdPedido;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
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

    public int getPedidoIdPedido() {
        return pedidoIdPedido;
    }

    public void setPedidoIdPedido(int pedidoIdPedido) {
        this.pedidoIdPedido = pedidoIdPedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idFactura;
        hash += (cuentaNumeroDocumento != null ? cuentaNumeroDocumento.hashCode() : 0);
        hash += (cuentaTipoDocumento != null ? cuentaTipoDocumento.hashCode() : 0);
        hash += (int) pedidoIdPedido;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaPK)) {
            return false;
        }
        FacturaPK other = (FacturaPK) object;
        if (this.idFactura != other.idFactura) {
            return false;
        }
        if ((this.cuentaNumeroDocumento == null && other.cuentaNumeroDocumento != null) || (this.cuentaNumeroDocumento != null && !this.cuentaNumeroDocumento.equals(other.cuentaNumeroDocumento))) {
            return false;
        }
        if ((this.cuentaTipoDocumento == null && other.cuentaTipoDocumento != null) || (this.cuentaTipoDocumento != null && !this.cuentaTipoDocumento.equals(other.cuentaTipoDocumento))) {
            return false;
        }
        if (this.pedidoIdPedido != other.pedidoIdPedido) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.FacturaPK[ idFactura=" + idFactura + ", cuentaNumeroDocumento=" + cuentaNumeroDocumento + ", cuentaTipoDocumento=" + cuentaTipoDocumento + ", pedidoIdPedido=" + pedidoIdPedido + " ]";
    }
    
}
