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
import edu.co.sena.onlineshop.integracion.jpa.entities.Producto;
import edu.co.sena.onlineshop.integracion.jpa.entities.CarritoCompras;
import edu.co.sena.onlineshop.integracion.jpa.entities.ItemCarrito;
import edu.co.sena.onlineshop.integracion.jpa.entities.ItemCarritoPK;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.NonexistentEntityException;
import edu.co.sena.onlineshop.integracion.jpa.controllers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author hernando
 */
public class ItemCarritoJpaController implements Serializable {

    public ItemCarritoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemCarrito itemCarrito) throws PreexistingEntityException, Exception {
        if (itemCarrito.getItemCarritoPK() == null) {
            itemCarrito.setItemCarritoPK(new ItemCarritoPK());
        }
        itemCarrito.getItemCarritoPK().setProductoIdProducto(itemCarrito.getProducto().getIdProducto());
        itemCarrito.getItemCarritoPK().setCarritoComprasIdCarrito(itemCarrito.getCarritoCompras().getIdCarrito());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
                producto.getItemCarritoCollection().add(itemCarrito);
                producto = em.merge(producto);
            }
            if (carritoCompras != null) {
                carritoCompras.getItemCarritoCollection().add(itemCarrito);
                carritoCompras = em.merge(carritoCompras);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void edit(ItemCarrito itemCarrito) throws NonexistentEntityException, Exception {
        itemCarrito.getItemCarritoPK().setProductoIdProducto(itemCarrito.getProducto().getIdProducto());
        itemCarrito.getItemCarritoPK().setCarritoComprasIdCarrito(itemCarrito.getCarritoCompras().getIdCarrito());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
                productoOld.getItemCarritoCollection().remove(itemCarrito);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getItemCarritoCollection().add(itemCarrito);
                productoNew = em.merge(productoNew);
            }
            if (carritoComprasOld != null && !carritoComprasOld.equals(carritoComprasNew)) {
                carritoComprasOld.getItemCarritoCollection().remove(itemCarrito);
                carritoComprasOld = em.merge(carritoComprasOld);
            }
            if (carritoComprasNew != null && !carritoComprasNew.equals(carritoComprasOld)) {
                carritoComprasNew.getItemCarritoCollection().add(itemCarrito);
                carritoComprasNew = em.merge(carritoComprasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(ItemCarritoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ItemCarrito itemCarrito;
            try {
                itemCarrito = em.getReference(ItemCarrito.class, id);
                itemCarrito.getItemCarritoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemCarrito with id " + id + " no longer exists.", enfe);
            }
            Producto producto = itemCarrito.getProducto();
            if (producto != null) {
                producto.getItemCarritoCollection().remove(itemCarrito);
                producto = em.merge(producto);
            }
            CarritoCompras carritoCompras = itemCarrito.getCarritoCompras();
            if (carritoCompras != null) {
                carritoCompras.getItemCarritoCollection().remove(itemCarrito);
                carritoCompras = em.merge(carritoCompras);
            }
            em.remove(itemCarrito);
            em.getTransaction().commit();
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
