/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.controllers;

import integracion.controllers.exceptions.IllegalOrphanException;
import integracion.controllers.exceptions.NonexistentEntityException;
import integracion.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import integracion.entities.Pago;
import integracion.entities.Cuenta;
import integracion.entities.Factura;
import integracion.entities.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Enrique Moreno
 */
public class FacturaJpaController implements Serializable {

    public FacturaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pago pago = factura.getPago();
            if (pago != null) {
                pago = em.getReference(pago.getClass(), pago.getFacturaIdFactura());
                factura.setPago(pago);
            }
            Cuenta cuenta = factura.getCuenta();
            if (cuenta != null) {
                cuenta = em.getReference(cuenta.getClass(), cuenta.getCuentaPK());
                factura.setCuenta(cuenta);
            }
            Pedido pedido = factura.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getFacturaIdFactura());
                factura.setPedido(pedido);
            }
            em.persist(factura);
            if (pago != null) {
                Factura oldFacturaOfPago = pago.getFactura();
                if (oldFacturaOfPago != null) {
                    oldFacturaOfPago.setPago(null);
                    oldFacturaOfPago = em.merge(oldFacturaOfPago);
                }
                pago.setFactura(factura);
                pago = em.merge(pago);
            }
            if (cuenta != null) {
                cuenta.getFacturaList().add(factura);
                cuenta = em.merge(cuenta);
            }
            if (pedido != null) {
                Factura oldFacturaOfPedido = pedido.getFactura();
                if (oldFacturaOfPedido != null) {
                    oldFacturaOfPedido.setPedido(null);
                    oldFacturaOfPedido = em.merge(oldFacturaOfPedido);
                }
                pedido.setFactura(factura);
                pedido = em.merge(pedido);
            }
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

    public void edit(Factura factura) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Factura persistentFactura = em.find(Factura.class, factura.getIdFactura());
            Pago pagoOld = persistentFactura.getPago();
            Pago pagoNew = factura.getPago();
            Cuenta cuentaOld = persistentFactura.getCuenta();
            Cuenta cuentaNew = factura.getCuenta();
            Pedido pedidoOld = persistentFactura.getPedido();
            Pedido pedidoNew = factura.getPedido();
            List<String> illegalOrphanMessages = null;
            if (pagoOld != null && !pagoOld.equals(pagoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pago " + pagoOld + " since its factura field is not nullable.");
            }
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pedido " + pedidoOld + " since its factura field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pagoNew != null) {
                pagoNew = em.getReference(pagoNew.getClass(), pagoNew.getFacturaIdFactura());
                factura.setPago(pagoNew);
            }
            if (cuentaNew != null) {
                cuentaNew = em.getReference(cuentaNew.getClass(), cuentaNew.getCuentaPK());
                factura.setCuenta(cuentaNew);
            }
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getFacturaIdFactura());
                factura.setPedido(pedidoNew);
            }
            factura = em.merge(factura);
            if (pagoNew != null && !pagoNew.equals(pagoOld)) {
                Factura oldFacturaOfPago = pagoNew.getFactura();
                if (oldFacturaOfPago != null) {
                    oldFacturaOfPago.setPago(null);
                    oldFacturaOfPago = em.merge(oldFacturaOfPago);
                }
                pagoNew.setFactura(factura);
                pagoNew = em.merge(pagoNew);
            }
            if (cuentaOld != null && !cuentaOld.equals(cuentaNew)) {
                cuentaOld.getFacturaList().remove(factura);
                cuentaOld = em.merge(cuentaOld);
            }
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                cuentaNew.getFacturaList().add(factura);
                cuentaNew = em.merge(cuentaNew);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                Factura oldFacturaOfPedido = pedidoNew.getFactura();
                if (oldFacturaOfPedido != null) {
                    oldFacturaOfPedido.setPedido(null);
                    oldFacturaOfPedido = em.merge(oldFacturaOfPedido);
                }
                pedidoNew.setFactura(factura);
                pedidoNew = em.merge(pedidoNew);
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
                Integer id = factura.getIdFactura();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getIdFactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Pago pagoOrphanCheck = factura.getPago();
            if (pagoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the Pago " + pagoOrphanCheck + " in its pago field has a non-nullable factura field.");
            }
            Pedido pedidoOrphanCheck = factura.getPedido();
            if (pedidoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the Pedido " + pedidoOrphanCheck + " in its pedido field has a non-nullable factura field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cuenta cuenta = factura.getCuenta();
            if (cuenta != null) {
                cuenta.getFacturaList().remove(factura);
                cuenta = em.merge(cuenta);
            }
            em.remove(factura);
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

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
