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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByPrimerNombre", query = "SELECT c FROM Cuenta c WHERE c.primerNombre = :primerNombre"),
    @NamedQuery(name = "Cuenta.findBySegundoNombre", query = "SELECT c FROM Cuenta c WHERE c.segundoNombre = :segundoNombre"),
    @NamedQuery(name = "Cuenta.findByPrimerApellido", query = "SELECT c FROM Cuenta c WHERE c.primerApellido = :primerApellido"),
    @NamedQuery(name = "Cuenta.findBySegundoApellido", query = "SELECT c FROM Cuenta c WHERE c.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "Cuenta.findByTipoDocumento", query = "SELECT c FROM Cuenta c WHERE c.cuentaPK.tipoDocumento = :tipoDocumento"),
    @NamedQuery(name = "Cuenta.findByNumeroDocumento", query = "SELECT c FROM Cuenta c WHERE c.cuentaPK.numeroDocumento = :numeroDocumento"),
    @NamedQuery(name = "Cuenta.findByPassword", query = "SELECT c FROM Cuenta c WHERE c.password = :password"),
    @NamedQuery(name = "Cuenta.findByCorreo", query = "SELECT c FROM Cuenta c WHERE c.correo = :correo"),
    @NamedQuery(name = "Cuenta.findByDireccion", query = "SELECT c FROM Cuenta c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Cuenta.findByTelefono", query = "SELECT c FROM Cuenta c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Cuenta.findByUsuario", query = "SELECT c FROM Cuenta c WHERE c.usuario = :usuario")})
public class Cuenta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CuentaPK cuentaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PRIMER_NOMBRE")
    private String primerNombre;
    @Size(max = 45)
    @Column(name = "SEGUNDO_NOMBRE")
    private String segundoNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;
    @Size(max = 45)
    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;
    @Size(max = 45)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 45)
    @Column(name = "CORREO")
    private String correo;
    @Size(max = 45)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 45)
    @Column(name = "TELEFONO")
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "USUARIO")
    private String usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
    private Collection<Factura> facturaCollection;

    public Cuenta() {
    }

    public Cuenta(CuentaPK cuentaPK) {
        this.cuentaPK = cuentaPK;
    }

    public Cuenta(CuentaPK cuentaPK, String primerNombre, String primerApellido, String usuario) {
        this.cuentaPK = cuentaPK;
        this.primerNombre = primerNombre;
        this.primerApellido = primerApellido;
        this.usuario = usuario;
    }

    public Cuenta(String tipoDocumento, String numeroDocumento) {
        this.cuentaPK = new CuentaPK(tipoDocumento, numeroDocumento);
    }

    public CuentaPK getCuentaPK() {
        return cuentaPK;
    }

    public void setCuentaPK(CuentaPK cuentaPK) {
        this.cuentaPK = cuentaPK;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<Factura> getFacturaCollection() {
        return facturaCollection;
    }

    public void setFacturaCollection(Collection<Factura> facturaCollection) {
        this.facturaCollection = facturaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cuentaPK != null ? cuentaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.cuentaPK == null && other.cuentaPK != null) || (this.cuentaPK != null && !this.cuentaPK.equals(other.cuentaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.Cuenta[ cuentaPK=" + cuentaPK + " ]";
    }
    
}
