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
import integracion.entities.Departamento;
import integracion.entities.DomicilioProvee;
import java.util.ArrayList;
import java.util.List;
import integracion.entities.DomicilioCuentas;
import integracion.entities.Municipio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class MunicipioJpaController implements Serializable {

    public MunicipioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipio municipio) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (municipio.getDomicilioProveeList() == null) {
            municipio.setDomicilioProveeList(new ArrayList<DomicilioProvee>());
        }
        if (municipio.getDomicilioCuentasList() == null) {
            municipio.setDomicilioCuentasList(new ArrayList<DomicilioCuentas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Departamento DEPARTAMENTOidDEPARTAMENTO = municipio.getDEPARTAMENTOidDEPARTAMENTO();
            if (DEPARTAMENTOidDEPARTAMENTO != null) {
                DEPARTAMENTOidDEPARTAMENTO = em.getReference(DEPARTAMENTOidDEPARTAMENTO.getClass(), DEPARTAMENTOidDEPARTAMENTO.getIdDEPARTAMENTO());
                municipio.setDEPARTAMENTOidDEPARTAMENTO(DEPARTAMENTOidDEPARTAMENTO);
            }
            List<DomicilioProvee> attachedDomicilioProveeList = new ArrayList<DomicilioProvee>();
            for (DomicilioProvee domicilioProveeListDomicilioProveeToAttach : municipio.getDomicilioProveeList()) {
                domicilioProveeListDomicilioProveeToAttach = em.getReference(domicilioProveeListDomicilioProveeToAttach.getClass(), domicilioProveeListDomicilioProveeToAttach.getDomicilioProveePK());
                attachedDomicilioProveeList.add(domicilioProveeListDomicilioProveeToAttach);
            }
            municipio.setDomicilioProveeList(attachedDomicilioProveeList);
            List<DomicilioCuentas> attachedDomicilioCuentasList = new ArrayList<DomicilioCuentas>();
            for (DomicilioCuentas domicilioCuentasListDomicilioCuentasToAttach : municipio.getDomicilioCuentasList()) {
                domicilioCuentasListDomicilioCuentasToAttach = em.getReference(domicilioCuentasListDomicilioCuentasToAttach.getClass(), domicilioCuentasListDomicilioCuentasToAttach.getDomicilioCuentasPK());
                attachedDomicilioCuentasList.add(domicilioCuentasListDomicilioCuentasToAttach);
            }
            municipio.setDomicilioCuentasList(attachedDomicilioCuentasList);
            em.persist(municipio);
            if (DEPARTAMENTOidDEPARTAMENTO != null) {
                DEPARTAMENTOidDEPARTAMENTO.getMunicipioList().add(municipio);
                DEPARTAMENTOidDEPARTAMENTO = em.merge(DEPARTAMENTOidDEPARTAMENTO);
            }
            for (DomicilioProvee domicilioProveeListDomicilioProvee : municipio.getDomicilioProveeList()) {
                Municipio oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListDomicilioProvee = domicilioProveeListDomicilioProvee.getMUNICIPIOidMUNICIPIO();
                domicilioProveeListDomicilioProvee.setMUNICIPIOidMUNICIPIO(municipio);
                domicilioProveeListDomicilioProvee = em.merge(domicilioProveeListDomicilioProvee);
                if (oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListDomicilioProvee != null) {
                    oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListDomicilioProvee.getDomicilioProveeList().remove(domicilioProveeListDomicilioProvee);
                    oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListDomicilioProvee = em.merge(oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListDomicilioProvee);
                }
            }
            for (DomicilioCuentas domicilioCuentasListDomicilioCuentas : municipio.getDomicilioCuentasList()) {
                Municipio oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListDomicilioCuentas = domicilioCuentasListDomicilioCuentas.getMUNICIPIOidMUNICIPIO();
                domicilioCuentasListDomicilioCuentas.setMUNICIPIOidMUNICIPIO(municipio);
                domicilioCuentasListDomicilioCuentas = em.merge(domicilioCuentasListDomicilioCuentas);
                if (oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListDomicilioCuentas != null) {
                    oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListDomicilioCuentas.getDomicilioCuentasList().remove(domicilioCuentasListDomicilioCuentas);
                    oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListDomicilioCuentas = em.merge(oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListDomicilioCuentas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
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

    public void edit(Municipio municipio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getIdMUNICIPIO());
            Departamento DEPARTAMENTOidDEPARTAMENTOOld = persistentMunicipio.getDEPARTAMENTOidDEPARTAMENTO();
            Departamento DEPARTAMENTOidDEPARTAMENTONew = municipio.getDEPARTAMENTOidDEPARTAMENTO();
            List<DomicilioProvee> domicilioProveeListOld = persistentMunicipio.getDomicilioProveeList();
            List<DomicilioProvee> domicilioProveeListNew = municipio.getDomicilioProveeList();
            List<DomicilioCuentas> domicilioCuentasListOld = persistentMunicipio.getDomicilioCuentasList();
            List<DomicilioCuentas> domicilioCuentasListNew = municipio.getDomicilioCuentasList();
            List<String> illegalOrphanMessages = null;
            for (DomicilioProvee domicilioProveeListOldDomicilioProvee : domicilioProveeListOld) {
                if (!domicilioProveeListNew.contains(domicilioProveeListOldDomicilioProvee)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DomicilioProvee " + domicilioProveeListOldDomicilioProvee + " since its MUNICIPIOidMUNICIPIO field is not nullable.");
                }
            }
            for (DomicilioCuentas domicilioCuentasListOldDomicilioCuentas : domicilioCuentasListOld) {
                if (!domicilioCuentasListNew.contains(domicilioCuentasListOldDomicilioCuentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DomicilioCuentas " + domicilioCuentasListOldDomicilioCuentas + " since its MUNICIPIOidMUNICIPIO field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (DEPARTAMENTOidDEPARTAMENTONew != null) {
                DEPARTAMENTOidDEPARTAMENTONew = em.getReference(DEPARTAMENTOidDEPARTAMENTONew.getClass(), DEPARTAMENTOidDEPARTAMENTONew.getIdDEPARTAMENTO());
                municipio.setDEPARTAMENTOidDEPARTAMENTO(DEPARTAMENTOidDEPARTAMENTONew);
            }
            List<DomicilioProvee> attachedDomicilioProveeListNew = new ArrayList<DomicilioProvee>();
            for (DomicilioProvee domicilioProveeListNewDomicilioProveeToAttach : domicilioProveeListNew) {
                domicilioProveeListNewDomicilioProveeToAttach = em.getReference(domicilioProveeListNewDomicilioProveeToAttach.getClass(), domicilioProveeListNewDomicilioProveeToAttach.getDomicilioProveePK());
                attachedDomicilioProveeListNew.add(domicilioProveeListNewDomicilioProveeToAttach);
            }
            domicilioProveeListNew = attachedDomicilioProveeListNew;
            municipio.setDomicilioProveeList(domicilioProveeListNew);
            List<DomicilioCuentas> attachedDomicilioCuentasListNew = new ArrayList<DomicilioCuentas>();
            for (DomicilioCuentas domicilioCuentasListNewDomicilioCuentasToAttach : domicilioCuentasListNew) {
                domicilioCuentasListNewDomicilioCuentasToAttach = em.getReference(domicilioCuentasListNewDomicilioCuentasToAttach.getClass(), domicilioCuentasListNewDomicilioCuentasToAttach.getDomicilioCuentasPK());
                attachedDomicilioCuentasListNew.add(domicilioCuentasListNewDomicilioCuentasToAttach);
            }
            domicilioCuentasListNew = attachedDomicilioCuentasListNew;
            municipio.setDomicilioCuentasList(domicilioCuentasListNew);
            municipio = em.merge(municipio);
            if (DEPARTAMENTOidDEPARTAMENTOOld != null && !DEPARTAMENTOidDEPARTAMENTOOld.equals(DEPARTAMENTOidDEPARTAMENTONew)) {
                DEPARTAMENTOidDEPARTAMENTOOld.getMunicipioList().remove(municipio);
                DEPARTAMENTOidDEPARTAMENTOOld = em.merge(DEPARTAMENTOidDEPARTAMENTOOld);
            }
            if (DEPARTAMENTOidDEPARTAMENTONew != null && !DEPARTAMENTOidDEPARTAMENTONew.equals(DEPARTAMENTOidDEPARTAMENTOOld)) {
                DEPARTAMENTOidDEPARTAMENTONew.getMunicipioList().add(municipio);
                DEPARTAMENTOidDEPARTAMENTONew = em.merge(DEPARTAMENTOidDEPARTAMENTONew);
            }
            for (DomicilioProvee domicilioProveeListNewDomicilioProvee : domicilioProveeListNew) {
                if (!domicilioProveeListOld.contains(domicilioProveeListNewDomicilioProvee)) {
                    Municipio oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListNewDomicilioProvee = domicilioProveeListNewDomicilioProvee.getMUNICIPIOidMUNICIPIO();
                    domicilioProveeListNewDomicilioProvee.setMUNICIPIOidMUNICIPIO(municipio);
                    domicilioProveeListNewDomicilioProvee = em.merge(domicilioProveeListNewDomicilioProvee);
                    if (oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListNewDomicilioProvee != null && !oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListNewDomicilioProvee.equals(municipio)) {
                        oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListNewDomicilioProvee.getDomicilioProveeList().remove(domicilioProveeListNewDomicilioProvee);
                        oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListNewDomicilioProvee = em.merge(oldMUNICIPIOidMUNICIPIOOfDomicilioProveeListNewDomicilioProvee);
                    }
                }
            }
            for (DomicilioCuentas domicilioCuentasListNewDomicilioCuentas : domicilioCuentasListNew) {
                if (!domicilioCuentasListOld.contains(domicilioCuentasListNewDomicilioCuentas)) {
                    Municipio oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListNewDomicilioCuentas = domicilioCuentasListNewDomicilioCuentas.getMUNICIPIOidMUNICIPIO();
                    domicilioCuentasListNewDomicilioCuentas.setMUNICIPIOidMUNICIPIO(municipio);
                    domicilioCuentasListNewDomicilioCuentas = em.merge(domicilioCuentasListNewDomicilioCuentas);
                    if (oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListNewDomicilioCuentas != null && !oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListNewDomicilioCuentas.equals(municipio)) {
                        oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListNewDomicilioCuentas.getDomicilioCuentasList().remove(domicilioCuentasListNewDomicilioCuentas);
                        oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListNewDomicilioCuentas = em.merge(oldMUNICIPIOidMUNICIPIOOfDomicilioCuentasListNewDomicilioCuentas);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getIdMUNICIPIO();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DomicilioProvee> domicilioProveeListOrphanCheck = municipio.getDomicilioProveeList();
            for (DomicilioProvee domicilioProveeListOrphanCheckDomicilioProvee : domicilioProveeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipio (" + municipio + ") cannot be destroyed since the DomicilioProvee " + domicilioProveeListOrphanCheckDomicilioProvee + " in its domicilioProveeList field has a non-nullable MUNICIPIOidMUNICIPIO field.");
            }
            List<DomicilioCuentas> domicilioCuentasListOrphanCheck = municipio.getDomicilioCuentasList();
            for (DomicilioCuentas domicilioCuentasListOrphanCheckDomicilioCuentas : domicilioCuentasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipio (" + municipio + ") cannot be destroyed since the DomicilioCuentas " + domicilioCuentasListOrphanCheckDomicilioCuentas + " in its domicilioCuentasList field has a non-nullable MUNICIPIOidMUNICIPIO field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento DEPARTAMENTOidDEPARTAMENTO = municipio.getDEPARTAMENTOidDEPARTAMENTO();
            if (DEPARTAMENTOidDEPARTAMENTO != null) {
                DEPARTAMENTOidDEPARTAMENTO.getMunicipioList().remove(municipio);
                DEPARTAMENTOidDEPARTAMENTO = em.merge(DEPARTAMENTOidDEPARTAMENTO);
            }
            em.remove(municipio);
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
