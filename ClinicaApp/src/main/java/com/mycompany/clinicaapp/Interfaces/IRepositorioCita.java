package com.mycompany.clinicaapp.Interfaces;

import com.mycompany.clinicaapp.Modelos.Cita;
import java.util.List;

public interface IRepositorioCita {
    void guardar(List<Cita> citas);
    List<Cita> cargar();
    void agregarCita(Cita cita);
    void actualizarCita(Cita cita);
    void eliminarCita(String idCita);
}