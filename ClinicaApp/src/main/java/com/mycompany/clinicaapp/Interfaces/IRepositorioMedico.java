package com.mycompany.clinicaapp.Interfaces;
import com.mycompany.clinicaapp.Modelos.Medico;
import java.util.List;

public interface IRepositorioMedico {

    /**
     * Guarda la lista completa de médicos en el archivo JSON o formato de persistencia.
     * @param medicos lista de médicos a guardar
     */
    void guardar(List<Medico> medicos);

    /**
     * Carga y devuelve la lista de médicos desde el archivo de persistencia.
     * @return lista de médicos
     */
    List<Medico> cargar();
}

