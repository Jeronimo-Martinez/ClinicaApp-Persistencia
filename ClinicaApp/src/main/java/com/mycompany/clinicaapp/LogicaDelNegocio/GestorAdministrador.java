package com.mycompany.clinicaapp.LogicaDelNegocio;
import com.mycompany.clinicaapp.Interfaces.IMedicoService;
import com.mycompany.clinicaapp.Interfaces.IEspecialidadService;
import com.mycompany.clinicaapp.Interfaces.IPacienteService;
import com.mycompany.clinicaapp.Interfaces.IGestorAdministrador;
import com.mycompany.clinicaapp.Modelos.*;

import java.util.ArrayList;
import java.util.List;



/**
 * Esta clase se encarga de implementar las operaciones
 * que el único administrador del sistema puede realizar sobre médicos, pacientes
 * y especialidades.
 */
public class GestorAdministrador implements IGestorAdministrador {

    private final IMedicoService medicoService;
    private final IPacienteService pacienteService;
    private final IEspecialidadService especialidadService;
    private final List<Administrador> listaAdministradores;

    /**
     * Constructor que inicializa el gestor con las dependencias requeridas.
     * 
     * @param medicoService       servicio encargado de la gestión de médicos
     * @param pacienteService     servicio encargado de la gestión de pacientes
     * @param especialidadService servicio encargado de la gestión de especialidades
     */
    public GestorAdministrador(IMedicoService medicoService, IPacienteService pacienteService, IEspecialidadService especialidadService) {
        this.medicoService = medicoService;
        this.pacienteService = pacienteService;
        this.especialidadService = especialidadService;
        this.listaAdministradores = new ArrayList<>();

        // Admin por defecto
        listaAdministradores.add(new Administrador("1234567890", "admin1234"));
    }

   /**
     * Busca un administrador registrado por su número de cédula.
     * 
     * @param cedula la cédula del administrador a buscar
     */
    @Override
    public Administrador buscarPorCedulaAdministrador(String cedula) {
        for (Administrador a : listaAdministradores) {
            if (a.getCedula().equals(cedula)) {
                return a;
            }
        }
        return null;
    }
   

    /*
     * Estos son los métodos que debe llamar del gestor de médicos
     */
    @Override
    public boolean registrarMedico(Medico medico) {
        return medicoService.agregarMedic(medico);
    }

    @Override
    public boolean editarMedico(Medico medico, String nombre, Especialidad esp) {
        return medicoService.editarMedico(medico, nombre, esp);
    }

    @Override
    public boolean eliminarMedico(String cedula) {
        return medicoService.eliminarMedico(cedula);
    }


    /**
     * Estos son los métodos que debe llamar del gestor de pacientes
     */

    @Override
    public boolean registrarPaciente(Paciente paciente) {
        return pacienteService.registrarPaciente(paciente);
    }

    @Override
    public boolean editarPaciente(Paciente paciente) {
        return pacienteService.editarPaciente(paciente);
    }

    @Override
    public boolean eliminarPaciente(Paciente paciente) {
        return pacienteService.eliminarPaciente(paciente);
    }


    /**
     * Estos son los métodos que se deben llamar del gestor de especialidades
     */

    @Override
    public void registrarEspecialidad(Especialidad especialidad) {
        especialidadService.ingresarEspecialidad(especialidad);
    }

    @Override
    public void eliminarEspecialidad(Especialidad especialidad) {
        especialidadService.eliminarEspecialidad(especialidad);
    }

    @Override
    public List<Medico> listarMedicos() {
        return medicoService.listaMedicos();
    }


    /**
     * Métods que llaman a distintos gestores para mostrar las entidades
     */
    @Override
    public List<Paciente> listarPacientes() {
        return pacienteService.listarPacientes();
    }

    @Override
    public List<Especialidad> listarEspecialidades() {
        return especialidadService.listarEspecialidades();
    }

    /**
     * Métodos de encapsulamiento
     */
    public IMedicoService getMedicoService() {
        return this.medicoService;
    }


    public IPacienteService getPacienteService() {
        return this.pacienteService;
    }


    public IEspecialidadService getEspecialidadService() {
        return this.especialidadService;
    }
    }
