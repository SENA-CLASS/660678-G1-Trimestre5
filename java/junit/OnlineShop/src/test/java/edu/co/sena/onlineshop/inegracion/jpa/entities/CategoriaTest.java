/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.inegracion.jpa.entities;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
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
public class CategoriaTest {
    
     public Categoria instance= null;
       
    
    public CategoriaTest() {
        System.out.println("estoy en e constructor");
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("estoy en el BeforeClass");
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("estoy en el AfterClass");
    }
    
    
    @Before
    public void setUp() {
        System.out.println("estoy en el Before");
        instance = new Categoria();
        instance.setIdCategoria(1);
        instance.setNombre("categoria");
        instance.setPariente(1);
        instance.setActiva(Boolean.TRUE);
        instance.setProductoCollection(new ArrayList<Producto>());
        System.out.println(instance.toString());
        System.out.println("aca termina el Before");
    }
    
    @After
    public void tearDown() {
        System.out.println("estoy en el After");
    }

    /**
     * Test of getIdCategoria method, of class Categoria.
     */
    @Test
    public void testGetIdCategoria() {
        System.out.println("getIdCategoria");
        Integer expResult = 1;
        Integer result = instance.getIdCategoria();
        assertEquals(expResult, result);
    }

    /**
     * Test of setIdCategoria method, of class Categoria.
     */
    @Test
    public void testSetIdCategoria() {
        System.out.println("setIdCategoria");
        Integer idCategoria = 2;
        
        instance.setIdCategoria(2);
        assertEquals(idCategoria, this.instance.getIdCategoria());
      
    }

    /**
     * Test of getNombre method, of class Categoria.
     */
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        Categoria instance = new Categoria();
        String expResult = "";
        String result = instance.getNombre();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNombre method, of class Categoria.
     */
    @Test
    public void testSetNombre() {
        System.out.println("setNombre");
        String nombre = "";
        Categoria instance = new Categoria();
        instance.setNombre(nombre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActiva method, of class Categoria.
     */
    @Test
    public void testGetActiva() {
        System.out.println("getActiva");
        Categoria instance = new Categoria();
        Boolean expResult = null;
        Boolean result = instance.getActiva();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActiva method, of class Categoria.
     */
    @Test
    public void testSetActiva() {
        System.out.println("setActiva");
        Boolean activa = null;
        Categoria instance = new Categoria();
        instance.setActiva(activa);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPariente method, of class Categoria.
     */
    @Test
    public void testGetPariente() {
        System.out.println("getPariente");
        Categoria instance = new Categoria();
        Integer expResult = null;
        Integer result = instance.getPariente();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPariente method, of class Categoria.
     */
    @Test
    public void testSetPariente() {
        System.out.println("setPariente");
        Integer pariente = null;
        Categoria instance = new Categoria();
        instance.setPariente(pariente);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProductoCollection method, of class Categoria.
     */
    @Test
    public void testGetProductoCollection() {
        System.out.println("getProductoCollection");
        Categoria instance = new Categoria();
        Collection<Producto> expResult = null;
        Collection<Producto> result = instance.getProductoCollection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setProductoCollection method, of class Categoria.
     */
    @Test
    public void testSetProductoCollection() {
        System.out.println("setProductoCollection");
        Collection<Producto> productoCollection = null;
        Categoria instance = new Categoria();
        instance.setProductoCollection(productoCollection);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Categoria.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Categoria instance = new Categoria();
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Categoria.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object object = null;
        Categoria instance = new Categoria();
        boolean expResult = false;
        boolean result = instance.equals(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Categoria.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Categoria instance = new Categoria();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
