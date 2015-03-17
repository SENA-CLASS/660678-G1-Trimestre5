/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.sessionbeans;

import integracion.entities.CarritoCompras;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Enrique Moreno
 */
@Local
public interface CarritoComprasFacadeLocal {

    void create(CarritoCompras carritoCompras);

    void edit(CarritoCompras carritoCompras);

    void remove(CarritoCompras carritoCompras);

    CarritoCompras find(Object id);

    List<CarritoCompras> findAll();

    List<CarritoCompras> findRange(int[] range);

    int count();
    
}
