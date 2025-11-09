package com.mycompany.clinicaapp.Presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.clinicaapp.Interfaces.IPacienteService;
import com.mycompany.clinicaapp.Modelos.Paciente;

/**
 * Clase que representa la ventana de registro de nuevos pacientes en la aplicación ClínicaApp.

 * Permite a los usuarios ingresar sus datos personales (cédula, nombre, teléfono, edad y contraseña)
 * para crear una cuenta en el sistema. Los datos son validados y enviados al servicio de pacientes
 * para su registro. 
 */
public class VentanaRegistrarse extends JFrame {
    // Declaración de variables
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEdad;
    private JPasswordField txtContrasena;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    private final IPacienteService gestorPaciente;

    public VentanaRegistrarse(IPacienteService gestorPaciente) {
        this.gestorPaciente = gestorPaciente;

        inicializarComponentes();
        configurarVentana();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Registro de Paciente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Cédula:"), gbc);
        txtCedula = new JTextField(15);
        gbc.gridx = 1;
        add(txtCedula, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(15);
        gbc.gridx = 1;
        add(txtNombre, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(15);
        gbc.gridx = 1;
        add(txtTelefono, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Edad:"), gbc);
        txtEdad = new JTextField(15);
        gbc.gridx = 1;
        add(txtEdad, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Contraseña:"), gbc);
        txtContrasena = new JPasswordField(15);
        gbc.gridx = 1;
        add(txtContrasena, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        btnRegistrar = new JButton("Registrar");
        add(btnRegistrar, gbc);

        gbc.gridx = 1;
        btnCancelar = new JButton("Cancelar");
        add(btnCancelar, gbc);
    }

    private void configurarVentana() {
        setTitle("ClínicaApp - Registro de Paciente");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void configurarEventos() {
        btnRegistrar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String cedula = txtCedula.getText().trim();
                String nombre = txtNombre.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String edadTexto = txtEdad.getText().trim();
                String contrasena = new String(txtContrasena.getPassword()).trim();

                // Validar campos vacíos
                if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || edadTexto.isEmpty()
                        || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "Por favor complete todos los campos.",
                            "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                //  Validar cédula numérica
                if (!cedula.matches("\\d+")) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "La cédula debe contener solo números.",
                            "Error en cédula", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar nombre solo letras
                if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "El nombre solo puede contener letras y espacios.",
                            "Error en nombre", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar edad numérica y rango
                int edad;
                try {
                    edad = Integer.parseInt(edadTexto);
                    if (edad <= 0 || edad > 120) {
                        JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                                "La edad debe estar entre 1 y 120 años.",
                                "Error en edad", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "La edad debe ser un número válido.",
                            "Error en edad", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar teléfono
                if (!telefono.matches("\\d{10}")) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "El teléfono debe tener exactamente 10 dígitos.",
                            "Error en teléfono", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validar contraseña
                if (contrasena.length() < 6) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "La contraseña debe tener al menos 6 caracteres.",
                            "Error en contraseña", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Crear paciente si todo está correcto
                Paciente nuevoPaciente = new Paciente(cedula, nombre, telefono, edad, contrasena);
                boolean exito = gestorPaciente.registrarPaciente(nuevoPaciente);

                if (exito) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "Paciente registrado con éxito.",
                            "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // cerrar la ventana
                } else {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "Ya existe un paciente con esa cédula.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
