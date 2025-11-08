package com.mycompany.clinicaapp.Interfaces;
import com.mycompany.clinicaapp.Modelos.Medico;
import java.util.List;


/**
 * Esta interfaz se encarga de la persistencia de los médicos
 * @author Silvana
 */
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

