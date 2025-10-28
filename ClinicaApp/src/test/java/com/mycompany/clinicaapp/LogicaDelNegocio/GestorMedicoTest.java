package com.mycompany.clinicaapp.LogicaDelNegocio;

import com.mycompany.clinicaapp.Modelos.Medico;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class GestorMedicoTest {

    @Test
    public void testListaMedicosInicial() {
        GestorMedico gestor = new GestorMedico();
        List<Medico> medicos = gestor.listaMedicos();
        assertNotNull(medicos, "La lista de médicos no debe ser null");
        assertTrue(medicos.size() >= 2, "Debe contener al menos los médicos de ejemplo");
    }

    @Test
    public void testFiltrarPorEspecialidad() {
        GestorMedico gestor = new GestorMedico();
        List<Medico> cardiologos = gestor.listarMedicosEspecialidad("Cardiología");
        assertNotNull(cardiologos);
        // Al menos un médico de cardiología creado en el constructor
        boolean encontrado = cardiologos.stream().anyMatch(m -> m.getEspecialidad() != null && "Cardiología".equals(m.getEspecialidad().getNombre()));
        assertTrue(encontrado, "Debe existir al menos un médico con especialidad Cardiología");
    }
}
