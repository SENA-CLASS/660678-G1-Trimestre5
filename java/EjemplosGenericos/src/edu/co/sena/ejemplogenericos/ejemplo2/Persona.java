/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplogenericos.ejemplo2;

/**
 *
 * @author Enrique Moreno
 * @param <TprimerNombre>
 * @param <TsegundoNombre>
 * @param <TprimerApellido>
 * @param <TsegundoApellido>
 */
public class Persona <TprimerNombre, TsegundoNombre, TprimerApellido, TsegundoApellido> {
    private TprimerNombre primerNombre;
    private TsegundoNombre segundoNombre;
    private TprimerApellido primeApellido;
    private TsegundoApellido segundoApellido;

    public TprimerNombre getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(TprimerNombre primerNombre) {
        this.primerNombre = primerNombre;
    }

    public TsegundoNombre getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(TsegundoNombre segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public TprimerApellido getPrimeApellido() {
        return primeApellido;
    }

    public void setPrimeApellido(TprimerApellido primeApellido) {
        this.primeApellido = primeApellido;
    }

    public TsegundoApellido getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(TsegundoApellido segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    @Override
    public String toString() {
        return "Persona{" + "primerNombre=" + primerNombre + ", segundoNombre=" + segundoNombre + ", primeApellido=" + primeApellido + ", segundoApellido=" + segundoApellido + '}';
    }
    
    
    
    
    
}
