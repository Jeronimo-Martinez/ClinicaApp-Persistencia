/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicaapp.Modelos;

/**
 *
 * @author hecto
 */
public class Medico {
    private String cedula;
    private String nombre;
    private Especialidad especialidad;
    private String contrasena;

    
    
    
    public Medico(String cedula, String nombre, Especialidad especialidad, String contrasena) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.contrasena = contrasena;
    }
    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    
    
    
}