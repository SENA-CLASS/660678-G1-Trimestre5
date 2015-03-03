/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplo.DAO.memory;

import edu.co.sena.ejemplo.DAO.EmployeedDAO;
import edu.co.sena.ejemplo.model.Employeed;
import edu.co.sena.ejemplo.DAO.GenericDAO;
import edu.co.sena.ejemplo.model.People;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Enrique Moreno
 */
public class EmployeedDAOMemroyImpl implements EmployeedDAO{

    private List<People> listaDeempleados = new ArrayList<>();    
    
    
    @Override
    public void insert(People e) {
        
        System.out.println("se guardo"+e.toString());
    }

    @Override
    public void update(People e) {
        
        System.out.println("se actualizo"+e.toString());
    }

    @Override
    public void delete(People e) {
        System.out.println("se borro"+e.toString());
    }

    @Override
    public void findById(People e) {
        System.out.println("se guardo"+e.toString());
    }

    @Override
    public List<People> findByAll() {
        System.out.println("retorna la lista");
        
        return listaDeempleados;
    }
    
}
