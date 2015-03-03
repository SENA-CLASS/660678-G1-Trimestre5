/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejemplosjdbcant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Enrique Moreno
 */
public class EjemploConexion2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
       Connection conexion = null;
        
        try {
            
            String servidor = "jdbc:mysql://localhost/pijamax";
            String usuarioDB="nicolas";
            String passwordDB="12345";
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
