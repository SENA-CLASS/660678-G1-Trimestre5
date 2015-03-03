/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplo.negocio;

import edu.co.sena.ejemplo.DAO.CustommerDAO;
import edu.co.sena.ejemplo.DAO.DAOAbstractFactory;
import edu.co.sena.ejemplo.DAO.DAOFactory;
import edu.co.sena.ejemplo.DAO.EmployeedDAO;
import edu.co.sena.ejemplo.DAO.memory.MemoryDAOFactory;
import edu.co.sena.ejemplo.DAO.mysql.MySQLDAOFactory;
import edu.co.sena.ejemplo.DAO.oracle.OracleDAOFactory;
import edu.co.sena.ejemplo.model.Custommer;
import edu.co.sena.ejemplo.model.Employeed;



/**
 *
 * @author Enrique Moreno
 */
public class APP {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        Employeed emp1 = new Employeed();
        emp1.setIdEmpleado("12345");
        emp1.setPrimerNombre("jose");
        
        Custommer cli1 = new Custommer();
        cli1.setIdCustommer("12");
        cli1.setPrimerNombre("pedro");
        
        DAOFactory fabrica = DAOAbstractFactory.getDAOFactory(DAOAbstractFactory.MYSQL_FACTORY);
        EmployeedDAO emp1SalidoFabrica = fabrica.createEmployeedDAO();
        CustommerDAO cli1salidoFbrica = fabrica.createCustommerDAO();
        
        emp1SalidoFabrica.insert(emp1);
        emp1SalidoFabrica.update(emp1);
        emp1SalidoFabrica.delete(emp1);
        
        cli1salidoFbrica.insert(cli1);
        cli1salidoFbrica.update(cli1);
        cli1salidoFbrica.delete(cli1);
        
        
        
        
        
        
        
        
        
        
    }
    
}
