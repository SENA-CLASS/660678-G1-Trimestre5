/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.onlineshop.integracion.jpa.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.co.sena.onlineshop.integracion.jpa.entities.DomicilioProvee;
import edu.co.sena.onlineshop.integracion.jpa.entities.InventarioCompras;
import edu.co.sena.onlineshop.integracion.jpa.entities.Proveedor;
import edu.co.sena.onlineshop.integracion.jpa.entities.ProveedorPK;
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
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) throws PreexistingEntityException, Exception {
        if (proveedor.getProveedorPK() == null) {
            proveedor.setProveedorPK(new ProveedorPK());
        }
        if (proveedor.getInventarioComprasCollection() == null) {
            proveedor.setInventarioComprasCollection(new ArrayList<InventarioCompras>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DomicilioProvee domicilioProvee = proveedor.getDomicilioProvee();
            if (domicilioProvee != null) {
                domicilioProvee = em.getReference(domicilioProvee.getClass(), domicilioProvee.getDomicilioProveePK());
                proveedor.setDomicilioProvee(domicilioProvee);
            }
            Collection<InventarioCompras> attachedInventarioComprasCollection = new ArrayList<InventarioCompras>();
            for (InventarioCompras inventarioComprasCollectionInventarioComprasToAttach : proveedor.getInventarioComprasCollection()) {
                inventarioComprasCollectionInventarioComprasToAttach = em.getReference(inventarioComprasCollectionInventarioComprasToAttach.getClass(), inventarioComprasCollectionInventarioComprasToAttach.getInventarioComprasPK());
                attachedInventarioComprasCollection.add(inventarioComprasCollectionInventarioComprasToAttach);
            }
            proveedor.setInventarioComprasCollection(attachedInventarioComprasCollection);
            em.persist(proveedor);
            if (domicilioProvee != null) {
                Proveedor oldProveedorOfDomicilioProvee = domicilioProvee.getProveedor();
                if (oldProveedorOfDomicilioProvee != null) {
                    oldProveedorOfDomicilioProvee.setDomicilioProvee(null);
                    oldProveedorOfDomicilioProvee = em.merge(oldProveedorOfDomicilioProvee);
                }
                domicilioProvee.setProveedor(proveedor);
                domicilioProvee = em.merge(domicilioProvee);
            }
            for (InventarioCompras inventarioComprasCollectionInventarioCompras : proveedor.getInventarioComprasCollection()) {
                Proveedor oldProveedorOfInventarioComprasCollectionInventarioCompras = inventarioComprasCollectionInventarioCompras.getProveedor();
                inventarioComprasCollectionInventarioCompras.setProveedor(proveedor);
                inventarioComprasCollectionInventarioCompras = em.merge(inventarioComprasCollectionInventarioCompras);
                if (oldProveedorOfInventarioComprasCollectionInventarioCompras != null) {
                    oldProveedorOfInventarioComprasCollectionInventarioCompras.getInventarioComprasCollection().remove(inventarioComprasCollectionInventarioCompras);
                    oldProveedorOfInventarioComprasCollectionInventarioCompras = em.merge(oldProveedorOfInventarioComprasCollectionInventarioCompras);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProveedor(proveedor.getProveedorPK()) != null) {
                throw new PreexistingEntityException("Proveedor " + proveedor + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getProveedorPK());
            DomicilioProvee domicilioProveeOld = persistentProveedor.getDomicilioProvee();
            DomicilioProvee domicilioProveeNew = proveedor.getDomicilioProvee();
            Collection<InventarioCompras> inventarioComprasCollectionOld = persistentProveedor.getInventarioComprasCollection();
            Collection<InventarioCompras> inventarioComprasCollectionNew = proveedor.getInventarioComprasCollection();
            List<String> illegalOrphanMessages = null;
            if (domicilioProveeOld != null && !domicilioProveeOld.equals(domicilioProveeNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DomicilioProvee " + domicilioProveeOld + " since its proveedor field is not nullable.");
            }
            for (InventarioCompras inventarioComprasCollectionOldInventarioCompras : inventarioComprasCollectionOld) {
                if (!inventarioComprasCollectionNew.contains(inventarioComprasCollectionOldInventarioCompras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InventarioCompras " + inventarioComprasCollectionOldInventarioCompras + " since its proveedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (domicilioProveeNew != null) {
                domicilioProveeNew = em.getReference(domicilioProveeNew.getClass(), domicilioProveeNew.getDomicilioProveePK());
                proveedor.setDomicilioProvee(domicilioProveeNew);
            }
            Collection<InventarioCompras> attachedInventarioComprasCollectionNew = new ArrayList<InventarioCompras>();
            for (InventarioCompras inventarioComprasCollectionNewInventarioComprasToAttach : inventarioComprasCollectionNew) {
                inventarioComprasCollectionNewInventarioComprasToAttach = em.getReference(inventarioComprasCollectionNewInventarioComprasToAttach.getClass(), inventarioComprasCollectionNewInventarioComprasToAttach.getInventarioComprasPK());
                attachedInventarioComprasCollectionNew.add(inventarioComprasCollectionNewInventarioComprasToAttach);
            }
            inventarioComprasCollectionNew = attachedInventarioComprasCollectionNew;
            proveedor.setInventarioComprasCollection(inventarioComprasCollectionNew);
            proveedor = em.merge(proveedor);
            if (domicilioProveeNew != null && !domicilioProveeNew.equals(domicilioProveeOld)) {
                Proveedor oldProveedorOfDomicilioProvee = domicilioProveeNew.getProveedor();
                if (oldProveedorOfDomicilioProvee != null) {
                    oldProveedorOfDomicilioProvee.setDomicilioProvee(null);
                    oldProveedorOfDomicilioProvee = em.merge(oldProveedorOfDomicilioProvee);
                }
                domicilioProveeNew.setProveedor(proveedor);
                domicilioProveeNew = em.merge(domicilioProveeNew);
            }
            for (InventarioCompras inventarioComprasCollectionNewInventarioCompras : inventarioComprasCollectionNew) {
                if (!inventarioComprasCollectionOld.contains(inventarioComprasCollectionNewInventarioCompras)) {
                    Proveedor oldProveedorOfInventarioComprasCollectionNewInventarioCompras = inventarioComprasCollectionNewInventarioCompras.getProveedor();
                    inventarioComprasCollectionNewInventarioCompras.setProveedor(proveedor);
                    inventarioComprasCollectionNewInventarioCompras = em.merge(inventarioComprasCollectionNewInventarioCompras);
                    if (oldProveedorOfInventarioComprasCollectionNewInventarioCompras != null && !oldProveedorOfInventarioComprasCollectionNewInventarioCompras.equals(proveedor)) {
                        oldProveedorOfInventarioComprasCollectionNewInventarioCompras.getInventarioComprasCollection().remove(inventarioComprasCollectionNewInventarioCompras);
                        oldProveedorOfInventarioComprasCollectionNewInventarioCompras = em.merge(oldProveedorOfInventarioComprasCollectionNewInventarioCompras);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProveedorPK id = proveedor.getProveedorPK();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProveedorPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getProveedorPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            DomicilioProvee domicilioProveeOrphanCheck = proveedor.getDomicilioProvee();
            if (domicilioProveeOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the DomicilioProvee " + domicilioProveeOrphanCheck + " in its domicilioProvee field has a non-nullable proveedor field.");
            }
            Collection<InventarioCompras> inventarioComprasCollectionOrphanCheck = proveedor.getInventarioComprasCollection();
            for (InventarioCompras inventarioComprasCollectionOrphanCheckInventarioCompras : inventarioComprasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the InventarioCompras " + inventarioComprasCollectionOrphanCheckInventarioCompras + " in its inventarioComprasCollection field has a non-nullable proveedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(ProveedorPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
