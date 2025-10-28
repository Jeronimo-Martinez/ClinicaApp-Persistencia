/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.clinicaapp.Interfaces;

import java.util.List;
import com.mycompany.clinicaapp.Modelos.Paciente;
import com.mycompany.clinicaapp.Modelos.Cita;
import com.mycompany.clinicaapp.Modelos.Medico;

/**
 *
 * @author jmart
 */
public interface IGestorCita {
    
    boolean registrarCita(Cita c);
    List<Cita> consultarCitasPaciente(Paciente paciente);
    List<Cita> consultarCitasMedico(Medico medico);
    Cita buscarCitaPorId(String id);
    boolean eliminarCita(String idCita);
    boolean modificarCita(String idCita, Cita nueva);
    List<Cita> getCitas();
    
}
