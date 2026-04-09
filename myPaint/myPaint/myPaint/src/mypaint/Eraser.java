/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mypaint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Mthss
 */
public class Eraser extends Form {
      
    @Override
    public void draw(Graphics g) {
        g = g.create();
        g.setColor(Color.WHITE);
        g.fillOval(x1 - 8, y1 - 8, 50, 50);
        g.dispose();
    }
}
    