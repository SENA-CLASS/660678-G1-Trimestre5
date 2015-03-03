/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication22;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Enrique Moreno
 */
public class JavaApplication22 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Integer> listaNumeros = new ArrayList<>();
        listaNumeros.add(1);
        listaNumeros.add(2);
        listaNumeros.add(3);
        listaNumeros.add(4);
        listaNumeros.add(5);
        listaNumeros.add(6);
        listaNumeros.add(7);
        listaNumeros.add(8);
        listaNumeros.add(9);
        listaNumeros.add(10);
        Iterator i = listaNumeros.iterator();
        
        while(i.hasNext()){
        Integer numeroTemporal = (Integer)i.next();
        
            System.out.println(numeroTemporal);
        
        }
        
    }
    
}
