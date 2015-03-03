/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemplosjdbcant;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;

/**
 *
 * @author Enrique Moreno
 */
public class EjemploConexion6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        //mysql
        java.sql.Connection conexion = null;
        String servidor = "jdbc:mysql://localhost:3306/pijamax";
        String usuarioDB = "nicolas";
        String passwordDB = "12345";
        try {

            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
            SQLWarning warn = conexion.getWarnings();
            //System.out.println(warn.getSQLState());
            System.out.println("Aviso(s) producido(s) al conectar");
            System.out.println("=================================");
            while (warn != null) {
                System.out.println("SQLState : " + warn.getSQLState() + "\n");
                System.out.println("Mensaje : " + warn.getMessage() + "\n");
                System.out.println("Código de error: " + warn.getErrorCode() + "\n");
                System.out.println("\n");
                warn = warn.getNextWarning();
            }

            System.out.println("se conecto mysql");

        } catch (SQLException e) {
            System.err.println(e.toString());
        } finally {
            if (conexion != null) {
                conexion.close();
                System.out.println("se cerro la conexion correctamente");
            }
        }
    }

}
