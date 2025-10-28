package com.mycompany.clinicaapp.Presentacion;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.mycompany.clinicaapp.Interfaces.IGestorAdministrador;


/**
 * Panel principal del Administrador.
 * Desde aquí puede gestionar Médicos, Pacientes y Especialidades.
 */
public class PanelAdministrador extends JPanel {

    private final IGestorAdministrador gestor;

    public PanelAdministrador(IGestorAdministrador gestor) {
        this.gestor = gestor;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Panel del Administrador"));

        JLabel lblTitulo = new JLabel("Gestión del Sistema Médico", JLabel.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton btnMedicos = new JButton("Gestionar Médicos");
        JButton btnPacientes = new JButton("Gestionar Pacientes");
        JButton btnEspecialidades = new JButton("Gestionar Especialidades");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        panelBotones.add(btnMedicos);
        panelBotones.add(btnPacientes);
        panelBotones.add(btnEspecialidades);
        panelBotones.add(btnCerrarSesion);

        add(panelBotones, BorderLayout.CENTER);

        // Eventos
        btnMedicos.addActionListener(this::abrirPanelMedicos);
        btnPacientes.addActionListener(this::abrirPanelPacientes);
        btnEspecialidades.addActionListener(this::abrirPanelEspecialidades);
        btnCerrarSesion.addActionListener(this::cerrarSesion);
    }

    private void abrirPanelMedicos(ActionEvent e) {
        cambiarContenido(new GestionAdminEnMedicos(gestor));
    }

    private void abrirPanelPacientes(ActionEvent e) {
        cambiarContenido(new GestionAdminEnPacientes(gestor));
    }

    private void abrirPanelEspecialidades(ActionEvent e) {
        cambiarContenido(new GestorAdminEnEspecialidad(gestor));
    }

    private void cambiarContenido(JPanel nuevoPanel) {
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);

        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.add(nuevoPanel, BorderLayout.CENTER);

        JButton btnVolver = new JButton("Volver al Panel Administrador");
        // Use the captured ventana reference so the action still works even after
        // this panel has been replaced in the frame's content pane.
        btnVolver.addActionListener(a -> {
            if (ventana != null) {
                ventana.setContentPane(new PanelAdministrador(gestor));
                ventana.revalidate();
                ventana.repaint();
                ventana.pack();
                ventana.setLocationRelativeTo(null);
                ventana.setVisible(true);
            } else {
                // Fallback: no ancestor frame found, open a new window with the admin panel
                JFrame nueva = new JFrame("Panel Administrador");
                nueva.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nueva.setContentPane(new PanelAdministrador(gestor));
                nueva.pack();
                nueva.setLocationRelativeTo(null);
                nueva.setVisible(true);
            }
        });
        panelContenedor.add(btnVolver, BorderLayout.SOUTH);

        if (ventana != null) {
            ventana.setContentPane(panelContenedor);
            ventana.revalidate();
            ventana.repaint();
            ventana.pack();
            ventana.setLocationRelativeTo(null);
        } else {
            // If no ancestor frame is available, open a new window to display the panel
            JFrame nueva = new JFrame("Administrador");
            nueva.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nueva.setContentPane(panelContenedor);
            nueva.pack();
            nueva.setLocationRelativeTo(null);
            nueva.setVisible(true);
        }
    }

    

    private void cerrarSesion(ActionEvent e) {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cerrar sesión y volver al inicio?",
                "Cerrar sesión",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
            ventana.dispose();

            // Volver al login
            VentanaIniciarSesion ventanaLogin = new VentanaIniciarSesion();
            ventanaLogin.setVisible(true);
                }
    }
}