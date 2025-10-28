/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.clinicaapp.Interfaces;

import com.mycompany.clinicaapp.Modelos.Medico;

/**
 *
 * @author johan
 */
public interface IInterfazAdminMedica {
    void mostrarVentanaMedica();
    void mostrarVentanaAgregarMedico();
    void mostrarVentanaEditarMedico(Medico medico);
    void actualizarTablaMedicos();
    
    
    
    
}
