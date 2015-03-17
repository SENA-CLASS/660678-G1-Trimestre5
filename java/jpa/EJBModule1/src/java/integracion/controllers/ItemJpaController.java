/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.controllers;

import integracion.controllers.exceptions.NonexistentEntityException;
import integracion.controllers.exceptions.PreexistingEntityException;
import integracion.controllers.exceptions.RollbackFailureException;
import integracion.entities.Item;
import integracion.entities.ItemPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import integracion.entities.Pedido;
import integracion.entities.Producto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class ItemJpaController implements Serializable {

    public ItemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Item item) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (item.getItemPK() == null) {
            item.setItemPK(new ItemPK());
        }
        item.getItemPK().setProductoIdProducto(item.getProducto().getIdProducto());
        item.getItemPK().setPedidoFacturaIdFactura(item.getPedido().getFacturaIdFactura());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pedido pedido = item.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getFacturaIdFactura());
                item.setPedido(pedido);
            }
            Producto producto = item.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                item.setProducto(producto);
            }
            em.persist(item);
            if (pedido != null) {
                pedido.getItemList().add(item);
                pedido = em.merge(pedido);
            }
            if (producto != null) {
                producto.getItemList().add(item);
                producto = em.merge(producto);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findItem(item.getItemPK()) != null) {
                throw new PreexistingEntityException("Item " + item + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Item item) throws NonexistentEntityException, RollbackFailureException, Exception {
        item.getItemPK().setProductoIdProducto(item.getProducto().getIdProducto());
        item.getItemPK().setPedidoFacturaIdFactura(item.getPedido().getFacturaIdFactura());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item persistentItem = em.find(Item.class, item.getItemPK());
            Pedido pedidoOld = persistentItem.getPedido();
            Pedido pedidoNew = item.getPedido();
            Producto productoOld = persistentItem.getProducto();
            Producto productoNew = item.getProducto();
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getFacturaIdFactura());
                item.setPedido(pedidoNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                item.setProducto(productoNew);
            }
            item = em.merge(item);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getItemList().remove(item);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getItemList().add(item);
                pedidoNew = em.merge(pedidoNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getItemList().remove(item);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getItemList().add(item);
                productoNew = em.merge(productoNew);
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
                ItemPK id = item.getItemPK();
                if (findItem(id) == null) {
                    throw new NonexistentEntityException("The item with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ItemPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item item;
            try {
                item = em.getReference(Item.class, id);
                item.getItemPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The item with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = item.getPedido();
            if (pedido != null) {
                pedido.getItemList().remove(item);
                pedido = em.merge(pedido);
            }
            Producto producto = item.getProducto();
            if (producto != null) {
                producto.getItemList().remove(item);
                producto = em.merge(producto);
            }
            em.remove(item);
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

    public List<Item> findItemEntities() {
        return findItemEntities(true, -1, -1);
    }

    public List<Item> findItemEntities(int maxResults, int firstResult) {
        return findItemEntities(false, maxResults, firstResult);
    }

    private List<Item> findItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
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

    public Item findItem(ItemPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Item.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Item> rt = cq.from(Item.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
