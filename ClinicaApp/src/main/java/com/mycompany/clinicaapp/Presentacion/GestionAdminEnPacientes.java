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
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
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

        panelForm.add(new JLabel("Contraseña"));
        txtContrasena = new JTextField();
        panelForm.add(txtContrasena);

        add(panelForm, BorderLayout.NORTH);

        // Tabla central
        String[] columnas = {"Cédula", "Nombre", "Dirección", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPacientes = new JTable(modeloTabla);
        add(new JScrollPane(tablaPacientes), BorderLayout.CENTER);

        // Botones inferiores
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVer = new JButton("Ver Pacientes");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVer);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnRegistrar.addActionListener(this::registrarPaciente);
        btnEditar.addActionListener(this::editarPaciente);
        btnEliminar.addActionListener(this::eliminarPaciente);
        btnVer.addActionListener(this::verPacientes);
    }

    /**
     * Este método se encarga de leer lo que ingrese el administrador en los campos de texto 
     * y crear a un paciente con estos datos para luego enviarlo al gestor, que luego llamará al método para
     * registrar pacientes del gestor de médicos
     * @param e
     */
    private void registrarPaciente(ActionEvent e) {
    // Obtener y limpiar valores de los campos de texto
    String cedula = txtCedula.getText().trim();
    String nombre = txtNombre.getText().trim();
    String edadTexto = txtEdad.getText().trim();
    String telefono = txtTelefono.getText().trim();
    String contrasena = txtContrasena.getText().trim();

    // Validar que los campos obligatorios no estén vacíos
    if (cedula.isEmpty() || nombre.isEmpty() || edadTexto.isEmpty() || telefono.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
        return;
    }

    int edad;
    // Validar que la edad sea un número entero positivo
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

    // Validar que el teléfono contenga solo dígitos 
    if (!telefono.matches("\\d{10}")) { 
        JOptionPane.showMessageDialog(this, "El teléfono debe contener solo números (10 dígitos)");
        return;
    }

    // Crear el nuevo paciente
    Paciente nuevo = new Paciente(cedula, nombre, telefono, edad, contrasena);
    boolean exito = gestor.registrarPaciente(nuevo);

    // Mensaje según el resultado
    if (exito) {
        JOptionPane.showMessageDialog(this, "Paciente registrado correctamente");
        limpiarCampos();
        cargarPacientes();
    } else {
        JOptionPane.showMessageDialog(this, "Error: ya existe un paciente con esa cédula o no se pudo registrar");
    }
}

    /**
     * Este método se encarga de leer los datos que el paciente ingresa, 
     * para crear uno nuevo pero con los datos ya actualizados, el cual se envía al gestor de pacientes por medio
     * del gestor del administrador 
     * @param e: Es la acción de cuando el administrador presiona el botón de editar paciente
     */
    private void editarPaciente(ActionEvent e) {
        try {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String edadTexto = txtEdad.getText().trim();
            String contrasena = txtContrasena.getText().trim();

            // Validar campos vacíos
            if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || edadTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }

            // Validar edad numérica y rango
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

            // Validar teléfono
            if (!telefono.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "El teléfono debe tener exactamente 10 dígitos numéricos");
                return;
            }

            // Crear objeto actualizado
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

    /**
     * Este método se encarga de pedir la cédula del paciente a eliminar, para luego crear uno y mandarlo a el gestor
     * de pacientes con ayuda del gestor del administrador y hacer la eliminación
     * @param e: Acción del administrador al presionar el botón de eliminar un paciente
     */

    private void eliminarPaciente(ActionEvent e) {
        String cedula = txtCedula.getText().trim();

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
    /**
     * Carga en la tabla todos los pacientes registrados en el sistema.
     * Este método obtiene la lista actual de pacientes desde el gestor
     * y la muestra en la interfaz gráfica, dentro de la tabla asociada
     * al modelo `modeloTabla`.
     */
    private void cargarPacientes() {
        modeloTabla.setRowCount(0); // limpia la tabla
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
    /**
     * Muestra una lista con todos los pacientes registrados en el sistema.
     * Este método obtiene la lista actual de pacientes desde el gestor 
     * y los presenta en un cuadro de diálogo en formato legible. 
     * Si no existen pacientes registrados, muestra un mensaje de advertencia.
     * @param e el evento que dispara la acción (clic en el botón "Ver pacientes")
     */
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

    /**
     * Limpia todos los campos del formulario de pacientes.
     *
     * Este método restablece los valores de los campos de texto 
     * a vacío, preparando el formulario para una nueva entrada.
     */
    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
    }
}