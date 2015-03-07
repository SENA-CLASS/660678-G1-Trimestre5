/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplosjdbcant;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Enrique Moreno
 */
public class EjemploConexion10 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        java.sql.Connection conexion = null;
        String servidor = "jdbc:mysql://localhost/pijamax";
        String usuarioDB = "nicolas";
        String passwordDB = "12345";
        PreparedStatement sentencia = null;
        String sql = "INSERT INTO pijamax.categorias (  activo, nombre_categoria, descripcion ) VALUES ( ?, ?, ? )";

        System.out.println(sql);

        try {
            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
            System.out.println("se conecto");

            sentencia = conexion.prepareStatement(sql);

            sentencia.setShort(1, new Short("1"));
            sentencia.setString(2, "prueba categoria");
            sentencia.setString(3, "asdfasdfasdfsdf");
            System.out.println("sentencia ejecutada " + sql );

            int resultado = sentencia.executeUpdate();
			
            if (resultado>0){
                System.out.println("se insertaron "+resultado);
            }else{
                System.out.println("no se inserto");
            }

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
