/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.controllers;

import integracion.controllers.exceptions.IllegalOrphanException;
import integracion.controllers.exceptions.NonexistentEntityException;
import integracion.controllers.exceptions.PreexistingEntityException;
import integracion.controllers.exceptions.RollbackFailureException;
import integracion.entities.DomicilioProvee;
import integracion.entities.DomicilioProveePK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import integracion.entities.Municipio;
import integracion.entities.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class DomicilioProveeJpaController implements Serializable {

    public DomicilioProveeJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DomicilioProvee domicilioProvee) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
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
            utx.begin();
            em = getEntityManager();
            Municipio MUNICIPIOidMUNICIPIO = domicilioProvee.getMUNICIPIOidMUNICIPIO();
            if (MUNICIPIOidMUNICIPIO != null) {
                MUNICIPIOidMUNICIPIO = em.getReference(MUNICIPIOidMUNICIPIO.getClass(), MUNICIPIOidMUNICIPIO.getIdMUNICIPIO());
                domicilioProvee.setMUNICIPIOidMUNICIPIO(MUNICIPIOidMUNICIPIO);
            }
            Proveedor proveedor = domicilioProvee.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getProveedorPK());
                domicilioProvee.setProveedor(proveedor);
            }
            em.persist(domicilioProvee);
            if (MUNICIPIOidMUNICIPIO != null) {
                MUNICIPIOidMUNICIPIO.getDomicilioProveeList().add(domicilioProvee);
                MUNICIPIOidMUNICIPIO = em.merge(MUNICIPIOidMUNICIPIO);
            }
            if (proveedor != null) {
                proveedor.setDomicilioProvee(domicilioProvee);
                proveedor = em.merge(proveedor);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
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

    public void edit(DomicilioProvee domicilioProvee) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        domicilioProvee.getDomicilioProveePK().setProveedorNumDocumento(domicilioProvee.getProveedor().getProveedorPK().getNumDocumento());
        domicilioProvee.getDomicilioProveePK().setProveedorTipoDocumento(domicilioProvee.getProveedor().getProveedorPK().getTipoDocumento());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DomicilioProvee persistentDomicilioProvee = em.find(DomicilioProvee.class, domicilioProvee.getDomicilioProveePK());
            Municipio MUNICIPIOidMUNICIPIOOld = persistentDomicilioProvee.getMUNICIPIOidMUNICIPIO();
            Municipio MUNICIPIOidMUNICIPIONew = domicilioProvee.getMUNICIPIOidMUNICIPIO();
            Proveedor proveedorOld = persistentDomicilioProvee.getProveedor();
            Proveedor proveedorNew = domicilioProvee.getProveedor();
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
            if (MUNICIPIOidMUNICIPIONew != null) {
                MUNICIPIOidMUNICIPIONew = em.getReference(MUNICIPIOidMUNICIPIONew.getClass(), MUNICIPIOidMUNICIPIONew.getIdMUNICIPIO());
                domicilioProvee.setMUNICIPIOidMUNICIPIO(MUNICIPIOidMUNICIPIONew);
            }
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getProveedorPK());
                domicilioProvee.setProveedor(proveedorNew);
            }
            domicilioProvee = em.merge(domicilioProvee);
            if (MUNICIPIOidMUNICIPIOOld != null && !MUNICIPIOidMUNICIPIOOld.equals(MUNICIPIOidMUNICIPIONew)) {
                MUNICIPIOidMUNICIPIOOld.getDomicilioProveeList().remove(domicilioProvee);
                MUNICIPIOidMUNICIPIOOld = em.merge(MUNICIPIOidMUNICIPIOOld);
            }
            if (MUNICIPIOidMUNICIPIONew != null && !MUNICIPIOidMUNICIPIONew.equals(MUNICIPIOidMUNICIPIOOld)) {
                MUNICIPIOidMUNICIPIONew.getDomicilioProveeList().add(domicilioProvee);
                MUNICIPIOidMUNICIPIONew = em.merge(MUNICIPIOidMUNICIPIONew);
            }
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.setDomicilioProvee(null);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.setDomicilioProvee(domicilioProvee);
                proveedorNew = em.merge(proveedorNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
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

    public void destroy(DomicilioProveePK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DomicilioProvee domicilioProvee;
            try {
                domicilioProvee = em.getReference(DomicilioProvee.class, id);
                domicilioProvee.getDomicilioProveePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The domicilioProvee with id " + id + " no longer exists.", enfe);
            }
            Municipio MUNICIPIOidMUNICIPIO = domicilioProvee.getMUNICIPIOidMUNICIPIO();
            if (MUNICIPIOidMUNICIPIO != null) {
                MUNICIPIOidMUNICIPIO.getDomicilioProveeList().remove(domicilioProvee);
                MUNICIPIOidMUNICIPIO = em.merge(MUNICIPIOidMUNICIPIO);
            }
            Proveedor proveedor = domicilioProvee.getProveedor();
            if (proveedor != null) {
                proveedor.setDomicilioProvee(null);
                proveedor = em.merge(proveedor);
            }
            em.remove(domicilioProvee);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
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
