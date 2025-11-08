package com.mycompany.clinicaapp.LogicaDelNegocio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mycompany.clinicaapp.Interfaces.IMedicoService;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import com.mycompany.clinicaapp.Modelos.Medico;
import com.mycompany.clinicaapp.Interfaces.IRepositorioMedico;
import com.mycompany.clinicaapp.Persistencia.RepositorioMedico;


public class GestorMedico implements IMedicoService {

    private final IRepositorioMedico repositorio; // instancia del repositorio
    private final ArrayList<Medico> listaMedicos;

    public GestorMedico() {
        this.repositorio = new RepositorioMedico();

        //  Cargar médicos del archivo al iniciar
        List<Medico> cargados = repositorio.cargar();
        this.listaMedicos = new ArrayList<>(cargados != null ? cargados : new ArrayList<>());

        //  Si está vacío (primera vez), agregar médicos de ejemplo
        if (listaMedicos.isEmpty()) {
            Especialidad cardio = new Especialidad("Cardiología");
            Especialidad general = new Especialidad("Medicina General");

            listaMedicos.add(new Medico("1111", "Andrés Gómez", cardio, "1111"));
            listaMedicos.add(new Medico("2222", "Laura Torres", general, "2222"));

            repositorio.guardar(listaMedicos);
        }
    }

    //  Listar médicos por especialidad
    @Override
    public List<Medico> listarMedicosEspecialidad(String nombreEspecialidad) {
        return listaMedicos.stream()
                .filter(m -> m.getEspecialidad().getNombre().equalsIgnoreCase(nombreEspecialidad))
                .collect(Collectors.toList());
    }

    //  Listar todos los médicos
    @Override
    public List<Medico> listarMedicosEspecialidad() {
        return new ArrayList<>(listaMedicos);
    }

    @Override
    public List<Medico> listaMedicos() {
        return new ArrayList<>(listaMedicos);
    }

    // Editar un médico
    @Override
    public boolean editarMedico(Medico medico, String nuevoNombre, Especialidad nuevaEspecialidad) {
       // Validar datos de entrada
        if (medico == null || nuevoNombre == null || nuevaEspecialidad == null) {
            System.err.println("Error: datos nulos al intentar editar un médico.");
            return false;
        }

        // Buscar el médico existente en la lista
        Medico medicoExistente = null;
        for (Medico m : listaMedicos) {
            if (m.getCedula().equals(medico.getCedula())) {
                medicoExistente = m;
                break;
            }
        }

        // Si no se encontró, no se puede editar
        if (medicoExistente == null) {
            System.err.println("No se encontró el médico con cédula: " + medico.getCedula());
            return false;
        }

        // Actualizar datos
        medicoExistente.setNombre(nuevoNombre);
        medicoExistente.setEspecialidad(nuevaEspecialidad);

        // Guardar cambios en el repositorio
        repositorio.guardar(listaMedicos);

        return true;
    }

    // Agregar un nuevo médico
    @Override
    public boolean agregarMedic(Medico medico) {
        listaMedicos.add(medico);
        repositorio.guardar(listaMedicos);
        return true;
    }

    // Eliminar un médico por cédula
    @Override
    public boolean eliminarMedico(String cedula) {
        boolean eliminado = listaMedicos.removeIf(m -> m.getCedula().equals(cedula));
        if (eliminado) {
            repositorio.guardar(listaMedicos);
        }
        return eliminado;
    }

    // Buscar médico por cédula
    @Override
    public Medico buscarPorCedula(String cedula) {
        for (Medico m : listaMedicos) {
            if (m.getCedula().equals(cedula)) {
                return m;
            }
        }
        return null;
    }

    // Nota: la autenticación se realiza en GestorInicioSesion usando buscarPorCedula + comparación de contraseña
}

