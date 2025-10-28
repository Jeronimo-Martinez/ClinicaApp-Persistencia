package com.mycompany.clinicaapp.Presentacion;
import com.mycompany.clinicaapp.Interfaces.IHistorialService;
import com.mycompany.clinicaapp.Modelos.Cita;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import com.mycompany.clinicaapp.Presentacion.AdminMedicos.VentanaMedica;
     
/**
 * Este panel está encargado de mostrar el historial clínico de un paciente.
 */

public class PanelHistorial extends JPanel {

    private final IHistorialService gestorHistorial;
    private DefaultTableModel modeloTabla;
    private JTextField txtDocumentoPaciente;
    private JButton btnBuscarHistorial;
    private JButton btnVolver;
    private JTable tablaHistorial;
    private final VentanaMedica ventanaMedica; // referencia para volver

    /**
     * Constructor del panel de historial.
     * @param gestorHistorial 
     * @param ventanaMedica 
     */
     public PanelHistorial(IHistorialService gestorHistorial, VentanaMedica ventanaMedica) {
        this.gestorHistorial = gestorHistorial;
        this.ventanaMedica = ventanaMedica;
        inicializarComponentes();
        configurarEstilosDelHistorial();
        configurarEventosHistorial();
    }

    /**
     * Este método inicializa y organiza los componentes principales del panel de historial.
     * Configura el diseño, los colores de fondo y agrega las secciones visuales del área de búsqueda y de la tabla de resultados.
     */
    private void inicializarComponentes(){
        setLayout(new BorderLayout(15,15));
        setBackground(new Color(245, 247, 252));

        add(crearPanelBusqueda(), BorderLayout.NORTH);
        add(crearPanelTabla(), BorderLayout.CENTER);
    }


    /**
     * Crea y configura el panel superior de búsqueda, donde el usuario puede ingresar el documento del paciente y ejecutar la consulta.
     * @return un JPanel configurado con etiqueta, campo de texto y botón.
     */
    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(230, 235, 245));

        JLabel etiquetaDocumento = new JLabel("Documento del paciente:");
        txtDocumentoPaciente = new JTextField(15);
        btnBuscarHistorial = new JButton("Buscar historial");
        btnVolver = new JButton("Volver al menú");

        panel.add(etiquetaDocumento);
        panel.add(txtDocumentoPaciente);
        panel.add(btnBuscarHistorial);
        panel.add(btnVolver);
        return panel;
      

    }
    /**
     * Este método crea el panel central que contiene la tabla del historial de citas.
     * Esta tabla se envuelve dentro de un JScrollPane para permitir el desplazamiento
     * @return JScrollPane configurado con la tabla del historial del paciente.
     */
    private JScrollPane crearPanelTabla() {
        modeloTabla = new DefaultTableModel(new String[]{"Fecha", "Médico", "Diagnóstico"}, 0);
        tablaHistorial = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaHistorial);
        scroll.setBorder(BorderFactory.createTitledBorder("Historial del paciente"));
        return scroll;
    }


    /**
     * Este método es para aplicar estilos visuales a los componentes del panel.
     * Define fuentes, colores y tamaños para mejorar la presentación y la experiencia visual.
     */

    private void configurarEstilosDelHistorial() {
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);
        txtDocumentoPaciente.setFont(fuente);
        btnBuscarHistorial.setFont(fuente);
        btnVolver.setFont(fuente);
        tablaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaHistorial.setRowHeight(22);
        btnBuscarHistorial.setBackground(new Color(66, 133, 244));
        btnBuscarHistorial.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(219, 68, 55));
        btnVolver.setForeground(Color.WHITE);
    }


    /**
     * Este método le dice al botón que debe hacer cuando el usuario lo presione
     */
    private void configurarEventosHistorial() {
        btnBuscarHistorial.addActionListener((ActionEvent click) -> buscarHistorial());
        btnVolver.addActionListener((ActionEvent e) -> volverAlMenuMedico());
    }


    private void buscarHistorial() {
        String documento = txtDocumentoPaciente.getText().trim();
        if (documento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el documento del paciente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Limpiar tabla
        modeloTabla.setRowCount(0);

        try {
            List<Cita> historial = gestorHistorial.consultarHistorialPaciente(documento);

            if (historial == null || historial.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No se encontraron citas para este paciente.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Mostrar resultados
            for (Cita cita : historial) {
                modeloTabla.addRow(new Object[]{
                        cita.getFecha(),
                        cita.getMedico().getNombre(),
                        cita.getDiagnostico()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar el historial: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Permite volver al menú principal del médico.
     * Reemplaza el panel actual por la VentanaMedica.
     */
    private void volverAlMenuMedico() {
        // Intentar reemplazar el contenido del JFrame contenedor si existe
        java.awt.Window win = javax.swing.SwingUtilities.getWindowAncestor(this);
        if (win instanceof javax.swing.JFrame) {
            javax.swing.JFrame frame = (javax.swing.JFrame) win;
            frame.setContentPane(ventanaMedica);
            frame.revalidate();
            frame.repaint();
            return;
        }
        // Fallback: si no hay JFrame, intentar con el padre inmediato
        Container contenedor = getParent();
        if (contenedor != null) {
            contenedor.removeAll();
            contenedor.add(ventanaMedica);
            contenedor.revalidate();
            contenedor.repaint();
        }
    }
}