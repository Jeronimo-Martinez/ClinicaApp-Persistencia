
package com.mycompany.clinicaapp.LogicaDelNegocio;

import com.mycompany.clinicaapp.Interfaces.IEspecialidadService;
import com.mycompany.clinicaapp.Interfaces.IRepositorioEspecialidad;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import com.mycompany.clinicaapp.Persistencia.RepositorioEspecialidad;
import java.util.ArrayList;
import java.util.List;

public class GestorEspecialidad implements IEspecialidadService {

    private final IRepositorioEspecialidad repositorio = new RepositorioEspecialidad();
    private final List<Especialidad> listaEspecialidades;

    public GestorEspecialidad() {
        List<Especialidad> cargadas = repositorio.cargar();
        if (cargadas == null || cargadas.isEmpty()) {
            listaEspecialidades = new ArrayList<>();
            listaEspecialidades.add(new Especialidad("Cardiología"));
            listaEspecialidades.add(new Especialidad("Pediatría"));
            // Guardar valores por defecto
            repositorio.guardar(listaEspecialidades);
        } else {
            listaEspecialidades = cargadas;
        }
    }

    @Override
    public boolean ingresarEspecialidad(Especialidad especialidad) {
        listaEspecialidades.add(especialidad);
        repositorio.guardar(listaEspecialidades);
        return true;
    }

    @Override
    public boolean eliminarEspecialidad(Especialidad especialidad) {
        listaEspecialidades.removeIf(e -> e.getNombre().equalsIgnoreCase(especialidad.getNombre()));
        repositorio.guardar(listaEspecialidades);
        return true;
    }

    @Override
    public List<Especialidad> listarEspecialidades() {
        return listaEspecialidades;
    }
}

