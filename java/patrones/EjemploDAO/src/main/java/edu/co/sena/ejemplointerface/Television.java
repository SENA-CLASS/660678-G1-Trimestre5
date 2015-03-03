/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplointerface;

/**
 *
 * @author Enrique Moreno
 */
public class Television implements ElectronicDevice, java.io.Serializable{

    @Override
    public void turnOff() {
        System.out.println("apagado");
    }

    @Override
    public void turnON() {
        System.out.println("prendido");
    }
    
    public void changeChannel(int channel){
        System.out.println("su canal actual es "+channel);
    }
    
}
