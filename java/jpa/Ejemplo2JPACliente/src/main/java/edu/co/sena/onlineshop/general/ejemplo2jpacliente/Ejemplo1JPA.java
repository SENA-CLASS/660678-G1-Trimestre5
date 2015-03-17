/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.general.ejemplo2jpacliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Enrique Moreno
 */
public class Ejemplo1JPA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
            EntityManagerFactory emfabrica1 = Persistence.createEntityManagerFactory(ConfiguracionJPA.UNIT_PERS);
            
            EntityManager em1 = emfabrica1.createEntityManager();
            
            em1.getTransaction().begin();
            Employee emp = new Employee(160);
            emp.setName("dfdsfasdf");
            emp.setSalary(341423);
            
            em1.merge(emp);
            
            em1.getTransaction().commit();
            
            em1.close();
            emfabrica1.close();

        

    }

}
