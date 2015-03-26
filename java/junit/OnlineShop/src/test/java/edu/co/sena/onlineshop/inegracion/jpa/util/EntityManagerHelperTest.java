/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.inegracion.jpa.util;

import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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
public class EntityManagerHelperTest {
    
    public EntityManagerHelperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getEntityManager method, of class EntityManagerHelper.
     */
    @Test
    public void testGetEntityManager() {
        System.out.println("getEntityManager");
        EntityManager expResult = null;
        EntityManager result = EntityManagerHelper.getEntityManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeEntityManager method, of class EntityManagerHelper.
     */
    @Test
    public void testCloseEntityManager() {
        System.out.println("closeEntityManager");
        EntityManagerHelper.closeEntityManager();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beginTransaction method, of class EntityManagerHelper.
     */
    @Test
    public void testBeginTransaction() {
        System.out.println("beginTransaction");
        EntityManagerHelper.beginTransaction();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of commit method, of class EntityManagerHelper.
     */
    @Test
    public void testCommit() {
        System.out.println("commit");
        EntityManagerHelper.commit();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rollback method, of class EntityManagerHelper.
     */
    @Test
    public void testRollback() {
        System.out.println("rollback");
        EntityManagerHelper.rollback();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createQuery method, of class EntityManagerHelper.
     */
    @Test
    public void testCreateQuery() {
        System.out.println("createQuery");
        String query = "";
        Query expResult = null;
        Query result = EntityManagerHelper.createQuery(query);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of log method, of class EntityManagerHelper.
     */
    @Test
    public void testLog() {
        System.out.println("log");
        String info = "";
        Level level = null;
        Throwable ex = null;
        EntityManagerHelper.log(info, level, ex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
