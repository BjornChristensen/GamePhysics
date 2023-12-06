package Basis;// GamePhysics
// Fitzpatrick example 2.3 p 26: The Brooklyn Bridge (No air resistance)
// Bjørn Christensen, 17/4-2009
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class Example_Brooklyn_Bridge_Without_Airresistance extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Example_Brooklyn_Bridge_Without_Airresistance() {
        add(panel);
    }  // Constructor
  
    class PaintPanel extends JPanel {
        S2 S2=new S2(10,10,100,500);
        int frameRate=24;                 // No of frames/second
        int frameDelay = 1000/frameRate;  // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Time
        double t;               // Current time in seconds
        double t0;              // Start time in seconds

        // Start conditions
        double g=9.81;          // Acceleration due to gravity. m/s^2
        double x0=41.15;        // Start position = Height of Brooklyn Bridge
        double v0=0;            // Initial velocity
        double a=-g;            // Acceleration


        PaintPanel() {
            t0=System.currentTimeMillis()/1000.0;
            myTimer.start();
        }

        // Position:
        double x(double t) {
            return x0+v0*t+0.5*a*t*t;           // Without Air Resistance
        }

        // Velocity:
        double v(double t) {
            return v0+a*t;                      // Without Air Resistance
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Update time
            t=System.currentTimeMillis()/1000.0-t0;
            
            // Draw
            double x=x(t);
            double v=v(t);
            g.drawString("t="+t, 5, 15);
            g.drawString("x="+x, 5, 30);
            g.drawString("v="+v, 5, 45);
            S2.drawAxis(g);
            S2.drawPoint(g, new V2(0,x), Color.BLUE, 5);
            S2.drawPoint(g, new V2(0,x0), Color.BLACK, 5);

            // Stop simulation
            if ( x<0 ) myTimer.stop();          // Hit ground
//            if ( v<0 ) myTimer.stop();          // Top
        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } // class PaintPanel
    
  public static void main(String[] args) {
    Example_Brooklyn_Bridge_Without_Airresistance frame = new Example_Brooklyn_Bridge_Without_Airresistance();
    frame.setTitle("Game Physics - Basis.Example_Brooklyn_Bridge");
    frame.setSize(1000, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
