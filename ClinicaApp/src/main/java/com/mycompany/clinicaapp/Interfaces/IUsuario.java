package com.mycompany.clinicaapp.Interfaces;

/**
 *  Esta interfaz representa la información básica de un usuario del sistema.
 * 
 * Esta interfaz define los métodos esenciales para acceder a los datos 
 * principales de un usuario.
 * Las clases que implementen esta interfaz deben tener naturalmente los métodos 
 * que cumplan con el encapsulamiento de POO
 * @author Silvana
 */
public interface IUsuario {

    String getCedula();
    String getContrasena();
    
}