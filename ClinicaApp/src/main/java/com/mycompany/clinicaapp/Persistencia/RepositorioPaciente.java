package com.mycompany.clinicaapp.Persistencia;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.clinicaapp.Interfaces.IRepositorioPaciente;
import com.mycompany.clinicaapp.Modelos.Paciente;

/**
 * Esta clase implementa la interfaz IRepositorioPaciente y se encarga de la persistencia del paciente
 * @author Valentina
 */
public class RepositorioPaciente implements IRepositorioPaciente{
    
    private static final String ARCHIVO = System.getProperty("user.dir") + File.separator + "pacientes.json";

    // Crear instancia Gson con formato legible (pretty printing)
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Este método guarda la lista de pacientes
     * @param pacientes Lista de pacientes
     */
    @Override
    public void guardar(List<Paciente> listaPacientes){
        try (Writer writer = new FileWriter(ARCHIVO)){
            gson.toJson(listaPacientes, writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Este método carga la lista de pacientes
     * @return listaPacientes si existe, en caso contrario, retorna una lista vacía
     */
    @Override
    public List<Paciente> cargar() {
        File archivo = new File(ARCHIVO);
        
        // verifica si el archivo no existe, si es true, devuelve una lista vacía
        if (!archivo.exists()){
            System.out.println("No se encontró la lista de pacientes");
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<List<Paciente>>() {}.getType();
            List<Paciente> listaPacientes = gson.fromJson(reader, tipoLista);

            if (listaPacientes == null) {
                return new ArrayList<>();
            }

            System.out.println("Pacientes cargados correctamente");
            return listaPacientes;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
