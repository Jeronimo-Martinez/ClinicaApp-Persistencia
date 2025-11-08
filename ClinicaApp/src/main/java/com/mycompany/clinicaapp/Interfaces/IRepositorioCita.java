package com.mycompany.clinicaapp.Interfaces;

import com.mycompany.clinicaapp.Modelos.Cita;
import java.util.List;

public interface IRepositorioCita {
    boolean guardarCitas(List<Cita> citas);
    List<Cita> cargarCitas();
    boolean agregarCita(Cita cita);
    boolean actualizarCita(Cita cita);
    boolean eliminarCita(String idCita);
}