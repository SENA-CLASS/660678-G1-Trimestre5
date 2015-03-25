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
import edu.co.sena.onlineshop.integracion.jpa.entities.Catalogo;
import edu.co.sena.onlineshop.integracion.jpa.entities.Categoria;
import edu.co.sena.onlineshop.integracion.jpa.entities.Item;
import java.util.ArrayList;
import java.util.Collection;
import edu.co.sena.onlineshop.integracion.jpa.entities.ItemCarrito;
import edu.co.sena.onlineshop.integracion.jpa.entities.InventarioCompras;
import edu.co.sena.onlineshop.integracion.jpa.entities.Producto;
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
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getItemCollection() == null) {
            producto.setItemCollection(new ArrayList<Item>());
        }
        if (producto.getItemCarritoCollection() == null) {
            producto.setItemCarritoCollection(new ArrayList<ItemCarrito>());
        }
        if (producto.getInventarioComprasCollection() == null) {
            producto.setInventarioComprasCollection(new ArrayList<InventarioCompras>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catalogo catalogoIdCatalogo = producto.getCatalogoIdCatalogo();
            if (catalogoIdCatalogo != null) {
                catalogoIdCatalogo = em.getReference(catalogoIdCatalogo.getClass(), catalogoIdCatalogo.getIdCatalogo());
                producto.setCatalogoIdCatalogo(catalogoIdCatalogo);
            }
            Categoria categoriaIdCategoria = producto.getCategoriaIdCategoria();
            if (categoriaIdCategoria != null) {
                categoriaIdCategoria = em.getReference(categoriaIdCategoria.getClass(), categoriaIdCategoria.getIdCategoria());
                producto.setCategoriaIdCategoria(categoriaIdCategoria);
            }
            Collection<Item> attachedItemCollection = new ArrayList<Item>();
            for (Item itemCollectionItemToAttach : producto.getItemCollection()) {
                itemCollectionItemToAttach = em.getReference(itemCollectionItemToAttach.getClass(), itemCollectionItemToAttach.getItemPK());
                attachedItemCollection.add(itemCollectionItemToAttach);
            }
            producto.setItemCollection(attachedItemCollection);
            Collection<ItemCarrito> attachedItemCarritoCollection = new ArrayList<ItemCarrito>();
            for (ItemCarrito itemCarritoCollectionItemCarritoToAttach : producto.getItemCarritoCollection()) {
                itemCarritoCollectionItemCarritoToAttach = em.getReference(itemCarritoCollectionItemCarritoToAttach.getClass(), itemCarritoCollectionItemCarritoToAttach.getItemCarritoPK());
                attachedItemCarritoCollection.add(itemCarritoCollectionItemCarritoToAttach);
            }
            producto.setItemCarritoCollection(attachedItemCarritoCollection);
            Collection<InventarioCompras> attachedInventarioComprasCollection = new ArrayList<InventarioCompras>();
            for (InventarioCompras inventarioComprasCollectionInventarioComprasToAttach : producto.getInventarioComprasCollection()) {
                inventarioComprasCollectionInventarioComprasToAttach = em.getReference(inventarioComprasCollectionInventarioComprasToAttach.getClass(), inventarioComprasCollectionInventarioComprasToAttach.getInventarioComprasPK());
                attachedInventarioComprasCollection.add(inventarioComprasCollectionInventarioComprasToAttach);
            }
            producto.setInventarioComprasCollection(attachedInventarioComprasCollection);
            em.persist(producto);
            if (catalogoIdCatalogo != null) {
                catalogoIdCatalogo.getProductoCollection().add(producto);
                catalogoIdCatalogo = em.merge(catalogoIdCatalogo);
            }
            if (categoriaIdCategoria != null) {
                categoriaIdCategoria.getProductoCollection().add(producto);
                categoriaIdCategoria = em.merge(categoriaIdCategoria);
            }
            for (Item itemCollectionItem : producto.getItemCollection()) {
                Producto oldProductoOfItemCollectionItem = itemCollectionItem.getProducto();
                itemCollectionItem.setProducto(producto);
                itemCollectionItem = em.merge(itemCollectionItem);
                if (oldProductoOfItemCollectionItem != null) {
                    oldProductoOfItemCollectionItem.getItemCollection().remove(itemCollectionItem);
                    oldProductoOfItemCollectionItem = em.merge(oldProductoOfItemCollectionItem);
                }
            }
            for (ItemCarrito itemCarritoCollectionItemCarrito : producto.getItemCarritoCollection()) {
                Producto oldProductoOfItemCarritoCollectionItemCarrito = itemCarritoCollectionItemCarrito.getProducto();
                itemCarritoCollectionItemCarrito.setProducto(producto);
                itemCarritoCollectionItemCarrito = em.merge(itemCarritoCollectionItemCarrito);
                if (oldProductoOfItemCarritoCollectionItemCarrito != null) {
                    oldProductoOfItemCarritoCollectionItemCarrito.getItemCarritoCollection().remove(itemCarritoCollectionItemCarrito);
                    oldProductoOfItemCarritoCollectionItemCarrito = em.merge(oldProductoOfItemCarritoCollectionItemCarrito);
                }
            }
            for (InventarioCompras inventarioComprasCollectionInventarioCompras : producto.getInventarioComprasCollection()) {
                Producto oldProductoOfInventarioComprasCollectionInventarioCompras = inventarioComprasCollectionInventarioCompras.getProducto();
                inventarioComprasCollectionInventarioCompras.setProducto(producto);
                inventarioComprasCollectionInventarioCompras = em.merge(inventarioComprasCollectionInventarioCompras);
                if (oldProductoOfInventarioComprasCollectionInventarioCompras != null) {
                    oldProductoOfInventarioComprasCollectionInventarioCompras.getInventarioComprasCollection().remove(inventarioComprasCollectionInventarioCompras);
                    oldProductoOfInventarioComprasCollectionInventarioCompras = em.merge(oldProductoOfInventarioComprasCollectionInventarioCompras);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProducto(producto.getIdProducto()) != null) {
                throw new PreexistingEntityException("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Catalogo catalogoIdCatalogoOld = persistentProducto.getCatalogoIdCatalogo();
            Catalogo catalogoIdCatalogoNew = producto.getCatalogoIdCatalogo();
            Categoria categoriaIdCategoriaOld = persistentProducto.getCategoriaIdCategoria();
            Categoria categoriaIdCategoriaNew = producto.getCategoriaIdCategoria();
            Collection<Item> itemCollectionOld = persistentProducto.getItemCollection();
            Collection<Item> itemCollectionNew = producto.getItemCollection();
            Collection<ItemCarrito> itemCarritoCollectionOld = persistentProducto.getItemCarritoCollection();
            Collection<ItemCarrito> itemCarritoCollectionNew = producto.getItemCarritoCollection();
            Collection<InventarioCompras> inventarioComprasCollectionOld = persistentProducto.getInventarioComprasCollection();
            Collection<InventarioCompras> inventarioComprasCollectionNew = producto.getInventarioComprasCollection();
            List<String> illegalOrphanMessages = null;
            for (Item itemCollectionOldItem : itemCollectionOld) {
                if (!itemCollectionNew.contains(itemCollectionOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemCollectionOldItem + " since its producto field is not nullable.");
                }
            }
            for (ItemCarrito itemCarritoCollectionOldItemCarrito : itemCarritoCollectionOld) {
                if (!itemCarritoCollectionNew.contains(itemCarritoCollectionOldItemCarrito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemCarrito " + itemCarritoCollectionOldItemCarrito + " since its producto field is not nullable.");
                }
            }
            for (InventarioCompras inventarioComprasCollectionOldInventarioCompras : inventarioComprasCollectionOld) {
                if (!inventarioComprasCollectionNew.contains(inventarioComprasCollectionOldInventarioCompras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InventarioCompras " + inventarioComprasCollectionOldInventarioCompras + " since its producto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (catalogoIdCatalogoNew != null) {
                catalogoIdCatalogoNew = em.getReference(catalogoIdCatalogoNew.getClass(), catalogoIdCatalogoNew.getIdCatalogo());
                producto.setCatalogoIdCatalogo(catalogoIdCatalogoNew);
            }
            if (categoriaIdCategoriaNew != null) {
                categoriaIdCategoriaNew = em.getReference(categoriaIdCategoriaNew.getClass(), categoriaIdCategoriaNew.getIdCategoria());
                producto.setCategoriaIdCategoria(categoriaIdCategoriaNew);
            }
            Collection<Item> attachedItemCollectionNew = new ArrayList<Item>();
            for (Item itemCollectionNewItemToAttach : itemCollectionNew) {
                itemCollectionNewItemToAttach = em.getReference(itemCollectionNewItemToAttach.getClass(), itemCollectionNewItemToAttach.getItemPK());
                attachedItemCollectionNew.add(itemCollectionNewItemToAttach);
            }
            itemCollectionNew = attachedItemCollectionNew;
            producto.setItemCollection(itemCollectionNew);
            Collection<ItemCarrito> attachedItemCarritoCollectionNew = new ArrayList<ItemCarrito>();
            for (ItemCarrito itemCarritoCollectionNewItemCarritoToAttach : itemCarritoCollectionNew) {
                itemCarritoCollectionNewItemCarritoToAttach = em.getReference(itemCarritoCollectionNewItemCarritoToAttach.getClass(), itemCarritoCollectionNewItemCarritoToAttach.getItemCarritoPK());
                attachedItemCarritoCollectionNew.add(itemCarritoCollectionNewItemCarritoToAttach);
            }
            itemCarritoCollectionNew = attachedItemCarritoCollectionNew;
            producto.setItemCarritoCollection(itemCarritoCollectionNew);
            Collection<InventarioCompras> attachedInventarioComprasCollectionNew = new ArrayList<InventarioCompras>();
            for (InventarioCompras inventarioComprasCollectionNewInventarioComprasToAttach : inventarioComprasCollectionNew) {
                inventarioComprasCollectionNewInventarioComprasToAttach = em.getReference(inventarioComprasCollectionNewInventarioComprasToAttach.getClass(), inventarioComprasCollectionNewInventarioComprasToAttach.getInventarioComprasPK());
                attachedInventarioComprasCollectionNew.add(inventarioComprasCollectionNewInventarioComprasToAttach);
            }
            inventarioComprasCollectionNew = attachedInventarioComprasCollectionNew;
            producto.setInventarioComprasCollection(inventarioComprasCollectionNew);
            producto = em.merge(producto);
            if (catalogoIdCatalogoOld != null && !catalogoIdCatalogoOld.equals(catalogoIdCatalogoNew)) {
                catalogoIdCatalogoOld.getProductoCollection().remove(producto);
                catalogoIdCatalogoOld = em.merge(catalogoIdCatalogoOld);
            }
            if (catalogoIdCatalogoNew != null && !catalogoIdCatalogoNew.equals(catalogoIdCatalogoOld)) {
                catalogoIdCatalogoNew.getProductoCollection().add(producto);
                catalogoIdCatalogoNew = em.merge(catalogoIdCatalogoNew);
            }
            if (categoriaIdCategoriaOld != null && !categoriaIdCategoriaOld.equals(categoriaIdCategoriaNew)) {
                categoriaIdCategoriaOld.getProductoCollection().remove(producto);
                categoriaIdCategoriaOld = em.merge(categoriaIdCategoriaOld);
            }
            if (categoriaIdCategoriaNew != null && !categoriaIdCategoriaNew.equals(categoriaIdCategoriaOld)) {
                categoriaIdCategoriaNew.getProductoCollection().add(producto);
                categoriaIdCategoriaNew = em.merge(categoriaIdCategoriaNew);
            }
            for (Item itemCollectionNewItem : itemCollectionNew) {
                if (!itemCollectionOld.contains(itemCollectionNewItem)) {
                    Producto oldProductoOfItemCollectionNewItem = itemCollectionNewItem.getProducto();
                    itemCollectionNewItem.setProducto(producto);
                    itemCollectionNewItem = em.merge(itemCollectionNewItem);
                    if (oldProductoOfItemCollectionNewItem != null && !oldProductoOfItemCollectionNewItem.equals(producto)) {
                        oldProductoOfItemCollectionNewItem.getItemCollection().remove(itemCollectionNewItem);
                        oldProductoOfItemCollectionNewItem = em.merge(oldProductoOfItemCollectionNewItem);
                    }
                }
            }
            for (ItemCarrito itemCarritoCollectionNewItemCarrito : itemCarritoCollectionNew) {
                if (!itemCarritoCollectionOld.contains(itemCarritoCollectionNewItemCarrito)) {
                    Producto oldProductoOfItemCarritoCollectionNewItemCarrito = itemCarritoCollectionNewItemCarrito.getProducto();
                    itemCarritoCollectionNewItemCarrito.setProducto(producto);
                    itemCarritoCollectionNewItemCarrito = em.merge(itemCarritoCollectionNewItemCarrito);
                    if (oldProductoOfItemCarritoCollectionNewItemCarrito != null && !oldProductoOfItemCarritoCollectionNewItemCarrito.equals(producto)) {
                        oldProductoOfItemCarritoCollectionNewItemCarrito.getItemCarritoCollection().remove(itemCarritoCollectionNewItemCarrito);
                        oldProductoOfItemCarritoCollectionNewItemCarrito = em.merge(oldProductoOfItemCarritoCollectionNewItemCarrito);
                    }
                }
            }
            for (InventarioCompras inventarioComprasCollectionNewInventarioCompras : inventarioComprasCollectionNew) {
                if (!inventarioComprasCollectionOld.contains(inventarioComprasCollectionNewInventarioCompras)) {
                    Producto oldProductoOfInventarioComprasCollectionNewInventarioCompras = inventarioComprasCollectionNewInventarioCompras.getProducto();
                    inventarioComprasCollectionNewInventarioCompras.setProducto(producto);
                    inventarioComprasCollectionNewInventarioCompras = em.merge(inventarioComprasCollectionNewInventarioCompras);
                    if (oldProductoOfInventarioComprasCollectionNewInventarioCompras != null && !oldProductoOfInventarioComprasCollectionNewInventarioCompras.equals(producto)) {
                        oldProductoOfInventarioComprasCollectionNewInventarioCompras.getInventarioComprasCollection().remove(inventarioComprasCollectionNewInventarioCompras);
                        oldProductoOfInventarioComprasCollectionNewInventarioCompras = em.merge(oldProductoOfInventarioComprasCollectionNewInventarioCompras);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Item> itemCollectionOrphanCheck = producto.getItemCollection();
            for (Item itemCollectionOrphanCheckItem : itemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Item " + itemCollectionOrphanCheckItem + " in its itemCollection field has a non-nullable producto field.");
            }
            Collection<ItemCarrito> itemCarritoCollectionOrphanCheck = producto.getItemCarritoCollection();
            for (ItemCarrito itemCarritoCollectionOrphanCheckItemCarrito : itemCarritoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ItemCarrito " + itemCarritoCollectionOrphanCheckItemCarrito + " in its itemCarritoCollection field has a non-nullable producto field.");
            }
            Collection<InventarioCompras> inventarioComprasCollectionOrphanCheck = producto.getInventarioComprasCollection();
            for (InventarioCompras inventarioComprasCollectionOrphanCheckInventarioCompras : inventarioComprasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the InventarioCompras " + inventarioComprasCollectionOrphanCheckInventarioCompras + " in its inventarioComprasCollection field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Catalogo catalogoIdCatalogo = producto.getCatalogoIdCatalogo();
            if (catalogoIdCatalogo != null) {
                catalogoIdCatalogo.getProductoCollection().remove(producto);
                catalogoIdCatalogo = em.merge(catalogoIdCatalogo);
            }
            Categoria categoriaIdCategoria = producto.getCategoriaIdCategoria();
            if (categoriaIdCategoria != null) {
                categoriaIdCategoria.getProductoCollection().remove(producto);
                categoriaIdCategoria = em.merge(categoriaIdCategoria);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
