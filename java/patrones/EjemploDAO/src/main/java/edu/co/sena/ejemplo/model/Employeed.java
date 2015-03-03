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
public class Employeed extends People{
    private String idEmpleado;

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public String toString() {
        return "Employeed{" + "idEmpleado=" + idEmpleado + 
                ", primer nombre"+super.getPrimerNombre()+
                ", segundo nombre"+super.getSegundoNombre()+
                ", primer apellido"+super.getPrimerApellido()+
                ", segundo apellido"+super.getSeundoApellido()+'}';
    }
   
    
    
    
    
    
          
}
