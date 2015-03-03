/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.co.sena.ejemplo.DAO;

import edu.co.sena.ejemplo.DAO.memory.MemoryDAOFactory;
import edu.co.sena.ejemplo.DAO.mysql.MySQLDAOFactory;
import edu.co.sena.ejemplo.DAO.oracle.OracleDAOFactory;

/**
 *
 * @author Enrique Moreno
 */
public abstract class DAOAbstractFactory implements DAOFactory {

    public final static int ORACLE_FACTORY = 1;
    public final static int MYSQL_FACTORY = 2;
    public final static int MEMORY_FACTORY = 3;

    public final static DAOAbstractFactory getDAOFactory(int factoryType) throws Exception {
        switch (factoryType) {

            case ORACLE_FACTORY: {
                return new OracleDAOFactory();
            }
            case MYSQL_FACTORY: {
                return new MySQLDAOFactory();
            }
            case MEMORY_FACTORY: {
                return new MemoryDAOFactory();
            }
            default: {
                System.out.println("El tipo de fabrica no puede ser implementada o no existe");
                return null;
            }
        }
    }

}
