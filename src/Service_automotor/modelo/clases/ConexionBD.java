package Service_automotor.modelo.clases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConexionBD {

    private static Connection con;

    public static Connection getConexion(String driver, String url, String user, String pass) {
        if (con == null) {
            try {
                Runtime.getRuntime().addShutdownHook(new MiShutDownHook()); // Agregar hook de fin de programa
                Class.forName(driver); // Chequeo de Driver (sujeto a excepciones)
                con = DriverManager.getConnection(url, user, pass); // Obtener la conexión
                System.out.println("Conexión exitosa: " + con.getClass().getName());
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("No se encuentra driver " + driver, ex);
            } catch (Exception ex) {
                throw new RuntimeException("No se pudo establecer conexión con la BD", ex);
            }
        }
        return con;
    }

//    public static Connection getConexion() throws SQLException, ClassNotFoundException {
//        ResourceBundle rb = ResourceBundle.getBundle("bd-params");
//        String driver = rb.getString("driver");
//        String url = rb.getString("url");
//        String user = rb.getString("user");
//        String pass = rb.getString("pass");
//        return getConexion(driver, url, user, pass);
//    }
    public static Connection getConexion() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
        Properties properties = new Properties();
        FileInputStream fis = null;
        String user= null, pass= null,driver= null,url = null;

        try {
            // Ruta del archivo
            String rutaArchivo = "src/Service_automotor/modelo/bbdd/bd-params.properties";
            fis = new FileInputStream(rutaArchivo);

            // Cargando los datos del archivo
            properties.load(fis);

            // Obtener los valores de las propiedades
             user = properties.getProperty("user");
             pass = properties.getProperty("pass");
             driver = properties.getProperty("driver");
             url = properties.getProperty("url");
            // ... agregar más propiedades según sea necesario

            /*
            // Imprimir los valores obtenidos
            System.out.println("Usuario: " + user);
            System.out.println("Contraseña: " + pass);
            // ... imprimir más propiedades según sea necesario
            System.out.println("Driver: " + driver);
            System.out.println("URL: " + url);
            */

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return getConexion(driver, url, user, pass);
    }

    private static class MiShutDownHook extends Thread {

        /* Justo antes de finalizar el programa la JVM invocará
           a este método donde podemos cerrar la conexion */
        @Override
        public void run() {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Conexión liberada");
                }
            } catch (Exception ex) {
                System.out.println("Error liberando la conexión en la app: " + ex.getMessage());
            }
        }
    }
}
