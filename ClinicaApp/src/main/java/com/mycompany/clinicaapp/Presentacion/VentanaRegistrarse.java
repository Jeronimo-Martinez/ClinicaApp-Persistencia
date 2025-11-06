package com.mycompany.clinicaapp.Presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mycompany.clinicaapp.Interfaces.IPacienteService;
import com.mycompany.clinicaapp.Modelos.Paciente;


public class VentanaRegistrarse extends JFrame {
    //Declaración de variables
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
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula = txtCedula.getText().trim();
                String nombre = txtNombre.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String edadTexto = txtEdad.getText().trim();
                String contrasena = new String(txtContrasena.getPassword()).trim();

                if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || edadTexto.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "Por favor complete todos los campos.",
                            "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int edad;
                try {
                    edad = Integer.parseInt(edadTexto);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "La edad debe ser un número entero.",
                            "Error en la edad", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Paciente nuevoPaciente = new Paciente(cedula, nombre, telefono, edad, contrasena);

                boolean exito = gestorPaciente.registrarPaciente(nuevoPaciente);

                if (exito) {
                    JOptionPane.showMessageDialog(VentanaRegistrarse.this,
                            "Paciente registrado con éxito.",
                            "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // cerrar ventana después del registro
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
