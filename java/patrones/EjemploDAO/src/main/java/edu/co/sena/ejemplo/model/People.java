/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplo.model;

/**
 *
 * @author Enrique Moreno
 */
public class People {
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String seundoApellido;

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

    public String getSeundoApellido() {
        return seundoApellido;
    }

    public void setSeundoApellido(String seundoApellido) {
        this.seundoApellido = seundoApellido;
    }

    @Override
    public String toString() {
        return "Persona{" + "primerNombre=" + primerNombre + ", segundoNombre=" + segundoNombre + ", primerApellido=" + primerApellido + ", seundoApellido=" + seundoApellido + '}';
    }
    
    
}
