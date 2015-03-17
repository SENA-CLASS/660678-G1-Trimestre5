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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import integracion.entities.DomicilioProvee;
import integracion.entities.InventarioCompras;
import integracion.entities.Proveedor;
import integracion.entities.ProveedorPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (proveedor.getProveedorPK() == null) {
            proveedor.setProveedorPK(new ProveedorPK());
        }
        if (proveedor.getInventarioComprasList() == null) {
            proveedor.setInventarioComprasList(new ArrayList<InventarioCompras>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DomicilioProvee domicilioProvee = proveedor.getDomicilioProvee();
            if (domicilioProvee != null) {
                domicilioProvee = em.getReference(domicilioProvee.getClass(), domicilioProvee.getDomicilioProveePK());
                proveedor.setDomicilioProvee(domicilioProvee);
            }
            List<InventarioCompras> attachedInventarioComprasList = new ArrayList<InventarioCompras>();
            for (InventarioCompras inventarioComprasListInventarioComprasToAttach : proveedor.getInventarioComprasList()) {
                inventarioComprasListInventarioComprasToAttach = em.getReference(inventarioComprasListInventarioComprasToAttach.getClass(), inventarioComprasListInventarioComprasToAttach.getInventarioComprasPK());
                attachedInventarioComprasList.add(inventarioComprasListInventarioComprasToAttach);
            }
            proveedor.setInventarioComprasList(attachedInventarioComprasList);
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
            for (InventarioCompras inventarioComprasListInventarioCompras : proveedor.getInventarioComprasList()) {
                Proveedor oldProveedorOfInventarioComprasListInventarioCompras = inventarioComprasListInventarioCompras.getProveedor();
                inventarioComprasListInventarioCompras.setProveedor(proveedor);
                inventarioComprasListInventarioCompras = em.merge(inventarioComprasListInventarioCompras);
                if (oldProveedorOfInventarioComprasListInventarioCompras != null) {
                    oldProveedorOfInventarioComprasListInventarioCompras.getInventarioComprasList().remove(inventarioComprasListInventarioCompras);
                    oldProveedorOfInventarioComprasListInventarioCompras = em.merge(oldProveedorOfInventarioComprasListInventarioCompras);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
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

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getProveedorPK());
            DomicilioProvee domicilioProveeOld = persistentProveedor.getDomicilioProvee();
            DomicilioProvee domicilioProveeNew = proveedor.getDomicilioProvee();
            List<InventarioCompras> inventarioComprasListOld = persistentProveedor.getInventarioComprasList();
            List<InventarioCompras> inventarioComprasListNew = proveedor.getInventarioComprasList();
            List<String> illegalOrphanMessages = null;
            if (domicilioProveeOld != null && !domicilioProveeOld.equals(domicilioProveeNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DomicilioProvee " + domicilioProveeOld + " since its proveedor field is not nullable.");
            }
            for (InventarioCompras inventarioComprasListOldInventarioCompras : inventarioComprasListOld) {
                if (!inventarioComprasListNew.contains(inventarioComprasListOldInventarioCompras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InventarioCompras " + inventarioComprasListOldInventarioCompras + " since its proveedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (domicilioProveeNew != null) {
                domicilioProveeNew = em.getReference(domicilioProveeNew.getClass(), domicilioProveeNew.getDomicilioProveePK());
                proveedor.setDomicilioProvee(domicilioProveeNew);
            }
            List<InventarioCompras> attachedInventarioComprasListNew = new ArrayList<InventarioCompras>();
            for (InventarioCompras inventarioComprasListNewInventarioComprasToAttach : inventarioComprasListNew) {
                inventarioComprasListNewInventarioComprasToAttach = em.getReference(inventarioComprasListNewInventarioComprasToAttach.getClass(), inventarioComprasListNewInventarioComprasToAttach.getInventarioComprasPK());
                attachedInventarioComprasListNew.add(inventarioComprasListNewInventarioComprasToAttach);
            }
            inventarioComprasListNew = attachedInventarioComprasListNew;
            proveedor.setInventarioComprasList(inventarioComprasListNew);
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
            for (InventarioCompras inventarioComprasListNewInventarioCompras : inventarioComprasListNew) {
                if (!inventarioComprasListOld.contains(inventarioComprasListNewInventarioCompras)) {
                    Proveedor oldProveedorOfInventarioComprasListNewInventarioCompras = inventarioComprasListNewInventarioCompras.getProveedor();
                    inventarioComprasListNewInventarioCompras.setProveedor(proveedor);
                    inventarioComprasListNewInventarioCompras = em.merge(inventarioComprasListNewInventarioCompras);
                    if (oldProveedorOfInventarioComprasListNewInventarioCompras != null && !oldProveedorOfInventarioComprasListNewInventarioCompras.equals(proveedor)) {
                        oldProveedorOfInventarioComprasListNewInventarioCompras.getInventarioComprasList().remove(inventarioComprasListNewInventarioCompras);
                        oldProveedorOfInventarioComprasListNewInventarioCompras = em.merge(oldProveedorOfInventarioComprasListNewInventarioCompras);
                    }
                }
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

    public void destroy(ProveedorPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
            List<InventarioCompras> inventarioComprasListOrphanCheck = proveedor.getInventarioComprasList();
            for (InventarioCompras inventarioComprasListOrphanCheckInventarioCompras : inventarioComprasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the InventarioCompras " + inventarioComprasListOrphanCheckInventarioCompras + " in its inventarioComprasList field has a non-nullable proveedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(proveedor);
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
