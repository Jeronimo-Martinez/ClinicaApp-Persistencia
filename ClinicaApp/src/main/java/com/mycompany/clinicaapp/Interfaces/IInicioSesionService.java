package com.mycompany.clinicaapp.Interfaces;

/**
 * Servicio que define las operaciones relacionadas con el inicio de sesión de usuarios.
 * 
 * Esta interfaz permite autenticar a un usuario del sistema verificando su cédula y contraseña.
 * @author Silvana
 */
public interface IInicioSesionService {
    
    IUsuario iniciarSesion(String cedula, String contrasena);
    
    
}

