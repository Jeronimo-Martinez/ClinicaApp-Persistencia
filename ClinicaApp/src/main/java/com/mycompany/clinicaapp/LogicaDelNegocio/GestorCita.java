/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicaapp.LogicaDelNegocio;

import com.mycompany.clinicaapp.Interfaces.IGestorCita;
import com.mycompany.clinicaapp.Modelos.Paciente;
import com.mycompany.clinicaapp.Modelos.Especialidad;
import com.mycompany.clinicaapp.Modelos.Medico;
import com.mycompany.clinicaapp.Modelos.Cita;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 *
 * @author jmart
 * @version 1.0.0
 */
public class GestorCita implements IGestorCita {
    private final ArrayList<Cita> listaCitas = new ArrayList<>(); 
    
    public GestorCita(){
        // citas de ejemplo
        Paciente p1 = new Paciente("0000", "Juan Pérez", "3001234567", 28, "0000");
        Paciente p2 = new Paciente("1002","María Gómez","3003334444",25,"pass2");
        Paciente p3 = new Paciente("1003","Carlos Ruiz","3005556666",40,"pass3");
        
        Especialidad e1 = new Especialidad("Medicina General");
        Especialidad e2 = new Especialidad("Pediatría");
        
        Medico m1 = new Medico("2001","Dr. Suárez", e1, "m1pass");
        Medico m2 = new Medico("2002","Dra. López", e2, "m2pass");
        Medico m3 = new Medico("222", "Laura Torres",e1, "2222");
        listaCitas.add(new Cita("001",LocalDate.of(2025, 10, 22),null, m1, p1));
        listaCitas.add(new Cita("006",LocalDate.of(2025, 10, 22),null, m2, p1));
        listaCitas.add(new Cita("004",LocalDate.of(2025, 10, 22),null, m3, p1));
        listaCitas.add(new Cita("002",LocalDate.of(2025, 11, 5),null, m2, p2));
        listaCitas.add(new Cita("003",LocalDate.of(2025, 12, 1),null, m1, p3));
    
    }
    @Override
    public boolean registrarCita(Cita c) {
        try {
            listaCitas.add(c);
            return true;
        }catch (Exception exception) {
            System.out.println("Error inesperado, no se pudo almacenar la cita");
            return false;
        }

        }

        @Override
        public List<Cita> consultarCitasPaciente(Paciente paciente){
        try {
            return this.listaCitas.stream().filter(c -> c.getPaciente() == paciente).collect(Collectors.toList()); // haskell me traumo con los maps y filter ;-;
        }catch (Exception exception) {
            System.out.println("Error inesperado");
            return null;
        }

        }
        
        @Override
        public List<Cita> consultarCitasMedico(Medico medico){
        try {
            return this.listaCitas.stream().filter(c -> c.getMedico() == medico).collect(Collectors.toList()); // haskell me traumo con los maps y filter ;-;
        }catch (Exception exception) {
            System.out.println("Error inesperado");
            return null;
        }
        }
        
        @Override
        public Cita buscarCitaPorId(String idCita) {
            for (Cita c : listaCitas) {
                if (c.getId().equals(idCita)) {
                     return c;
            }
        }return null;}

        @Override
        public boolean eliminarCita(String idCita) {
        try {
            return this.listaCitas.removeIf(c -> c.getId() == idCita); // la funcion remove if retorna un valor booleano dependiendo de si se elimino o no un elemento de la lista 
        }catch (Exception exception) {                                  
            System.out.println("Error inesperado");
            return false;
        }}

        @Override
        public boolean modificarCita(String idCita, Cita nueva) {
            try{
                for(int i = 0 ; i < listaCitas.size(); i++){
                    Cita c = listaCitas.get(i);
                    if (c.getId().equals(idCita)){
                        listaCitas.set(i, nueva);
                        return true;
                    
                }  
            }return false;
            }catch (Exception exception) {                                  
                System.out.println("Error inesperado");
                return false;
        }}
        @Override
         public List<Cita> getCitas() {
            return listaCitas;

    }
    }   
