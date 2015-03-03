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
public class Shirt extends Camisa{
    private String marca;
    private String talla;
    private String material;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Shirt{" + "marca=" + marca + ", talla=" + talla + ", material=" + material + '}';
    }
    
    
    
    
    
    
    
}
