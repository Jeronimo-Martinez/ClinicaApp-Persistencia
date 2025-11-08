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
        this.gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
    }

    @Override
    public boolean guardarCitas(List<Cita> citas) {
        try  {
            Writer writer = new FileWriter(ARCHIVO_CITAS);
            var jsonToSave = gson.toJsonTree(citas);
            gson.toJson(jsonToSave,writer);
            writer.close();
            System.out.println("citas guardadas exitosamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar las citas");
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Cita> cargarCitas() {
        File archivo = new File(ARCHIVO_CITAS);
        if (!archivo.exists()) {
            return new ArrayList<>();//archivo no existe, se retorna lista vacia 
        }

        try  {
            Reader reader = new FileReader(ARCHIVO_CITAS);
            Type listType = new TypeToken<ArrayList<Cita>>(){}.getType();
            List<Cita> citas = gson.fromJson(reader, listType);
            return citas != null ? citas : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean agregarCita(Cita cita) {
        List<Cita> citas = cargarCitas();
        citas.add(cita);
        if (guardarCitas(citas)){return true;}
        return false
        ;
    }

    @Override
    public boolean actualizarCita(Cita citaActualizada) {
        List<Cita> citas = cargarCitas();
        for (int i = 0; i < citas.size(); i++) {
            if (citas.get(i).getId().equals(citaActualizada.getId())) {
                citas.set(i, citaActualizada);
                break;
            }
        }
        if (guardarCitas(citas)){return true;}
        return false;
    }

    @Override
    public boolean eliminarCita(String idCita) {
        List<Cita> citas = cargarCitas();
        citas.removeIf(c -> c.getId().equals(idCita));
        if (guardarCitas(citas)){return true;}
        return false;
    }
}