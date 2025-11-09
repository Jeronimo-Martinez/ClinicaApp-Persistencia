/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicaapp.LogicaDelNegocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mycompany.clinicaapp.Interfaces.IGestorCita;
import com.mycompany.clinicaapp.Interfaces.IRepositorioCita;
import com.mycompany.clinicaapp.Persistencia.RepositorioCita;
import com.mycompany.clinicaapp.Modelos.Cita;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import com.mycompany.clinicaapp.Modelos.Medico;
import com.mycompany.clinicaapp.Modelos.Paciente;

/**
 *
 * @author jmart
 * @version 1.0.0
 */
public class GestorCita implements IGestorCita {
    private List<Cita> citas;
    private IRepositorioCita repositorio;

    public GestorCita() {
        this.repositorio = new RepositorioCita();
        this.citas = repositorio.cargarCitas(); // Cargar citas al inicializar
    }

    @Override
    public boolean registrarCita(Cita c) {
        try {
            citas.add(c);
            repositorio.agregarCita(c); // Guardar en JSON
            return true;
        }catch (Exception exception) {
            System.out.println("Error inesperado, no se pudo almacenar la cita");
            return false;
        }

        }

        @Override
        public List<Cita> consultarCitasPaciente(Paciente paciente){
        try {
            return this.citas.stream()
                    .filter(c -> c.getPaciente() != null && paciente != null && c.getPaciente().getCedula().equals(paciente.getCedula()))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            System.out.println("Error inesperado");
            return null;
        }

        }
        
        @Override
        public List<Cita> consultarCitasMedico(Medico medico){
        try {
            return this.citas.stream()
                    .filter(c -> c.getMedico() != null && medico != null && c.getMedico().getCedula().equals(medico.getCedula()))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            System.out.println("Error inesperado");
            return null;
        }
        }
        
        @Override
        public Cita buscarCitaPorId(String idCita) {
            for (Cita c : citas) {
                if (c.getId().equals(idCita)) {
                     return c;
            }
        }return null;}

        @Override
        public boolean eliminarCita(String idCita) {
        try {
            citas.removeIf(c -> c.getId().equals(idCita));
            repositorio.eliminarCita(idCita); // Eliminar del JSON
            return true;
        }catch (Exception exception) {                                  
            System.out.println("Error inesperado");
            return false;
        }}

        /**
         * Modifica una cita existente identificada por id.
         * Reemplaza la entrada en la lista y actualiza el registro en el repositorio JSON.
         *
         * @param idCita identificador de la cita a modificar
         * @param nueva  objeto Cita con los nuevos datos
         * @return true si la modificación fue exitosa, false si no se encontró la cita o ocurrió un error
         */
        @Override
        public boolean modificarCita(String idCita, Cita nueva) {
            try{
                System.out.println("GestorCita.modificarCita: buscando cita id=" + idCita + " en lista de " + citas.size() + " citas");
                for(int i = 0 ; i < citas.size(); i++){
                    Cita c = citas.get(i);
                    if (c.getId().equals(idCita)){
                        System.out.println("GestorCita.modificarCita: cita encontrada en índice " + i);
                        citas.set(i, nueva);
                        boolean guardado = repositorio.actualizarCita(nueva); // Guardar cambios en JSON
                        System.out.println("GestorCita.modificarCita: repositorio.actualizarCita devolvió " + guardado);
                        return guardado;
                    
                }  
            }
            System.out.println("GestorCita.modificarCita: cita id=" + idCita + " NO encontrada en la lista");
            return false;
            }catch (Exception exception) {                                  
                System.err.println("GestorCita.modificarCita: excepción - " + exception.getMessage());
                exception.printStackTrace();
                return false;
        }}
        @Override
         public List<Cita> getCitas() {
            return citas;

    }
         @Override
    public void cargarCitasjson() {
        this.citas = repositorio.cargarCitas();
    }

    @Override
    public void guardarCitasjson() {
        repositorio.guardarCitas(citas);
    }
    }

