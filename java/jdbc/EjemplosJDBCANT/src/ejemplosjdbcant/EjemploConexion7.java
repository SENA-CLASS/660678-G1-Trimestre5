/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplosjdbcant;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Enrique Moreno
 */
public class EjemploConexion7 {

    public static void main(String[] args) throws SQLException {
        java.sql.Connection conexion = null;
        String servidor = "jdbc:mysql://localhost/pijamax";
        String usuarioDB = "nicolas";
        String passwordDB = "12345";
        Statement sentencia = null;
        ResultSet rs = null;
        int i = 1;
        String sql = "SELECT * FROM pijamax.categorias where id_categoria=" + i+";";
        System.out.println(sql);
        
        try {
            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
            System.out.println("se conecto");
            sentencia = conexion.createStatement();
            
            rs = sentencia.executeQuery(sql);

            if (!rs.wasNull()) {
                while (rs.next() == true) {
                    System.out.println("id categoria: " + rs.getString(1));
                    System.out.println("activo: " + rs.getString(2));
                    System.out.println("nombre categoria: " + rs.getString("nombre_categoria"));
                    System.out.println("------------------------------------------------------");
                }
            } else {
                System.out.println("no hay datos");
            }

//se liberan los recursos utilizados por la sentencia
        } catch (SQLException e) {
            System.err.println(e.toString());
        } finally {
            //cerrar el statement
            if (sentencia != null) {
                sentencia.close();
                System.out.println("se cerro el statement");
            }
            //cerre el rs
            if (!rs.wasNull()) {
                rs.close();
                System.out.println("se cerro el resultset");
            }
            //cerre la conexion
            if (conexion != null) {
                conexion.close();
                System.out.println("se cerro la conexion correctamente");
            }
        }
    }
}
