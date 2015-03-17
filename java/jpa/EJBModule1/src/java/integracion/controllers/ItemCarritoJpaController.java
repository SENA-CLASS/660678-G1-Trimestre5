/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.controllers;

import integracion.controllers.exceptions.NonexistentEntityException;
import integracion.controllers.exceptions.PreexistingEntityException;
import integracion.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import integracion.entities.Producto;
import integracion.entities.CarritoCompras;
import integracion.entities.ItemCarrito;
import integracion.entities.ItemCarritoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class ItemCarritoJpaController implements Serializable {

    public ItemCarritoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemCarrito itemCarrito) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (itemCarrito.getItemCarritoPK() == null) {
            itemCarrito.setItemCarritoPK(new ItemCarritoPK());
        }
        itemCarrito.getItemCarritoPK().setProductoIdProducto(itemCarrito.getProducto().getIdProducto());
        itemCarrito.getItemCarritoPK().setCarritoComprasIdCarrito(itemCarrito.getCarritoCompras().getIdCarrito());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto producto = itemCarrito.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                itemCarrito.setProducto(producto);
            }
            CarritoCompras carritoCompras = itemCarrito.getCarritoCompras();
            if (carritoCompras != null) {
                carritoCompras = em.getReference(carritoCompras.getClass(), carritoCompras.getIdCarrito());
                itemCarrito.setCarritoCompras(carritoCompras);
            }
            em.persist(itemCarrito);
            if (producto != null) {
                producto.getItemCarritoList().add(itemCarrito);
                producto = em.merge(producto);
            }
            if (carritoCompras != null) {
                carritoCompras.getItemCarritoList().add(itemCarrito);
                carritoCompras = em.merge(carritoCompras);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findItemCarrito(itemCarrito.getItemCarritoPK()) != null) {
                throw new PreexistingEntityException("ItemCarrito " + itemCarrito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemCarrito itemCarrito) throws NonexistentEntityException, RollbackFailureException, Exception {
        itemCarrito.getItemCarritoPK().setProductoIdProducto(itemCarrito.getProducto().getIdProducto());
        itemCarrito.getItemCarritoPK().setCarritoComprasIdCarrito(itemCarrito.getCarritoCompras().getIdCarrito());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemCarrito persistentItemCarrito = em.find(ItemCarrito.class, itemCarrito.getItemCarritoPK());
            Producto productoOld = persistentItemCarrito.getProducto();
            Producto productoNew = itemCarrito.getProducto();
            CarritoCompras carritoComprasOld = persistentItemCarrito.getCarritoCompras();
            CarritoCompras carritoComprasNew = itemCarrito.getCarritoCompras();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                itemCarrito.setProducto(productoNew);
            }
            if (carritoComprasNew != null) {
                carritoComprasNew = em.getReference(carritoComprasNew.getClass(), carritoComprasNew.getIdCarrito());
                itemCarrito.setCarritoCompras(carritoComprasNew);
            }
            itemCarrito = em.merge(itemCarrito);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getItemCarritoList().remove(itemCarrito);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getItemCarritoList().add(itemCarrito);
                productoNew = em.merge(productoNew);
            }
            if (carritoComprasOld != null && !carritoComprasOld.equals(carritoComprasNew)) {
                carritoComprasOld.getItemCarritoList().remove(itemCarrito);
                carritoComprasOld = em.merge(carritoComprasOld);
            }
            if (carritoComprasNew != null && !carritoComprasNew.equals(carritoComprasOld)) {
                carritoComprasNew.getItemCarritoList().add(itemCarrito);
                carritoComprasNew = em.merge(carritoComprasNew);
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
                ItemCarritoPK id = itemCarrito.getItemCarritoPK();
                if (findItemCarrito(id) == null) {
                    throw new NonexistentEntityException("The itemCarrito with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ItemCarritoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemCarrito itemCarrito;
            try {
                itemCarrito = em.getReference(ItemCarrito.class, id);
                itemCarrito.getItemCarritoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemCarrito with id " + id + " no longer exists.", enfe);
            }
            Producto producto = itemCarrito.getProducto();
            if (producto != null) {
                producto.getItemCarritoList().remove(itemCarrito);
                producto = em.merge(producto);
            }
            CarritoCompras carritoCompras = itemCarrito.getCarritoCompras();
            if (carritoCompras != null) {
                carritoCompras.getItemCarritoList().remove(itemCarrito);
                carritoCompras = em.merge(carritoCompras);
            }
            em.remove(itemCarrito);
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

    public List<ItemCarrito> findItemCarritoEntities() {
        return findItemCarritoEntities(true, -1, -1);
    }

    public List<ItemCarrito> findItemCarritoEntities(int maxResults, int firstResult) {
        return findItemCarritoEntities(false, maxResults, firstResult);
    }

    private List<ItemCarrito> findItemCarritoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemCarrito.class));
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

    public ItemCarrito findItemCarrito(ItemCarritoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemCarrito.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCarritoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemCarrito> rt = cq.from(ItemCarrito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
