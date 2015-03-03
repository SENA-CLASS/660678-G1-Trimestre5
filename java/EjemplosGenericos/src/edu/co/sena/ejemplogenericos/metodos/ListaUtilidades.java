/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplogenericos.metodos;

import java.util.List;

/**
 *
 * @author Enrique Moreno
 */
public class ListaUtilidades {
    public static <T> void imprimirLista(List<T> a){
        for (T telemento : a) {
            System.out.println(telemento.toString());
        }
    
    }
}
