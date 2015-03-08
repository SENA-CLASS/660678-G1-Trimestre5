/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ejemplosjdbcant;

import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author Enrique Moreno
 */
public class EjemploConexion13 {

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

            sentencia = conexion.prepareCall(sql);
            sentencia.setInt(1, 8);
            
            System.out.println("sentencia ejecutada " + sql );

            int resultado = sentencia.executeUpdate();
			
            if (resultado>0){
                System.out.println("se ejecuto el procedimiento ");
            }else{
                System.out.println("no se ejecuto el procedimiento");
            }
            
            rs = sentencia.getResultSet();
            
            ResultSetMetaData rsMetaData = rs.getMetaData();
            
            System.out.print(rsMetaData.getColumnName(1)+"\t");
            System.out.print(rsMetaData.getColumnName(2)+"\t");
            System.out.print(rsMetaData.getColumnName(3)+"\t");
            System.out.print(rsMetaData.getColumnName(4)+"\n");
            
            System.out.print(rsMetaData.getColumnTypeName(1)+"\t");
            System.out.print(rsMetaData.getColumnTypeName(2)+"\t");
            System.out.print(rsMetaData.getColumnTypeName(3)+"\t");
            System.out.print(rsMetaData.getColumnTypeName(4)+"\n");
            
            
            if (rs.next() == true) {

                    if (!rs.isFirst()){
                    rs.beforeFirst();
                    }
                    while (rs.next() == true) {
                        System.out.print(rs.getString(1)+"\t");
                        System.out.print(rs.getString(2)+"\t");
                        System.out.print(rs.getString(3)+"\t");
                        System.out.print(rs.getString(4)+"\n");
                    }
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
