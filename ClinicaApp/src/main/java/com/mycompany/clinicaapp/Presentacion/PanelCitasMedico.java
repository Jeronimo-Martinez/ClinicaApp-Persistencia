/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.clinicaapp.Presentacion;

import com.mycompany.clinicaapp.LogicaDelNegocio.GestorCita;
import com.mycompany.clinicaapp.Interfaces.IHistorialService;
import com.mycompany.clinicaapp.Modelos.Cita;
import com.mycompany.clinicaapp.Modelos.Medico;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JButton;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jmart
 */


public class PanelCitasMedico extends JPanel {

    private IHistorialService gestorHistorial;
    private GestorCita gestor;
    private Medico medico;
    private DefaultTableModel modelotabla;

    private JTable tablaCitas;
    private JTextField diagnostico;
    private JTextField idCita;
    private JPanel panelDinamico;

    public PanelCitasMedico(List<Cita> citas, GestorCita gestor, IHistorialService gestorHistorial, Medico medico) {
        this.gestor = gestor;
        this.gestorHistorial = gestorHistorial;
        this.medico = medico;

        initComponents(citas);
    }

    private void initComponents(List<Cita> citas) {
        // Componentes
        JLabel lblCitas = new JLabel("Citas Pendientes:");
        JLabel lblDiag = new JLabel("Diagnóstico:");
        JLabel lblId = new JLabel("ID Cita:");

        diagnostico = new JTextField();
        idCita = new JTextField();

        JButton btnSubir = new JButton("Subir Diagnóstico");
        JButton btnHistorial = new JButton("Ver Historial");

        modelotabla = new DefaultTableModel(new Object[]{"ID", "Fecha", "Paciente", "Médico"},0);
        tablaCitas = new JTable(modelotabla);
        tablaCitas.setRowHeight(32);
        cargarCitas(citas);
        JScrollPane scrollTabla = new JScrollPane(tablaCitas);

        // Panel para historial
        panelDinamico = new JPanel(new BorderLayout());
        panelDinamico.setPreferredSize(new Dimension(400,300));

        // Layout tipo NetBeans (GroupLayout)
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblCitas)
                .addComponent(scrollTabla)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblDiag)
                    .addComponent(diagnostico, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblId)
                    .addComponent(idCita, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubir)
                    .addComponent(btnHistorial))
                .addComponent(panelDinamico)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblCitas)
                .addComponent(scrollTabla, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiag)
                    .addComponent(diagnostico, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblId)
                    .addComponent(idCita, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSubir)
                    .addComponent(btnHistorial))
                .addComponent(panelDinamico, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
        );

        // Acciones de botones
        btnSubir.addActionListener(e -> subirDiagnostico());
        btnHistorial.addActionListener(e -> abrirHistorial());
    }

    private void cargarCitas(List<Cita> citas) {
        modelotabla.setRowCount(0);
        for (Cita c : citas) {
            modelotabla.addRow(new Object[]{
                c.getId(),
                c.getFecha(),
                c.getPaciente().getNombre(),
                c.getMedico().getNombre()
            });
        }
    }

    private void subirDiagnostico() {
        try {
            String id = idCita.getText().trim();
            String diag = diagnostico.getText().trim();
            Cita cita = gestor.buscarCitaPorId(id);
            if (cita != null) {
                cita.setDiagnostico(diag);
                JOptionPane.showMessageDialog(this, "Diagnóstico registrado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cita no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(this, "Error al subir diagnóstico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirHistorial() {
        PanelHistorial panelHistorial = new PanelHistorial(gestorHistorial, this);
        panelDinamico.removeAll();
        panelDinamico.add(panelHistorial, BorderLayout.CENTER);
        panelDinamico.revalidate();
        panelDinamico.repaint();
    }

    public void mostrarPanelCitas() {
        panelDinamico.removeAll();
        panelDinamico.revalidate();
        panelDinamico.repaint();
    }

    public void refrescarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tablaCitas.getModel();
        modelo.setRowCount(0);
        List<Cita> citasActualizadas = gestor.getCitas();
        for (Cita c : citasActualizadas) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getFecha(),
                c.getPaciente().getNombre(),
                c.getMedico().getNombre()
            });
        }
    }
}
