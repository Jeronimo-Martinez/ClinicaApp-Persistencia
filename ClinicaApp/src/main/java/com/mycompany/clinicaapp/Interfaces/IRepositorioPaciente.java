package com.mycompany.clinicaapp.Interfaces;
import com.mycompany.clinicaapp.Modelos.Paciente;
import java.util.List;

/**
 * Esta interfaz se encarga de la persistencia de los pacientes
 * @author Valentina
 */
public interface IRepositorioPaciente {

    /**
     * Guarda la lista completa de pacientes en el archivo JSON o formato de persistencia.
     * @param pacientes lista de pacientes a guardar
     */
    void guardar(List<Paciente> pacientes);


    /**
     * Carga y devuelve la lista de pacientes desde el archivo de persistencia.
     * @return lista de pacientes
     */
    List<Paciente> cargar();
    
}