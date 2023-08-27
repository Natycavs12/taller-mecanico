/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service_automotor.Vista;

import Service_automotor.modelo.clases.Automovil;
import Service_automotor.modelo.clases.Cliente;
import Service_automotor.modelo.clases.Servicio;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Collection;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author natyb
 */
public class VistaTabla extends javax.swing.JFrame {

    /**
     * Creates new form VistaTabla
     */
    public VistaTabla() {
        initComponents();
    }

    public void habilitarBotones(boolean habilitado) {
        jButtonAdd.setEnabled(habilitado);
        jButtonDelete.setEnabled(habilitado);
        jButtonUpdate.setEnabled(habilitado);
    }

    public void completarTitulo(String nombreTabla) {
        String tituloTabla = "Consulta de " + nombreTabla;
        jLabelTitulo.setText(tituloTabla);
    }

    public void addAtrasListener(ActionListener al) {
        this.jButtonAtras.addActionListener(al);
    }

    public void addMostrarVistaDatosListener(ActionListener al) {
        this.jButtonAdd.addActionListener(al);
        this.jButtonUpdate.addActionListener(al);
        this.jButtonDelete.addActionListener(al);
    }

    public void mostrarAutos(Collection<Automovil> autos) {
        agregarUnaCol();
        limpiarFilas();
        generarNombreColumnasAuto();
        for (Automovil au : autos) {
            agregarFilaAuto(au.getIDAuto(), au.getPatente(), au.getMarca(), au.getModelo(), au.getAnioFabricacion(), au.getIdCliente());
        }
        seleccionarPrimeraFila();
    }

    public void actualizarAutos(Collection<Automovil> autos) { // actualiza la vtabla de autos despues de realizar una accion
        limpiarFilas();
        for (Automovil au : autos) {
            agregarFilaAuto(au.getIDAuto(), au.getPatente(), au.getMarca(), au.getModelo(), au.getAnioFabricacion(), au.getIdCliente());
        }
        seleccionarPrimeraFila();
    }

    private void agregarFilaAuto(int idAuto, String patente, String marca, String modelo, int anioFab, int idClien) {
        DefaultTableModel dtf = (DefaultTableModel) this.jTable1.getModel();
        dtf.addRow(new Object[]{idAuto, patente, marca, modelo, anioFab, idClien});
    }

    public void mostrarClientes(Collection<Cliente> clien) {
//        DefaultTableModel dtf = (DefaultTableModel) this.jTable1.getModel();

        agregarUnaCol();
        limpiarFilas();
        generarNombreColumnasCliente();
        for (Cliente cli : clien) {
            agregarFilaCliente(cli.getIDCliente(), cli.getDNI(), cli.getNombre(), cli.getApellido(), cli.getMail(), cli.getCelular());
            if (!cli.isActivo()) {
//                this.jTable1.setEnabled(false);
//                DefaultTableModel dtf = (DefaultTableModel) this.jTable1.getModel();
//                dtf.removeRow(jTable1.getSelectedRow());
//                dtf.removeRow(jTable1.getr);
//                int fila = this.jTable1.getSelectedRow();
//                this.jTable1.setRowSelectionInterval(fila, fila);
//                dtf.isCellEditable(fila, 0);
             }
        }

        seleccionarPrimeraFila();
    }

    public void actualizarClientes(Collection<Cliente> clien) { // actualiza la vtabla de clientes despues de realizar una accion
        limpiarFilas();
        for (Cliente cli : clien) {
            agregarFilaCliente(cli.getIDCliente(), cli.getDNI(), cli.getNombre(), cli.getApellido(), cli.getMail(), cli.getCelular());
        }
        seleccionarPrimeraFila();
    }

    private void agregarFilaCliente(int idCliente, String dni, String nombre, String apellido, String mail, String celular) {
        DefaultTableModel dtf = (DefaultTableModel) this.jTable1.getModel();
        dtf.addRow(new Object[]{idCliente, dni, nombre, apellido, mail, celular});
    }

    public void mostrarServicios(Collection<Servicio> servi) {
        eliminarUltCol();
        limpiarFilas();
        generarNombreColumnasServicio();
        for (Servicio ser : servi) {
            agregarFilaServicio(ser.getIDServicio(), ser.getCosto(), ser.getCantKms(), ser.getFechaRealizacion(), ser.getIDAuto());
        }
        seleccionarPrimeraFila();
    }

    public void actualizarServicios(Collection<Servicio> servi) { // actualiza la vtabla de servicios despues de realizar una accion
        limpiarFilas();
        for (Servicio ser : servi) {
            agregarFilaServicio(ser.getIDServicio(), ser.getCosto(), ser.getCantKms(), ser.getFechaRealizacion(), ser.getIDAuto());
        }
        seleccionarPrimeraFila();
    }

    private void agregarFilaServicio(int idServ, int costo, int cantKms, LocalDate fechaReal, int idAuto) {
        DefaultTableModel dtf = (DefaultTableModel) this.jTable1.getModel();
        dtf.addRow(new Object[]{idServ, costo, cantKms, fechaReal, idAuto});
    }

    private void limpiarFilas() {
        DefaultTableModel dtf = (DefaultTableModel) this.jTable1.getModel();
        while (dtf.getRowCount() > 0) {
            dtf.removeRow(0);
        }
    }

    private void eliminarUltCol() { // borrar la ultima columna para q entren los datos de la tabla de Servicios
        TableColumnModel tcm = this.jTable1.getColumnModel();
        if (tcm.getColumnCount() == 6) {
            TableColumn columnaABorrar = tcm.getColumn(5);
            this.jTable1.removeColumn(columnaABorrar);
        }
    }

    private void agregarUnaCol() { // agregar la ultima columna xq si viene de Servicios viene con 5 columnas
        TableColumnModel tcm = this.jTable1.getColumnModel();
        if (tcm.getColumnCount() < 6) {
//            this.jTable1.repaint();
            this.jTable1.addColumn(new TableColumn());
        }
    }

    private void seleccionarPrimeraFila() {
        this.jTable1.setRowSelectionInterval(0, 0);

    }

    private void generarNombreColumnasAuto() {
        JTableHeader tableHeader = this.jTable1.getTableHeader();
        TableColumnModel tableColumnModel = tableHeader.getColumnModel();

        TableColumn tableColumnIDAuto = tableColumnModel.getColumn(0);
        tableColumnIDAuto.setHeaderValue("ID");
        TableColumn tableColumnPatente = tableColumnModel.getColumn(1);
        tableColumnPatente.setHeaderValue("Patente");
        TableColumn tableColumnMarca = tableColumnModel.getColumn(2);
        tableColumnMarca.setHeaderValue("Marca");
        TableColumn tableColumnModelo = tableColumnModel.getColumn(3);
        tableColumnModelo.setHeaderValue("Modelo");
        TableColumn tableColumnAnio = tableColumnModel.getColumn(4);
        tableColumnAnio.setHeaderValue("Año de Fab");
        TableColumn tableColumnIDCli = tableColumnModel.getColumn(5);
        tableColumnIDCli.setHeaderValue("Cliente");
        tableHeader.repaint();
    }

    private void generarNombreColumnasCliente() {
        JTableHeader tableHeader = this.jTable1.getTableHeader();
        TableColumnModel tableColumnModel = tableHeader.getColumnModel();

        TableColumn tableColumnIDClie = tableColumnModel.getColumn(0);
        tableColumnIDClie.setHeaderValue("ID");
        TableColumn tableColumnDNI = tableColumnModel.getColumn(1);
        tableColumnDNI.setHeaderValue("DNI");
        TableColumn tableColumnNombre = tableColumnModel.getColumn(2);
        tableColumnNombre.setHeaderValue("Nombre");
        TableColumn tableColumnApellido = tableColumnModel.getColumn(3);
        tableColumnApellido.setHeaderValue("Apellido");
        TableColumn tableColumnEmail = tableColumnModel.getColumn(4);
        tableColumnEmail.setHeaderValue("Email");
        TableColumn tableColumnCelu = tableColumnModel.getColumn(5);
        tableColumnCelu.setHeaderValue("Celular");
        tableHeader.repaint();
    }

    private void generarNombreColumnasServicio() {
        JTableHeader tableHeader = this.jTable1.getTableHeader();
        TableColumnModel tableColumnModel = tableHeader.getColumnModel();

        TableColumn tableColumnIDServi = tableColumnModel.getColumn(0);
        tableColumnIDServi.setHeaderValue("ID");
        TableColumn tableColumnCosto = tableColumnModel.getColumn(1);
        tableColumnCosto.setHeaderValue("Costo");
        TableColumn tableColumnKms = tableColumnModel.getColumn(2);
        tableColumnKms.setHeaderValue("Cantidad KMS");
        TableColumn tableColumnFecha = tableColumnModel.getColumn(3);
        tableColumnFecha.setHeaderValue("Fecha de Realización");
        TableColumn tableColumnIDAuto = tableColumnModel.getColumn(4);
        tableColumnIDAuto.setHeaderValue("Auto");
        tableHeader.repaint();
    }

    public int obtenerFilaSeleccionada() {
        int fila = jTable1.getSelectedRow();

        return fila;
    }

    public Servicio obtenerDatosServicioFilaSeleccionada(int fila) {
        int idServ = (Integer) jTable1.getValueAt(fila, 0);
        int costo = (Integer) jTable1.getValueAt(fila, 1);
        int cantKms = (Integer) jTable1.getValueAt(fila, 2);
        LocalDate fecha = (LocalDate) jTable1.getValueAt(fila, 3);
        int idAuto = (Integer) jTable1.getValueAt(fila, 4);

        Servicio selectedServicio = new Servicio(idServ, costo, cantKms, fecha, idAuto);
        return selectedServicio;
    }

    public Automovil obtenerDatosAutoFilaSeleccionada(int fila) {
        int idAuto = (Integer) jTable1.getValueAt(fila, 0);
        String patente = (String) jTable1.getValueAt(fila, 1).toString();
        String marca = (String) jTable1.getValueAt(fila, 2).toString();
        String modelo = (String) jTable1.getValueAt(fila, 3).toString();
        int anioFab = (Integer) jTable1.getValueAt(fila, 4);
        int idClien = (Integer) jTable1.getValueAt(fila, 5);

        Automovil selectedAuto = new Automovil(idAuto, patente, marca, modelo, anioFab, idClien);
        return selectedAuto;

    }

    public Cliente obtenerDatosClienteFilaSeleccionada(int fila) {
        int idCliente = (Integer) jTable1.getValueAt(fila, 0);
        String dni = (String) jTable1.getValueAt(fila, 1).toString();
        String nombre = (String) jTable1.getValueAt(fila, 2).toString();
        String apellido = (String) jTable1.getValueAt(fila, 3).toString();
        String mail = (String) jTable1.getValueAt(fila, 4).toString();
        String celular = (String) jTable1.getValueAt(fila, 5).toString();

        Cliente selectedCliente = new Cliente(idCliente, dni, nombre, apellido, mail, celular);
        return selectedCliente;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonAtras = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Consulta de ");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Title 2", "Title 3", "Title 4", "titulo5", "titulo6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButtonAtras.setBackground(new java.awt.Color(51, 102, 255));
        jButtonAtras.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButtonAtras.setText("Atrás");
        jButtonAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasActionPerformed(evt);
            }
        });

        jButtonDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Service_automotor/Vista/assets/eliminar-icon.png"))); // NOI18N
        jButtonDelete.setToolTipText("Eliminar registro");
        jButtonDelete.setActionCommand("delete");

        jButtonUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Service_automotor/Vista/assets/icono_editar.png"))); // NOI18N
        jButtonUpdate.setToolTipText("Editar registro");
        jButtonUpdate.setActionCommand("edit");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jButtonAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Service_automotor/Vista/assets/agregar-icon.png"))); // NOI18N
        jButtonAdd.setToolTipText("Agregar registro");
        jButtonAdd.setActionCommand("new");
        jButtonAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAddMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonAdd)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonDelete)
                        .addGap(27, 27, 27)
                        .addComponent(jButtonAtras))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAtrasActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jButtonAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAddMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAddMouseClicked

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_jTable1FocusGained

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(VistaTabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(VistaTabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(VistaTabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(VistaTabla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new VistaTabla().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAtras;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
