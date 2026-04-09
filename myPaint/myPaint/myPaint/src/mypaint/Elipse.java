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
public class Elipse extends Form {
    
    @Override
    public void draw( Graphics g ) {
        int x1d = x1 < x2 ? x1 : x2;
        int y1d = y1 < y2 ? y1 : y2;

        int x2d = x1 > x2 ? x1 : x2;
        int y2d = y1 > y2 ? y1 : y2;

        g = g.create();
        g.setColor( fill );
        g.fillOval( x1d, y1d, x2d-x1d, y2d-y1d );
        g.setColor( outline );
        g.drawOval( x1d, y1d, x2d-x1d, y2d-y1d );
        g.dispose();
        
    }
    
}
