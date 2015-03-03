/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplosjdbcant;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Enrique Moreno
 */
public class EjemploConexion5 {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        //mysql
        java.sql.Connection conexion = null;
        String servidor = "jdbc:mysql://localhost:3306/pijamax";
        String usuarioDB = "nicolas";
        String passwordDB = "12345";
        try {
            

            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
            System.out.println("se conecto mysql");
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        } finally {
            if (conexion != null) {
                conexion.close();
                System.out.println("se cerro la conexion correctamente");
            }
        }
        
        //postgreSQL
        java.sql.Connection conexion2 = null;
        String servidor2 = "jdbc:postgresql://localhost:5432/hibernatedb";
        String usuarioDB2 = "postgres";
        String passwordDB2 = "root";
        try {
            

            conexion2 = DriverManager.getConnection(servidor2, usuarioDB2, passwordDB2);
            System.out.println("se conecto en prostgreSQL");
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        } finally {
            if (conexion2 != null) {
                conexion2.close();
                System.out.println("se cerro la conexion correctamente");
            }
        }
        
        //oracle
        java.sql.Connection conexion3 = null;
        String servidor3 = "jdbc:oracle:thin:@localhost:1521:XE";
        String usuarioDB3 = "SENA";
        String passwordDB3 = "Sena2014";
        try {
            

            conexion3 = DriverManager.getConnection(servidor3, usuarioDB3, passwordDB3);
            System.out.println("se conecto en Oracle");
            
        } catch (SQLException e) {
            System.err.println(e.toString());
        } finally {
            if (conexion3 != null) {
                conexion3.close();
                System.out.println("se cerro la conexion correctamente");
            }
        }
        
        
        
        
    }

}
