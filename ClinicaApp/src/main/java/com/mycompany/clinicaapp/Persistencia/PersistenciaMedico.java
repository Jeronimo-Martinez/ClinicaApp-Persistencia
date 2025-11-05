package com.mycompany.clinicaapp.Persistencia;

import com.mycompany.clinicaapp.Modelos.Medico;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
  

//Esta persistencia es un plan b...


public class PersistenciaMedico {

    private static final String ARCHIVO =
        System.getProperty("user.dir") + File.separator + "medicos.json";

    // 🔹 Crear instancia Gson con formato legible (pretty printing)
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // 🔹 Guardar lista de médicos
    public static void guardar(List<Medico> lista) {
        try (Writer writer = new FileWriter(ARCHIVO)) {
            gson.toJson(lista, writer);
            System.out.println("✅ Médicos guardados correctamente en: " + ARCHIVO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 🔹 Cargar lista de médicos
    public static List<Medico> cargar() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("⚠️ No se encontró el archivo de médicos, devolviendo lista vacía.");
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<List<Medico>>() {}.getType();
            List<Medico> lista = gson.fromJson(reader, tipoLista);

            if (lista == null) {
                return new ArrayList<>();
            }

            System.out.println("📂 Médicos cargados desde: " + ARCHIVO);
            return lista;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

