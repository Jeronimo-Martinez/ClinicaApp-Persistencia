package com.mycompany.clinicaapp.Modelos;

import com.mycompany.clinicaapp.Interfaces.IUsuario;

/**
 * Modelo que representa a un administrador del sistema.
 */
public class Administrador implements IUsuario {

	private String nombre;
	private String cedula;
	private String telefono;
	private String contrasena;
	private String correo;

	/**
	 * Constructor vacío.
	 */
	public Administrador() {
	}
    // Constructor con parámetros
    public Administrador(String cedula, String contrasena){
		// Guardar correctamente la cédula y la contraseña
		this.cedula = cedula;
		this.contrasena = contrasena;

    }

	// Getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

}