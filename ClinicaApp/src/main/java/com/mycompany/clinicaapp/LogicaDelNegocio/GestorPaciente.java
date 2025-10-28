package com.mycompany.clinicaapp.LogicaDelNegocio;
import com.mycompany.clinicaapp.Modelos.Paciente;
import com.mycompany.clinicaapp.Interfaces.IPacienteService;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase implementa la interfaz IPacienteService y se encarga de gestionar las operaciones CRUD sobre los objetos Paciente
 * @author Valentina
 */

public class GestorPaciente implements IPacienteService{

    private List<Paciente> pacientes;

    /**
     * Constructor por defecto 
     */

    public GestorPaciente() {
        this.pacientes = new ArrayList<>();
    }

    /**
     * Constructor con parámetros
     * 
     * @param gestorPaciente
     * @param pacientes
     */

    public GestorPaciente (List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    /**
     * Este método registra a un nuevo paciente
     * @param paciente  Paciente nuevo
     * @return true  (En caso de que el paciente sea creado con éxito)
     */
    @Override
    public boolean registrarPaciente(Paciente paciente) {
        // en caso de que este vacío
        if (paciente == null) {
            return false;
        }

        for (Paciente pac : pacientes) {
            if (pac.getCedula().equals(paciente.getCedula())) {
                return false; // si ya existe un paciente con esa cédula
            }
        }
        
        pacientes.add(paciente);
        return true;
    }

    /**
     * Este método edita los datos de un paciente
     * @param paciente  Paciente a editar
     * @return true  (En caso de que el paciente sea encontrado y editado con éxito)
     */
    @Override
    public boolean editarPaciente(Paciente paciente) {
        for (int i=0; i < pacientes.size(); i++){
            if (paciente.getCedula().equals(pacientes.get(i).getCedula())){
                pacientes.set(i, paciente);
                return true;
            }
        }
        return false;
    }

    /**
     * Este método elimina a un paciente
     * @param paciente  Paciente a eliminar
     * @return true  (En caso de que el paciente sea eliminado con éxito)
     */
    @Override
    public boolean eliminarPaciente(Paciente paciente) {
        for (int i=0; i < pacientes.size(); i++){
            if (paciente.getCedula().equals(pacientes.get(i).getCedula())) {
                pacientes.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Este método lista a los pacientes registrados
     * @return pacientes (la lista de pacientes registrados)
     */
    @Override
    public List<Paciente> listarPacientes() {
        return pacientes;
    }

    /**
     * Este método permite que el paciente inicie sesión
     *
     * @param cedula Cédula del paciente
     * @param contrasena Contraseña del paciente
     * @return Paciente
     */
    public Paciente iniciarSesion(String cedula, String contrasena) {
        for (Paciente paciente : pacientes) {
            if (paciente.getCedula().equals(cedula) && paciente.getContrasena().equals(contrasena)) {
                return paciente;
            }
        }
        return null;
    }

}