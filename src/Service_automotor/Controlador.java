package Service_automotor;

import Service_automotor.Vista.VistaDatosServicio;
import Service_automotor.Vista.VistaDatosCliente;
import Service_automotor.Vista.VistaDatosAuto;
import Service_automotor.Vista.Vista;
import Service_automotor.Vista.VistaTabla;
import Service_automotor.modelo.clases.Automovil;
import Service_automotor.modelo.clases.Cliente;
import Service_automotor.modelo.clases.Modelo;
import Service_automotor.modelo.clases.Servicio;
import java.awt.event.*;
import java.util.Collection;

/**
 *
 * @author natyb
 */
public class Controlador {

    private final Modelo m;
    private final Vista v;
    private final VistaDatosCliente vDatosCliente = new VistaDatosCliente();
    private final VistaDatosAuto vDatosAuto = new VistaDatosAuto();
    private final VistaDatosServicio vDatosServicio = new VistaDatosServicio();
    private final VistaTabla vTabla = new VistaTabla();
    String botonCategoria;
    String botonAccion;

    public Controlador(Modelo m, Vista v) {
        this.m = m;
        this.v = v;
    }

    //INICIA LA APP Y MUESTRA V (VISTA MENU PRINCIPAL)
    public void iniciar() {
        iniciarBD();
        v.addAutosListener(new llamarVistaTabla());
        v.addClientesListener(new llamarVistaTabla());
        v.addServiciosListener(new llamarVistaTabla());
        v.addListadoListener(new llamarVistaTabla());
        v.mostrar();

        vDatosCliente.addGrabarDatosListener(new grabarDatosCliente());
        vDatosCliente.addVolverListener(new volverATabla());
        vDatosServicio.addGrabarDatosListener(new grabarDatosServicio());
        vDatosServicio.addVolverListener(new volverATabla());
        vDatosAuto.addGrabarDatosListener(new grabarDatosAuto());
        vDatosAuto.addVolverListener(new volverATabla());
        vTabla.addAtrasListener(new volverAVentanaPrincipal());
        vTabla.addMostrarVistaDatosListener(new mostrarVistaDatos());
    }

    //CONECTA A LA BBDD
    private void iniciarBD() {
        try {
//            System.out.println("driver: " + dri + "\nurl: " + url + "\nuser: " + usr + "\npass: " + pss);
//            m.conectarBD("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/taller_mecanico", "root", "@BbDd-5713.@");
//            m.conectarBD(dri, url, usr, pss);
            m.conectarBD();
        } catch (Exception ex) {
            v.mostrarAviso(ex.toString());
        }
    }

//CIERRA LA VISTA DE DATOS Y ACTUALIZA LAS TABLAS Y ABRE LA VISTA TABLA
    private class volverATabla implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            vDatosServicio.setVisible(false);
            vDatosAuto.setVisible(false);
            vDatosCliente.setVisible(false);
            switch (botonCategoria) {
                case "Autos":
                    vTabla.actualizarAutos(m.consultaAutosBBDD());
                    break;
                case "Clientes":
                    vTabla.actualizarClientes(m.consultaClientesBBDD());
                    break;
                case "Servicios":
                    vTabla.actualizarServicios(m.consultaServiciosBBDD());
                    break;
                default:
                    break;
            }
            vTabla.setVisible(true);
        }
    }

    // ABRE LA VISTA PARA EL ABM DE LOS DATOS Y CIERRA VTABLA
    private class mostrarVistaDatos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e2) {
            botonAccion = e2.getActionCommand();

            switch (botonCategoria) {
                case "Servicios":

                    if (botonAccion.equals("new")) {
                        vDatosServicio.completarTitulo("Ingrese el detalle del Servicio realizado");
                        vDatosServicio.setVisible(true);
                        vDatosServicio.limpiarCampos();
                        vDatosServicio.setearIdServicio(m.buscarMaximo(botonCategoria));

                    } else if (botonAccion.equals("edit")) {
                        int fila = vTabla.obtenerFilaSeleccionada();
                        Servicio servicioSelected = vTabla.obtenerDatosServicioFilaSeleccionada(fila);
                        vDatosServicio.mostrarDatosServicio(servicioSelected);
                        vDatosServicio.completarTitulo("Actualice los datos de este Servicio");
                        vDatosServicio.setVisible(true);

                    } else if (botonAccion.equals("delete")) {
                        int fila = vTabla.obtenerFilaSeleccionada();
                        Servicio servicioSelected = vTabla.obtenerDatosServicioFilaSeleccionada(fila);
                        vDatosServicio.mostrarDatosServicio(servicioSelected);
                        vDatosServicio.completarTitulo("<html><body>Va a eliminar los datos de este Servicio <br> ¿Quiere continuar?</body></html>");
                        vDatosServicio.setVisible(true);
                    }
                    break;
                case "Autos":
                    if (botonAccion.equals("new")) {
                        vDatosAuto.completarTitulo("Ingrese los datos del Auto");
                        vDatosAuto.setVisible(true);
                        vDatosAuto.limpiarCampos();
                        vDatosAuto.setearIdAuto(m.buscarMaximo(botonCategoria));

                    } else if (botonAccion.equals("edit")) {
                        int fila = vTabla.obtenerFilaSeleccionada();
                        Automovil autoSelected = vTabla.obtenerDatosAutoFilaSeleccionada(fila);
                        vDatosAuto.mostrarDatosAuto(autoSelected);
                        vDatosAuto.completarTitulo("Actualice los datos de este Auto");
                        vDatosAuto.setVisible(true);

                    } else if (botonAccion.equals("delete")) {
                        int fila = vTabla.obtenerFilaSeleccionada();
                        Automovil autoSelected = vTabla.obtenerDatosAutoFilaSeleccionada(fila);
                        vDatosAuto.mostrarDatosAuto(autoSelected);
                        vDatosAuto.completarTitulo("<html><body>Va a eliminar los datos de este Auto <br> ¿Quiere continuar?</body></html>");
                        vDatosAuto.setVisible(true);
                    }
                    break;

                case "Clientes":
                    if (botonAccion.equals("new")) {
                        vDatosCliente.completarTitulo("Ingrese los datos personales del Cliente");
                        vDatosCliente.setVisible(true);
                        vDatosCliente.limpiarCampos();
                        vDatosCliente.setearIdCliente(m.buscarMaximo(botonCategoria));

                    } else if (botonAccion.equals("edit")) {
                        int fila = vTabla.obtenerFilaSeleccionada();
                        Cliente clienteSelected = vTabla.obtenerDatosClienteFilaSeleccionada(fila);
                        vDatosCliente.mostrarDatosCliente(clienteSelected);
                        vDatosCliente.completarTitulo("Actualice los datos del Cliente");
                        vDatosCliente.setVisible(true);

                    } else if (botonAccion.equals("delete")) {
                        int fila = vTabla.obtenerFilaSeleccionada();
                        Cliente clienteSelected = vTabla.obtenerDatosClienteFilaSeleccionada(fila);
                        vDatosCliente.mostrarDatosCliente(clienteSelected);
                        vDatosCliente.completarTitulo("<html><body>Va a eliminar los datos de este Cliente <br> ¿Quiere continuar?</body></html>");
                        vDatosCliente.setVisible(true);
                    }
                case "Generar Listado":
//                    System.out.println("Esta vista es de sólo lectura/consulta");
                    break;
                default:
                    System.out.println("cerrar app");
            }
            vTabla.setVisible(false);
        }
    }

    // REALIZA EL ABM DE LOS CLIENTES (CONTROL DE LOS BOTONES)
    private class grabarDatosCliente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (botonAccion.equals("new")) {
                    m.agregarClienteBBDD(vDatosCliente.getCliente());
                    v.mostrarAviso("Datos guardados exitosamente");
                } else if (botonAccion.equals("edit")) {
                    m.actualizarClienteBBDD(vDatosCliente.getCliente());
                    v.mostrarAviso("Datos actualizados exitosamente");

                } else if (botonAccion.equals("delete")) {
                    m.eliminarClienteBBDD(vDatosCliente.getCliente());
                    // VER INTEGRIDAD DE CLAVES FORANEAS
                    v.mostrarAviso("Datos eliminados exitosamente");
                }

            } catch (Exception ex) {
                tratarExcepcion(ex);
            }
        }

    }

    // REALIZA EL ABM DE LOS AUTOS (CONTROL DE LOS BOTONES)
    private class grabarDatosAuto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (botonAccion.equals("new")) {
                    m.agregarAutoBBDD(vDatosAuto.getAuto());
                    v.mostrarAviso("Datos guardados exitosamente");

                } else if (botonAccion.equals("edit")) {
                    m.actualizarAutoBBDD(vDatosAuto.getAuto());
                    v.mostrarAviso("Datos actualizados exitosamente");
                } else if (botonAccion.equals("delete")) {
                    m.eliminarAutoBBDD(vDatosAuto.getAuto());
// VER INTEGRIDAD DE CLAVES FORANEAS
                    v.mostrarAviso("Datos eliminados exitosamente");
                }

            } catch (Exception ex) {
                tratarExcepcion(ex);
            }
        }
    }

    // REALIZA EL ABM DE LOS SERVICIOS (CONTROL DE LOS BOTONES)
    private class grabarDatosServicio implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (botonAccion.equals("new")) {
                    m.agregaServicioBBDD(vDatosServicio.getServicio());
                    v.mostrarAviso("Datos guardados exitosamente");
                    vDatosServicio.limpiarCampos();

                } else if (botonAccion.equals("edit")) {
                    m.actualizarServicioBBDD(vDatosServicio.getServicio());
                    v.mostrarAviso("Datos actualizados exitosamente");

                } else if (botonAccion.equals("delete")) {
                    m.eliminarServicioBBDD(vDatosServicio.getServicio());
                    v.mostrarAviso("Datos eliminados exitosamente");
                }

//            yo cuando guardo le tengo q mandar los datos de la vista al modelo para q valide y guarde
// LOS DATOS LOS  GUARDA EL MODELO
            } catch (Exception ex) {
                tratarExcepcion(ex);
            }
        }
    }

    // CIERRA LA VISTA TABLA Y MUESTRA EL MENU PRINCIPAL
    private class volverAVentanaPrincipal implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            vTabla.setVisible(false);
            v.setVisible(true);
            v.setLocationRelativeTo(null);
        }
    }

    // CIERRA LA VISTA PRINCIPAL Y MUESTRA LA VISTA TABLA
    private class llamarVistaTabla implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            botonCategoria = e.getActionCommand();
            String titulo = "";
            v.setVisible(false);
            vTabla.setVisible(true);
            vTabla.setLocationRelativeTo(null);
            if (botonCategoria.equals("Generar Listado")) {
                titulo = "Autos con +3a y 1 solo servicio";
            } else if (botonCategoria.equals("Autos")) {
                titulo = "Autos";
            } else if (botonCategoria.equals("Servicios")) {
                titulo = "Servicios";
            } else if (botonCategoria.equals("Clientes")) {
                titulo = "Clientes";
            }
            vTabla.completarTitulo(titulo);
            ventanaTabla();
        }

        // CONSTRUYE LA VISTA TABLA SEGUN EL BOTON OPRIMIDO Y MUESTRA LOS DATOS CORRESPONDIENTES
        private void ventanaTabla() {

            // *************** LLAMADA A MENU DE AUTOS *************************
            if (botonCategoria.equals("Autos")) {
                try {
                    Collection<Automovil> automoviles = m.consultaAutosBBDD();
                    vTabla.mostrarAutos(automoviles);
                    vTabla.habilitarBotones(true);
                } catch (Exception ex) {
                    tratarExcepcion(ex);
                }

                // *************** LLAMADA A MENU DE CLIENTES *************************
            } else if (botonCategoria.equals("Clientes")) {
                try {
                    Collection<Cliente> clientes = m.consultaClientesBBDD();
                    vTabla.mostrarClientes(clientes);
                    vTabla.habilitarBotones(true);
//                    vTabla.mostrarActivos();
                } catch (Exception ex) {
                    tratarExcepcion(ex);
                }

                // *************** LLAMADA A MENU DE SERVICIOS *************************
            } else if (botonCategoria.equals("Servicios")) {
                try {
                    Collection<Servicio> servicios = m.consultaServiciosBBDD();
                    vTabla.mostrarServicios(servicios);
                    vTabla.habilitarBotones(true);
                } catch (Exception ex) {
                    tratarExcepcion(ex);
                }

                // *************** LLAMADA A LISTADO DE AUTOS CON +3A Y 1 SERVICIO *************************
            } else if (botonCategoria.equals("Generar Listado")) {
                try {
                    Collection<Automovil> automovil = m.generarListado();
                    vTabla.mostrarAutos(automovil);
                    vTabla.habilitarBotones(false);
                } catch (Exception ex) {
                    tratarExcepcion(ex);
                }
            }
        }

    }

    private void tratarExcepcion(Exception ex) {
        String causeMessage = ex.getCause() == null ? "" : "\n" + ex.getCause().getMessage();
        v.mostrarCartelDeError(ex.getMessage() + "\n" + causeMessage);
    }

}
