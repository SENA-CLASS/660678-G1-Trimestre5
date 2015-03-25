/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.negocio.prueba;

import edu.co.sena.onlineshop.integracion.jpa.entities.Categoria;
import edu.co.sena.onlineshop.integracion.jpa.entities.Producto;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author hernando
 */
public class CategoriaAPPEclipseLink {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Categoria c1 = new Categoria();
        c1.setIdCategoria(0);
        c1.setActiva(Boolean.TRUE);
        c1.setNombre("categoria 1");
        c1.setPariente(0);
       
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("edu.co.sena_OnlineShop_jar_1.0-SNAPSHOTPU");
        EntityManager em1 = emf1.createEntityManager();
        em1.getTransaction().begin();

        em1.persist(c1);

        em1.getTransaction().commit();

        em1.close();
        emf1.close();

    }

}
