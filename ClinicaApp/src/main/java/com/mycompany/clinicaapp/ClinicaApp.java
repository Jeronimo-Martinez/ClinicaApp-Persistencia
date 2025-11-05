/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicaapp;
import javax.swing.SwingUtilities;
import com.mycompany.clinicaapp.Presentacion.VentanaIniciarSesion;

/**
 * Punto de entrada de la aplicación.
 * Crea instancias concretas de los gestores y abre la ventana de inicio de sesión
 */
public class ClinicaApp {

	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaIniciarSesion().setVisible(true));
    }
}
