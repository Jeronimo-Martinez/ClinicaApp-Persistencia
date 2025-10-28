/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.clinicaapp.Presentacion;

import com.mycompany.clinicaapp.LogicaDelNegocio.GestorMedico;
import com.mycompany.clinicaapp.Interfaces.IGestorCita;
import com.mycompany.clinicaapp.Interfaces.IMedicoService;
import com.mycompany.clinicaapp.Modelos.Cita;
import com.mycompany.clinicaapp.Modelos.Medico;
import com.mycompany.clinicaapp.Modelos.Paciente;
import com.mycompany.clinicaapp.Utilidades.BotonTablaCita;
import java.awt.Component;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author jmart
 */
public class PanelCitasPaciente extends javax.swing.JFrame {
    private DefaultTableModel modelotabla;
    private IGestorCita gestor;
    private IMedicoService medicoService;
    private Paciente pacienteActual;
    public List<Cita> citas;
    public PanelCitasPaciente(List<Cita> citas, IGestorCita gestor, Paciente paciente) {
        initComponents();
        this.gestor = gestor;
        this.medicoService = new GestorMedico();
        this.pacienteActual = paciente;
        // Obtener dimensiones de la pantalla
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        
        // Calcular la mitad del ancho y alto
        int width = screenSize.width* 2/3;
        int height = screenSize.height* 2/3;
        
        // Centrar la ventana
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        
        // Establecer tamaño y posición
        this.setBounds(x, y, width, height);
        // Configurar fecha con formato placeholder
        fechaStr.setText("YYYY-MM-DD");
        
        // Configurar ComboBox Especialidad
        DefaultComboBoxModel<String> modeloEsp = new DefaultComboBoxModel<>();
        modeloEsp.addElement("Seleccione especialidad");
        for(Medico m : medicoService.listaMedicos()) {
            if(m.getEspecialidad() != null && modeloEsp.getIndexOf(m.getEspecialidad().getNombre()) == -1) {
                modeloEsp.addElement(m.getEspecialidad().getNombre());
            }
        }
        boxEsp.setModel(modeloEsp);

        // Listener para cuando cambie la especialidad
        boxEsp.addActionListener(evt -> {
            String especialidadSeleccionada = (String)boxEsp.getSelectedItem();
            actualizarComboMedicos(especialidadSeleccionada);
        });

        // Inicializar combo médicos vacío
        DefaultComboBoxModel<Medico> modeloMed = new DefaultComboBoxModel<>();
        modeloMed.addElement(null);
        boxMed.setModel(modeloMed);

        // Configurar renderer para mostrar solo nombre del médico
        boxMed.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Medico) {
                    setText(((Medico) value).getNombre());
                } else {
                    setText("Seleccione médico");
                }
                return this;
            }
        });

        configurarTabla();
        cargarCitas(citas);
        tablaCitas.setRowHeight(32);

        
        
    }

    private PanelCitasPaciente() {
        initComponents();
    }
    public void refrescarTabla() {
    // Limpia el modelo de la tabla
    DefaultTableModel modelo = (DefaultTableModel) tablaCitas.getModel();
    modelo.setRowCount(0);

    // Vuelve a obtener las citas actualizadas 
    List<Cita> citasActualizadas = citas; 
    
    // Agrega las filas nuevamente
        for (Cita c : citasActualizadas) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getFecha().toString(),
                c.getPaciente().getNombre(),
                c.getMedico().getNombre()
            });
        }
    }

  
    private void configurarTabla(){
        modelotabla = new DefaultTableModel(new Object[]{"ID", "Fecha", "Paciente", "Médico"},0){
            @Override
            public boolean isCellEditable(int fila, int columna) {
                // Para pacientes ninguna celda es editable desde la tabla (no pueden modificar citas)
                return false;
            }
        };
        tablaCitas.setModel(modelotabla);

    }
    private void cargarCitas(List<Cita> citas) {
    modelotabla.setRowCount(0); // limpia tabla
    for (Cita c : citas) {
        modelotabla.addRow(new Object[]{
            c.getId(),
            c.getFecha(),
            c.getPaciente().getNombre(),
            c.getMedico().getNombre(),
            "Acciones"
        });
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCitas = new javax.swing.JTable();
        botonVolver = new javax.swing.JButton();
        botonCerrar = new javax.swing.JButton();
        titulo = new java.awt.Label();
        reservartitle = new java.awt.Label();
        botonReserva = new javax.swing.JButton();
        boxMed = new javax.swing.JComboBox<>();
        boxEsp = new javax.swing.JComboBox<>();
        fechaStr = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tablaCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tablaCitas);

        botonVolver.setText("Volver");
        botonVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonVolverActionPerformed(evt);
            }
        });

        botonCerrar.setText("Cerrar");
        botonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCerrarActionPerformed(evt);
            }
        });

        titulo.setAlignment(java.awt.Label.CENTER);
        titulo.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        titulo.setText("Citas Pendientes: ");

        reservartitle.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        reservartitle.setText("Reservar Cita :");

        botonReserva.setText("Reservar");
        botonReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReservaActionPerformed(evt);
            }
        });

        boxMed.setModel(new javax.swing.DefaultComboBoxModel<Medico>());

        boxEsp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        fechaStr.setText("jTextField1");
        fechaStr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechaStrActionPerformed(evt);
            }
        });

        jLabel1.setText("Fecha");

        jLabel2.setText("Especialidad");

        jLabel3.setText("Medico");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(botonVolver)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonCerrar)
                                .addGap(6, 6, 6))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(reservartitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fechaStr, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boxEsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(boxMed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addComponent(botonReserva)
                        .addGap(19, 19, 19))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(jLabel1))
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reservartitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(botonReserva)
                        .addComponent(boxMed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boxEsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fechaStr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonVolver)
                    .addComponent(botonCerrar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolverActionPerformed
        VentanaIniciarSesion ventana = new VentanaIniciarSesion();
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null); 
        this.dispose();
        // TODO : Modificar ventana anterior 
    }//GEN-LAST:event_botonVolverActionPerformed

    private void botonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCerrarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_botonCerrarActionPerformed

    private void actualizarComboMedicos(String especialidad) {
        DefaultComboBoxModel<Medico> modeloMed = new DefaultComboBoxModel<>();
        modeloMed.addElement(null);
        
        if(especialidad != null && !especialidad.equals("Seleccione especialidad")) {
            for(Medico m : medicoService.listaMedicos()) {
                if(m.getEspecialidad() != null && 
                   m.getEspecialidad().getNombre().equals(especialidad)) {
                    modeloMed.addElement(m);
                }
            }
        }
        
        boxMed.setModel(modeloMed);
    }

    private void botonReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReservaActionPerformed
        try {
            // Validar fecha
            String fechaStr = this.fechaStr.getText();
            LocalDate fecha = LocalDate.parse(fechaStr);
            
            // Validar médico
            Medico medico = (Medico)boxMed.getSelectedItem();
            if(medico == null) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor seleccione un médico", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear y guardar cita
            String id = UUID.randomUUID().toString().substring(0, 8);
            Cita nuevaCita = new Cita(id, fecha, "", medico, pacienteActual);
            
            if(gestor.registrarCita(nuevaCita)) {
                JOptionPane.showMessageDialog(this, 
                    "Cita registrada exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                citas = gestor.consultarCitasPaciente(pacienteActual);
                refrescarTabla();
                
                // Limpiar campos
                this.fechaStr.setText("YYYY-MM-DD");
                boxEsp.setSelectedIndex(0);
                boxMed.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al registrar la cita", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch(DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Formato de fecha inválido. Use YYYY-MM-DD", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al procesar la solicitud: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_botonReservaActionPerformed

    private void fechaStrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechaStrActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fechaStrActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PanelCitasPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PanelCitasPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PanelCitasPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PanelCitasPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PanelCitasPaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCerrar;
    private javax.swing.JButton botonReserva;
    private javax.swing.JButton botonVolver;
    private javax.swing.JComboBox<String> boxEsp;
    private javax.swing.JComboBox<Medico> boxMed;
    private javax.swing.JTextField fechaStr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private java.awt.Label reservartitle;
    private javax.swing.JTable tablaCitas;
    private java.awt.Label titulo;
    // End of variables declaration//GEN-END:variables
}
