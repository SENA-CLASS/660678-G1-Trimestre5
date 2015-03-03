/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.modelo.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Enrique Moreno
 */
@Entity
@Table(name = "factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByIdFactura", query = "SELECT f FROM Factura f WHERE f.facturaPK.idFactura = :idFactura"),
    @NamedQuery(name = "Factura.findByCuentaNumeroDocumento", query = "SELECT f FROM Factura f WHERE f.facturaPK.cuentaNumeroDocumento = :cuentaNumeroDocumento"),
    @NamedQuery(name = "Factura.findByCuentaTipoDocumento", query = "SELECT f FROM Factura f WHERE f.facturaPK.cuentaTipoDocumento = :cuentaTipoDocumento"),
    @NamedQuery(name = "Factura.findByPedidoIdPedido", query = "SELECT f FROM Factura f WHERE f.facturaPK.pedidoIdPedido = :pedidoIdPedido"),
    @NamedQuery(name = "Factura.findByFechaFactura", query = "SELECT f FROM Factura f WHERE f.fechaFactura = :fechaFactura"),
    @NamedQuery(name = "Factura.findByFormaPago", query = "SELECT f FROM Factura f WHERE f.formaPago = :formaPago")})
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FacturaPK facturaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_FACTURA")
    @Temporal(TemporalType.DATE)
    private Date fechaFactura;
    @Size(max = 45)
    @Column(name = "FORMA_PAGO")
    private String formaPago;
    @JoinColumns({
        @JoinColumn(name = "CUENTA_NUMERO_DOCUMENTO", referencedColumnName = "NUMERO_DOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "CUENTA_TIPO_DOCUMENTO", referencedColumnName = "TIPO_DOCUMENTO", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Cuenta cuenta;
    @JoinColumn(name = "PEDIDO_ID_PEDIDO", referencedColumnName = "ID_PEDIDO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pedido pedido;

    public Factura() {
    }

    public Factura(FacturaPK facturaPK) {
        this.facturaPK = facturaPK;
    }

    public Factura(FacturaPK facturaPK, Date fechaFactura) {
        this.facturaPK = facturaPK;
        this.fechaFactura = fechaFactura;
    }

    public Factura(int idFactura, String cuentaNumeroDocumento, String cuentaTipoDocumento, int pedidoIdPedido) {
        this.facturaPK = new FacturaPK(idFactura, cuentaNumeroDocumento, cuentaTipoDocumento, pedidoIdPedido);
    }

    public FacturaPK getFacturaPK() {
        return facturaPK;
    }

    public void setFacturaPK(FacturaPK facturaPK) {
        this.facturaPK = facturaPK;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facturaPK != null ? facturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.facturaPK == null && other.facturaPK != null) || (this.facturaPK != null && !this.facturaPK.equals(other.facturaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.Factura[ facturaPK=" + facturaPK + " ]";
    }
    
}
