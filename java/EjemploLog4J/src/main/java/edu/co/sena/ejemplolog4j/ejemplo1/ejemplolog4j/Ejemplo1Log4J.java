/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplolog4j.ejemplo1.ejemplolog4j;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Enrique Moreno
 */
public class Ejemplo1Log4J {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Throwable{
        
         Logger log = Logger.getLogger("Ejemplo1Log4J.class");
         
         PropertyConfigurator.configure("log4jDB.properties");
         try {
         log.fatal("esto es un error fatal");
         log.error("esto es un error");
         log.warn("esto es una alvertencia"); 
         log.info("esto es un mensaje de información");
         log.debug("esto es un mensaje de debug");
         log.trace("esto es un mensaje de razabilidad");
         
         int a =1, b=0;
         int c =a/b;
          
        } catch (Exception e) {
            log.fatal("error "+e.getMessage());
        }
         
         
         
    }
    
}
