package com.mycompany.clinicaapp.Persistencia;

import com.mycompany.clinicaapp.Modelos.Medico;
import java.io.*;
import java.util.*;

public class PersistenciaMedico {

    private static final String ARCHIVO = "medicos.dat";

    public static void guardar(List<Medico> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(lista);
            System.out.println("Médicos guardados correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Medico> cargar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            return (List<Medico>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se encontró archivo de médicos, devolviendo lista vacía.");
            return new ArrayList<>();
        }
    }
}
