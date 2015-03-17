/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.sessionbeans;

import integracion.entities.ItemCarrito;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Enrique Moreno
 */
@Stateless
public class ItemCarritoFacade extends AbstractFacade<ItemCarrito> implements ItemCarritoFacadeLocal {
    @PersistenceContext(unitName = "EJBModule1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemCarritoFacade() {
        super(ItemCarrito.class);
    }
    
}
