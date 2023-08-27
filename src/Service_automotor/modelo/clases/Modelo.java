package Service_automotor.modelo.clases;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author natyb
 */
public class Modelo {

    private String nombreApp;

    private Connection conexion;

    public void conectarBD(String driver, String url, String user, String pass) {
        this.conexion = ConexionBD.getConexion(driver, url, user, pass);
        System.out.println("Se conectó a la BBDD");
    }

    public void conectarBD() {
        try {
            this.conexion = ConexionBD.getConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Se conectó a la BBDD");
    }

    public Modelo() {
        this.nombreApp = "Taller Mecánico BBDD";
    }

    public String getNombreApp() {
        return nombreApp;
    }

    //*********************************CONSULTA DE SERVICIOS**************************/
    public Collection<Servicio> consultaServiciosBBDD() {
        try ( Statement stmt = this.conexion.createStatement();) {
            Collection<Servicio> servi = new ArrayList<>();
            String query = "SELECT * FROM servicios; ";

            try ( ResultSet rs = stmt.executeQuery(query);) {
                while (rs.next()) {
                    servi.add(obtenerServicios(rs));
                }
                return servi;
            }
        } catch (Exception ex) {
            throw new RuntimeException("No se pudieron obtener los serviciosBBDD", ex);
        }
    }

//    //********************************* AGREGAR NUEVO SERVICIO / NO BORRAR **************************/
//    -public void agregaServicioBBDD(Servicio servi) {
//        String query = "INSERT INTO servicios VALUES (null, ?, ?, ?, ?); ";
//        try ( PreparedStatement pst = this.conexion.prepareStatement(query);) {
//            cargarDatosServicioEnSentencia(servi, pst);
//            pst.executeUpdate();
//
//        } catch (Exception ex) {
//            throw new RuntimeException("No se pudo agregar el nuevo servicio en la BBDD\n" + servi, ex);
//        }
//    }
    //********************************* AGREGAR NUEVO SERVICIO **************************/
    public void agregaServicioBBDD(Servicio serv) throws SQLException {
        // PARA AGREGAR EL SERVICIO, EL AUTO TIENE QUE EXISTIR
        String query = "INSERT INTO servicios VALUES (null,?,?,?,?)";
//        System.out.println(query);
//        boolean existe = buscarExistenciaAutoEnBD(serv.getIDAuto());
//        System.out.println(existe);
        if (!buscarExistenciaAutoEnBD(serv.getIDAuto())) {
            throw new SQLException("No se puede agregar el Servicio porque no ingresó un idAuto existente");
        } else {
            try ( PreparedStatement ps = this.conexion.prepareStatement(query);) {
                cargarDatosServicioEnSentencia(serv, ps);
                ps.executeUpdate();
            } catch (Exception ex) {
                throw new RuntimeException("No se pudo agregar el servicio\n" + serv, ex);
            }
        }
    }

    private void cargarDatosServicioEnSentencia(Servicio servi, PreparedStatement ps) throws SQLException {
        ps.setInt(1, servi.getCosto());
        ps.setInt(2, servi.getCantKms());
        ps.setString(3, servi.getFechaRealizacion().toString());
        ps.setInt(4, servi.getIDAuto());
    }

    //********************************* CONSULTA DE CLIENTES **************************/
    public Collection<Cliente> consultaClientesBBDD() {

        try ( Statement stmt = this.conexion.createStatement();) {
            Collection<Cliente> client = new ArrayList<>();
            String query = "SELECT * FROM clientes where activo = 1; ";

            try ( ResultSet rs = stmt.executeQuery(query);) {
                while (rs.next()) {
                    client.add(obtenerClientes(rs));
                }
                return client;
            }
        } catch (Exception ex) {
            throw new RuntimeException("No se pudieron obtener los clientesBBDD", ex);
        }
    }

    //********************************* AGREGAR NUEVO CLIENTE **************************/
    public void agregarClienteBBDD(Cliente cli) {
        String query = "INSERT INTO clientes VALUES (null, ?, ?, ?, ?, ?); ";
        try ( PreparedStatement pst = this.conexion.prepareStatement(query);) {
            cargarDatosClienteEnSentencia(cli, pst);
            pst.executeUpdate();

        } catch (Exception ex) {
            throw new RuntimeException("No se pudo agregar el nuevo cliente en la BBDD\n" + cli, ex);
        }
    }

    //********************************* ACTUALIZAR CLIENTE **************************/
    public void actualizarClienteBBDD(Cliente cli) {
        String columnas = "dni = ?, nombre = ?, apellido = ?, mail = ?, celular = ? ";
        String query = "UPDATE clientes SET " + columnas + " WHERE idCliente = " + cli.getIDCliente() + "; ";
        try ( PreparedStatement pst = this.conexion.prepareStatement(query);) {
            cargarDatosClienteEnSentencia(cli, pst);
            pst.executeUpdate();

        } catch (Exception ex) {
            throw new RuntimeException("No se pudo actualizar el cliente en la BBDD\n" + cli, ex);
        }
    }

    //********************************* ELIMINAR CLIENTE **************************/
    public void eliminarClienteBBDD(Cliente cli) throws SQLException {
        //buscar cliente en tabla automoviles, si existe, no se puede eliminar
        String query = "UPDATE clientes set activo = false where idCliente = " + +cli.getIDCliente() + "; ";
        if (buscarClienteEnTablaAutosBD(cli.getIDCliente())) {
            throw new SQLException("No se puede eliminar el cliente porque existe una relación con un auto");
        } else {
            try ( Statement st = this.conexion.createStatement();) {
                st.executeUpdate(query);
            } catch (Exception ex) {
                throw new RuntimeException("No se pudo eliminar el cliente de la BBDD\n" + cli, ex);
            }
        }

    }

//    //********************************* ELIMINAR CLIENTE / NO BORRAR **************************/
//    public void eliminarClienteBBDD(Cliente cli) throws SQLException {
//        //buscar cliente en tabla automoviles, si existe, no se puede eliminar
//        String query = "DELETE FROM clientes WHERE idCliente = " + cli.getIDCliente() + "; ";
//        if (buscarClienteEnTablaAutosBD(cli.getIDCliente())) {
//            throw new SQLException("No se puede eliminar el cliente porque existe una relación con un auto");
//        } else {
//            try ( Statement st = this.conexion.createStatement();) {
//                st.executeUpdate(query);
//            } catch (Exception ex) {
//                throw new RuntimeException("No se pudo eliminar el cliente de la BBDD\n" + cli, ex);
//            }
//        }
//    }
    //********************************* ELIMINAR AUTO **************************/
    public void eliminarAutoBBDD(Automovil auto) throws SQLException {
        //buscar auto en tabla servicios, si existe, no se puede eliminar
        String query = "DELETE FROM automoviles WHERE idAuto = " + auto.getIDAuto() + "; ";
        if (buscarAutoEnTablaServiciosBD(auto.getIDAuto())) {
            throw new SQLException("No se puede eliminar el auto porque existe una relación con un servicio");
        } else {
            try ( Statement st = this.conexion.createStatement();) {
                st.executeUpdate(query);
            } catch (Exception ex) {
                throw new RuntimeException("No se pudo eliminar el auto de la BBDD\n" + auto, ex);
            }
        }

    }

    private void cargarDatosClienteEnSentencia(Cliente cli, PreparedStatement ps) throws SQLException {
        ps.setString(1, cli.getDNI());
        ps.setString(2, cli.getNombre());
        ps.setString(3, cli.getApellido());
        ps.setString(4, cli.getMail());
        ps.setString(5, cli.getCelular());
    }

    //********************************* CONSULTA DE AUTOMOVILES **************************/
    public Collection<Automovil> consultaAutosBBDD() {

        try ( Statement stmt = this.conexion.createStatement();) {
            Collection<Automovil> auto = new ArrayList<>();
            String query = "SELECT * FROM automoviles; ";

            try ( ResultSet rs = stmt.executeQuery(query);) {
                while (rs.next()) {
                    auto.add(obtenerAutos(rs));
                }
                return auto;
            }
        } catch (Exception ex) {
            throw new RuntimeException("No se pudieron obtener los autosBBDD", ex);
        }
    }

    //********************************* AGREGAR NUEVO AUTO **************************/
    public void agregarAutoBBDD(Automovil auto) {
        String query = "INSERT INTO automoviles VALUES (null, ?, ?, ?, ?, ?); ";

        try ( PreparedStatement pst = this.conexion.prepareStatement(query);) {
            cargarDatosAutoEnSentencia(auto, pst);
            pst.executeUpdate();

        } catch (Exception ex) {
            throw new RuntimeException("No se pudo agregar el nuevo auto en la BBDD\n" + auto);
        }
    }

    private void cargarDatosAutoEnSentencia(Automovil auto, PreparedStatement ps) throws SQLException {
        ps.setString(1, auto.getPatente());
        ps.setString(2, auto.getMarca());
        ps.setString(3, auto.getModelo());
        ps.setInt(4, auto.getAnioFabricacion());
        ps.setInt(5, auto.getIdCliente());
    }

    //************************** OBTENER SERVICIOS DESDE BBDD **************************/
    private Servicio obtenerServicios(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        int cost = rs.getInt(2);
        int kms = rs.getInt(3);
        LocalDate fechaLoc = LocalDate.parse(rs.getString(4));
        int idAu = rs.getInt(5);
        return new Servicio(id, cost, kms, fechaLoc, idAu);
    }

    //************************** OBTENER CLIENTES DESDE BBDD **************************/
    private Cliente obtenerClientes(ResultSet rs) throws SQLException {
        int idCli = rs.getInt(1);
        String dni = rs.getString(2);
        String nombre = rs.getString(3);
        String apellido = rs.getString(4);
        String mail = rs.getString(5);
        String celu = rs.getString(6);
        return new Cliente(idCli, dni, nombre, apellido, mail, celu);
    }

    //************************** OBTENER AUTOS DESDE BBDD **************************/
    private Automovil obtenerAutos(ResultSet rs) throws SQLException {
        int idAuto = rs.getInt(1);
        String patente = rs.getString(2);
        String marca = rs.getString(3);
        String modelo = rs.getString(4);
        int anioFab = rs.getInt(5);
        int idCli = rs.getInt(6);
        return new Automovil(idAuto, patente, marca, modelo, anioFab, idCli);
    }

    public String validarDatosServicio(int costo, int cantKMS, int idAuto, int idServ, String fecha) {

        LocalDate fechaLocal;
        boolean fechaValida = validarFecha(fecha); //VALIDA QUE LA FECHA SEA CORRECTA (X CANT DE DIAS)
        boolean existeAuto = buscarAutoEnTablaServiciosBD(idAuto); //VALIDA QUE EXISTA EL AUTO EN LA TABLA DE SERVICIOS

//        if (existeServ) // SI SE ENCONTRÓ EL SERVICIO, SE ACTUALIZA, SINO, SE CARGA UNO NUEVO
//        {
//            actualizarServicioBBDD(); // VER QUE ES LO QUE ACTUALIZA 
//        } else // NO ENCONTRÓ EL SERVICIO, SE CARGA UNO NUEVO
//        {
        if (existeAuto && fechaValida) { // SI EL ID DE AUTO EXISTE Y LA FECHA ES VALIDA (TIENE Q SER MENOR O IGUAL A HOY)
            fechaLocal = LocalDate.parse(fecha);
            //            actualizarServicioBBDD();
        } else {
            return "La fecha es inválida o el id de auto no existe";
        }
//        }

        return ("Se han grabado los datos modificados correctamente");
    }

    private boolean validarFecha(String fechaIngresada) {

        try {
            // Crea un objeto SimpleDateFormat con el formato yyyy-MM-dd
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            formatoFecha.setLenient(false); // Impide que se permitan fechas inválidas

            // Convierte la cadena fechaStr en un objeto Calendar
            Calendar calendario = new GregorianCalendar();
            try {
                calendario.setTime(formatoFecha.parse(fechaIngresada));
            } catch (Exception e) {
                System.out.println("ERROR EN FECHA - VALIDAR FECHA");
            }

            int año = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            // Verifica si el año es bisiesto
            boolean esBisiesto = (año % 4 == 0 && año % 100 != 0) || (año % 400 == 0);

            // Calcula la cantidad de días del mes
            int[] diasPorMes = {31, esBisiesto ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
            int diasEnMes = diasPorMes[mes];

            // Verifica si el día ingresado es válido
            return dia <= diasEnMes;

        } catch (Exception e) {
            // Si ocurre un error al convertir la fecha o al calcular los días, devuelve falso
            return false;
        }
    }

    //**************** BUSQUEDA DE CLIENTE EN TABLA DE AUTOMOVILES PARA INTEGRIDAD REFERENCIAL **************************/
    private boolean buscarClienteEnTablaAutosBD(int idClienteABuscar) {

        try ( Statement stmt = this.conexion.createStatement();) {
            String query = "SELECT * FROM automoviles WHERE idCliente = " + idClienteABuscar;

            try ( ResultSet rs = stmt.executeQuery(query);) {
                if (rs.next()) {
                    return true; // si la query retorna datos, el cliente existe en la tabla de Autos
//                }else{
//                    return false;
                }
            } catch (Exception ex) {
                throw new RuntimeException("No se pudo obtener el auto, no existe en la BBDD", ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo obtener el auto, no existe en la BBDD", ex);
        }
        return false;
    }

    //**************** BUSQUEDA DE AUTO EN TABLA DE SERVICIOS PARA INTEGRIDAD REFERENCIAL **************************/
    private boolean buscarAutoEnTablaServiciosBD(int idAutoABuscar) {

        try ( Statement stmt = this.conexion.createStatement();) {
            String query = "SELECT * FROM servicios WHERE idAuto = " + idAutoABuscar;

            try ( ResultSet rs = stmt.executeQuery(query);) {
                if (rs.next()) {
                    return true; // si la query retorna datos, el auto existe en la tabla de Servicios
                } else {
                    return false;
                }
            } catch (Exception ex) {
                throw new RuntimeException("No se pudo obtener el servicio, no existe en la BBDD", ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo obtener el servicio, no existe en la BBDD", ex);
        }
        //return false;
    }

    //**************** BUSQUEDA DE AUTO EN BBDD PARA INTEGRIDAD REFERENCIAL **************************/
    private boolean buscarExistenciaAutoEnBD(int idAutoABuscar) {
//String query = "SELECT * FROM automoviles WHERE idAuto = " + idAutoABuscar;
        try ( Statement stmt = this.conexion.createStatement();) {
        String query = "SELECT * FROM automoviles WHERE idAuto = " + idAutoABuscar;
//try ( PreparedStatement pstmt = this.conexion.prepareStatement(query)) {
//            pstmt.setInt(6, idAutoABuscar);
//            System.out.println(query);
            try ( ResultSet rs = stmt.executeQuery(query);) {
                 return rs.next(); // si la query retorna datos, el auto existe en la tabla de automoviles

            } catch (Exception ex) {
                throw new RuntimeException("Error al ejecutar la consulta", ex);
            }
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo obtener el auto, no existe en la BBDD", ex);
        }
        //return false;
    }
//    //**************** BUSQUEDA DE AUTO EN BBDD PARA INTEGRIDAD REFERENCIAL **************************/
//    private boolean buscarExistenciaAutoEnBD(int idAutoABuscar) {
//
//        try ( Statement stmt = this.conexion.createStatement();) {
//            String query = "SELECT * FROM automoviles WHERE idAuto = " + idAutoABuscar;
////            System.out.println(query);
//            try ( ResultSet rs = stmt.executeQuery(query);) {
//                if (rs.next()) {
//                    return true; // si la query retorna datos, el auto existe en la tabla de automoviles
//                } else {
//                    return false;
//                }
//            } catch (Exception ex) {
//                throw new RuntimeException("No se pudo obtener el auto, no existe en la BBDD", ex);
//            }
//        } catch (Exception ex) {
//            throw new RuntimeException("No se pudo obtener el auto, no existe en la BBDD", ex);
//        }
//        //return false;
//    }

//    //********************************* AGREGAR NUEVO SERVICIO **************************/
//    public void agregarServicio(Servicio serv) throws SQLException {
//        // PARA AGREGAR EL SERVICIO, EL AUTO TIENE QUE EXISTIR
//        String query = "INSERT INTO servicios VALUES (null,?,?,?,?,?,?,?)";
//        System.out.println(query);
//        boolean existe = buscarExistenciaAutoEnBD(serv.getIDAuto());
//        System.out.println(existe);
//        if (buscarExistenciaAutoEnBD(serv.getIDAuto())) {
    //            throw new SQLException("No se puede agregar el Servicio porque no ingresó un idAuto existente");
//        } else {
//            try ( PreparedStatement ps = this.conexion.prepareStatement(query);) {
//                //cargarServicio(serv, ps);
//                ps.executeUpdate();
//            } catch (Exception ex) {
//                throw new RuntimeException("No se pudo agregar el servicio\n" + serv, ex);
//            }
//        }
//    }
//        //********************************* AGREGAR NUEVO SERVICIO / VIEJO NO BORRAR **************************/
//    public void agregarServicio(Servicio serv) throws SQLException {
//        String query = "INSERT INTO servicios VALUES (null,?,?,?,?,?,?,?)";
//        try ( PreparedStatement ps = this.conexion.prepareStatement(query);) {
//            //cargarServicio(serv, ps);
//            ps.executeUpdate();
//        } catch (Exception ex) {
//            throw new RuntimeException("No se pudo agregar el servicio\n" + serv, ex);
//        }
//    }
    //********************************* GENERAR LISTADO DE AUTOS +3A CON 1 SERVICIO **************************/
    public Collection<Automovil> generarListado() {
        try ( Statement stmt = this.conexion.createStatement();) {
            Collection<Automovil> auto = new ArrayList<>();
            String idAutos = "";
            ArrayList<Integer> ids = consultarAuto_3a_1Servicio();

            for (Integer id : ids) {
                idAutos = idAutos + id + ",";
            }
            idAutos = idAutos.substring(0, idAutos.length() - 1);
            String queryConsulta = "SELECT * FROM automoviles c WHERE c.idAuto IN ( " + idAutos + " )";
            try ( ResultSet rs = stmt.executeQuery(queryConsulta);) {
                while (rs.next()) {
                    auto.add(obtenerAutos(rs));
                }
                return auto;
            }

        } catch (Exception ex) {
            throw new RuntimeException("No se pudo realizar la consulta\n", ex);
        }
    }

    //********************************* GENERAR LISTADO DE AUTOS +3A CON 1 SERVICIO **************************/
    public ArrayList<Integer> consultarAuto_3a_1Servicio() {
        try ( Statement stmt = this.conexion.createStatement();) {
            ArrayList<Integer> idAuto = new ArrayList<>();
            String querySubcon = "SELECT a.idAuto FROM automoviles a JOIN servicios s ON a.idAuto = s.idAuto WHERE anio_fabricacion < YEAR(NOW()) - 3 GROUP BY a.idAuto HAVING count(idServicio) = 1;";

            try ( ResultSet rs = stmt.executeQuery(querySubcon);) {
                while (rs.next()) {
                    idAuto.add(rs.getInt(1));
                }
                return idAuto;
            }

        } catch (Exception ex) {
            throw new RuntimeException("No se pudo realizar la consulta\n", ex);
        }
    }

    public int buscarMaximo(String botonOprimido) {
        int idMaximo = 0;
        String idABuscar = "";
        String tablaAbuscar = "";

        switch (botonOprimido) {
            case "Servicios":
                idABuscar = "idServicio";
                tablaAbuscar = "servicios";
                break;
            case "Autos":
                idABuscar = "idAuto";
                tablaAbuscar = "automoviles";
                break;
            case "Clientes":
                idABuscar = "idCliente";
                tablaAbuscar = "clientes";
                break;
        }
        try {
            Statement stmt = this.conexion.createStatement();
            String query = "SELECT MAX( " + idABuscar + " ) FROM " + tablaAbuscar + " HAVING MAX( " + idABuscar + " );";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                idMaximo = rs.getInt(1) + 1; //RETORNA EL NUMERO MAXIMO  
            }
        } catch (Exception ex) {
            throw new RuntimeException("ERROR al intentar ejecutar la query", ex);
        }
        return idMaximo;
    }

    //********************************* ACTUALIZAR SERVICIO **************************/
    public void actualizarServicioBBDD(Servicio ser) {
        String columnas = "costo = ?, cantKms = ?, fecha_realizacion = ?, idauto = ? ";
        String query = "UPDATE servicios SET " + columnas + " WHERE idServicio = " + ser.getIDServicio() + "; ";
        try ( PreparedStatement pst = this.conexion.prepareStatement(query);) {
            cargarDatosServicioEnSentencia(ser, pst);
            pst.executeUpdate();

        } catch (Exception ex) {
            throw new RuntimeException("No se pudo actualizar el servicio en la BBDD\n" + ser, ex);
        }
    }

    //********************************* ACTUALIZAR AUTO **************************/
    public void actualizarAutoBBDD(Automovil auto) {
        String columnas = "patente = ?, marca = ?, modelo = ?, anio_fabricacion = ?, idCliente = ? ";
        String query = "UPDATE automoviles SET " + columnas + " WHERE idAuto = " + auto.getIDAuto() + "; ";

        try ( PreparedStatement pst = this.conexion.prepareStatement(query);) {

            cargarDatosAutoEnSentencia(auto, pst);
            pst.executeUpdate();

        } catch (Exception ex) {
            throw new RuntimeException("No se pudo actualizar el auto en la BBDD\n" + auto, ex);
        }
    }

    //********************************* ELIMINAR SERVICIO **************************/
    public void eliminarServicioBBDD(Servicio ser) {
        //buscar cliente en tabla automoviles, si existe, no se puede eliminar
        String query = "DELETE FROM servicios WHERE idServicio = " + ser.getIDServicio() + "; ";

        try ( Statement st = this.conexion.createStatement();) {
            st.executeUpdate(query);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo eliminar el servicio de la BBDD\n" + ser, ex);
        }
    }

}
