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
public class EjemploConexion8 {

    public static void main(String[] args) throws SQLException {
        java.sql.Connection conexion = null;
        String servidor = "jdbc:mysql://localhost/pijamax";
        String usuarioDB = "nicolas";
        String passwordDB = "12345";
        Statement sentencia = null;
        ResultSet rs = null;
        int i = 1;
        //String sql = "SELECT * FROM pijamax.categorias where id_categoria=" + i+";";
        String sql = "INSERT INTO pijamax.categorias"
                + "("
                + "`activo`,"
                + "`nombre_categoria`,"
                + "`descripcion`)"
                + "VALUES"
                + "("
                + "true,"
                + "'ropa infantil 2',"
                + "'dfasdfasdfasdfasdf');";
        System.out.println(sql);

        try {
            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
            System.out.println("se conecto");
            sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

            int resultado = sentencia.executeUpdate(sql);

            if (resultado==1){
                System.out.println("se inserto");
            }else{
                System.out.println("no se inserto");
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
            
            //cerre la conexion
            if (conexion != null) {
                conexion.close();
                System.out.println("se cerro la conexion correctamente");
            }
        }
    }

}
