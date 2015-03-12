/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.pruebalog4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Enrique Moreno
 */
public class PruebaLog4J {
    
    private final static Logger log2 = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger log = Logger.getLogger("PruebaLog4J.class");
        PropertyConfigurator.configure("log4j.properties");
        log.warn("mensaje de alvertencia");
        log.info("mensaje de info");
        log.trace("mensaje de trace");
        log.error("mensaje error");
        log.debug("mensaje debug");
        log.fatal("mensaje debug");
        
    }
    
}
