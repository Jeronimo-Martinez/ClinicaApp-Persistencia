package com.mycompany.clinicaapp.LogicaDelNegocio;

import com.mycompany.clinicaapp.Modelos.Cita;
import com.mycompany.clinicaapp.Interfaces.IHistorialService;
import com.mycompany.clinicaapp.Interfaces.IGestorCita;
import java.util.ArrayList;

/**
 * Implementación concreta del gestor de historial médico.
 *
 * @author Silvana
 */
public class GestorHistorial implements IHistorialService {

    private IGestorCita gestorCita;

    /**
     * Constructor: Recibe una referencia al gestor de citas.
     */
    public GestorHistorial(IGestorCita gestorCita) {
        this.gestorCita = gestorCita;
    }

    /**
     * Devuelve el historial médico (solo citas ya atendidas o con diagnóstico)
     * del paciente indicado.
     *
     * @param nombrePac Nombre del paciente a consultar.
     * @return Lista de citas con diagnóstico del paciente.
     */
    @Override
    public ArrayList<Cita> consultarHistorialPaciente(String documentoPac) {
        ArrayList<Cita> historial = new ArrayList<>();

        for (Cita c : gestorCita.getCitas()) {
            if (c.getPaciente().getNombre().equalsIgnoreCase(documentoPac)
                    && c.getDiagnostico() != null && !c.getDiagnostico().isEmpty()) {
                historial.add(c);
            }
        }
        return historial;
    }
}
