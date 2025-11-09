package com.mycompany.clinicaapp.LogicaDelNegocio;
import com.mycompany.clinicaapp.Interfaces.*;
import com.mycompany.clinicaapp.Modelos.*;


/**
 * Clase encargada de gestionar el proceso de inicio de sesión en el sistema.
 * Permite autenticar a distintos tipos de usuarios del sistema: 
 * pacientes, médicos y administradores.
 * @author Silvana
 */

public class GestorInicioSesion implements IInicioSesionService {

    private final IPacienteService gestorPaciente;
    private final IMedicoService gestorMedico;
    private final IGestorAdministrador gestorAdmin;

       /**
     * Crea una nueva instancia del gestor de inicio de sesión, 
     * recibiendo los servicios necesarios mediante inyección de dependencias.
     *
     * @param gestorPaciente servicio encargado de la gestión de pacientes
     * @param gestorMedico   servicio encargado de la gestión de médicos
     * @param gestorAdmin    servicio encargado de la gestión de administradores
     */
    public GestorInicioSesion(IPacienteService gestorPaciente, IMedicoService gestorMedico,IGestorAdministrador gestorAdmin) {
        this.gestorPaciente = gestorPaciente;
        this.gestorMedico = gestorMedico;
        this.gestorAdmin = gestorAdmin;
    }

    /**
     * Inicia sesión en el sistema validando las credenciales del usuario.
     * Si no encuentra a nungún usuario de ningún tipo, entonces retorna nulo
     */
    @Override
    public IUsuario iniciarSesion(String cedula, String contrasena) {
        // Buscar paciente
        Paciente p = gestorPaciente.buscarPorCedulaPaciente(cedula);
        if (p != null && p.getContrasena().equals(contrasena)) {
            return p;
        }

        // Buscar médico
        Medico m = gestorMedico.buscarPorCedulaMedico(cedula);
        if (m != null && m.getContrasena().equals(contrasena)) {
            return m;
        }

        // Buscar administrador
        Administrador a = gestorAdmin.buscarPorCedulaAdministrador(cedula);
        if (a != null && a.getContrasena().equals(contrasena)) {
            return a;
        }

        // Si no se encontró
        return null;
    }
}

