/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.co.sena.ejemplojdbcmaven;


import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Enrique Moreno
 */
public class EjemploConexion1 {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        java.sql.Connection conexion = null;
        String servidor = "jdbc:mysql://localhost/pijamax";
            String usuarioDB="nicolas";
            String passwordDB="12345";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            conexion= DriverManager.getConnection(servidor,usuarioDB,passwordDB);
            System.out.println("se conecto");
            conexion.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.toString());
        }finally{
            if (conexion!=null) {
                conexion.close();
                System.out.println("se cerro la conexion correctamente");
            }
        
        }
        
        
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.toString());
        }
        try {
            conexion= DriverManager.getConnection(servidor,usuarioDB,passwordDB);
            System.out.println("se conecto");
            
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        }finally{
            if (conexion!=null) {
                conexion.close();
                System.out.println("se cerro la conexion correctamente");
            }
        
        }
        
        
        
        
    }
    
}
