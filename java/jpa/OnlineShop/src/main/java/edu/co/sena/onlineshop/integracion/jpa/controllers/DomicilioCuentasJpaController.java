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
import edu.co.sena.onlineshop.integracion.jpa.entities.Cuenta;
import edu.co.sena.onlineshop.integracion.jpa.entities.DomicilioCuentas;
import edu.co.sena.onlineshop.integracion.jpa.entities.DomicilioCuentasPK;
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
public class DomicilioCuentasJpaController implements Serializable {

    public DomicilioCuentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DomicilioCuentas domicilioCuentas) throws IllegalOrphanException, PreexistingEntityException, Exception {
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
            em = getEntityManager();
            em.getTransaction().begin();
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
                MUNICIPIOidMUNICIPIO.getDomicilioCuentasCollection().add(domicilioCuentas);
                MUNICIPIOidMUNICIPIO = em.merge(MUNICIPIOidMUNICIPIO);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void edit(DomicilioCuentas domicilioCuentas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        domicilioCuentas.getDomicilioCuentasPK().setCuentaNumeroDocumento(domicilioCuentas.getCuenta().getCuentaPK().getNumeroDocumento());
        domicilioCuentas.getDomicilioCuentasPK().setCuentaTipoDocumento(domicilioCuentas.getCuenta().getCuentaPK().getTipoDocumento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
                MUNICIPIOidMUNICIPIOOld.getDomicilioCuentasCollection().remove(domicilioCuentas);
                MUNICIPIOidMUNICIPIOOld = em.merge(MUNICIPIOidMUNICIPIOOld);
            }
            if (MUNICIPIOidMUNICIPIONew != null && !MUNICIPIOidMUNICIPIONew.equals(MUNICIPIOidMUNICIPIOOld)) {
                MUNICIPIOidMUNICIPIONew.getDomicilioCuentasCollection().add(domicilioCuentas);
                MUNICIPIOidMUNICIPIONew = em.merge(MUNICIPIOidMUNICIPIONew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(DomicilioCuentasPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
                MUNICIPIOidMUNICIPIO.getDomicilioCuentasCollection().remove(domicilioCuentas);
                MUNICIPIOidMUNICIPIO = em.merge(MUNICIPIOidMUNICIPIO);
            }
            em.remove(domicilioCuentas);
            em.getTransaction().commit();
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
