/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplogenericos.metodos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Enrique Moreno
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Estudiante e1 = new Estudiante();
        e1.setId(1);
        e1.setNombre("jose");
        Estudiante e2 = new Estudiante();
        e2.setId(2);
        e2.setNombre("pedro");
        Estudiante e3 = new Estudiante();
        e3.setId(3);
        e3.setNombre("miguel");
        
        List<Estudiante> listaEsctudiantes = new ArrayList<>();
        listaEsctudiantes.add(e1);
        listaEsctudiantes.add(e2);
        listaEsctudiantes.add(e3);
        
        ListaUtilidades.imprimirLista(listaEsctudiantes);
        
        
        
    }
    
}
