/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.sessionbeans;

import integracion.entities.InventarioCompras;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Enrique Moreno
 */
@Local
public interface InventarioComprasFacadeLocal {

    void create(InventarioCompras inventarioCompras);

    void edit(InventarioCompras inventarioCompras);

    void remove(InventarioCompras inventarioCompras);

    InventarioCompras find(Object id);

    List<InventarioCompras> findAll();

    List<InventarioCompras> findRange(int[] range);

    int count();
    
}
