package com.mycompany.clinicaapp.Modelos;

/**
 * Esta clase representa a un paciente de la clínica
 * @author Valentina 
 */ 

public class Paciente {

    private String cedula;
    private String nombre;
    private String telefono;
    private int edad;
    private String contrasena;

    /**
     * Constructor vacío por defecto
     */

    public Paciente() {
    }

    /**
     * Constructor para la instancia de un Paciente
     * @param cedula Cédula (identificación) del paciente que se va a instanciar
     * @param nombre Nombre del paciente que se va a instanciar
     * @param telefono Número de telefóno del paciente que se va a instanciar
     * @param edad Edad del paciente que se va a instanciar
     * @param contrasena Contraseña del paciente que se va a instanciar
     */
    
    public Paciente(String cedula, String nombre, String telefono, int edad, String contrasena) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.contrasena = contrasena;
    }

    /**
     * Constructor para la instancia de un Paciente
     * @param cedula Cédula (identificación) del paciente que se va a instanciar
     * 
     */
    public Paciente (String cedula){
        this.cedula = cedula;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getContrasena(){
        return contrasena;
    }

    public void setContrasena(String contrasena){ 
        this.contrasena = contrasena;
    }

    /**
     * Este método sobrescribe el método toString de la clase Object para mostar de manera legible la información
     * del paciente, ya sea en consola o en componentes gráficos
     * @return Una representación en texto del paciente (su información)
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + " - Cédula: " + cedula + " - Edad: " + edad + " - Teléfono: " + telefono; 
    }
}