/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplogenericos.ejemplo3;

/**
 *
 * @author Enrique Moreno
 */
public class Estudiante implements Comparable<Estudiante>{
    private String nombre;
    private int id;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Estudiante{" + "nombre=" + nombre + ", id=" + id + '}';
    }

    @Override
    public int compareTo(Estudiante o) {
        int resultado= 0;
        if(o.nombre.equals(this.nombre)){
            resultado=-1;
        }
        return resultado;   
    }
    
    


}
