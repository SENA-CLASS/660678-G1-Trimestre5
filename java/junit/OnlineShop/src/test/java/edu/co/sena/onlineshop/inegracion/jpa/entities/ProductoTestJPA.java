/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.inegracion.jpa.entities;

import edu.co.sena.onlineshop.inegracion.jpa.util.EntityManagerHelper;
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
public class ProductoTestJPA {
    
    Producto producto;
    EntityManagerFactory emf;
    EntityManager em;
    
    public ProductoTestJPA() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
       producto = new Producto();
       producto.setIdProducto("00002");
       producto.setActivo(true);
       producto.setNombreProducto("nombre producto prueba");
       producto.setCatalogoIdCatalogo(new Catalogo(1));
       producto.setCategoriaIdCategoria(new Categoria(1));
       
       
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void insertarProducto(){
        
        em = EntityManagerHelper.getEntityManager();
        EntityManagerHelper.beginTransaction();
        em.persist(producto);
        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        
    
    }
    
}
