/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplo.DAO;

import edu.co.sena.ejemplo.model.People;
import java.util.List;

/**
 *
 * @author Enrique Moreno
 */
public interface GenericDAO {
   
    public void insert(People e);
    public void update(People e);

    public void delete(People e);
    public void findById(People e); 
    public List<People> findByAll(); 
}
