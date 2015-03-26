/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.inegracion.jpa.entities;

import edu.co.sena.onlineshop.inegracion.jpa.util.EntityManagerHelper;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;
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
        categoria = new Categoria();
        categoria.setNombre("categoria prueba");
        categoria.setPariente(0);
        categoria.setActiva(Boolean.TRUE);

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
    public void insertarCategoria() {
        em = EntityManagerHelper.getEntityManager();
        EntityManagerHelper.beginTransaction();
        em.persist(categoria);
        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }

    @Test
    public void actualizarCategoria() {

        String catLeida = JOptionPane.showInputDialog("digite la caegoria a actualizar");
        String catLeidaValor = JOptionPane.showInputDialog("digite el valor");

        categoria.setIdCategoria(Integer.parseInt(catLeida));
        categoria.setActiva(Boolean.valueOf(catLeidaValor));

        em = EntityManagerHelper.getEntityManager();
        EntityManagerHelper.beginTransaction();
        em.merge(categoria);
        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }
    
    @Test
    public void buscarUnaCategoria() {
        String idCategoria = JOptionPane.showInputDialog("inserte el id a buscar");

        em = EntityManagerHelper.getEntityManager();
        EntityManagerHelper.beginTransaction();

        Categoria ct = em.find(Categoria.class, Integer.parseInt(idCategoria));

        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
        
        System.out.println(ct.toString());    
    }

    @Test
    public void eliminarUnaCategoria() {
        String idCategoria = JOptionPane.showInputDialog("inserte el id a borrar");

        em = EntityManagerHelper.getEntityManager();
        EntityManagerHelper.beginTransaction();

        em.remove(em.find(Categoria.class, Integer.parseInt(idCategoria)));

        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }

    @Test
    public void buscarCategorias() {
        em = EntityManagerHelper.getEntityManager();
        EntityManagerHelper.beginTransaction();
        Query q = em.createNamedQuery("Categoria.findAll");
        List<Categoria> lis = q.getResultList();
        for (Categoria categoriaT : lis) {
            System.out.println(categoriaT.getIdCategoria() + " " + categoriaT.getNombre());
        }

        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }

    @Test
    public void eliminarTodasLasCategorias() {
        em = EntityManagerHelper.getEntityManager();
        EntityManagerHelper.beginTransaction();

        Query q = em.createNamedQuery("Categoria.findAll");
        List<Categoria> lis = q.getResultList();

        for (Categoria categoriaT : lis) {
            em.remove(em.find(Categoria.class, categoriaT.getIdCategoria()));
        }

        EntityManagerHelper.commit();
        EntityManagerHelper.closeEntityManager();
        EntityManagerHelper.closeEntityManagerFactory();
    }

}
