/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.ejemplogenerico.ejemplo1;

/**
 *
 * @author Enrique Moreno
 * @param <T> para cualquiere clase
 */
public class CacheAny<T extends Object> {

    private T t;

    public void add(T t) {
        this.t = t;
    }

    public T get() {
        return this.t;

    }
}
