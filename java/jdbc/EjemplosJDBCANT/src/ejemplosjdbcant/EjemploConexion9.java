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
import javax.swing.JOptionPane;

/**
 *
 * @author Enrique Moreno
 */
public class EjemploConexion9 {

    public static void main(String[] args) throws SQLException {
        java.sql.Connection conexion = null;
        String servidor = "jdbc:mysql://localhost/pijamax";
        String usuarioDB = "nicolas";
        String passwordDB = "12345";
        Statement sentencia = null;
        ResultSet rs = null;
        String idProduto = JOptionPane.showInputDialog("digite el id del producto");
        String sql = "SELECT *\n"
                + "FROM pijamax.productos pro "
                + "where pro.id_producto = '" + idProduto + "'"
                + ";";
        System.out.println(sql);

        try {
            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
            System.out.println("se conecto");
            sentencia = conexion.createStatement();

            rs = sentencia.executeQuery(sql);

                if (rs.next() == true) {

                    if (!rs.isFirst()){
                    rs.beforeFirst();
                    }
                    while (rs.next() == true) {
                        System.out.println("id producto: " + rs.getString(1));
                        System.out.println("nombre producto: " + rs.getString(2));
                        System.out.println("activo: " + rs.getString(3));
                        System.out.println("talla: " + rs.getString(6));
                        System.out.println("material: " + rs.getString(7));

                        System.out.println("------------------------------------------------------");
                    }
               
                

                String nombreProduto = JOptionPane.showInputDialog("digite el nombre del producto");
                String talla = JOptionPane.showInputDialog("digite la talla del producto");
                String material = JOptionPane.showInputDialog("digite el material del producto");

                String sql2 = "UPDATE `pijamax`.`productos`"
                        + "SET"
                        + "`nombre_producto` = '"+nombreProduto+"',"
                        + "`tallas` = '"+talla+"',"
                        + "`material` = '"+material+"'"
                        + "WHERE `id_producto` = '"+idProduto+"';";
                
                sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                    
                int resultado = sentencia.executeUpdate(sql2);

                if (resultado == 1) {
                    System.out.println("se modifico");
                } else {
                    System.out.println("no se inserto");
                }

            } else {
                System.out.println("no existe el producto");
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
