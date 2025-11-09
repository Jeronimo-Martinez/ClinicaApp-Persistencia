package com.mycompany.clinicaapp.Presentacion;

import com.mycompany.clinicaapp.Interfaces.*;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorAdministrador;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorCita;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorEspecialidad;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorHistorial;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorInicioSesion;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorMedico;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorPaciente;
import com.mycompany.clinicaapp.Modelos.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Clase que representa la ventana principal de inicio de sesión de la aplicación ClínicaApp.
 * Esta ventana permite al usuario ingresar su cédula y contraseña para autenticarse en el sistema.
 * Según el tipo de usuario (Paciente, Médico o Administrador), se redirige al panel correspondiente.
 * También ofrece opciones para registrarse o salir del sistema.
 */
public class VentanaIniciarSesion extends JFrame {

    private JTextField txtCedula;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    private JButton btnSalir;
    private JButton btnRegistrarse;

    // Servicios / gestores
    private final IPacienteService gestorPaciente;
    private final IMedicoService gestorMedico;
    private final IEspecialidadService gestorEspecialidad;
    private final IGestorAdministrador gestorAdmin;
    private final IInicioSesionService gestorInicioSesion;
    private final GestorCita gestorCita;

    /**
     * Constructor.
     * Inicializa los gestores y servicios necesarios para manejar la lógica de inicio de sesión.
     * Configura la interfaz gráfica y los eventos asociados a los botones.
     */
    public VentanaIniciarSesion() {
        // Inicialización de gestores
        gestorPaciente = new GestorPaciente();
        gestorMedico = new GestorMedico();
        gestorEspecialidad = new GestorEspecialidad();
        gestorAdmin = new GestorAdministrador(gestorMedico, gestorPaciente, gestorEspecialidad);
        gestorInicioSesion = new GestorInicioSesion(gestorPaciente, gestorMedico, gestorAdmin);
        gestorCita = new GestorCita();

        inicializarComponentes();
        configurarVentana();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Cédula:"), gbc);
        txtCedula = new JTextField(15);
        gbc.gridx = 1;
        add(txtCedula, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Contraseña:"), gbc);
        txtContrasena = new JPasswordField(15);
        gbc.gridx = 1;
        add(txtContrasena, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        btnIniciarSesion = new JButton("Iniciar Sesión");
        add(btnIniciarSesion, gbc);

        gbc.gridx = 1;
        btnSalir = new JButton("Salir");
        add(btnSalir, gbc);

        // --- Nuevo bloque: texto y botón para registrarse ---
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JLabel lblNoCuenta = new JLabel("¿Aún no tienes una cuenta?", SwingConstants.CENTER);
        add(lblNoCuenta, gbc);

        gbc.gridy++;
        btnRegistrarse = new JButton("Registrarse");
        add(btnRegistrarse, gbc);
    }
    /**
     * Configura las propiedades principales de la ventana (título, tamaño, cierre, posición).
     */

    private void configurarVentana() {
        setTitle("ClínicaApp - Inicio de Sesión");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
     /**
     * Define los eventos de los botones
     */
    private void configurarEventos() {
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = txtCedula.getText().trim();
                String contrasena = new String(txtContrasena.getPassword()).trim();

                if (cedula.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaIniciarSesion.this,
                            "Por favor ingrese cédula y contraseña.",
                            "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                IUsuario usuario = gestorInicioSesion.iniciarSesion(cedula, contrasena);

                if (usuario == null) {
                    JOptionPane.showMessageDialog(VentanaIniciarSesion.this,
                            "Cédula o contraseña incorrectas.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Según el tipo de usuario autenticado
                if (usuario instanceof Paciente) {
                    Paciente paciente = (Paciente) usuario;
                    PanelPaciente panelPaciente = new PanelPaciente(
                            gestorPaciente, gestorCita, paciente, VentanaIniciarSesion.this);
                    setContentPane(panelPaciente);
                    revalidate();
                    repaint();

                } else if (usuario instanceof Administrador) {
                    PanelAdministrador panelAdmin = new PanelAdministrador(gestorAdmin);
                    setContentPane(panelAdmin);
                    revalidate();
                    repaint();
                    pack();
                    setLocationRelativeTo(null);
                } else if (usuario instanceof Medico){
                        Medico medico = (Medico) usuario;

                        // Crear gestores
                        IHistorialService gestorHistorial = new GestorHistorial(gestorCita);
                        ArrayList<Cita> citas = (ArrayList<Cita>) gestorCita.consultarCitasMedico(medico);

                        // Crear panel del médico
                        PanelCitasMedico panelMedico = new PanelCitasMedico(citas, gestorCita, gestorHistorial, medico);
                        setContentPane(panelMedico);
                        revalidate();
                        repaint();
                        pack();
                        setLocationRelativeTo(null);
                        
                    }
                }
        });

        btnRegistrarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaRegistrarse ventanaRegistro =
                        new VentanaRegistrarse(gestorPaciente);
                ventanaRegistro.setVisible(true);
            }
        });

        btnSalir.addActionListener(e -> System.exit(0));
    }
}

