/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.sessionbeans;

import integracion.entities.DomicilioProvee;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Enrique Moreno
 */
@Local
public interface DomicilioProveeFacadeLocal {

    void create(DomicilioProvee domicilioProvee);

    void edit(DomicilioProvee domicilioProvee);

    void remove(DomicilioProvee domicilioProvee);

    DomicilioProvee find(Object id);

    List<DomicilioProvee> findAll();

    List<DomicilioProvee> findRange(int[] range);

    int count();
    
}
