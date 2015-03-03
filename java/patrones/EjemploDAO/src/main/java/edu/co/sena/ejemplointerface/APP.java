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
public class APP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ElectronicDevice t = new Television();
        
        if (t instanceof Television){
            Television tt = (Television)t;
            tt.changeChannel(3);
            System.out.println("verdad");
        }else{
            System.out.println("falso");
        }
                
    }
    
}
