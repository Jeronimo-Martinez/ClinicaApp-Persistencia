package com.mycompany.clinicaapp.Presentacion;

import com.mycompany.clinicaapp.Interfaces.IGestorAdministrador;
import com.mycompany.clinicaapp.Modelos.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Panel para que el administrador pueda eliminar y crear especialidades
 */
public class GestorAdminEnEspecialidad extends JPanel {

    private final IGestorAdministrador gestor;
    private JTextField txtNombre;

    public GestorAdminEnEspecialidad(IGestorAdministrador gestor) {
        this.gestor = gestor;
        initComponents();
    }
    /**
     * Este método inicializa los componentes gráficos del sub panel del administrador cuando va a gestionar las 
     * especialidades
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Gestión de Especialidades"));

        // === Panel central con el campo de texto ===
        JPanel panelForm = new JPanel(new GridLayout(1, 2, 5, 5));
        panelForm.add(new JLabel("Nombre de la especialidad:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);
        add(panelForm, BorderLayout.CENTER);

        // === Panel inferior con botones ===
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVer = new JButton("Ver Especialidades");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);
        panelBotones.add(btnVer);
        // === Listeners ===
        btnRegistrar.addActionListener(this::registrarEspecialidad);
        btnEliminar.addActionListener(this::eliminarEspecialidad);
        btnVer.addActionListener(this::verEspecialidades);


    }
    /**
     * Este método se encarga de obtener el nombre de la especialidad cuando el administrador la crea
     * y envía la nueva especialidad al gestor, que a su vez llama al gestor especialidad para que la cree
     * @param e: Acción de cuando el admin presiona el botón de crear especialidad
     */
    private void registrarEspecialidad(ActionEvent e) {
        String nombre = txtNombre.getText().trim();
        // Verifica que el campo no esté vacío 
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre de la especialidad");
            return;
        }
        //Crea la nueva especialidad y llama al gestor 
        Especialidad nueva = new Especialidad(nombre);
        try {
            gestor.registrarEspecialidad(nueva);
            JOptionPane.showMessageDialog(this, "Especialidad registrada correctamente");
            txtNombre.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar: " + ex.getMessage());
        }
    }
    /**
     * Este método se encarga de obtener el nombre ingresado en el campo de texto 
     * para crear una especialidad temporal para enviarla al gestor y que el gestor de las especialidades pueda eliminarla
     * correctamente
     * @param e: Acción del administrador
     */
    private void eliminarEspecialidad(ActionEvent e) {
        String nombre = txtNombre.getText().trim();
        // Verifica campo vacío 
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el nombre de la especialidad a eliminar");
            return;
        }

        Especialidad espProvicional = new Especialidad(nombre);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar la especialidad \"" + nombre + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                gestor.eliminarEspecialidad(espProvicional);
                JOptionPane.showMessageDialog(this, "Especialidad eliminada correctamente");
                txtNombre.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
            }
        }
    }

    /**
     * Este método se encarga de obtener la lista de especialidades del gestor de especialidad,
     * para lugo
     * @param e: Acción del administrador
     */
    private void verEspecialidades(ActionEvent e) {
       
        List<Especialidad> especialidades = gestor.listarEspecialidades();

        // Si la lista está vacía, muestra un mensaje y detiene la ejecución
        if (especialidades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay especialidades registradas");
            return;
        }

        // Construye el texto que mostrará todas las especialidades
        StringBuilder sb = new StringBuilder("Listado de Especialidades:\n\n");
        for (Especialidad esp : especialidades) {
            sb.append("• ").append(esp.getNombre()).append("\n");
        }

        //  Crea un área de texto no editable para mostrar el listado
        JTextArea area = new JTextArea(sb.toString(), 10, 30);
        area.setEditable(false);

        //  Muestra el área de texto dentro de un cuadro de diálogo con scroll
        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(area),
                "Especialidades",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}