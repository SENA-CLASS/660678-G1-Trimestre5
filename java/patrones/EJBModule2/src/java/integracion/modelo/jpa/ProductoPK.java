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
public class ProductoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ID_PRODUCTO")
    private String idProducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CATEGORIA_ID_CATEGORIA")
    private int categoriaIdCategoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CATALOGO_ID_CATALOGO")
    private int catalogoIdCatalogo;

    public ProductoPK() {
    }

    public ProductoPK(String idProducto, int categoriaIdCategoria, int catalogoIdCatalogo) {
        this.idProducto = idProducto;
        this.categoriaIdCategoria = categoriaIdCategoria;
        this.catalogoIdCatalogo = catalogoIdCatalogo;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getCategoriaIdCategoria() {
        return categoriaIdCategoria;
    }

    public void setCategoriaIdCategoria(int categoriaIdCategoria) {
        this.categoriaIdCategoria = categoriaIdCategoria;
    }

    public int getCatalogoIdCatalogo() {
        return catalogoIdCatalogo;
    }

    public void setCatalogoIdCatalogo(int catalogoIdCatalogo) {
        this.catalogoIdCatalogo = catalogoIdCatalogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        hash += (int) categoriaIdCategoria;
        hash += (int) catalogoIdCatalogo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoPK)) {
            return false;
        }
        ProductoPK other = (ProductoPK) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        if (this.categoriaIdCategoria != other.categoriaIdCategoria) {
            return false;
        }
        if (this.catalogoIdCatalogo != other.catalogoIdCatalogo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "integracion.modelo.jpa.ProductoPK[ idProducto=" + idProducto + ", categoriaIdCategoria=" + categoriaIdCategoria + ", catalogoIdCatalogo=" + catalogoIdCatalogo + " ]";
    }
    
}
