/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mypaint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author mas
 */
public class PanelDraw extends JPanel {

    private Form formTemp;
    private List<Form> forms;
    
    public PanelDraw() {
        forms = new ArrayList<>();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Antialiasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setColor( Color.WHITE );
        g.fillRect( 0, 0, getWidth(), getHeight() );
        
        g.setColor( Color.BLACK );
        g.drawRect( 0, 0, getWidth()-1, getHeight()-1 );
        
        for( Form form : forms ) {
            form.draw( g );
        }

        if( formTemp != null ) {
            formTemp.draw( g );
        }
        
    }

    public void setFormTemp(Form formTemp ) {
        this.formTemp = formTemp;
    }

    public void addForm( Form form ) {
        forms.add( form );
    }
 
    public List<Form> getForms() {
        return new ArrayList<>(forms);
    }

    public void setForms( List<Form> newForms ) {
        this.forms = new ArrayList<>( newForms );
    }
    
}
