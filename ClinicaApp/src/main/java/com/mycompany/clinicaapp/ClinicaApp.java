/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicaapp;

import com.mycompany.clinicaapp.LogicaDelNegocio.GestorMedico;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorPaciente;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorEspecialidad;
import com.mycompany.clinicaapp.LogicaDelNegocio.GestorAdministrador;
import com.mycompany.clinicaapp.Presentacion.VentanaIniciarSesion;

/**
 * Punto de entrada de la aplicación.
 * Crea instancias concretas de los gestores y abre la ventana de inicio de sesión
 */
public class ClinicaApp {

	public static void main(String[] args) {
		GestorMedico gestorMedico = new GestorMedico();
		GestorPaciente gestorPaciente = new GestorPaciente();
		GestorEspecialidad gestorEspecialidad = new GestorEspecialidad();

		// Inicializar el GestorAdministrador (contendrá el administrador por defecto)
		GestorAdministrador.getInstanciaAdministrador(gestorMedico, gestorPaciente, gestorEspecialidad);

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				VentanaIniciarSesion ventana = new VentanaIniciarSesion(gestorMedico, gestorPaciente, gestorEspecialidad);
				ventana.setLocationRelativeTo(null);
				ventana.setVisible(true);
			}
		});
	}

}
