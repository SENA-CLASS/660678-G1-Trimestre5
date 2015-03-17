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
import integracion.entities.Cuenta;
import integracion.entities.DomicilioCuentas;
import integracion.entities.DomicilioCuentasPK;
import integracion.entities.Municipio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class DomicilioCuentasJpaController implements Serializable {

    public DomicilioCuentasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DomicilioCuentas domicilioCuentas) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (domicilioCuentas.getDomicilioCuentasPK() == null) {
            domicilioCuentas.setDomicilioCuentasPK(new DomicilioCuentasPK());
        }
        domicilioCuentas.getDomicilioCuentasPK().setCuentaNumeroDocumento(domicilioCuentas.getCuenta().getCuentaPK().getNumeroDocumento());
        domicilioCuentas.getDomicilioCuentasPK().setCuentaTipoDocumento(domicilioCuentas.getCuenta().getCuentaPK().getTipoDocumento());
        List<String> illegalOrphanMessages = null;
        Cuenta cuentaOrphanCheck = domicilioCuentas.getCuenta();
        if (cuentaOrphanCheck != null) {
            DomicilioCuentas oldDomicilioCuentasOfCuenta = cuentaOrphanCheck.getDomicilioCuentas();
            if (oldDomicilioCuentasOfCuenta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cuenta " + cuentaOrphanCheck + " already has an item of type DomicilioCuentas whose cuenta column cannot be null. Please make another selection for the cuenta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cuenta cuenta = domicilioCuentas.getCuenta();
            if (cuenta != null) {
                cuenta = em.getReference(cuenta.getClass(), cuenta.getCuentaPK());
                domicilioCuentas.setCuenta(cuenta);
            }
            Municipio MUNICIPIOidMUNICIPIO = domicilioCuentas.getMUNICIPIOidMUNICIPIO();
            if (MUNICIPIOidMUNICIPIO != null) {
                MUNICIPIOidMUNICIPIO = em.getReference(MUNICIPIOidMUNICIPIO.getClass(), MUNICIPIOidMUNICIPIO.getIdMUNICIPIO());
                domicilioCuentas.setMUNICIPIOidMUNICIPIO(MUNICIPIOidMUNICIPIO);
            }
            em.persist(domicilioCuentas);
            if (cuenta != null) {
                cuenta.setDomicilioCuentas(domicilioCuentas);
                cuenta = em.merge(cuenta);
            }
            if (MUNICIPIOidMUNICIPIO != null) {
                MUNICIPIOidMUNICIPIO.getDomicilioCuentasList().add(domicilioCuentas);
                MUNICIPIOidMUNICIPIO = em.merge(MUNICIPIOidMUNICIPIO);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDomicilioCuentas(domicilioCuentas.getDomicilioCuentasPK()) != null) {
                throw new PreexistingEntityException("DomicilioCuentas " + domicilioCuentas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DomicilioCuentas domicilioCuentas) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        domicilioCuentas.getDomicilioCuentasPK().setCuentaNumeroDocumento(domicilioCuentas.getCuenta().getCuentaPK().getNumeroDocumento());
        domicilioCuentas.getDomicilioCuentasPK().setCuentaTipoDocumento(domicilioCuentas.getCuenta().getCuentaPK().getTipoDocumento());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DomicilioCuentas persistentDomicilioCuentas = em.find(DomicilioCuentas.class, domicilioCuentas.getDomicilioCuentasPK());
            Cuenta cuentaOld = persistentDomicilioCuentas.getCuenta();
            Cuenta cuentaNew = domicilioCuentas.getCuenta();
            Municipio MUNICIPIOidMUNICIPIOOld = persistentDomicilioCuentas.getMUNICIPIOidMUNICIPIO();
            Municipio MUNICIPIOidMUNICIPIONew = domicilioCuentas.getMUNICIPIOidMUNICIPIO();
            List<String> illegalOrphanMessages = null;
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                DomicilioCuentas oldDomicilioCuentasOfCuenta = cuentaNew.getDomicilioCuentas();
                if (oldDomicilioCuentasOfCuenta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cuenta " + cuentaNew + " already has an item of type DomicilioCuentas whose cuenta column cannot be null. Please make another selection for the cuenta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuentaNew != null) {
                cuentaNew = em.getReference(cuentaNew.getClass(), cuentaNew.getCuentaPK());
                domicilioCuentas.setCuenta(cuentaNew);
            }
            if (MUNICIPIOidMUNICIPIONew != null) {
                MUNICIPIOidMUNICIPIONew = em.getReference(MUNICIPIOidMUNICIPIONew.getClass(), MUNICIPIOidMUNICIPIONew.getIdMUNICIPIO());
                domicilioCuentas.setMUNICIPIOidMUNICIPIO(MUNICIPIOidMUNICIPIONew);
            }
            domicilioCuentas = em.merge(domicilioCuentas);
            if (cuentaOld != null && !cuentaOld.equals(cuentaNew)) {
                cuentaOld.setDomicilioCuentas(null);
                cuentaOld = em.merge(cuentaOld);
            }
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                cuentaNew.setDomicilioCuentas(domicilioCuentas);
                cuentaNew = em.merge(cuentaNew);
            }
            if (MUNICIPIOidMUNICIPIOOld != null && !MUNICIPIOidMUNICIPIOOld.equals(MUNICIPIOidMUNICIPIONew)) {
                MUNICIPIOidMUNICIPIOOld.getDomicilioCuentasList().remove(domicilioCuentas);
                MUNICIPIOidMUNICIPIOOld = em.merge(MUNICIPIOidMUNICIPIOOld);
            }
            if (MUNICIPIOidMUNICIPIONew != null && !MUNICIPIOidMUNICIPIONew.equals(MUNICIPIOidMUNICIPIOOld)) {
                MUNICIPIOidMUNICIPIONew.getDomicilioCuentasList().add(domicilioCuentas);
                MUNICIPIOidMUNICIPIONew = em.merge(MUNICIPIOidMUNICIPIONew);
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
                DomicilioCuentasPK id = domicilioCuentas.getDomicilioCuentasPK();
                if (findDomicilioCuentas(id) == null) {
                    throw new NonexistentEntityException("The domicilioCuentas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(DomicilioCuentasPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DomicilioCuentas domicilioCuentas;
            try {
                domicilioCuentas = em.getReference(DomicilioCuentas.class, id);
                domicilioCuentas.getDomicilioCuentasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The domicilioCuentas with id " + id + " no longer exists.", enfe);
            }
            Cuenta cuenta = domicilioCuentas.getCuenta();
            if (cuenta != null) {
                cuenta.setDomicilioCuentas(null);
                cuenta = em.merge(cuenta);
            }
            Municipio MUNICIPIOidMUNICIPIO = domicilioCuentas.getMUNICIPIOidMUNICIPIO();
            if (MUNICIPIOidMUNICIPIO != null) {
                MUNICIPIOidMUNICIPIO.getDomicilioCuentasList().remove(domicilioCuentas);
                MUNICIPIOidMUNICIPIO = em.merge(MUNICIPIOidMUNICIPIO);
            }
            em.remove(domicilioCuentas);
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

    public List<DomicilioCuentas> findDomicilioCuentasEntities() {
        return findDomicilioCuentasEntities(true, -1, -1);
    }

    public List<DomicilioCuentas> findDomicilioCuentasEntities(int maxResults, int firstResult) {
        return findDomicilioCuentasEntities(false, maxResults, firstResult);
    }

    private List<DomicilioCuentas> findDomicilioCuentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DomicilioCuentas.class));
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

    public DomicilioCuentas findDomicilioCuentas(DomicilioCuentasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DomicilioCuentas.class, id);
        } finally {
            em.close();
        }
    }

    public int getDomicilioCuentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DomicilioCuentas> rt = cq.from(DomicilioCuentas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
