package com.mycompany.clinicaapp.LogicaDelNegocio;
import com.mycompany.clinicaapp.Interfaces.*;
import com.mycompany.clinicaapp.Modelos.*;


public class GestorInicioSesion implements IInicioSesionService {

    private final IPacienteService gestorPaciente;
    private final IMedicoService gestorMedico;
    private final IGestorAdministrador gestorAdmin;

    // 🔹 Constructor con inyección de dependencias (principio de inversión)
    public GestorInicioSesion(IPacienteService gestorPaciente,
                              IMedicoService gestorMedico,
                              IGestorAdministrador gestorAdmin) {
        this.gestorPaciente = gestorPaciente;
        this.gestorMedico = gestorMedico;
        this.gestorAdmin = gestorAdmin;
    }


    @Override
    public IUsuario iniciarSesion(String cedula, String contrasena) {
        // Buscar paciente
        Paciente p = gestorPaciente.buscarPorCedula(cedula);
        if (p != null && p.getContrasena().equals(contrasena)) {
            return p;
        }

        // Buscar médico
        Medico m = gestorMedico.buscarPorCedula(cedula);
        if (m != null && m.getContrasena().equals(contrasena)) {
            return m;
        }

        // Buscar administrador
        Administrador a = gestorAdmin.buscarPorCedula(cedula);
        if (a != null && a.getContrasena().equals(contrasena)) {
            return a;
        }

        // Si no se encontró
        return null;
    }
}

