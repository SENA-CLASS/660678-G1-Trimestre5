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
public class EjemploConexion3 {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        java.sql.Connection conexion = null;

        try {
            conexion
                    = DriverManager.getConnection("jdbc:mysql://localhost/pijamax?"
                            + "user=nicolas&password=12345");
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
