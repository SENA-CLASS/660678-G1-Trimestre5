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
import integracion.entities.CarritoCompras;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import integracion.entities.ItemCarrito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class CarritoComprasJpaController implements Serializable {

    public CarritoComprasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CarritoCompras carritoCompras) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (carritoCompras.getItemCarritoList() == null) {
            carritoCompras.setItemCarritoList(new ArrayList<ItemCarrito>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<ItemCarrito> attachedItemCarritoList = new ArrayList<ItemCarrito>();
            for (ItemCarrito itemCarritoListItemCarritoToAttach : carritoCompras.getItemCarritoList()) {
                itemCarritoListItemCarritoToAttach = em.getReference(itemCarritoListItemCarritoToAttach.getClass(), itemCarritoListItemCarritoToAttach.getItemCarritoPK());
                attachedItemCarritoList.add(itemCarritoListItemCarritoToAttach);
            }
            carritoCompras.setItemCarritoList(attachedItemCarritoList);
            em.persist(carritoCompras);
            for (ItemCarrito itemCarritoListItemCarrito : carritoCompras.getItemCarritoList()) {
                CarritoCompras oldCarritoComprasOfItemCarritoListItemCarrito = itemCarritoListItemCarrito.getCarritoCompras();
                itemCarritoListItemCarrito.setCarritoCompras(carritoCompras);
                itemCarritoListItemCarrito = em.merge(itemCarritoListItemCarrito);
                if (oldCarritoComprasOfItemCarritoListItemCarrito != null) {
                    oldCarritoComprasOfItemCarritoListItemCarrito.getItemCarritoList().remove(itemCarritoListItemCarrito);
                    oldCarritoComprasOfItemCarritoListItemCarrito = em.merge(oldCarritoComprasOfItemCarritoListItemCarrito);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCarritoCompras(carritoCompras.getIdCarrito()) != null) {
                throw new PreexistingEntityException("CarritoCompras " + carritoCompras + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CarritoCompras carritoCompras) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CarritoCompras persistentCarritoCompras = em.find(CarritoCompras.class, carritoCompras.getIdCarrito());
            List<ItemCarrito> itemCarritoListOld = persistentCarritoCompras.getItemCarritoList();
            List<ItemCarrito> itemCarritoListNew = carritoCompras.getItemCarritoList();
            List<String> illegalOrphanMessages = null;
            for (ItemCarrito itemCarritoListOldItemCarrito : itemCarritoListOld) {
                if (!itemCarritoListNew.contains(itemCarritoListOldItemCarrito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemCarrito " + itemCarritoListOldItemCarrito + " since its carritoCompras field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ItemCarrito> attachedItemCarritoListNew = new ArrayList<ItemCarrito>();
            for (ItemCarrito itemCarritoListNewItemCarritoToAttach : itemCarritoListNew) {
                itemCarritoListNewItemCarritoToAttach = em.getReference(itemCarritoListNewItemCarritoToAttach.getClass(), itemCarritoListNewItemCarritoToAttach.getItemCarritoPK());
                attachedItemCarritoListNew.add(itemCarritoListNewItemCarritoToAttach);
            }
            itemCarritoListNew = attachedItemCarritoListNew;
            carritoCompras.setItemCarritoList(itemCarritoListNew);
            carritoCompras = em.merge(carritoCompras);
            for (ItemCarrito itemCarritoListNewItemCarrito : itemCarritoListNew) {
                if (!itemCarritoListOld.contains(itemCarritoListNewItemCarrito)) {
                    CarritoCompras oldCarritoComprasOfItemCarritoListNewItemCarrito = itemCarritoListNewItemCarrito.getCarritoCompras();
                    itemCarritoListNewItemCarrito.setCarritoCompras(carritoCompras);
                    itemCarritoListNewItemCarrito = em.merge(itemCarritoListNewItemCarrito);
                    if (oldCarritoComprasOfItemCarritoListNewItemCarrito != null && !oldCarritoComprasOfItemCarritoListNewItemCarrito.equals(carritoCompras)) {
                        oldCarritoComprasOfItemCarritoListNewItemCarrito.getItemCarritoList().remove(itemCarritoListNewItemCarrito);
                        oldCarritoComprasOfItemCarritoListNewItemCarrito = em.merge(oldCarritoComprasOfItemCarritoListNewItemCarrito);
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
                String id = carritoCompras.getIdCarrito();
                if (findCarritoCompras(id) == null) {
                    throw new NonexistentEntityException("The carritoCompras with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CarritoCompras carritoCompras;
            try {
                carritoCompras = em.getReference(CarritoCompras.class, id);
                carritoCompras.getIdCarrito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carritoCompras with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ItemCarrito> itemCarritoListOrphanCheck = carritoCompras.getItemCarritoList();
            for (ItemCarrito itemCarritoListOrphanCheckItemCarrito : itemCarritoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CarritoCompras (" + carritoCompras + ") cannot be destroyed since the ItemCarrito " + itemCarritoListOrphanCheckItemCarrito + " in its itemCarritoList field has a non-nullable carritoCompras field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(carritoCompras);
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

    public List<CarritoCompras> findCarritoComprasEntities() {
        return findCarritoComprasEntities(true, -1, -1);
    }

    public List<CarritoCompras> findCarritoComprasEntities(int maxResults, int firstResult) {
        return findCarritoComprasEntities(false, maxResults, firstResult);
    }

    private List<CarritoCompras> findCarritoComprasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CarritoCompras.class));
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

    public CarritoCompras findCarritoCompras(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CarritoCompras.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarritoComprasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CarritoCompras> rt = cq.from(CarritoCompras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
