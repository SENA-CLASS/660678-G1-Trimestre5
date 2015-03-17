/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package integracion.sessionbeans;

import integracion.entities.Catalogo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Enrique Moreno
 */
@Local
public interface CatalogoFacadeLocal {

    void create(Catalogo catalogo);

    void edit(Catalogo catalogo);

    void remove(Catalogo catalogo);

    Catalogo find(Object id);

    List<Catalogo> findAll();

    List<Catalogo> findRange(int[] range);

    int count();
    
}
