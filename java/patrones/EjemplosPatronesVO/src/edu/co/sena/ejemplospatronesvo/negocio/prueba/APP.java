/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplospatronesvo.negocio.prueba;

import edu.co.sena.ejemplospatronesvo.integracion.model.PersonaVO;

/**
 *
 * @author Enrique Moreno
 */
public class APP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PersonaVO per1 = new PersonaVO();
        per1.setTipoDocumento("CC");
        per1.setNumeroDocumento("5345235234");
        
        PersonaVO per2 = new PersonaVO();
        per2.setTipoDocumento("CC");
        per2.setNumeroDocumento("5345235234");
        
        PersonaVO per3 = (PersonaVO)per1.clone();
        
        if (per1.compareTo(per3)==-2) {
            System.out.println("verdad");
        } else {
            System.out.println("falso");
        }
        
        System.out.println(per3.toString());
        
        System.out.println(per1);
        System.out.println(per2);
    }
    
}
