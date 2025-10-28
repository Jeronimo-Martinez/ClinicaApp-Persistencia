package com.mycompany.clinicaapp.Utilidades.AdminMedica;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author johan
 */
public class BotonJtableMedico extends JButton {

    private boolean presionaMouse;

    public BotonJtableMedico() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                presionaMouse = true;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                presionaMouse = false;
            }

        });

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int ancho = getWidth();
        int altura = getHeight();
        int tamaño = Math.min(ancho, altura);
        int x = (ancho - tamaño) / 2;
        int y = (altura - tamaño) / 2;
        if (presionaMouse) {
            g2.setColor(new Color(158, 158, 158));
        } else {
            g2.setColor(new Color(199, 199, 199));
        }
        g2.fill(new Ellipse2D.Double(x, y, tamaño, tamaño));

        g2.dispose();
        super.paintComponent(graphics); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
