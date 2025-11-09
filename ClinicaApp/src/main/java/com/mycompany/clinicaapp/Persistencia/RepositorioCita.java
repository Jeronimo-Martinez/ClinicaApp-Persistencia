package com.mycompany.clinicaapp.Persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonParseException;
import com.mycompany.clinicaapp.Interfaces.IRepositorioCita;
import com.mycompany.clinicaapp.Modelos.Cita;
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RepositorioCita implements IRepositorioCita {
    private static final String ARCHIVO_CITAS = "citas.json";
    private final Gson gson;

    public RepositorioCita() {
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

        this.gson = new GsonBuilder()
            // Serializador inline para LocalDate
            .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                @Override
                public JsonElement serialize(LocalDate src, Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
                    return src == null ? null : new JsonPrimitive(src.format(fmt));
                }
            })
            // Deserializador inline para LocalDate
            .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) throws JsonParseException {
                    if (json == null || json.isJsonNull()) return null;
                    return LocalDate.parse(json.getAsString(), fmt);
                }
            })
            .setPrettyPrinting()
            .create();
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
            System.out.println("Se cargo una Lista Vacia");
            return new ArrayList<>();//archivo no existe, se retorna lista vacia 
        }

        try  {
            Reader reader = new FileReader(ARCHIVO_CITAS);
            Type listType = new TypeToken<ArrayList<Cita>>(){}.getType();
            List<Cita> citas = gson.fromJson(reader, listType);
            System.out.println("Citas Cargadas Correctamente");
            return citas != null ? citas : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar citas , se cargo una lista vacia");
            return new ArrayList<>();
        }
    }

    @Override
    public boolean agregarCita(Cita cita) {
        List<Cita> citas = cargarCitas();
        citas.add(cita);
        if (guardarCitas(citas))
            {   System.out.println("Cita guardada exitosamente");
                return true;
            }else{
                System.out.println("error al guardar cita");
                return false;
            }
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
        if (guardarCitas(citas))
        {
            System.out.println("cita actualizada exitosamente");
            return true;
        }else{
            System.out.println("error al actualizar cita");
            return false;
        }
        
    }

    @Override
    public boolean eliminarCita(String idCita) {
        List<Cita> citas = cargarCitas();
        citas.removeIf(c -> c.getId().equals(idCita));
        if (guardarCitas(citas))
        {
            System.out.println("cita eliminada exitosamente");
            return true;
        }else{
            System.out.println("error al eliminar cita");
            return false;
        }
    }
}