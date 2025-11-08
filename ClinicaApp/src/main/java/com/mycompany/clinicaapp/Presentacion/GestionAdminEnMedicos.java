package com.mycompany.clinicaapp.Presentacion;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.mycompany.clinicaapp.Interfaces.IGestorAdministrador;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import com.mycompany.clinicaapp.Modelos.Medico;
import javax.swing.*;
import java.awt.*;





/**
 * Este panel es para las operaciones que puede hacer el administrador en la página en cuanto a los médicos, es decir, 
 * la creación, edición y eliminación de estos.
 */


public class GestionAdminEnMedicos extends JPanel {

    private final IGestorAdministrador gestor;
    private JTextField txtCedula, txtNombre, txtContrasena;
    private JComboBox<Especialidad> comboEspecialidad;

    public GestionAdminEnMedicos(IGestorAdministrador gestor) {
        this.gestor = gestor;
        initComponents();
        cargarEspecialidades();
    }

    /**
     * Inicializa los componentes gráficos del panel con un diseño ordenado (GridBagLayout).
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Gestión de Médicos"));

        // ===== Panel del formulario =====
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Fila 1 - Cédula
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Cédula:"), gbc);

        gbc.gridx = 1;
        txtCedula = new JTextField(15);
        panelForm.add(txtCedula, gbc);

        // Fila 2 - Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        panelForm.add(txtNombre, gbc);

        // Fila 3 - Contraseña
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        txtContrasena = new JTextField(15);
        panelForm.add(txtContrasena, gbc);

        // Fila 4 - Especialidad
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Especialidad:"), gbc);

        gbc.gridx = 1;
        comboEspecialidad = new JComboBox<>();
        panelForm.add(comboEspecialidad, gbc);

        // Agregar formulario al centro
        add(panelForm, BorderLayout.CENTER);

        // ===== Panel de botones =====
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVer = new JButton("Ver Médicos");
        JButton btnVolver = new JButton("Volver");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVer);
        panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        // ===== Acciones =====
        btnRegistrar.addActionListener(this::registrarMedico);
        btnEditar.addActionListener(this::editarMedico);
        btnEliminar.addActionListener(this::eliminarMedico);
        btnVer.addActionListener(this::verMedicos);
        btnVolver.addActionListener(this::volverAlPanelAdministrador);
    }

    /**
     * Acción del botón "Volver" al panel administrador.
     */
    private void volverAlPanelAdministrador(ActionEvent e) {
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
        ventana.setContentPane(new PanelAdministrador(gestor));
        ventana.revalidate();
        ventana.repaint();
        ventana.pack();
        ventana.setLocationRelativeTo(null);
    }

    /**
     * Registrar un nuevo médico.
     */
    private void registrarMedico(ActionEvent e) {
        try {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String contrasena = txtContrasena.getText().trim();
            Especialidad esp = (Especialidad) comboEspecialidad.getSelectedItem();

            if (cedula.isEmpty() || nombre.isEmpty() || contrasena.isEmpty() || esp == null) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos antes de continuar");
                return;
            }

            if (!cedula.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "La cédula debe contener solo números");
                return;
            }

            Medico nuevo = new Medico(cedula, nombre, esp, contrasena);
            boolean exito = gestor.registrarMedico(nuevo);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Médico registrado correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Ya existe un médico con esa cédula");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar médico: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Editar un médico existente.
     */
    private void editarMedico(ActionEvent e) {
        try {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String contrasena = txtContrasena.getText().trim();
            Especialidad esp = (Especialidad) comboEspecialidad.getSelectedItem();

            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese la cédula del médico a editar");
                return;
            }

            if (nombre.isEmpty() || contrasena.isEmpty() || esp == null) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos antes de editar");
                return;
            }

            Medico actualizado = new Medico(cedula, nombre, esp, contrasena);
            boolean exito = gestor.editarMedico(actualizado, nombre, esp);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Médico actualizado correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un médico con esa cédula");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al editar médico: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Eliminar un médico por cédula.
     */
    private void eliminarMedico(ActionEvent e) {
        String cedula = txtCedula.getText().trim();

        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cédula del médico a eliminar");
            return;
        }

        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "La cédula debe contener solo números");
            return;
        }

        if (cedula.length() > 10) {
            JOptionPane.showMessageDialog(this, "La cédula debe tener entre 6 y 10 dígitos");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar el médico con cédula " + cedula + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = gestor.eliminarMedico(cedula);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Médico eliminado correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un médico con esa cédula");
            }
        }
    }

    /**
     * Mostrar lista de médicos.
     */
    private void verMedicos(ActionEvent e) {
        List<Medico> medicos = gestor.listarMedicos();
        if (medicos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay médicos registrados");
            return;
        }

        StringBuilder sb = new StringBuilder("Listado de Médicos:\n\n");
        for (Medico m : medicos) {
            sb.append("• ").append(m.getCedula()).append(" - ").append(m.getNombre());
            if (m.getEspecialidad() != null)
                sb.append(" (").append(m.getEspecialidad().getNombre()).append(")");
            sb.append("\n");
        }

        JTextArea area = new JTextArea(sb.toString(), 15, 40);
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Médicos", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Carga las especialidades en el combo box.
     */
    private void cargarEspecialidades() {
        comboEspecialidad.removeAllItems();
        for (Especialidad esp : gestor.listarEspecialidades()) {
            comboEspecialidad.addItem(esp);
        }
    }

    /**
     * Limpia los campos del formulario.
     */
    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtContrasena.setText("");
        comboEspecialidad.setSelectedIndex(-1);
    }
}
