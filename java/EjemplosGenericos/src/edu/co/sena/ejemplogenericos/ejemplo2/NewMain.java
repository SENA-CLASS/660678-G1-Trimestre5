/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplogenericos.ejemplo2;

import java.util.ArrayList;
import java.util.HashMap;
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
        Persona<String, String, String, String>  p1 = new Persona<>();
        
        p1.setPrimeApellido("moreno");
        p1.setSegundoApellido("moreno");
        p1.setPrimerNombre("hernando");
        p1.setSegundoNombre("enrique");
        
        System.out.println(p1.toString());
        try {
            List<Integer> asdfasd = new ArrayList<>(-1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
        
        
        
    }
    
}
