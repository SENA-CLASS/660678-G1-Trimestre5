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
public class Custommer extends People{
    private String idCustommer;

    public String getIdCustommer() {
        return idCustommer;
    }

    public void setIdCustommer(String idCustommer) {
        this.idCustommer = idCustommer;
    }

    @Override
    public String toString() {
        return "Custommer{" + "idCustommer=" + idCustommer + 
                ", primer nombre=" + super.getPrimerNombre() + 
                ", segundo nombre"+ super.getSegundoNombre()+
                ", primer apellido"+ super.getPrimerApellido()+
                ", segundo apellido"+ super.getSeundoApellido()+'}';
    }
    
    
    
    
}
