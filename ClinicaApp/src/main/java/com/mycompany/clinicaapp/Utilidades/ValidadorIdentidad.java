package com.mycompany.clinicaapp.Utilidades;

import com.mycompany.clinicaapp.Modelos.Medico;
import com.mycompany.clinicaapp.Modelos.Paciente;
import com.mycompany.clinicaapp.Persistencia.RepositorioMedico;
import com.mycompany.clinicaapp.Persistencia.RepositorioPaciente;
import java.util.List;

/**
 * Utilidad para validar identificaciones (cédulas) a nivel global
 * revisando los archivos de pacientes y médicos.
 */
public final class ValidadorIdentidad {

    private ValidadorIdentidad() {
        // utilitario
    }

    public static boolean estaCedulaEnUso(String cedula) {
        if (cedula == null) return false;

        RepositorioMedico repoMed = new RepositorioMedico();
        List<Medico> medicos = repoMed.cargar();
        if (medicos != null) {
            for (Medico m : medicos) {
                if (cedula.equals(m.getCedula())) {
                    return true;
                }
            }
        }

        RepositorioPaciente repoPac = new RepositorioPaciente();
        List<Paciente> pacientes = repoPac.cargar();
        if (pacientes != null) {
            for (Paciente p : pacientes) {
                if (cedula.equals(p.getCedula())) {
                    return true;
                }
            }
        }

        return false;
    }
}
