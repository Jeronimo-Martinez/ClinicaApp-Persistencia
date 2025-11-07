package com.mycompany.clinicaapp.Persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mycompany.clinicaapp.Interfaces.IRepositorioCita;
import com.mycompany.clinicaapp.Modelos.Cita;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RepositorioCita implements IRepositorioCita {
    private static final String ARCHIVO_CITAS = "citas.json";
    private final Gson gson;

    public RepositorioCita() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    @Override
    public void guardar(List<Cita> citas) {
        try (Writer writer = new FileWriter(ARCHIVO_CITAS)) {
            gson.toJson(citas, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cita> cargar() {
        File archivo = new File(ARCHIVO_CITAS);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(ARCHIVO_CITAS)) {
            Type listType = new TypeToken<ArrayList<Cita>>(){}.getType();
            List<Cita> citas = gson.fromJson(reader, listType);
            return citas != null ? citas : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void agregarCita(Cita cita) {
        List<Cita> citas = cargar();
        citas.add(cita);
        guardar(citas);
    }

    @Override
    public void actualizarCita(Cita citaActualizada) {
        List<Cita> citas = cargar();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getId().equals(citaActualizada.getId())) {
                citas.set(i, citaActualizada);
                break;
            }
        }
        guardar(citas);
    }

    @Override
    public void eliminarCita(String idCita) {
        List<Cita> citas = cargar();
        citas.removeIf(c -> c.getId().equals(idCita));
        guardar(citas);
    }
}