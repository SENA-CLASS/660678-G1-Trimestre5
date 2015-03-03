/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplospatronesvo.integracion.model;



/**
 *
 * @author Enrique Moreno
 */
public class PersonaVO implements Comparable<PersonaVO>, Cloneable, java.io.Serializable{
    private String tipoDocumento;
    private String numeroDocumento;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String telefono;
    private String celular;
    private String direccion;

    public PersonaVO() {
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public int compareTo(PersonaVO o) {
       int resultado = -1;
        if (this.numeroDocumento.equals(o.numeroDocumento) 
                && this.tipoDocumento.equals(o.tipoDocumento)) {
        resultado = -2;    
        } 
        return resultado;
    }

    
    @Override
    public Object clone() {
        PersonaVO objClonado = new PersonaVO();
        objClonado.setTipoDocumento(this.tipoDocumento);
        objClonado.setNumeroDocumento(this.numeroDocumento);
        objClonado.setPrimerNombre(this.primerNombre);
        objClonado.setSegundoNombre(this.segundoNombre);
        objClonado.setPrimerApellido(this.primerApellido);
        objClonado.setTelefono(this.telefono);
        objClonado.setCelular(this.celular);
        objClonado.setDireccion(this.direccion);
        return objClonado;
    }

    @Override
    public String toString() {
        return "PersonaVO{" + "tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + ", primerNombre=" + primerNombre + ", segundoNombre=" + segundoNombre + ", primerApellido=" + primerApellido + ", telefono=" + telefono + ", celular=" + celular + ", direccion=" + direccion + '}';
    }
    
    
    
    
}
