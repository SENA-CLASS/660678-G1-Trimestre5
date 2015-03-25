/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.integracion.jpa.controllers;

import edu.co.sena.onlineshop.integracion.jpa.entities.DomicilioProvee;
import edu.co.sena.onlineshop.integracion.jpa.entities.DomicilioProveePK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.co.sena.onlineshop.integracion.jpa.entities.Proveedor;
import edu.co.sena.onlineshop.integracion.jpa.entities.Municipio;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.IllegalOrphanException;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.NonexistentEntityException;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernando
 */
public class DomicilioProveeJpaController implements Serializable {

    public DomicilioProveeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DomicilioProvee domicilioProvee) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (domicilioProvee.getDomicilioProveePK() == null) {
            domicilioProvee.setDomicilioProveePK(new DomicilioProveePK());
        }
        domicilioProvee.getDomicilioProveePK().setProveedorNumDocumento(domicilioProvee.getProveedor().getProveedorPK().getNumDocumento());
        domicilioProvee.getDomicilioProveePK().setProveedorTipoDocumento(domicilioProvee.getProveedor().getProveedorPK().getTipoDocumento());
        List<String> illegalOrphanMessages = null;
        Proveedor proveedorOrphanCheck = domicilioProvee.getProveedor();
        if (proveedorOrphanCheck != null) {
            DomicilioProvee oldDomicilioProveeOfProveedor = proveedorOrphanCheck.getDomicilioProvee();
            if (oldDomicilioProveeOfProveedor != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Proveedor " + proveedorOrphanCheck + " already has an item of type DomicilioProvee whose proveedor column cannot be null. Please make another selection for the proveedor field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedor = domicilioProvee.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getProveedorPK());
                domicilioProvee.setProveedor(proveedor);
            }
            Municipio MUNICIPIOidMUNICIPIO = domicilioProvee.getMUNICIPIOidMUNICIPIO();
            if (MUNICIPIOidMUNICIPIO != null) {
                MUNICIPIOidMUNICIPIO = em.getReference(MUNICIPIOidMUNICIPIO.getClass(), MUNICIPIOidMUNICIPIO.getIdMUNICIPIO());
                domicilioProvee.setMUNICIPIOidMUNICIPIO(MUNICIPIOidMUNICIPIO);
            }
            em.persist(domicilioProvee);
            if (proveedor != null) {
                proveedor.setDomicilioProvee(domicilioProvee);
                proveedor = em.merge(proveedor);
            }
            if (MUNICIPIOidMUNICIPIO != null) {
                MUNICIPIOidMUNICIPIO.getDomicilioProveeCollection().add(domicilioProvee);
                MUNICIPIOidMUNICIPIO = em.merge(MUNICIPIOidMUNICIPIO);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDomicilioProvee(domicilioProvee.getDomicilioProveePK()) != null) {
                throw new PreexistingEntityException("DomicilioProvee " + domicilioProvee + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DomicilioProvee domicilioProvee) throws IllegalOrphanException, NonexistentEntityException, Exception {
        domicilioProvee.getDomicilioProveePK().setProveedorNumDocumento(domicilioProvee.getProveedor().getProveedorPK().getNumDocumento());
        domicilioProvee.getDomicilioProveePK().setProveedorTipoDocumento(domicilioProvee.getProveedor().getProveedorPK().getTipoDocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DomicilioProvee persistentDomicilioProvee = em.find(DomicilioProvee.class, domicilioProvee.getDomicilioProveePK());
            Proveedor proveedorOld = persistentDomicilioProvee.getProveedor();
            Proveedor proveedorNew = domicilioProvee.getProveedor();
            Municipio MUNICIPIOidMUNICIPIOOld = persistentDomicilioProvee.getMUNICIPIOidMUNICIPIO();
            Municipio MUNICIPIOidMUNICIPIONew = domicilioProvee.getMUNICIPIOidMUNICIPIO();
            List<String> illegalOrphanMessages = null;
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                DomicilioProvee oldDomicilioProveeOfProveedor = proveedorNew.getDomicilioProvee();
                if (oldDomicilioProveeOfProveedor != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Proveedor " + proveedorNew + " already has an item of type DomicilioProvee whose proveedor column cannot be null. Please make another selection for the proveedor field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getProveedorPK());
                domicilioProvee.setProveedor(proveedorNew);
            }
            if (MUNICIPIOidMUNICIPIONew != null) {
                MUNICIPIOidMUNICIPIONew = em.getReference(MUNICIPIOidMUNICIPIONew.getClass(), MUNICIPIOidMUNICIPIONew.getIdMUNICIPIO());
                domicilioProvee.setMUNICIPIOidMUNICIPIO(MUNICIPIOidMUNICIPIONew);
            }
            domicilioProvee = em.merge(domicilioProvee);
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.setDomicilioProvee(null);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.setDomicilioProvee(domicilioProvee);
                proveedorNew = em.merge(proveedorNew);
            }
            if (MUNICIPIOidMUNICIPIOOld != null && !MUNICIPIOidMUNICIPIOOld.equals(MUNICIPIOidMUNICIPIONew)) {
                MUNICIPIOidMUNICIPIOOld.getDomicilioProveeCollection().remove(domicilioProvee);
                MUNICIPIOidMUNICIPIOOld = em.merge(MUNICIPIOidMUNICIPIOOld);
            }
            if (MUNICIPIOidMUNICIPIONew != null && !MUNICIPIOidMUNICIPIONew.equals(MUNICIPIOidMUNICIPIOOld)) {
                MUNICIPIOidMUNICIPIONew.getDomicilioProveeCollection().add(domicilioProvee);
                MUNICIPIOidMUNICIPIONew = em.merge(MUNICIPIOidMUNICIPIONew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                DomicilioProveePK id = domicilioProvee.getDomicilioProveePK();
                if (findDomicilioProvee(id) == null) {
                    throw new NonexistentEntityException("The domicilioProvee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DomicilioProveePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DomicilioProvee domicilioProvee;
            try {
                domicilioProvee = em.getReference(DomicilioProvee.class, id);
                domicilioProvee.getDomicilioProveePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The domicilioProvee with id " + id + " no longer exists.", enfe);
            }
            Proveedor proveedor = domicilioProvee.getProveedor();
            if (proveedor != null) {
                proveedor.setDomicilioProvee(null);
                proveedor = em.merge(proveedor);
            }
            Municipio MUNICIPIOidMUNICIPIO = domicilioProvee.getMUNICIPIOidMUNICIPIO();
            if (MUNICIPIOidMUNICIPIO != null) {
                MUNICIPIOidMUNICIPIO.getDomicilioProveeCollection().remove(domicilioProvee);
                MUNICIPIOidMUNICIPIO = em.merge(MUNICIPIOidMUNICIPIO);
            }
            em.remove(domicilioProvee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DomicilioProvee> findDomicilioProveeEntities() {
        return findDomicilioProveeEntities(true, -1, -1);
    }

    public List<DomicilioProvee> findDomicilioProveeEntities(int maxResults, int firstResult) {
        return findDomicilioProveeEntities(false, maxResults, firstResult);
    }

    private List<DomicilioProvee> findDomicilioProveeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DomicilioProvee.class));
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

    public DomicilioProvee findDomicilioProvee(DomicilioProveePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DomicilioProvee.class, id);
        } finally {
            em.close();
        }
    }

    public int getDomicilioProveeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DomicilioProvee> rt = cq.from(DomicilioProvee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
