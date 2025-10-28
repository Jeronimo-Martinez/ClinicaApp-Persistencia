/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.clinicaapp.Utilidades;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author johan
 */
public class RenderizadoTablaMedico extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component com= super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        if (column == 3) { 
        BotonesJtableMedico panel = new BotonesJtableMedico();
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        return panel;
    }

       return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
 
    
}
