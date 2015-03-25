/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.onlineshop.integracion.jpa.controllers;

import edu.co.sena.onlineshop.integracion.jpa.entities.Cuenta;
import edu.co.sena.onlineshop.integracion.jpa.entities.CuentaPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.co.sena.onlineshop.integracion.jpa.entities.Usuario;
import edu.co.sena.onlineshop.integracion.jpa.entities.DomicilioCuentas;
import edu.co.sena.onlineshop.integracion.jpa.entities.Factura;
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
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) throws PreexistingEntityException, Exception {
        if (cuenta.getCuentaPK() == null) {
            cuenta.setCuentaPK(new CuentaPK());
        }
        if (cuenta.getFacturaCollection() == null) {
            cuenta.setFacturaCollection(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioIdusuario = cuenta.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario = em.getReference(usuarioIdusuario.getClass(), usuarioIdusuario.getIdusuario());
                cuenta.setUsuarioIdusuario(usuarioIdusuario);
            }
            DomicilioCuentas domicilioCuentas = cuenta.getDomicilioCuentas();
            if (domicilioCuentas != null) {
                domicilioCuentas = em.getReference(domicilioCuentas.getClass(), domicilioCuentas.getDomicilioCuentasPK());
                cuenta.setDomicilioCuentas(domicilioCuentas);
            }
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : cuenta.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getIdFactura());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            cuenta.setFacturaCollection(attachedFacturaCollection);
            em.persist(cuenta);
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getCuentaCollection().add(cuenta);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            if (domicilioCuentas != null) {
                Cuenta oldCuentaOfDomicilioCuentas = domicilioCuentas.getCuenta();
                if (oldCuentaOfDomicilioCuentas != null) {
                    oldCuentaOfDomicilioCuentas.setDomicilioCuentas(null);
                    oldCuentaOfDomicilioCuentas = em.merge(oldCuentaOfDomicilioCuentas);
                }
                domicilioCuentas.setCuenta(cuenta);
                domicilioCuentas = em.merge(domicilioCuentas);
            }
            for (Factura facturaCollectionFactura : cuenta.getFacturaCollection()) {
                Cuenta oldCuentaOfFacturaCollectionFactura = facturaCollectionFactura.getCuenta();
                facturaCollectionFactura.setCuenta(cuenta);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldCuentaOfFacturaCollectionFactura != null) {
                    oldCuentaOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldCuentaOfFacturaCollectionFactura = em.merge(oldCuentaOfFacturaCollectionFactura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuenta(cuenta.getCuentaPK()) != null) {
                throw new PreexistingEntityException("Cuenta " + cuenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getCuentaPK());
            Usuario usuarioIdusuarioOld = persistentCuenta.getUsuarioIdusuario();
            Usuario usuarioIdusuarioNew = cuenta.getUsuarioIdusuario();
            DomicilioCuentas domicilioCuentasOld = persistentCuenta.getDomicilioCuentas();
            DomicilioCuentas domicilioCuentasNew = cuenta.getDomicilioCuentas();
            Collection<Factura> facturaCollectionOld = persistentCuenta.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = cuenta.getFacturaCollection();
            List<String> illegalOrphanMessages = null;
            if (domicilioCuentasOld != null && !domicilioCuentasOld.equals(domicilioCuentasNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain DomicilioCuentas " + domicilioCuentasOld + " since its cuenta field is not nullable.");
            }
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaCollectionOldFactura + " since its cuenta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioIdusuarioNew != null) {
                usuarioIdusuarioNew = em.getReference(usuarioIdusuarioNew.getClass(), usuarioIdusuarioNew.getIdusuario());
                cuenta.setUsuarioIdusuario(usuarioIdusuarioNew);
            }
            if (domicilioCuentasNew != null) {
                domicilioCuentasNew = em.getReference(domicilioCuentasNew.getClass(), domicilioCuentasNew.getDomicilioCuentasPK());
                cuenta.setDomicilioCuentas(domicilioCuentasNew);
            }
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getIdFactura());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            cuenta.setFacturaCollection(facturaCollectionNew);
            cuenta = em.merge(cuenta);
            if (usuarioIdusuarioOld != null && !usuarioIdusuarioOld.equals(usuarioIdusuarioNew)) {
                usuarioIdusuarioOld.getCuentaCollection().remove(cuenta);
                usuarioIdusuarioOld = em.merge(usuarioIdusuarioOld);
            }
            if (usuarioIdusuarioNew != null && !usuarioIdusuarioNew.equals(usuarioIdusuarioOld)) {
                usuarioIdusuarioNew.getCuentaCollection().add(cuenta);
                usuarioIdusuarioNew = em.merge(usuarioIdusuarioNew);
            }
            if (domicilioCuentasNew != null && !domicilioCuentasNew.equals(domicilioCuentasOld)) {
                Cuenta oldCuentaOfDomicilioCuentas = domicilioCuentasNew.getCuenta();
                if (oldCuentaOfDomicilioCuentas != null) {
                    oldCuentaOfDomicilioCuentas.setDomicilioCuentas(null);
                    oldCuentaOfDomicilioCuentas = em.merge(oldCuentaOfDomicilioCuentas);
                }
                domicilioCuentasNew.setCuenta(cuenta);
                domicilioCuentasNew = em.merge(domicilioCuentasNew);
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Cuenta oldCuentaOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getCuenta();
                    facturaCollectionNewFactura.setCuenta(cuenta);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldCuentaOfFacturaCollectionNewFactura != null && !oldCuentaOfFacturaCollectionNewFactura.equals(cuenta)) {
                        oldCuentaOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldCuentaOfFacturaCollectionNewFactura = em.merge(oldCuentaOfFacturaCollectionNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CuentaPK id = cuenta.getCuentaPK();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CuentaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getCuentaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            DomicilioCuentas domicilioCuentasOrphanCheck = cuenta.getDomicilioCuentas();
            if (domicilioCuentasOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the DomicilioCuentas " + domicilioCuentasOrphanCheck + " in its domicilioCuentas field has a non-nullable cuenta field.");
            }
            Collection<Factura> facturaCollectionOrphanCheck = cuenta.getFacturaCollection();
            for (Factura facturaCollectionOrphanCheckFactura : facturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Factura " + facturaCollectionOrphanCheckFactura + " in its facturaCollection field has a non-nullable cuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuarioIdusuario = cuenta.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getCuentaCollection().remove(cuenta);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(CuentaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
