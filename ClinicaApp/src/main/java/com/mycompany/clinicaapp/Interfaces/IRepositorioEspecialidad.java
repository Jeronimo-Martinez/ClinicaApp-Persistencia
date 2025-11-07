package com.mycompany.clinicaapp.Interfaces;

import com.mycompany.clinicaapp.Modelos.Especialidad;
import java.util.List;

public interface IRepositorioEspecialidad {

    /**
     * Guarda la lista completa de especialidades en el archivo de persistencia.
     * @param especialidades lista de especialidades a guardar
     */
    void guardar(List<Especialidad> especialidades);

    /**
     * Carga y devuelve la lista de especialidades desde el archivo de persistencia.
     * @return lista de especialidades
     */
    List<Especialidad> cargar();
}
