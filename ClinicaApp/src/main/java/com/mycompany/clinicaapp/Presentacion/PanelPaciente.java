package com.mycompany.clinicaapp.Presentacion;
import com.mycompany.clinicaapp.Interfaces.IGestorCita;
import com.mycompany.clinicaapp.Interfaces.IPacienteService;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorCita;
import com.mycompany.clinicaapp.Modelos.Paciente;
import com.mycompany.clinicaapp.Modelos.Cita;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.util.List;

public class PanelPaciente extends JPanel {

    private final IPacienteService gestorPaciente;
    private final GestorCita gestorCita;
    private Paciente pacienteAutenticado;
    private JButton btnCitas;
    private JButton btnEditarDatos;
    private JButton btnCerrarSesion;
    private JTextField txtNombre;
    private JTextField txtCedula;
    private JTextField txtEdad;
    private JTextField txtTelefono;
    private JLabel tituloPanelPaciente;
    private JFrame ventanaPrincipal;

    /**
     * Este es el constructor del panel paciente
     * Inicializa los servicios (gestorPaciente) y los datos del paciente autenticado
     * @param gestorPaciente Servicio que gestiona las operaciones del paciente 
     * @param pacienteAutenticado Paciente que se ha autenticado
     */
    public PanelPaciente(IPacienteService gestorPaciente, GestorCita gestorCita, Paciente pacienteAutenticado, JFrame ventanaPrincipal) {
        this.gestorPaciente = gestorPaciente;
        this.gestorCita = gestorCita;
        this.pacienteAutenticado = pacienteAutenticado;
        this.ventanaPrincipal = ventanaPrincipal;
        inicializarComponentes();
        configurarEstilodelPanelPaciente();
        configurarEventosdelPanelPaciente();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 252));
        tituloPanelPaciente = new JLabel("Panel del Paciente", SwingConstants.CENTER);
        add(tituloPanelPaciente, BorderLayout.NORTH);
        add(crearPanelDatosPaciente(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelDatosPaciente() {
        JPanel panelDatos = new JPanel (new GridLayout(4, 2, 12, 12));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos Personales: "));

    txtNombre = new JTextField(pacienteAutenticado.getNombre());
    txtCedula = new JTextField(pacienteAutenticado.getCedula());
    txtCedula.setEditable(false);
    // Mostrar la edad como texto (evitar usar el constructor que toma columnas)
    txtEdad = new JTextField(String.valueOf(pacienteAutenticado.getEdad()));
    txtTelefono = new JTextField(pacienteAutenticado.getTelefono());

        panelDatos.add(new JLabel("Nombre: "));
        panelDatos.add(txtNombre);
        panelDatos.add(new JLabel("Cédula: "));
        panelDatos.add(txtCedula);
        panelDatos.add(new JLabel("Edad: "));
        panelDatos.add(txtEdad);
        panelDatos.add(new JLabel("Teléfono"));
        panelDatos.add(txtTelefono);

        return panelDatos;
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new GridLayout(1, 4, 10, 10));

        // Creación de los bontones en el panelBotones
        btnCitas = new JButton("Citas");
        btnEditarDatos = new JButton("Editar Datos");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        // Se añaden los botones al panelBotones
        panelBotones.add(btnCitas);
        panelBotones.add(btnEditarDatos);
        panelBotones.add(btnCerrarSesion);

        return panelBotones;
    }

    private void configurarEstilodelPanelPaciente() {

        // Creación de Fuentes
        Font fuenteTitulo = new Font("Roboto", Font.BOLD, 20);
        Font fuenteCuerpo = new Font("Segoe UI", Font.PLAIN, 14);
        Font fuenteBtn = new Font("Segoe UI", Font.BOLD, 14);

        // Asignación de fuentes
        tituloPanelPaciente.setFont(fuenteTitulo);
        txtNombre.setFont(fuenteCuerpo);
        txtCedula.setFont(fuenteCuerpo);
        txtEdad.setFont(fuenteCuerpo);
        txtTelefono.setFont(fuenteCuerpo);
        btnCitas.setFont(fuenteBtn);
        btnEditarDatos.setFont(fuenteBtn);
        btnCerrarSesion.setFont(fuenteBtn);

        // Personalización de color de los botones y fuentes
        btnCitas.setBackground(new Color(66, 133, 244));
        btnCerrarSesion.setBackground(new Color(229, 57, 53));
        tituloPanelPaciente.setForeground(new Color(51, 51, 51));
        btnCitas.setForeground(new Color(255, 255, 255));
        btnEditarDatos.setForeground(new Color(255, 255, 255));
        btnCerrarSesion.setForeground(new Color(255, 255, 255));
    }

    private void configurarEventosdelPanelPaciente() {

        // Configutación del botón "Citas"
        btnCitas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent clickEvento) {
                
                try {
                    
                    // Se crea la lista de citas del paciente que se necesita para el panelCita
                    List<Cita> citasPaciente = gestorCita.consultarCitasPaciente(pacienteAutenticado);

                    PanelCitasPaciente panelCita = new PanelCitasPaciente(citasPaciente, gestorCita, pacienteAutenticado);
                    ventanaPrincipal = (JFrame) SwingUtilities.getWindowAncestor(PanelPaciente.this);
                    ventanaPrincipal.setContentPane(panelCita);
                    ventanaPrincipal.revalidate();
                    ventanaPrincipal.repaint();


                } catch (Exception e) {
                    JOptionPane.showMessageDialog(PanelPaciente.this, "¡Ups! Ha ocurrido un error al abrir el panel de citas ...", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Configuración del botón "Editar Datos"
        btnEditarDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent clickEvento) {

                try {
                    
                    // Se piden los datos
                    String nombre = txtNombre.getText().trim();
                    String cedula = pacienteAutenticado.getCedula();
                    String edad = txtEdad.getText().trim();
                    String telefono = txtTelefono.getText().trim();

                    // Se comprueba que no este ningún campo vacío
                    if (nombre.isEmpty() || cedula.isEmpty() || edad.isEmpty() || telefono.isEmpty()) {
                        JOptionPane.showMessageDialog(PanelPaciente.this, "Debe rellenar todos los campos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Se comprueba que la edad sea un número y que sea positivo
                    int edadValidacionNumero;
                    try {
                        edadValidacionNumero = Integer.parseInt(edad);
                        if (edadValidacionNumero <= 0){
                            JOptionPane.showMessageDialog(PanelPaciente.this, "La edad debe ser un número positivo", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(PanelPaciente.this, "La edad debe ser un número", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return; // evitar continuar y provocar crash
                    }

                    // Se comprueba que el nombre sean solo letras con opción de espacios
                    if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")){
                        JOptionPane.showMessageDialog(PanelPaciente.this, "Carácteres no válidos", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Se comprueba que la cédula sean números positivos
                    if (!cedula.matches("^\\d+$")) {
                        return;
                    }

                    // Se comprueba que el teléfono sean números positivos
                    if (!telefono.matches("^\\d+$")) {
                        return;
                    }

                    pacienteAutenticado.setNombre(nombre);
                    pacienteAutenticado.setCedula(cedula);
                    pacienteAutenticado.setEdad(edadValidacionNumero);
                    pacienteAutenticado.setTelefono(telefono);

                    // Se realiza la modificación
                    boolean estaPacienteEditado = gestorPaciente.editarPaciente(pacienteAutenticado);
                    if (estaPacienteEditado){
                        JOptionPane.showMessageDialog(PanelPaciente.this, "Datos actualizados con éxito", "Cambio Éxitoso", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(PanelPaciente.this, "No se encontró la cédula", "Error", JOptionPane.ERROR_MESSAGE);
                    }


                } catch (Exception e) {
                    JOptionPane.showMessageDialog(PanelPaciente.this, "¡Ups! Ha ocurrido un error en la edición de datos ...", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Configuración del botón "Cerrar Sesión"
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent clickEvento) {
                int opcion = JOptionPane.showConfirmDialog(PanelPaciente.this, "¿Está seguro que quiere cerrar la sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

                // En caso de que el paciente diga que sí
                if (opcion == JOptionPane.YES_OPTION) {
                    ventanaPrincipal = (JFrame) SwingUtilities.getWindowAncestor(PanelPaciente.this);
                    ventanaPrincipal.getContentPane().removeAll();
                    ventanaPrincipal.add(new VentanaIniciarSesion());
                    ventanaPrincipal.revalidate();
                    ventanaPrincipal.repaint();
                }
            }
        });

    }
}