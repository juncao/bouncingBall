/*
 * @(#)BouncingBall.java
 */
package bouncingball;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;

/**
 * This is the main for BouncingBall.
 * @author Jun
 */
public class BouncingBall {
    
     private static BallPanel ballCanvas;
    
    /**
     * The main will create a JFrame and add a BallPanel.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame("BouncingBall");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container a = frame.getContentPane();
        a.setBackground(Color.WHITE);
        ballCanvas = new BallPanel(frame);
        frame.add(ballCanvas);
        frame.pack();
        frame.setVisible(true);
        
    }
}
