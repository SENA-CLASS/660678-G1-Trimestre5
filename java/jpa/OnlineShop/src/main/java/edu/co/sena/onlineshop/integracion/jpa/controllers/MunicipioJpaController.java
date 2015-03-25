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
import edu.co.sena.onlineshop.integracion.jpa.entities.Departamento;
import edu.co.sena.onlineshop.integracion.jpa.entities.DomicilioProvee;
import java.util.ArrayList;
import java.util.Collection;
import edu.co.sena.onlineshop.integracion.jpa.entities.DomicilioCuentas;
import edu.co.sena.onlineshop.integracion.jpa.entities.Municipio;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.IllegalOrphanException;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.NonexistentEntityException;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernando
 */
public class MunicipioJpaController implements Serializable {

    public MunicipioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipio municipio) throws PreexistingEntityException, Exception {
        if (municipio.getDomicilioProveeCollection() == null) {
            municipio.setDomicilioProveeCollection(new ArrayList<DomicilioProvee>());
        }
        if (municipio.getDomicilioCuentasCollection() == null) {
            municipio.setDomicilioCuentasCollection(new ArrayList<DomicilioCuentas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento DEPARTAMENTOidDEPARTAMENTO = municipio.getDEPARTAMENTOidDEPARTAMENTO();
            if (DEPARTAMENTOidDEPARTAMENTO != null) {
                DEPARTAMENTOidDEPARTAMENTO = em.getReference(DEPARTAMENTOidDEPARTAMENTO.getClass(), DEPARTAMENTOidDEPARTAMENTO.getIdDEPARTAMENTO());
                municipio.setDEPARTAMENTOidDEPARTAMENTO(DEPARTAMENTOidDEPARTAMENTO);
            }
            Collection<DomicilioProvee> attachedDomicilioProveeCollection = new ArrayList<DomicilioProvee>();
            for (DomicilioProvee domicilioProveeCollectionDomicilioProveeToAttach : municipio.getDomicilioProveeCollection()) {
                domicilioProveeCollectionDomicilioProveeToAttach = em.getReference(domicilioProveeCollectionDomicilioProveeToAttach.getClass(), domicilioProveeCollectionDomicilioProveeToAttach.getDomicilioProveePK());
                attachedDomicilioProveeCollection.add(domicilioProveeCollectionDomicilioProveeToAttach);
            }
            municipio.setDomicilioProveeCollection(attachedDomicilioProveeCollection);
            Collection<DomicilioCuentas> attachedDomicilioCuentasCollection = new ArrayList<DomicilioCuentas>();
            for (DomicilioCuentas domicilioCuentasCollectionDomicilioCuentasToAttach : municipio.getDomicilioCuentasCollection()) {
                domicilioCuentasCollectionDomicilioCuentasToAttach = em.getReference(domicilioCuentasCollectionDomicilioCuentasToAttach.getClass(), domicilioCuentasCollectionDomicilioCuentasToAttach.getDomicilioCuentasPK());
                attachedDomicilioCuentasCollection.add(domicilioCuentasCollectionDomicilioCuentasToAttach);
            }
            municipio.setDomicilioCuentasCollection(attachedDomicilioCuentasCollection);
            em.persist(municipio);
            if (DEPARTAMENTOidDEPARTAMENTO != null) {
                DEPARTAMENTOidDEPARTAMENTO.getMunicipioCollection().add(municipio);
                DEPARTAMENTOidDEPARTAMENTO = em.merge(DEPARTAMENTOidDEPARTAMENTO);
            }
            for (DomicilioProvee domicilioProveeCollectionDomicilioProvee : municipio.getDomicilioProveeCollection()) {
                Municipio oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionDomicilioProvee = domicilioProveeCollectionDomicilioProvee.getMUNICIPIOidMUNICIPIO();
                domicilioProveeCollectionDomicilioProvee.setMUNICIPIOidMUNICIPIO(municipio);
                domicilioProveeCollectionDomicilioProvee = em.merge(domicilioProveeCollectionDomicilioProvee);
                if (oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionDomicilioProvee != null) {
                    oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionDomicilioProvee.getDomicilioProveeCollection().remove(domicilioProveeCollectionDomicilioProvee);
                    oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionDomicilioProvee = em.merge(oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionDomicilioProvee);
                }
            }
            for (DomicilioCuentas domicilioCuentasCollectionDomicilioCuentas : municipio.getDomicilioCuentasCollection()) {
                Municipio oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionDomicilioCuentas = domicilioCuentasCollectionDomicilioCuentas.getMUNICIPIOidMUNICIPIO();
                domicilioCuentasCollectionDomicilioCuentas.setMUNICIPIOidMUNICIPIO(municipio);
                domicilioCuentasCollectionDomicilioCuentas = em.merge(domicilioCuentasCollectionDomicilioCuentas);
                if (oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionDomicilioCuentas != null) {
                    oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionDomicilioCuentas.getDomicilioCuentasCollection().remove(domicilioCuentasCollectionDomicilioCuentas);
                    oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionDomicilioCuentas = em.merge(oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionDomicilioCuentas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMunicipio(municipio.getIdMUNICIPIO()) != null) {
                throw new PreexistingEntityException("Municipio " + municipio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipio municipio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getIdMUNICIPIO());
            Departamento DEPARTAMENTOidDEPARTAMENTOOld = persistentMunicipio.getDEPARTAMENTOidDEPARTAMENTO();
            Departamento DEPARTAMENTOidDEPARTAMENTONew = municipio.getDEPARTAMENTOidDEPARTAMENTO();
            Collection<DomicilioProvee> domicilioProveeCollectionOld = persistentMunicipio.getDomicilioProveeCollection();
            Collection<DomicilioProvee> domicilioProveeCollectionNew = municipio.getDomicilioProveeCollection();
            Collection<DomicilioCuentas> domicilioCuentasCollectionOld = persistentMunicipio.getDomicilioCuentasCollection();
            Collection<DomicilioCuentas> domicilioCuentasCollectionNew = municipio.getDomicilioCuentasCollection();
            List<String> illegalOrphanMessages = null;
            for (DomicilioProvee domicilioProveeCollectionOldDomicilioProvee : domicilioProveeCollectionOld) {
                if (!domicilioProveeCollectionNew.contains(domicilioProveeCollectionOldDomicilioProvee)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DomicilioProvee " + domicilioProveeCollectionOldDomicilioProvee + " since its MUNICIPIOidMUNICIPIO field is not nullable.");
                }
            }
            for (DomicilioCuentas domicilioCuentasCollectionOldDomicilioCuentas : domicilioCuentasCollectionOld) {
                if (!domicilioCuentasCollectionNew.contains(domicilioCuentasCollectionOldDomicilioCuentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DomicilioCuentas " + domicilioCuentasCollectionOldDomicilioCuentas + " since its MUNICIPIOidMUNICIPIO field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (DEPARTAMENTOidDEPARTAMENTONew != null) {
                DEPARTAMENTOidDEPARTAMENTONew = em.getReference(DEPARTAMENTOidDEPARTAMENTONew.getClass(), DEPARTAMENTOidDEPARTAMENTONew.getIdDEPARTAMENTO());
                municipio.setDEPARTAMENTOidDEPARTAMENTO(DEPARTAMENTOidDEPARTAMENTONew);
            }
            Collection<DomicilioProvee> attachedDomicilioProveeCollectionNew = new ArrayList<DomicilioProvee>();
            for (DomicilioProvee domicilioProveeCollectionNewDomicilioProveeToAttach : domicilioProveeCollectionNew) {
                domicilioProveeCollectionNewDomicilioProveeToAttach = em.getReference(domicilioProveeCollectionNewDomicilioProveeToAttach.getClass(), domicilioProveeCollectionNewDomicilioProveeToAttach.getDomicilioProveePK());
                attachedDomicilioProveeCollectionNew.add(domicilioProveeCollectionNewDomicilioProveeToAttach);
            }
            domicilioProveeCollectionNew = attachedDomicilioProveeCollectionNew;
            municipio.setDomicilioProveeCollection(domicilioProveeCollectionNew);
            Collection<DomicilioCuentas> attachedDomicilioCuentasCollectionNew = new ArrayList<DomicilioCuentas>();
            for (DomicilioCuentas domicilioCuentasCollectionNewDomicilioCuentasToAttach : domicilioCuentasCollectionNew) {
                domicilioCuentasCollectionNewDomicilioCuentasToAttach = em.getReference(domicilioCuentasCollectionNewDomicilioCuentasToAttach.getClass(), domicilioCuentasCollectionNewDomicilioCuentasToAttach.getDomicilioCuentasPK());
                attachedDomicilioCuentasCollectionNew.add(domicilioCuentasCollectionNewDomicilioCuentasToAttach);
            }
            domicilioCuentasCollectionNew = attachedDomicilioCuentasCollectionNew;
            municipio.setDomicilioCuentasCollection(domicilioCuentasCollectionNew);
            municipio = em.merge(municipio);
            if (DEPARTAMENTOidDEPARTAMENTOOld != null && !DEPARTAMENTOidDEPARTAMENTOOld.equals(DEPARTAMENTOidDEPARTAMENTONew)) {
                DEPARTAMENTOidDEPARTAMENTOOld.getMunicipioCollection().remove(municipio);
                DEPARTAMENTOidDEPARTAMENTOOld = em.merge(DEPARTAMENTOidDEPARTAMENTOOld);
            }
            if (DEPARTAMENTOidDEPARTAMENTONew != null && !DEPARTAMENTOidDEPARTAMENTONew.equals(DEPARTAMENTOidDEPARTAMENTOOld)) {
                DEPARTAMENTOidDEPARTAMENTONew.getMunicipioCollection().add(municipio);
                DEPARTAMENTOidDEPARTAMENTONew = em.merge(DEPARTAMENTOidDEPARTAMENTONew);
            }
            for (DomicilioProvee domicilioProveeCollectionNewDomicilioProvee : domicilioProveeCollectionNew) {
                if (!domicilioProveeCollectionOld.contains(domicilioProveeCollectionNewDomicilioProvee)) {
                    Municipio oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionNewDomicilioProvee = domicilioProveeCollectionNewDomicilioProvee.getMUNICIPIOidMUNICIPIO();
                    domicilioProveeCollectionNewDomicilioProvee.setMUNICIPIOidMUNICIPIO(municipio);
                    domicilioProveeCollectionNewDomicilioProvee = em.merge(domicilioProveeCollectionNewDomicilioProvee);
                    if (oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionNewDomicilioProvee != null && !oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionNewDomicilioProvee.equals(municipio)) {
                        oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionNewDomicilioProvee.getDomicilioProveeCollection().remove(domicilioProveeCollectionNewDomicilioProvee);
                        oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionNewDomicilioProvee = em.merge(oldMUNICIPIOidMUNICIPIOOfDomicilioProveeCollectionNewDomicilioProvee);
                    }
                }
            }
            for (DomicilioCuentas domicilioCuentasCollectionNewDomicilioCuentas : domicilioCuentasCollectionNew) {
                if (!domicilioCuentasCollectionOld.contains(domicilioCuentasCollectionNewDomicilioCuentas)) {
                    Municipio oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionNewDomicilioCuentas = domicilioCuentasCollectionNewDomicilioCuentas.getMUNICIPIOidMUNICIPIO();
                    domicilioCuentasCollectionNewDomicilioCuentas.setMUNICIPIOidMUNICIPIO(municipio);
                    domicilioCuentasCollectionNewDomicilioCuentas = em.merge(domicilioCuentasCollectionNewDomicilioCuentas);
                    if (oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionNewDomicilioCuentas != null && !oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionNewDomicilioCuentas.equals(municipio)) {
                        oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionNewDomicilioCuentas.getDomicilioCuentasCollection().remove(domicilioCuentasCollectionNewDomicilioCuentas);
                        oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionNewDomicilioCuentas = em.merge(oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasCollectionNewDomicilioCuentas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = municipio.getIdMUNICIPIO();
                if (findMunicipio(id) == null) {
                    throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.");
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
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getIdMUNICIPIO();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DomicilioProvee> domicilioProveeCollectionOrphanCheck = municipio.getDomicilioProveeCollection();
            for (DomicilioProvee domicilioProveeCollectionOrphanCheckDomicilioProvee : domicilioProveeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipio (" + municipio + ") cannot be destroyed since the DomicilioProvee " + domicilioProveeCollectionOrphanCheckDomicilioProvee + " in its domicilioProveeCollection field has a non-nullable MUNICIPIOidMUNICIPIO field.");
            }
            Collection<DomicilioCuentas> domicilioCuentasCollectionOrphanCheck = municipio.getDomicilioCuentasCollection();
            for (DomicilioCuentas domicilioCuentasCollectionOrphanCheckDomicilioCuentas : domicilioCuentasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipio (" + municipio + ") cannot be destroyed since the DomicilioCuentas " + domicilioCuentasCollectionOrphanCheckDomicilioCuentas + " in its domicilioCuentasCollection field has a non-nullable MUNICIPIOidMUNICIPIO field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento DEPARTAMENTOidDEPARTAMENTO = municipio.getDEPARTAMENTOidDEPARTAMENTO();
            if (DEPARTAMENTOidDEPARTAMENTO != null) {
                DEPARTAMENTOidDEPARTAMENTO.getMunicipioCollection().remove(municipio);
                DEPARTAMENTOidDEPARTAMENTO = em.merge(DEPARTAMENTOidDEPARTAMENTO);
            }
            em.remove(municipio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipio> findMunicipioEntities() {
        return findMunicipioEntities(true, -1, -1);
    }

    public List<Municipio> findMunicipioEntities(int maxResults, int firstResult) {
        return findMunicipioEntities(false, maxResults, firstResult);
    }

    private List<Municipio> findMunicipioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipio.class));
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

    public Municipio findMunicipio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipio> rt = cq.from(Municipio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
