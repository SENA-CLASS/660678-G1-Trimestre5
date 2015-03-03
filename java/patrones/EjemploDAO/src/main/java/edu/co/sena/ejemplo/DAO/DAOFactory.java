/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplo.DAO;

/**
 *
 * @author Enrique Moreno
 */
public interface DAOFactory {
    public EmployeedDAO createEmployeedDAO();
    public CustommerDAO createCustommerDAO();
}
