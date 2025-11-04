package com.mycompany.clinicaapp.Interfaces;
import java.util.List;

import com.mycompany.clinicaapp.Modelos.Paciente;
/**
 * Esta interfaz se encarga de definir las operaciones CRUD que deben realizarse sobre los pacientes
 * @author Valentina
 */

public interface IPacienteService {

    boolean registrarPaciente (Paciente paciente);
    boolean editarPaciente (Paciente paciente);
    boolean eliminarPaciente (Paciente paciente);
    List<Paciente> listarPacientes ();
    
    Paciente buscarPorCedula(String cedula);
}