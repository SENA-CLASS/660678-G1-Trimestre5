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
import integracion.entities.Departamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class DepartamentoJpaController implements Serializable {

    public DepartamentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamento departamento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (departamento.getMunicipioList() == null) {
            departamento.setMunicipioList(new ArrayList<Municipio>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Municipio> attachedMunicipioList = new ArrayList<Municipio>();
            for (Municipio municipioListMunicipioToAttach : departamento.getMunicipioList()) {
                municipioListMunicipioToAttach = em.getReference(municipioListMunicipioToAttach.getClass(), municipioListMunicipioToAttach.getIdMUNICIPIO());
                attachedMunicipioList.add(municipioListMunicipioToAttach);
            }
            departamento.setMunicipioList(attachedMunicipioList);
            em.persist(departamento);
            for (Municipio municipioListMunicipio : departamento.getMunicipioList()) {
                Departamento oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListMunicipio = municipioListMunicipio.getDEPARTAMENTOidDEPARTAMENTO();
                municipioListMunicipio.setDEPARTAMENTOidDEPARTAMENTO(departamento);
                municipioListMunicipio = em.merge(municipioListMunicipio);
                if (oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListMunicipio != null) {
                    oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListMunicipio.getMunicipioList().remove(municipioListMunicipio);
                    oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListMunicipio = em.merge(oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListMunicipio);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDepartamento(departamento.getIdDEPARTAMENTO()) != null) {
                throw new PreexistingEntityException("Departamento " + departamento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamento departamento) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Departamento persistentDepartamento = em.find(Departamento.class, departamento.getIdDEPARTAMENTO());
            List<Municipio> municipioListOld = persistentDepartamento.getMunicipioList();
            List<Municipio> municipioListNew = departamento.getMunicipioList();
            List<String> illegalOrphanMessages = null;
            for (Municipio municipioListOldMunicipio : municipioListOld) {
                if (!municipioListNew.contains(municipioListOldMunicipio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Municipio " + municipioListOldMunicipio + " since its DEPARTAMENTOidDEPARTAMENTO field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Municipio> attachedMunicipioListNew = new ArrayList<Municipio>();
            for (Municipio municipioListNewMunicipioToAttach : municipioListNew) {
                municipioListNewMunicipioToAttach = em.getReference(municipioListNewMunicipioToAttach.getClass(), municipioListNewMunicipioToAttach.getIdMUNICIPIO());
                attachedMunicipioListNew.add(municipioListNewMunicipioToAttach);
            }
            municipioListNew = attachedMunicipioListNew;
            departamento.setMunicipioList(municipioListNew);
            departamento = em.merge(departamento);
            for (Municipio municipioListNewMunicipio : municipioListNew) {
                if (!municipioListOld.contains(municipioListNewMunicipio)) {
                    Departamento oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListNewMunicipio = municipioListNewMunicipio.getDEPARTAMENTOidDEPARTAMENTO();
                    municipioListNewMunicipio.setDEPARTAMENTOidDEPARTAMENTO(departamento);
                    municipioListNewMunicipio = em.merge(municipioListNewMunicipio);
                    if (oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListNewMunicipio != null && !oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListNewMunicipio.equals(departamento)) {
                        oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListNewMunicipio.getMunicipioList().remove(municipioListNewMunicipio);
                        oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListNewMunicipio = em.merge(oldDEPARTAMENTOidDEPARTAMENTOOfMunicipioListNewMunicipio);
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
                Integer id = departamento.getIdDEPARTAMENTO();
                if (findDepartamento(id) == null) {
                    throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.");
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
            Departamento departamento;
            try {
                departamento = em.getReference(Departamento.class, id);
                departamento.getIdDEPARTAMENTO();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Municipio> municipioListOrphanCheck = departamento.getMunicipioList();
            for (Municipio municipioListOrphanCheckMunicipio : municipioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamento (" + departamento + ") cannot be destroyed since the Municipio " + municipioListOrphanCheckMunicipio + " in its municipioList field has a non-nullable DEPARTAMENTOidDEPARTAMENTO field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(departamento);
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

    public List<Departamento> findDepartamentoEntities() {
        return findDepartamentoEntities(true, -1, -1);
    }

    public List<Departamento> findDepartamentoEntities(int maxResults, int firstResult) {
        return findDepartamentoEntities(false, maxResults, firstResult);
    }

    private List<Departamento> findDepartamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamento.class));
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

    public Departamento findDepartamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamento> rt = cq.from(Departamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
