/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicaapp.LogicaDelNegocio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mycompany.clinicaapp.Interfaces.IMedicoService;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import com.mycompany.clinicaapp.Modelos.Medico;

/**
 *
 * @author hecto
 */


public class GestorMedico implements IMedicoService {

    private final ArrayList<Medico> listaMedicos = new ArrayList<>();
    
    
    
   
    
    
    public GestorMedico() {
        Especialidad cardio = new Especialidad("Cardiología");
        Especialidad general = new Especialidad("Medicina General");
        listaMedicos.add(new Medico("1111", "Andrés Gómez", cardio, "1111"));
        listaMedicos.add(new Medico("222", "Laura Torres", general, "2222"));
    }

    /**
     *
     * @param nombreEspecialidad
     * @param medico
     * @param nuevoNombre
     * @param nuevaEspecialidad
     * @return 
     */
    
    
    
    // Método con filtro por nombre de especialidad (no forma parte de la interfaz)
     public List<Medico> listarMedicosEspecialidad(String nombreEspecialidad){
        return this.listaMedicos.stream().filter(m -> (m.getEspecialidad().getNombre() == null ? nombreEspecialidad == null : m.getEspecialidad().getNombre().equals(nombreEspecialidad))).collect(Collectors.toList());
    }

    // Implementación requerida por la interfaz: lista de médicos por especialidad (sin parámetros)
    @Override
    public List<Medico> listarMedicosEspecialidad(){
        return new ArrayList<>(listaMedicos);
    }

    // Método auxiliar para compatibilidad con código que invoca "listarMedicos()"
    public List<Medico> listarMedicos(){
        return listaMedicos();
    }
    
    @Override
    public boolean editarMedico(Medico medico, String nuevoNombre, Especialidad nuevaEspecialidad) {
        medico.setNombre(nuevoNombre);
        medico.setEspecialidad(nuevaEspecialidad);
        return true;
    }

    @Override
    public List<Medico> listaMedicos() {
        return new ArrayList<>(listaMedicos);
    }

    /**
     *
     * @param medico
     * @return
     */
    @Override
    public boolean agregarMedic(Medico medico) {
        listaMedicos.add(medico);
        return true;
    }

    @Override
    public boolean eliminarMedico(String cedula) {
        return listaMedicos.removeIf(m -> m.getCedula().equals(cedula));
    }

    @Override

    public Medico iniciarSesion(String cedula, String contrasena) {
        for (Medico medico : listaMedicos) {
            if (medico.getCedula().equals(cedula) && medico.getContrasena().equals(contrasena)) {
                return medico;
            }
        }
        return null;
    }

}