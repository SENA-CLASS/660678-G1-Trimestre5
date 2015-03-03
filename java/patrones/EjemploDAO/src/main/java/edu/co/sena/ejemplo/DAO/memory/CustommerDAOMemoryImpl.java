/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplo.DAO.memory;

import edu.co.sena.ejemplo.DAO.CustommerDAO;
import edu.co.sena.ejemplo.model.Employeed;
import edu.co.sena.ejemplo.model.People;
import java.util.List;
/**
 *
 * @author Enrique Moreno
 */
public class CustommerDAOMemoryImpl implements CustommerDAO {

    @Override
    public void insert(People e) {
        System.out.println("inserte cliente"+e.toString());
    }

    @Override
    public void update(People e) {
        System.out.println("actualiza cliente"+e.toString());
    }

    @Override
    public void delete(People e) {
        System.out.println("borre cliente"+e.toString());
    }

    @Override
    public void findById(People e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<People> findByAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
