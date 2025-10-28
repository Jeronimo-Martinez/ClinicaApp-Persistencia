package com.mycompany.clinicaapp.LogicaDelNegocio;

import com.mycompany.clinicaapp.Interfaces.IInterfazAdminMedica;
import com.mycompany.clinicaapp.Interfaces.IMedicoService;
import com.mycompany.clinicaapp.Modelos.Medico;
import com.mycompany.clinicaapp.Presentacion.AdminMedicos.VentanaAgregarMedico;
import com.mycompany.clinicaapp.Presentacion.AdminMedicos.VentanaEditarMedico;
import com.mycompany.clinicaapp.Presentacion.AdminMedicos.VentanaMedica;
import javax.swing.JFrame;

/**
 * Maneja la lógica de navegación entre las ventanas de administración médica.
 */
public class InterfazAdminMedica implements IInterfazAdminMedica {

    private final IMedicoService medicoService;
    private JFrame framePrincipal;
    private VentanaMedica ventanaMedica;

    public InterfazAdminMedica(IMedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @Override
    public void mostrarVentanaMedica() {
        // Crear el frame principal si aún no existe
        if (framePrincipal == null) {
            framePrincipal = new JFrame("Administración de Médicos");
            framePrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            framePrincipal.setSize(900, 600);
            framePrincipal.setLocationRelativeTo(null);
        }

        // Crear y mostrar el panel principal
        ventanaMedica = new VentanaMedica(medicoService, this);
        ventanaMedica.setSize(framePrincipal.getSize());
        ventanaMedica.setLocation(0, 0);

        framePrincipal.setContentPane(ventanaMedica);
        framePrincipal.revalidate();
        framePrincipal.repaint();
        framePrincipal.setVisible(true);
    }

    @Override
    public void mostrarVentanaAgregarMedico() {
        if (framePrincipal != null) {
            VentanaAgregarMedico panel = new VentanaAgregarMedico(medicoService, this);
            panel.setSize(framePrincipal.getSize());
            panel.setLocation(0, 0);

            framePrincipal.setContentPane(panel);
            framePrincipal.revalidate();
            framePrincipal.repaint();
        }
    }

    @Override
    public void mostrarVentanaEditarMedico(Medico medico) {
        if (framePrincipal != null) {
            VentanaEditarMedico panel = new VentanaEditarMedico(medico, medicoService, this);
            panel.setSize(framePrincipal.getSize());
            panel.setLocation(0, 0);

            framePrincipal.setContentPane(panel);
            framePrincipal.revalidate();
            framePrincipal.repaint();
        }
    }

    @Override
    public void actualizarTablaMedicos() {
        if (ventanaMedica != null) {
            ventanaMedica.llenarFilas();
        }
    }

    //Este método no se usa xd, pero lo dejamos implementado para cumplir con la interfaz
   
    public void mostrarVentananMedica() {
        mostrarVentanaMedica();
    }
}
