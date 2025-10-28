package com.mycompany.clinicaapp.LogicaDelNegocio;

import com.mycompany.clinicaapp.Modelos.Paciente;
import com.mycompany.clinicaapp.Modelos.Medico;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import com.mycompany.clinicaapp.Modelos.Cita;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class GestorCitaTest {

    @Test
    public void testRegistrarYConsultarCitasPaciente() {
        GestorCita gestor = new GestorCita();
        Paciente p = new Paciente("999", "Test Paciente", "3000000000", 30, "pass");
        Especialidad e = new Especialidad("Medicina General");
        Medico m = new Medico("500", "Dr Test", e, "mypass");
        Cita c = new Cita("t1", LocalDate.now().plusDays(1), null, m, p);

        boolean ok = gestor.registrarCita(c);
        assertTrue(ok, "Debería registrar la cita correctamente");

        List<Cita> citasPaciente = gestor.consultarCitasPaciente(p);
        assertNotNull(citasPaciente);
        boolean encontrado = citasPaciente.stream().anyMatch(x -> "t1".equals(x.getId()));
        assertTrue(encontrado, "La cita registrada debería aparecer en la lista del paciente");
    }
}
