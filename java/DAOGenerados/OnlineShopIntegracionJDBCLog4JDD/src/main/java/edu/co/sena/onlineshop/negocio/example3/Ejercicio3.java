/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.onlineshop.negocio.example3;

import edu.co.sena.onlineshop.integracion.dao.CuentaDao;
import edu.co.sena.onlineshop.integracion.dto.Cuenta;
import edu.co.sena.onlineshop.integracion.exceptions.CuentaDaoException;
import edu.co.sena.onlineshop.integracion.factory.CuentaDaoFactory;

/**
 *
 * @author Enrique Moreno
 */
public class Ejercicio3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CuentaDaoException{
        Cuenta c1 = new Cuenta();
        
        //get y set
        
        CuentaDao daoCuenta1 = CuentaDaoFactory.create();
        daoCuenta1.insert(c1);
        
        Cuenta areglo1Cuentas[] = daoCuenta1.findAll();
        
        for (int i = 0; i < areglo1Cuentas.length; i++) {
            areglo1Cuentas[i].toString();
        }
        
        
    }
    
}
