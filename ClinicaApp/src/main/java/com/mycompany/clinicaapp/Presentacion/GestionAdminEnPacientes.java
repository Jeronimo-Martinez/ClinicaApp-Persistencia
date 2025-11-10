package com.mycompany.clinicaapp.Presentacion;

import com.mycompany.clinicaapp.Modelos.*;
import com.mycompany.clinicaapp.Interfaces.IGestorAdministrador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Panel para la gestión de pacientes:
 * registrar, editar y eliminar.
 */

public class GestionAdminEnPacientes extends JPanel {

    private final IGestorAdministrador gestor;

    private JTextField txtCedula, txtNombre, txtEdad, txtTelefono, txtContrasena;
    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;
    /**
     * Constructor
     * @param gestor
     */
    public GestionAdminEnPacientes(IGestorAdministrador gestor) {
        this.gestor = gestor;
        initComponents();
        cargarPacientes();
    }

    /**
     * Este método inicializa y declara los componentes gráficos del panel
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Gestión de Pacientes"));

        // Formulario superior
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelForm.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelForm.add(txtCedula);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        panelForm.add(txtEdad);

        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);

        panelForm.add(new JLabel("Contraseña:"));
        txtContrasena = new JTextField();
        panelForm.add(txtContrasena);

        add(panelForm, BorderLayout.NORTH);

        // Tabla central
        String[] columnas = {"Cédula", "Nombre", "Edad", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPacientes = new JTable(modeloTabla);
        add(new JScrollPane(tablaPacientes), BorderLayout.CENTER);

        // --- Panel inferior con botones bien alineados ---
        JPanel panelBotones = new JPanel(new GridBagLayout());
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // margen entre botones

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVer = new JButton("Ver Pacientes");

        // Configurar tamaño uniforme
        Dimension tamBoton = new Dimension(140, 30);
        btnRegistrar.setPreferredSize(tamBoton);
        btnEditar.setPreferredSize(tamBoton);
        btnEliminar.setPreferredSize(tamBoton);
        btnVer.setPreferredSize(tamBoton);

        // Añadir botones centrados horizontalmente
        gbc.gridx = 0;
        panelBotones.add(btnRegistrar, gbc);
        gbc.gridx = 1;
        panelBotones.add(btnEditar, gbc);
        gbc.gridx = 2;
        panelBotones.add(btnEliminar, gbc);
        gbc.gridx = 3;
        panelBotones.add(btnVer, gbc);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnRegistrar.addActionListener(this::registrarPaciente);
        btnEditar.addActionListener(this::editarPaciente);
        btnEliminar.addActionListener(this::eliminarPaciente);
        btnVer.addActionListener(this::verPacientes);
    }

    private void registrarPaciente(ActionEvent e) {
        String cedula = txtCedula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String edadTexto = txtEdad.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (cedula.isEmpty() || nombre.isEmpty() || edadTexto.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadTexto);
            if (edad <= 0) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un número positivo");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número válido");
            return;
        }
        if (contrasena.length() < 8 || 
            !contrasena.matches(".*[A-Za-z].*") || 
            !contrasena.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, 
                "La contraseña debe tener al menos 8 caracteres, incluir una letra y un número");
            return;
        }


        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener solo números (10 dígitos)");
            return;
        }
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
            JOptionPane.showMessageDialog(this, "El nombre solo puede contener letras y espacios");
            return;
        }

        Paciente nuevo = new Paciente(cedula, nombre, telefono, edad, contrasena);
        boolean exito = gestor.registrarPaciente(nuevo);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Paciente registrado correctamente");
            limpiarCampos();
            cargarPacientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error: La cédula ya se encuentra registrada o no se pudo registrar");
        }
    }

    private void editarPaciente(ActionEvent e) {
        try {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String edadTexto = txtEdad.getText().trim();
            String contrasena = txtContrasena.getText().trim();

            if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || edadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }

            int edad;
            try {
                edad = Integer.parseInt(edadTexto);
                if (edad <= 0 || edad > 120) {
                    JOptionPane.showMessageDialog(this, "Ingrese una edad válida (1-120 años)");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La edad debe ser un número entero válido");
                return;
            }

            if (!telefono.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "El teléfono debe tener exactamente 10 dígitos numéricos");
                return;
            }

            Paciente actualizado = new Paciente(cedula, nombre, telefono, edad, contrasena);
            boolean exito = gestor.editarPaciente(actualizado);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Paciente actualizado correctamente");
                limpiarCampos();
                cargarPacientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error: no se encontró un paciente con esa cédula");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al intentar editar el paciente: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void eliminarPaciente(ActionEvent e) {
        String cedula = txtCedula.getText().trim();
        JOptionPane.showMessageDialog(
            this,
            "Solo debe ingresar la cédula del paciente que desea eliminar.\n" +
            "Los demás campos no son necesarios.",
            "Aviso",
            JOptionPane.INFORMATION_MESSAGE
            );
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar la cédula del paciente a eliminar");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de eliminar al paciente con cédula " + cedula + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            Paciente pacienteAEliminar = new Paciente(cedula);
            boolean exito = gestor.eliminarPaciente(pacienteAEliminar);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Paciente eliminado correctamente");
                limpiarCampos();
                cargarPacientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error: no se pudo eliminar el paciente");
            }
        }
    }

    private void cargarPacientes() {
        modeloTabla.setRowCount(0);
        List<Paciente> pacientes = gestor.listarPacientes();
        for (Paciente p : pacientes) {
            modeloTabla.addRow(new Object[]{
                    p.getCedula(),
                    p.getNombre(),
                    p.getEdad(),
                    p.getTelefono()
            });
        }
    }

    private void verPacientes(ActionEvent e) {
        List<Paciente> pacientes = gestor.listarPacientes();
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pacientes registrados");
            return;
        }

        StringBuilder sb = new StringBuilder("Listado de Pacientes:\n\n");
        for (Paciente p : pacientes) {
            sb.append("• ").append(p.getCedula())
              .append(" - ").append(p.getNombre())
              .append(" (").append(p.getEdad()).append(", ")
              .append(p.getTelefono()).append(")\n");
        }

        JTextArea area = new JTextArea(sb.toString(), 15, 40);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Pacientes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
        txtContrasena.setText("");
    }
}
