/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicaapp.Utilidades.AdminMedica;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
/**
 *
 * @author johan
 */
public class ActivadorJtableMedicoBotones extends DefaultCellEditor {

    
    
    private EventosParaBotones evento;
    public ActivadorJtableMedicoBotones(EventosParaBotones evento) {
        super(new JCheckBox());
        this.evento= evento;
        
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable tablaMedica, Object value, boolean isSelected, int row, int column) {
       BotonesJtableMedico action= new BotonesJtableMedico();
       action.iniciarEvento(evento, row);
       return action;
    }
    
}
