package com.mycompany.clinicaapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.mycompany.clinicaapp.LogicaDelNegocio.GestorAdministrador;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorEspecialidad;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorMedico;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorPaciente;
import com.mycompany.clinicaapp.Modelos.Administrador;
import com.mycompany.clinicaapp.Modelos.Paciente;

public class TestLoginFlow {

    @Test
    public void testRegisterAndLoginPatient() {
        GestorMedico gm = new GestorMedico();
        GestorPaciente gp = new GestorPaciente();
        GestorEspecialidad ge = new GestorEspecialidad();
        // Ensure singleton admin is initialized
        GestorAdministrador.getInstanciaAdministrador(gm, gp, ge);

        Paciente p = new Paciente("999999999","Test User","3001234567",30,"pwd123");
        boolean created = gp.registrarPaciente(p);
        assertTrue(created, "Paciente debería registrarse correctamente");

        Paciente logged = gp.iniciarSesion("999999999", "pwd123");
        assertNotNull(logged, "El paciente debería iniciar sesión correctamente");
        assertEquals("Test User", logged.getNombre());
    }

    @Test
    public void testAdminDefaultLogin() {
        GestorMedico gm = new GestorMedico();
        GestorPaciente gp = new GestorPaciente();
        GestorEspecialidad ge = new GestorEspecialidad();
        GestorAdministrador ga = GestorAdministrador.getInstanciaAdministrador(gm, gp, ge);

        Administrador admin = ga.iniciarSesion("1234567890", "admin1234");
        assertNotNull(admin, "El administrador por defecto debería poder iniciar sesión");
    }
}
