/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.inegracion.jpa.entities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hernando
 */
public class CategoriaTestJPA {
    
    Categoria categoria;
    EntityManagerFactory emf;
    EntityManager em;
    
    public CategoriaTestJPA() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        categoria= new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNombre("categoria prueba");
        categoria.setPariente(0);
        categoria.setActiva(Boolean.TRUE);
        
        emf = Persistence.createEntityManagerFactory("edu.co.sena_OnlineShop_jar_1.0-SNAPSHOTPU");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    
    }
    
    @After
    public void tearDown() {
        em.close();
        emf.close();
        
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void insertarCategoria(){
        
        em.persist(categoria);
        em.getTransaction().commit();
        
    
    }
}