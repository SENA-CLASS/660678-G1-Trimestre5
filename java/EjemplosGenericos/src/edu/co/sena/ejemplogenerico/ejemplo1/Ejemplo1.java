/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplogenerico.ejemplo1;

/**
 *
 * @author Enrique Moreno
 */
public class Ejemplo1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Shirt cam1 = new Shirt();
        cam1.setMarca("Pat Primo");
        cam1.setMaterial("lino");
        cam1.setTalla("XL");
        
        
        CacheAny<Shirt> objeto1 = new CacheAny<>();
        CacheAny<String> objeto2 = new CacheAny<>();
        objeto1.add(cam1);
        objeto2.add("hola mundo");
        
        System.out.println(objeto1.get().toString());
        System.out.println(objeto2.get());
        
        
        
        
        
        
        
    }
    
}
