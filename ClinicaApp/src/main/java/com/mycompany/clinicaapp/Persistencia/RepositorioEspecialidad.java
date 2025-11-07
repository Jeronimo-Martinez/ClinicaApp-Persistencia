package com.mycompany.clinicaapp.Persistencia;

import com.mycompany.clinicaapp.Interfaces.IRepositorioEspecialidad;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEspecialidad implements IRepositorioEspecialidad {

    private static final String ARCHIVO =
            System.getProperty("user.dir") + File.separator + "especialidades.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void guardar(List<Especialidad> especialidades) {
        try (Writer writer = new FileWriter(ARCHIVO)) {
            gson.toJson(especialidades, writer);
            System.out.println("Especialidades guardadas en: " + ARCHIVO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Especialidad> cargar() {
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("No se encontró el archivo de especialidades, devolviendo lista vacía.");
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<List<Especialidad>>() {}.getType();
            List<Especialidad> lista = gson.fromJson(reader, tipoLista);
            if (lista == null) {
                return new ArrayList<>();
            }
            System.out.println("Especialidades cargadas desde: " + ARCHIVO);
            return lista;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
