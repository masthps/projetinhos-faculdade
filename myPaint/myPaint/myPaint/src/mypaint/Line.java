/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mypaint;

import java.awt.Graphics;

/**
 *
 * @author mas
 */
public class Line extends Form {

    @Override
    public void draw( Graphics g ) {
        
        g = g.create();
        g.setColor(outline);
        g.drawLine( x1, y1, x2, y2 );
        g.dispose();
        
    }
}
