package com.mycompany.clinicaapp.Presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
     * Este método se encarga de inicializar los componentes gráficos del panel, como los botones, campos de texto, etc...
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Gestión de Médicos"));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelForm.add(txtCedula);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

    // Añadir contraseña al formulario (antes se añadía al contenedor raíz por error)
    panelForm.add(new JLabel("Contraseña:"));
    txtContrasena = new JTextField();
    panelForm.add(txtContrasena);

        panelForm.add(new JLabel("Especialidad:"));
    comboEspecialidad = new JComboBox<>();
    panelForm.add(comboEspecialidad);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVer = new JButton("Ver Médicos");
    JButton btnVolver = new JButton("Volver");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVer);
    // Botón para volver al panel administrador
    panelBotones.add(btnVolver);

        add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(this::registrarMedico);
        btnEditar.addActionListener(this::editarMedico);
        btnEliminar.addActionListener(this::eliminarMedico);
        btnVer.addActionListener(this::verMedicos);
        btnVolver.addActionListener((ActionEvent e) -> {
            // Volver al panel administrador
            javax.swing.JFrame ventana = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            ventana.setContentPane(new PanelAdministrador(gestor));
            // Asegurar que el frame se actualice y mantenga tamaño razonable
            ventana.revalidate();
            ventana.repaint();
            ventana.pack();
            ventana.setLocationRelativeTo(null);
        });
    }

    /**
     * Este método permite al administrador completar los campos para crear un médico,
     * Luego verifica que estén completos y crea un objeto tipo médico para que luego el gestor llame al método de
     * la interfaz IMedicoService y cree el médico
     * @param e, es la acción del usuario al presionar el botón para registrar médicos
     */
    private void registrarMedico(ActionEvent e) {
        try {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String contrasena = txtContrasena.getText().trim();
            Especialidad esp = (Especialidad) comboEspecialidad.getSelectedItem();

            // Validaciones de campos vacíos o nulos
            if (cedula.isEmpty() || nombre.isEmpty() || contrasena.isEmpty() || esp == null) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos antes de continuar");
                return;
            }

            // Validar formato de cédula 
            if (!cedula.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "La cédula debe contener solo números");
                return;
            }

            // Crear objeto médico
            Medico nuevo = new Medico(cedula, nombre, esp, contrasena);

            // Intentar registrar en el gestor
            boolean exito = gestor.registrarMedico(nuevo);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Médico registrado correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Ya existe un médico con esa cédula");
            }

    } catch (NullPointerException ex) {
        JOptionPane.showMessageDialog(this, "Ocurrió un error: datos incompletos o nulos");
        ex.printStackTrace();

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Formato inválido en campos numéricos");
        ex.printStackTrace();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error inesperado al registrar médico: " + ex.getMessage());
        ex.printStackTrace();
    }
}
    /**
     * Este método se encarga de pedir nuevamente los datos del médico y enviarlo como objeto
     * nuevamente en el gestor que a su vez llama al gestor de médicos para realizar la modificación
     * @param e, es cuando el administrador presiona el botón para editar médico
     */
    private void editarMedico(ActionEvent e) {
        try {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String contrasena = txtContrasena.getText().trim(); 
            Especialidad esp = (Especialidad) comboEspecialidad.getSelectedItem();

            // Validaciones
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese la cédula del médico a editar");
                return;
            }

            if (nombre.isEmpty() || contrasena.isEmpty() || esp == null) {
                JOptionPane.showMessageDialog(this, "Complete todos los campos antes de editar");
                return;
            }

            // Crear objeto actualizado
            Medico actualizado = new Medico(cedula, nombre, esp, contrasena);

        
        boolean exito = gestor.editarMedico(actualizado, nombre, esp);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Médico actualizado correctamente");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error: no se encontró un médico con esa cédula");
        }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocurrió un error al editar el médico: " + ex.getMessage());
            ex.printStackTrace();
        }   
    }

    /**
     * Este método se encarga de pedir la cédula del médico que se va a eliminar al administrador
     * para luego mandarsela al gestor de médicos y realice la eliminación 
     * @param e, Es la acción de cuando el administrador presiona el botón de eliminar médico
     */
    private void eliminarMedico(ActionEvent e) {
        String cedula = txtCedula.getText().trim();

        //Validar campo vacío
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la cédula del médico a eliminar");
            return;
        }

        //Validar que sean solo números
        if (!cedula.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "La cédula debe contener solo números");
            return;
        }

        //Validar longitud 
        if (cedula.length() > 10) {
            JOptionPane.showMessageDialog(this, "La cédula debe tener entre 6 y 10 dígitos");
            return;
        }

        // Confirmación antes de eliminar
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
     * Muestra un listado de todos los médicos registrados en el sistema.
     * Este método obtiene la lista de médicos desde el gestor, el cual llama al método que obtiene la lista
     * de médicos del gestor de médicos
     * @param e Es la acción del administrador cuando presiona el botón para ver médicos
     */
    private void verMedicos(ActionEvent e) {
        // Obtener la lista de médicos desde el gestor
        List<Medico> medicos = gestor.listarMedicos();

        // Validar si la lista está vacía
        if (medicos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay médicos registrados");
            return;
        }

        // Construir el texto que se mostrará con la lista de médicos
        StringBuilder sb = new StringBuilder("Listado de Médicos:\n\n");

        // Recorrer cada médico y agregar su información al texto
        for (Medico m : medicos) {
            sb.append("• ").append(m.getCedula()) 
            .append(" - ").append(m.getNombre()); 

            // Si el médico tiene una especialidad asociada, mostrarla entre paréntesis
            if (m.getEspecialidad() != null)
                sb.append(" (").append(m.getEspecialidad().getNombre()).append(")");

            sb.append("\n"); 
        }

        // Crear un área de texto para mostrar el listado
        JTextArea area = new JTextArea(sb.toString(), 15, 40);
        area.setEditable(false); // No permitir edición

        // Mostrar el área de texto dentro de un cuadro de diálogo desplazable
        JOptionPane.showMessageDialog(
            this,
            new JScrollPane(area),
            "Médicos",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    /**
     * Carga todas las especialidades disponibles en el combo box del formulario.
     */
    private void cargarEspecialidades() {
        // Eliminar cualquier elemento existente en el combo box
        comboEspecialidad.removeAllItems();

        // Recorrer la lista de especialidades obtenida desde el gestor
        for (Especialidad espActual : gestor.listarEspecialidades()) {
            // Agregar cada especialidad al combo box
            comboEspecialidad.addItem(espActual);
        }
    }

    /**
     * Limpia todos los campos del formulario de gestión de médicos.
     */
    private void limpiarCampos() {

        txtCedula.setText("");
        txtNombre.setText("");
        txtContrasena.setText("");

        // Deseleccionar cualquier especialidad en el combo box
        comboEspecialidad.setSelectedIndex(-1);
    }
}