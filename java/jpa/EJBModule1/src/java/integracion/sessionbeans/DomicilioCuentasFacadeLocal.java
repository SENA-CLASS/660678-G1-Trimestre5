/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.sessionbeans;

import integracion.entities.DomicilioCuentas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Enrique Moreno
 */
@Local
public interface DomicilioCuentasFacadeLocal {

    void create(DomicilioCuentas domicilioCuentas);

    void edit(DomicilioCuentas domicilioCuentas);

    void remove(DomicilioCuentas domicilioCuentas);

    DomicilioCuentas find(Object id);

    List<DomicilioCuentas> findAll();

    List<DomicilioCuentas> findRange(int[] range);

    int count();
    
}
