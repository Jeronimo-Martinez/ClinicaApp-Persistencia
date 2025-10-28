/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicaapp.Utilidades;

/**
 *
 * @author jmart
 */
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.mycompany.clinicaapp.Interfaces.IGestorCita;
import com.mycompany.clinicaapp.Interfaces.IMedicoService;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorMedico;
import com.mycompany.clinicaapp.Modelos.Cita;
import com.mycompany.clinicaapp.Presentacion.ModificarCita;
import com.mycompany.clinicaapp.Presentacion.PanelCitasPaciente;

public class BotonTablaCita extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private JPanel panel;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JTable tabla;
    private IGestorCita gestor;

    public BotonTablaCita(IGestorCita gestor, JTable tabla) {
        this.gestor = gestor;
        this.tabla = tabla;
        crearPanelBotones();
    }

    private void crearPanelBotones() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");

        // Diseño más compacto
        btnModificar.setFocusPainted(false);
        btnModificar.setMargin(new Insets(2, 5, 2, 5));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setMargin(new Insets(2, 5, 2, 5));

        panel.add(btnModificar);
        panel.add(btnEliminar);

        // Acción de los botones
        btnModificar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String idCita = tabla.getValueAt(fila, 0).toString();
                IMedicoService gestorMedico = new GestorMedico();
                Cita cita = gestor.buscarCitaPorId(idCita);
                // obtener la ventana PanelCitasPaciente que contiene la tabla (si existe)
                PanelCitasPaciente ventanaLista = null;
                java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(tabla);
                if (win instanceof PanelCitasPaciente) {
                    ventanaLista = (PanelCitasPaciente) win;
                }
                ModificarCita ventanaModificar = new ModificarCita(gestor, gestorMedico, cita, ventanaLista);
                 ventanaModificar.setVisible(true);
                 Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                 int x = (pantalla.width - ventanaModificar.getWidth()) / 2;
                 int y = (pantalla.height - ventanaModificar.getHeight()) / 2;
                 ventanaModificar.setLocation(x, y);
                 
             }
             fireEditingStopped();
         });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                String id = tabla.getValueAt(fila, 0).toString();
                int confirmar = JOptionPane.showConfirmDialog(tabla,
                        "¿Eliminar cita ID " + id + "?", "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);
                if (confirmar == JOptionPane.YES_OPTION) {
                    gestor.eliminarCita(id);
                    ((DefaultTableModel) tabla.getModel()).removeRow(fila);
                }
            }
            fireEditingStopped();
        });
    }

    // Renderiza los botones (lo que se ve)
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        return panel;
    }

    // Devuelve el panel cuando se hace clic
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}

