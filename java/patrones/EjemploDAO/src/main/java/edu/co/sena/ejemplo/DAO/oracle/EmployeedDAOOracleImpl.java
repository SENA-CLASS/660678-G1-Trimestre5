/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplo.DAO.oracle;

import edu.co.sena.ejemplo.DAO.EmployeedDAO;
import edu.co.sena.ejemplo.model.People;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Enrique Moreno
 */
public class EmployeedDAOOracleImpl implements EmployeedDAO{

       
    List<People> listaDeEmpleados = new ArrayList<>();
    
    @Override
    public void insert(People e) {
        
        System.out.println("se guardo en oracle: "+e.toString());
    }

    @Override
    public void update(People e) {
        
        System.out.println("se actualizo en oracle: "+e.toString());
    }

    @Override
    public void delete(People e) {
        System.out.println("se borro en oracle: "+e.toString());
    }

    @Override
    public void findById(People e) {
        System.out.println("se busco por id"+e.toString());
    }

    @Override
    public List<People> findByAll() {
        System.out.println("retorna la lista");
        
        return listaDeEmpleados;
    }
    
}
