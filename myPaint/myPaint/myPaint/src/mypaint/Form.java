/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mypaint;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author mas
 */
public abstract class Form {
    
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;
    protected Color outline;
    protected Color fill;
    
    public Form() {
        outline = Color.BLACK;
        fill = Color.WHITE;
    }
    
    public abstract void draw( Graphics g );

        
    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public void setOutline(Color outline) {
        this.outline = outline;
    }

    public void setFill(Color fill) {
        this.fill = fill;
    }
 
}
