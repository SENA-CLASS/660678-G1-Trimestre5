/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejemplosjdbcant;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Enrique Moreno
 */
public class EjemploConexion12 {

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) throws SQLException {
        java.sql.Connection conexion = null;
        String servidor = "jdbc:mysql://localhost/pijamax";
        String usuarioDB = "nicolas";
        String passwordDB = "12345";
        CallableStatement sentencia = null;
        ResultSet rs  =null;
        String sql = "call pijamax.tablaItem(?);";
        
        
        System.out.println(sql);

        try {
            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
            System.out.println("se conecto");
            DatabaseMetaData ejemeta = conexion.getMetaData();
            System.out.println("obtuvo el metadata");
            System.out.println(ejemeta.getDatabaseProductName());
            System.out.println(ejemeta.getDriverVersion());
            System.out.println(ejemeta.getDatabaseMajorVersion());
            System.out.println(ejemeta.getDatabaseMinorVersion());
            System.out.println(ejemeta.getIdentifierQuoteString());
            System.out.println(ejemeta.getMaxRowSize());
            
        } catch (SQLException e) {
            System.err.println("error: " + e.toString());
        } finally {
            //cerrar el statement
            if (sentencia != null) {
                sentencia.close();
                System.out.println("se cerro el statement");
            }

            //cerre la conexion
            if (conexion != null) {
                conexion.close();
                System.out.println("se cerro la conexion correctamente");
            }
        }
    }
    
}
