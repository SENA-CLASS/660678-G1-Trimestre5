/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.integracion.jpa.controllers;

import edu.co.sena.onlineshop.integracion.jpa.entities.CarritoCompras;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.co.sena.onlineshop.integracion.jpa.entities.ItemCarrito;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.IllegalOrphanException;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.NonexistentEntityException;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernando
 */
public class CarritoComprasJpaController implements Serializable {

    public CarritoComprasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CarritoCompras carritoCompras) throws PreexistingEntityException, Exception {
        if (carritoCompras.getItemCarritoCollection() == null) {
            carritoCompras.setItemCarritoCollection(new ArrayList<ItemCarrito>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ItemCarrito> attachedItemCarritoCollection = new ArrayList<ItemCarrito>();
            for (ItemCarrito itemCarritoCollectionItemCarritoToAttach : carritoCompras.getItemCarritoCollection()) {
                itemCarritoCollectionItemCarritoToAttach = em.getReference(itemCarritoCollectionItemCarritoToAttach.getClass(), itemCarritoCollectionItemCarritoToAttach.getItemCarritoPK());
                attachedItemCarritoCollection.add(itemCarritoCollectionItemCarritoToAttach);
            }
            carritoCompras.setItemCarritoCollection(attachedItemCarritoCollection);
            em.persist(carritoCompras);
            for (ItemCarrito itemCarritoCollectionItemCarrito : carritoCompras.getItemCarritoCollection()) {
                CarritoCompras oldCarritoComprasOfItemCarritoCollectionItemCarrito = itemCarritoCollectionItemCarrito.getCarritoCompras();
                itemCarritoCollectionItemCarrito.setCarritoCompras(carritoCompras);
                itemCarritoCollectionItemCarrito = em.merge(itemCarritoCollectionItemCarrito);
                if (oldCarritoComprasOfItemCarritoCollectionItemCarrito != null) {
                    oldCarritoComprasOfItemCarritoCollectionItemCarrito.getItemCarritoCollection().remove(itemCarritoCollectionItemCarrito);
                    oldCarritoComprasOfItemCarritoCollectionItemCarrito = em.merge(oldCarritoComprasOfItemCarritoCollectionItemCarrito);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCarritoCompras(carritoCompras.getIdCarrito()) != null) {
                throw new PreexistingEntityException("CarritoCompras " + carritoCompras + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CarritoCompras carritoCompras) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CarritoCompras persistentCarritoCompras = em.find(CarritoCompras.class, carritoCompras.getIdCarrito());
            Collection<ItemCarrito> itemCarritoCollectionOld = persistentCarritoCompras.getItemCarritoCollection();
            Collection<ItemCarrito> itemCarritoCollectionNew = carritoCompras.getItemCarritoCollection();
            List<String> illegalOrphanMessages = null;
            for (ItemCarrito itemCarritoCollectionOldItemCarrito : itemCarritoCollectionOld) {
                if (!itemCarritoCollectionNew.contains(itemCarritoCollectionOldItemCarrito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemCarrito " + itemCarritoCollectionOldItemCarrito + " since its carritoCompras field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ItemCarrito> attachedItemCarritoCollectionNew = new ArrayList<ItemCarrito>();
            for (ItemCarrito itemCarritoCollectionNewItemCarritoToAttach : itemCarritoCollectionNew) {
                itemCarritoCollectionNewItemCarritoToAttach = em.getReference(itemCarritoCollectionNewItemCarritoToAttach.getClass(), itemCarritoCollectionNewItemCarritoToAttach.getItemCarritoPK());
                attachedItemCarritoCollectionNew.add(itemCarritoCollectionNewItemCarritoToAttach);
            }
            itemCarritoCollectionNew = attachedItemCarritoCollectionNew;
            carritoCompras.setItemCarritoCollection(itemCarritoCollectionNew);
            carritoCompras = em.merge(carritoCompras);
            for (ItemCarrito itemCarritoCollectionNewItemCarrito : itemCarritoCollectionNew) {
                if (!itemCarritoCollectionOld.contains(itemCarritoCollectionNewItemCarrito)) {
                    CarritoCompras oldCarritoComprasOfItemCarritoCollectionNewItemCarrito = itemCarritoCollectionNewItemCarrito.getCarritoCompras();
                    itemCarritoCollectionNewItemCarrito.setCarritoCompras(carritoCompras);
                    itemCarritoCollectionNewItemCarrito = em.merge(itemCarritoCollectionNewItemCarrito);
                    if (oldCarritoComprasOfItemCarritoCollectionNewItemCarrito != null && !oldCarritoComprasOfItemCarritoCollectionNewItemCarrito.equals(carritoCompras)) {
                        oldCarritoComprasOfItemCarritoCollectionNewItemCarrito.getItemCarritoCollection().remove(itemCarritoCollectionNewItemCarrito);
                        oldCarritoComprasOfItemCarritoCollectionNewItemCarrito = em.merge(oldCarritoComprasOfItemCarritoCollectionNewItemCarrito);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = carritoCompras.getIdCarrito();
                if (findCarritoCompras(id) == null) {
                    throw new NonexistentEntityException("The carritoCompras with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CarritoCompras carritoCompras;
            try {
                carritoCompras = em.getReference(CarritoCompras.class, id);
                carritoCompras.getIdCarrito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carritoCompras with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ItemCarrito> itemCarritoCollectionOrphanCheck = carritoCompras.getItemCarritoCollection();
            for (ItemCarrito itemCarritoCollectionOrphanCheckItemCarrito : itemCarritoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CarritoCompras (" + carritoCompras + ") cannot be destroyed since the ItemCarrito " + itemCarritoCollectionOrphanCheckItemCarrito + " in its itemCarritoCollection field has a non-nullable carritoCompras field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(carritoCompras);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CarritoCompras> findCarritoComprasEntities() {
        return findCarritoComprasEntities(true, -1, -1);
    }

    public List<CarritoCompras> findCarritoComprasEntities(int maxResults, int firstResult) {
        return findCarritoComprasEntities(false, maxResults, firstResult);
    }

    private List<CarritoCompras> findCarritoComprasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CarritoCompras.class));
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

    public CarritoCompras findCarritoCompras(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CarritoCompras.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarritoComprasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CarritoCompras> rt = cq.from(CarritoCompras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
