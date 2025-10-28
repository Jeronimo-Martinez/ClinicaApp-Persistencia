package com.mycompany.clinicaapp.Interfaces;

import java.util.List;

import com.mycompany.clinicaapp.Modelos.Cita;

/**
 * Esta interfaz se encarga de definir las operaciones para consultar el
 * historial de un paciente
 *
 * @author Silvana
 */
public interface IHistorialService {

    /**
     * Este método busca el historial de citas de un paciente a partir de su
     * identificador.
     *
     * @param idPaciente Identificación única del paciente.
     * @return Lista de citas asociadas al paciente.
     */
    List<Cita> consultarHistorialPaciente(String idPaciente);

}
