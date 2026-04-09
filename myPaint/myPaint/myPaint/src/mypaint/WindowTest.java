/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mypaint;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author mas
 */
public class WindowTest extends JFrame {
    
    private JButton button;
    
    public WindowTest() {
        
        // Window
        setTitle("my test window wow");
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( 800, 600 );
        setLocationRelativeTo( null );
        
        // Button
        button = new JButton( "click me fucker!" );
        button.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("AHHHHHHHH");
            }
            
        } );
  
        
        setLayout( new FlowLayout() );
        add( button );
        
        
        
    }
    
    public static void main(String[] args) {
        WindowTest window = new WindowTest();
        window.setVisible( true );
    }
    
    
}
