/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.controllers;

import integracion.controllers.exceptions.NonexistentEntityException;
import integracion.controllers.exceptions.PreexistingEntityException;
import integracion.controllers.exceptions.RollbackFailureException;
import integracion.entities.InventarioCompras;
import integracion.entities.InventarioComprasPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import integracion.entities.Producto;
import integracion.entities.Proveedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class InventarioComprasJpaController implements Serializable {

    public InventarioComprasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(InventarioCompras inventarioCompras) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (inventarioCompras.getInventarioComprasPK() == null) {
            inventarioCompras.setInventarioComprasPK(new InventarioComprasPK());
        }
        inventarioCompras.getInventarioComprasPK().setProveedorTipoDocumento(inventarioCompras.getProveedor().getProveedorPK().getTipoDocumento());
        inventarioCompras.getInventarioComprasPK().setProveedorNumDocumento(inventarioCompras.getProveedor().getProveedorPK().getNumDocumento());
        inventarioCompras.getInventarioComprasPK().setProductoIdProducto(inventarioCompras.getProducto().getIdProducto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto producto = inventarioCompras.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getIdProducto());
                inventarioCompras.setProducto(producto);
            }
            Proveedor proveedor = inventarioCompras.getProveedor();
            if (proveedor != null) {
                proveedor = em.getReference(proveedor.getClass(), proveedor.getProveedorPK());
                inventarioCompras.setProveedor(proveedor);
            }
            em.persist(inventarioCompras);
            if (producto != null) {
                producto.getInventarioComprasList().add(inventarioCompras);
                producto = em.merge(producto);
            }
            if (proveedor != null) {
                proveedor.getInventarioComprasList().add(inventarioCompras);
                proveedor = em.merge(proveedor);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findInventarioCompras(inventarioCompras.getInventarioComprasPK()) != null) {
                throw new PreexistingEntityException("InventarioCompras " + inventarioCompras + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(InventarioCompras inventarioCompras) throws NonexistentEntityException, RollbackFailureException, Exception {
        inventarioCompras.getInventarioComprasPK().setProveedorTipoDocumento(inventarioCompras.getProveedor().getProveedorPK().getTipoDocumento());
        inventarioCompras.getInventarioComprasPK().setProveedorNumDocumento(inventarioCompras.getProveedor().getProveedorPK().getNumDocumento());
        inventarioCompras.getInventarioComprasPK().setProductoIdProducto(inventarioCompras.getProducto().getIdProducto());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InventarioCompras persistentInventarioCompras = em.find(InventarioCompras.class, inventarioCompras.getInventarioComprasPK());
            Producto productoOld = persistentInventarioCompras.getProducto();
            Producto productoNew = inventarioCompras.getProducto();
            Proveedor proveedorOld = persistentInventarioCompras.getProveedor();
            Proveedor proveedorNew = inventarioCompras.getProveedor();
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getIdProducto());
                inventarioCompras.setProducto(productoNew);
            }
            if (proveedorNew != null) {
                proveedorNew = em.getReference(proveedorNew.getClass(), proveedorNew.getProveedorPK());
                inventarioCompras.setProveedor(proveedorNew);
            }
            inventarioCompras = em.merge(inventarioCompras);
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getInventarioComprasList().remove(inventarioCompras);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getInventarioComprasList().add(inventarioCompras);
                productoNew = em.merge(productoNew);
            }
            if (proveedorOld != null && !proveedorOld.equals(proveedorNew)) {
                proveedorOld.getInventarioComprasList().remove(inventarioCompras);
                proveedorOld = em.merge(proveedorOld);
            }
            if (proveedorNew != null && !proveedorNew.equals(proveedorOld)) {
                proveedorNew.getInventarioComprasList().add(inventarioCompras);
                proveedorNew = em.merge(proveedorNew);
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
                InventarioComprasPK id = inventarioCompras.getInventarioComprasPK();
                if (findInventarioCompras(id) == null) {
                    throw new NonexistentEntityException("The inventarioCompras with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(InventarioComprasPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            InventarioCompras inventarioCompras;
            try {
                inventarioCompras = em.getReference(InventarioCompras.class, id);
                inventarioCompras.getInventarioComprasPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The inventarioCompras with id " + id + " no longer exists.", enfe);
            }
            Producto producto = inventarioCompras.getProducto();
            if (producto != null) {
                producto.getInventarioComprasList().remove(inventarioCompras);
                producto = em.merge(producto);
            }
            Proveedor proveedor = inventarioCompras.getProveedor();
            if (proveedor != null) {
                proveedor.getInventarioComprasList().remove(inventarioCompras);
                proveedor = em.merge(proveedor);
            }
            em.remove(inventarioCompras);
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

    public List<InventarioCompras> findInventarioComprasEntities() {
        return findInventarioComprasEntities(true, -1, -1);
    }

    public List<InventarioCompras> findInventarioComprasEntities(int maxResults, int firstResult) {
        return findInventarioComprasEntities(false, maxResults, firstResult);
    }

    private List<InventarioCompras> findInventarioComprasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(InventarioCompras.class));
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

    public InventarioCompras findInventarioCompras(InventarioComprasPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(InventarioCompras.class, id);
        } finally {
            em.close();
        }
    }

    public int getInventarioComprasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<InventarioCompras> rt = cq.from(InventarioCompras.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
