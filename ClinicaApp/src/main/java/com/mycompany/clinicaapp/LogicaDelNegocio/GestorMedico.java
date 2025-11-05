package com.mycompany.clinicaapp.LogicaDelNegocio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mycompany.clinicaapp.Interfaces.IMedicoService;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import com.mycompany.clinicaapp.Modelos.Medico;
import com.mycompany.clinicaapp.Persistencia.PersistenciaMedico;

public class GestorMedico implements IMedicoService {

    private final ArrayList<Medico> listaMedicos;

    public GestorMedico() {
        // 🔹 Cargar médicos del archivo al iniciar
        listaMedicos = new ArrayList<>(PersistenciaMedico.cargar());

        // 🔹 Si está vacío (primera vez), agregar médicos de ejemplo
        if (listaMedicos.isEmpty()) {
            Especialidad cardio = new Especialidad("Cardiología");
            Especialidad general = new Especialidad("Medicina General");
            listaMedicos.add(new Medico("1111", "Andrés Gómez", cardio, "1111"));
            listaMedicos.add(new Medico("2222", "Laura Torres", general, "2222"));
            PersistenciaMedico.guardar(listaMedicos);
        }
    }

    // Filtro por especialidad
    @Override
    public List<Medico> listarMedicosEspecialidad(String nombreEspecialidad) {
        return listaMedicos.stream()
                .filter(m -> m.getEspecialidad().getNombre().equals(nombreEspecialidad))
                .collect(Collectors.toList());
    }

    @Override
    public List<Medico> listarMedicosEspecialidad() {
        return new ArrayList<>(listaMedicos);
    }

    public List<Medico> listarMedicos() {
        return listaMedicos();
    }

    @Override
    public boolean editarMedico(Medico medico, String nuevoNombre, Especialidad nuevaEspecialidad) {
        medico.setNombre(nuevoNombre);
        medico.setEspecialidad(nuevaEspecialidad);
        PersistenciaMedico.guardar(listaMedicos);
        return true;
    }

    @Override
    public List<Medico> listaMedicos() {
        return new ArrayList<>(listaMedicos);
    }

    @Override
    public boolean agregarMedic(Medico medico) {
        listaMedicos.add(medico);
        PersistenciaMedico.guardar(listaMedicos);
        return true;
    }

    @Override
    public boolean eliminarMedico(String cedula) {
        boolean eliminado = listaMedicos.removeIf(m -> m.getCedula().equals(cedula));
        if (eliminado) {
            PersistenciaMedico.guardar(listaMedicos);
        }
        return eliminado;
    }

    @Override
    public Medico buscarPorCedula(String cedula) {
        for (Medico m : listaMedicos) {
            if (m.getCedula().equals(cedula)) {
                return m;
            }
        }
        return null;
    }
}
