/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mypaint;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Mthss
 */
public class Pen extends Form {
    
    @Override
    public void draw(Graphics g) {
        g = g.create();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(outline);
        g2d.setStroke(new BasicStroke(3));
        g2d.fillOval(x1 - 2, y1 - 2, 4, 4);
        g.dispose();
    }
}
