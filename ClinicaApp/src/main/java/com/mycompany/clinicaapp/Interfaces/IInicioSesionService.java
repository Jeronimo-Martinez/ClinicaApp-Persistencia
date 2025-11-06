package com.mycompany.clinicaapp.Interfaces;


public interface IInicioSesionService {
    
    IUsuario iniciarSesion(String cedula, String contrasena);
    
    boolean registrarPaciente(String nombre, String cedula, String telefono, int edad, String contrasena);
    
}

