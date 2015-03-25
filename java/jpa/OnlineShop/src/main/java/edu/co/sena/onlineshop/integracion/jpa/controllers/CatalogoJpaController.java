/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.integracion.jpa.controllers;

import edu.co.sena.onlineshop.integracion.jpa.entities.Catalogo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.co.sena.onlineshop.integracion.jpa.entities.Producto;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.IllegalOrphanException;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernando
 */
public class CatalogoJpaController implements Serializable {

    public CatalogoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Catalogo catalogo) {
        if (catalogo.getProductoCollection() == null) {
            catalogo.setProductoCollection(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Producto> attachedProductoCollection = new ArrayList<Producto>();
            for (Producto productoCollectionProductoToAttach : catalogo.getProductoCollection()) {
                productoCollectionProductoToAttach = em.getReference(productoCollectionProductoToAttach.getClass(), productoCollectionProductoToAttach.getIdProducto());
                attachedProductoCollection.add(productoCollectionProductoToAttach);
            }
            catalogo.setProductoCollection(attachedProductoCollection);
            em.persist(catalogo);
            for (Producto productoCollectionProducto : catalogo.getProductoCollection()) {
                Catalogo oldCatalogoIdCatalogoOfProductoCollectionProducto = productoCollectionProducto.getCatalogoIdCatalogo();
                productoCollectionProducto.setCatalogoIdCatalogo(catalogo);
                productoCollectionProducto = em.merge(productoCollectionProducto);
                if (oldCatalogoIdCatalogoOfProductoCollectionProducto != null) {
                    oldCatalogoIdCatalogoOfProductoCollectionProducto.getProductoCollection().remove(productoCollectionProducto);
                    oldCatalogoIdCatalogoOfProductoCollectionProducto = em.merge(oldCatalogoIdCatalogoOfProductoCollectionProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Catalogo catalogo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catalogo persistentCatalogo = em.find(Catalogo.class, catalogo.getIdCatalogo());
            Collection<Producto> productoCollectionOld = persistentCatalogo.getProductoCollection();
            Collection<Producto> productoCollectionNew = catalogo.getProductoCollection();
            List<String> illegalOrphanMessages = null;
            for (Producto productoCollectionOldProducto : productoCollectionOld) {
                if (!productoCollectionNew.contains(productoCollectionOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoCollectionOldProducto + " since its catalogoIdCatalogo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Producto> attachedProductoCollectionNew = new ArrayList<Producto>();
            for (Producto productoCollectionNewProductoToAttach : productoCollectionNew) {
                productoCollectionNewProductoToAttach = em.getReference(productoCollectionNewProductoToAttach.getClass(), productoCollectionNewProductoToAttach.getIdProducto());
                attachedProductoCollectionNew.add(productoCollectionNewProductoToAttach);
            }
            productoCollectionNew = attachedProductoCollectionNew;
            catalogo.setProductoCollection(productoCollectionNew);
            catalogo = em.merge(catalogo);
            for (Producto productoCollectionNewProducto : productoCollectionNew) {
                if (!productoCollectionOld.contains(productoCollectionNewProducto)) {
                    Catalogo oldCatalogoIdCatalogoOfProductoCollectionNewProducto = productoCollectionNewProducto.getCatalogoIdCatalogo();
                    productoCollectionNewProducto.setCatalogoIdCatalogo(catalogo);
                    productoCollectionNewProducto = em.merge(productoCollectionNewProducto);
                    if (oldCatalogoIdCatalogoOfProductoCollectionNewProducto != null && !oldCatalogoIdCatalogoOfProductoCollectionNewProducto.equals(catalogo)) {
                        oldCatalogoIdCatalogoOfProductoCollectionNewProducto.getProductoCollection().remove(productoCollectionNewProducto);
                        oldCatalogoIdCatalogoOfProductoCollectionNewProducto = em.merge(oldCatalogoIdCatalogoOfProductoCollectionNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = catalogo.getIdCatalogo();
                if (findCatalogo(id) == null) {
                    throw new NonexistentEntityException("The catalogo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catalogo catalogo;
            try {
                catalogo = em.getReference(Catalogo.class, id);
                catalogo.getIdCatalogo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catalogo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Producto> productoCollectionOrphanCheck = catalogo.getProductoCollection();
            for (Producto productoCollectionOrphanCheckProducto : productoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Catalogo (" + catalogo + ") cannot be destroyed since the Producto " + productoCollectionOrphanCheckProducto + " in its productoCollection field has a non-nullable catalogoIdCatalogo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(catalogo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Catalogo> findCatalogoEntities() {
        return findCatalogoEntities(true, -1, -1);
    }

    public List<Catalogo> findCatalogoEntities(int maxResults, int firstResult) {
        return findCatalogoEntities(false, maxResults, firstResult);
    }

    private List<Catalogo> findCatalogoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Catalogo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Catalogo findCatalogo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Catalogo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatalogoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Catalogo> rt = cq.from(Catalogo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
