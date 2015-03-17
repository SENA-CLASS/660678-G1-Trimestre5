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
import integracion.entities.Catalogo;
import integracion.entities.Categoria;
import integracion.entities.Item;
import java.util.ArrayList;
import java.util.List;
import integracion.entities.ItemCarrito;
import integracion.entities.InventarioCompras;
import integracion.entities.Producto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (producto.getItemList() == null) {
            producto.setItemList(new ArrayList<Item>());
        }
        if (producto.getItemCarritoList() == null) {
            producto.setItemCarritoList(new ArrayList<ItemCarrito>());
        }
        if (producto.getInventarioComprasList() == null) {
            producto.setInventarioComprasList(new ArrayList<InventarioCompras>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
            List<Item> attachedItemList = new ArrayList<Item>();
            for (Item itemListItemToAttach : producto.getItemList()) {
                itemListItemToAttach = em.getReference(itemListItemToAttach.getClass(), itemListItemToAttach.getItemPK());
                attachedItemList.add(itemListItemToAttach);
            }
            producto.setItemList(attachedItemList);
            List<ItemCarrito> attachedItemCarritoList = new ArrayList<ItemCarrito>();
            for (ItemCarrito itemCarritoListItemCarritoToAttach : producto.getItemCarritoList()) {
                itemCarritoListItemCarritoToAttach = em.getReference(itemCarritoListItemCarritoToAttach.getClass(), itemCarritoListItemCarritoToAttach.getItemCarritoPK());
                attachedItemCarritoList.add(itemCarritoListItemCarritoToAttach);
            }
            producto.setItemCarritoList(attachedItemCarritoList);
            List<InventarioCompras> attachedInventarioComprasList = new ArrayList<InventarioCompras>();
            for (InventarioCompras inventarioComprasListInventarioComprasToAttach : producto.getInventarioComprasList()) {
                inventarioComprasListInventarioComprasToAttach = em.getReference(inventarioComprasListInventarioComprasToAttach.getClass(), inventarioComprasListInventarioComprasToAttach.getInventarioComprasPK());
                attachedInventarioComprasList.add(inventarioComprasListInventarioComprasToAttach);
            }
            producto.setInventarioComprasList(attachedInventarioComprasList);
            em.persist(producto);
            if (catalogoIdCatalogo != null) {
                catalogoIdCatalogo.getProductoList().add(producto);
                catalogoIdCatalogo = em.merge(catalogoIdCatalogo);
            }
            if (categoriaIdCategoria != null) {
                categoriaIdCategoria.getProductoList().add(producto);
                categoriaIdCategoria = em.merge(categoriaIdCategoria);
            }
            for (Item itemListItem : producto.getItemList()) {
                Producto oldProductoOfItemListItem = itemListItem.getProducto();
                itemListItem.setProducto(producto);
                itemListItem = em.merge(itemListItem);
                if (oldProductoOfItemListItem != null) {
                    oldProductoOfItemListItem.getItemList().remove(itemListItem);
                    oldProductoOfItemListItem = em.merge(oldProductoOfItemListItem);
                }
            }
            for (ItemCarrito itemCarritoListItemCarrito : producto.getItemCarritoList()) {
                Producto oldProductoOfItemCarritoListItemCarrito = itemCarritoListItemCarrito.getProducto();
                itemCarritoListItemCarrito.setProducto(producto);
                itemCarritoListItemCarrito = em.merge(itemCarritoListItemCarrito);
                if (oldProductoOfItemCarritoListItemCarrito != null) {
                    oldProductoOfItemCarritoListItemCarrito.getItemCarritoList().remove(itemCarritoListItemCarrito);
                    oldProductoOfItemCarritoListItemCarrito = em.merge(oldProductoOfItemCarritoListItemCarrito);
                }
            }
            for (InventarioCompras inventarioComprasListInventarioCompras : producto.getInventarioComprasList()) {
                Producto oldProductoOfInventarioComprasListInventarioCompras = inventarioComprasListInventarioCompras.getProducto();
                inventarioComprasListInventarioCompras.setProducto(producto);
                inventarioComprasListInventarioCompras = em.merge(inventarioComprasListInventarioCompras);
                if (oldProductoOfInventarioComprasListInventarioCompras != null) {
                    oldProductoOfInventarioComprasListInventarioCompras.getInventarioComprasList().remove(inventarioComprasListInventarioCompras);
                    oldProductoOfInventarioComprasListInventarioCompras = em.merge(oldProductoOfInventarioComprasListInventarioCompras);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
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

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Catalogo catalogoIdCatalogoOld = persistentProducto.getCatalogoIdCatalogo();
            Catalogo catalogoIdCatalogoNew = producto.getCatalogoIdCatalogo();
            Categoria categoriaIdCategoriaOld = persistentProducto.getCategoriaIdCategoria();
            Categoria categoriaIdCategoriaNew = producto.getCategoriaIdCategoria();
            List<Item> itemListOld = persistentProducto.getItemList();
            List<Item> itemListNew = producto.getItemList();
            List<ItemCarrito> itemCarritoListOld = persistentProducto.getItemCarritoList();
            List<ItemCarrito> itemCarritoListNew = producto.getItemCarritoList();
            List<InventarioCompras> inventarioComprasListOld = persistentProducto.getInventarioComprasList();
            List<InventarioCompras> inventarioComprasListNew = producto.getInventarioComprasList();
            List<String> illegalOrphanMessages = null;
            for (Item itemListOldItem : itemListOld) {
                if (!itemListNew.contains(itemListOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemListOldItem + " since its producto field is not nullable.");
                }
            }
            for (ItemCarrito itemCarritoListOldItemCarrito : itemCarritoListOld) {
                if (!itemCarritoListNew.contains(itemCarritoListOldItemCarrito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ItemCarrito " + itemCarritoListOldItemCarrito + " since its producto field is not nullable.");
                }
            }
            for (InventarioCompras inventarioComprasListOldInventarioCompras : inventarioComprasListOld) {
                if (!inventarioComprasListNew.contains(inventarioComprasListOldInventarioCompras)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain InventarioCompras " + inventarioComprasListOldInventarioCompras + " since its producto field is not nullable.");
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
            List<Item> attachedItemListNew = new ArrayList<Item>();
            for (Item itemListNewItemToAttach : itemListNew) {
                itemListNewItemToAttach = em.getReference(itemListNewItemToAttach.getClass(), itemListNewItemToAttach.getItemPK());
                attachedItemListNew.add(itemListNewItemToAttach);
            }
            itemListNew = attachedItemListNew;
            producto.setItemList(itemListNew);
            List<ItemCarrito> attachedItemCarritoListNew = new ArrayList<ItemCarrito>();
            for (ItemCarrito itemCarritoListNewItemCarritoToAttach : itemCarritoListNew) {
                itemCarritoListNewItemCarritoToAttach = em.getReference(itemCarritoListNewItemCarritoToAttach.getClass(), itemCarritoListNewItemCarritoToAttach.getItemCarritoPK());
                attachedItemCarritoListNew.add(itemCarritoListNewItemCarritoToAttach);
            }
            itemCarritoListNew = attachedItemCarritoListNew;
            producto.setItemCarritoList(itemCarritoListNew);
            List<InventarioCompras> attachedInventarioComprasListNew = new ArrayList<InventarioCompras>();
            for (InventarioCompras inventarioComprasListNewInventarioComprasToAttach : inventarioComprasListNew) {
                inventarioComprasListNewInventarioComprasToAttach = em.getReference(inventarioComprasListNewInventarioComprasToAttach.getClass(), inventarioComprasListNewInventarioComprasToAttach.getInventarioComprasPK());
                attachedInventarioComprasListNew.add(inventarioComprasListNewInventarioComprasToAttach);
            }
            inventarioComprasListNew = attachedInventarioComprasListNew;
            producto.setInventarioComprasList(inventarioComprasListNew);
            producto = em.merge(producto);
            if (catalogoIdCatalogoOld != null && !catalogoIdCatalogoOld.equals(catalogoIdCatalogoNew)) {
                catalogoIdCatalogoOld.getProductoList().remove(producto);
                catalogoIdCatalogoOld = em.merge(catalogoIdCatalogoOld);
            }
            if (catalogoIdCatalogoNew != null && !catalogoIdCatalogoNew.equals(catalogoIdCatalogoOld)) {
                catalogoIdCatalogoNew.getProductoList().add(producto);
                catalogoIdCatalogoNew = em.merge(catalogoIdCatalogoNew);
            }
            if (categoriaIdCategoriaOld != null && !categoriaIdCategoriaOld.equals(categoriaIdCategoriaNew)) {
                categoriaIdCategoriaOld.getProductoList().remove(producto);
                categoriaIdCategoriaOld = em.merge(categoriaIdCategoriaOld);
            }
            if (categoriaIdCategoriaNew != null && !categoriaIdCategoriaNew.equals(categoriaIdCategoriaOld)) {
                categoriaIdCategoriaNew.getProductoList().add(producto);
                categoriaIdCategoriaNew = em.merge(categoriaIdCategoriaNew);
            }
            for (Item itemListNewItem : itemListNew) {
                if (!itemListOld.contains(itemListNewItem)) {
                    Producto oldProductoOfItemListNewItem = itemListNewItem.getProducto();
                    itemListNewItem.setProducto(producto);
                    itemListNewItem = em.merge(itemListNewItem);
                    if (oldProductoOfItemListNewItem != null && !oldProductoOfItemListNewItem.equals(producto)) {
                        oldProductoOfItemListNewItem.getItemList().remove(itemListNewItem);
                        oldProductoOfItemListNewItem = em.merge(oldProductoOfItemListNewItem);
                    }
                }
            }
            for (ItemCarrito itemCarritoListNewItemCarrito : itemCarritoListNew) {
                if (!itemCarritoListOld.contains(itemCarritoListNewItemCarrito)) {
                    Producto oldProductoOfItemCarritoListNewItemCarrito = itemCarritoListNewItemCarrito.getProducto();
                    itemCarritoListNewItemCarrito.setProducto(producto);
                    itemCarritoListNewItemCarrito = em.merge(itemCarritoListNewItemCarrito);
                    if (oldProductoOfItemCarritoListNewItemCarrito != null && !oldProductoOfItemCarritoListNewItemCarrito.equals(producto)) {
                        oldProductoOfItemCarritoListNewItemCarrito.getItemCarritoList().remove(itemCarritoListNewItemCarrito);
                        oldProductoOfItemCarritoListNewItemCarrito = em.merge(oldProductoOfItemCarritoListNewItemCarrito);
                    }
                }
            }
            for (InventarioCompras inventarioComprasListNewInventarioCompras : inventarioComprasListNew) {
                if (!inventarioComprasListOld.contains(inventarioComprasListNewInventarioCompras)) {
                    Producto oldProductoOfInventarioComprasListNewInventarioCompras = inventarioComprasListNewInventarioCompras.getProducto();
                    inventarioComprasListNewInventarioCompras.setProducto(producto);
                    inventarioComprasListNewInventarioCompras = em.merge(inventarioComprasListNewInventarioCompras);
                    if (oldProductoOfInventarioComprasListNewInventarioCompras != null && !oldProductoOfInventarioComprasListNewInventarioCompras.equals(producto)) {
                        oldProductoOfInventarioComprasListNewInventarioCompras.getInventarioComprasList().remove(inventarioComprasListNewInventarioCompras);
                        oldProductoOfInventarioComprasListNewInventarioCompras = em.merge(oldProductoOfInventarioComprasListNewInventarioCompras);
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Item> itemListOrphanCheck = producto.getItemList();
            for (Item itemListOrphanCheckItem : itemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Item " + itemListOrphanCheckItem + " in its itemList field has a non-nullable producto field.");
            }
            List<ItemCarrito> itemCarritoListOrphanCheck = producto.getItemCarritoList();
            for (ItemCarrito itemCarritoListOrphanCheckItemCarrito : itemCarritoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ItemCarrito " + itemCarritoListOrphanCheckItemCarrito + " in its itemCarritoList field has a non-nullable producto field.");
            }
            List<InventarioCompras> inventarioComprasListOrphanCheck = producto.getInventarioComprasList();
            for (InventarioCompras inventarioComprasListOrphanCheckInventarioCompras : inventarioComprasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the InventarioCompras " + inventarioComprasListOrphanCheckInventarioCompras + " in its inventarioComprasList field has a non-nullable producto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Catalogo catalogoIdCatalogo = producto.getCatalogoIdCatalogo();
            if (catalogoIdCatalogo != null) {
                catalogoIdCatalogo.getProductoList().remove(producto);
                catalogoIdCatalogo = em.merge(catalogoIdCatalogo);
            }
            Categoria categoriaIdCategoria = producto.getCategoriaIdCategoria();
            if (categoriaIdCategoria != null) {
                categoriaIdCategoria.getProductoList().remove(producto);
                categoriaIdCategoria = em.merge(categoriaIdCategoria);
            }
            em.remove(producto);
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
