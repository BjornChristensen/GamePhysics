package Basis;

// GamePhysics
// Edwards p 324: Motion with air resistance
// Bjørn Christensen, 17/4-2009
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class Exercise_14_Brooklyn_Bridge extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_14_Brooklyn_Bridge() {
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
//        double a=-g;            // Acceleration

        double p=1.5;          // Drag coefficient
                                // No air resistance    : k=0.0000001
                                // With air resistance  : k=0.15
                                // Open overcoat        : k=0.5
                                // Parachute            : k=1.5
        double vT=g/p;          // Terminal velocity

        PaintPanel() {
            t0=System.currentTimeMillis()/1000.0;
            myTimer.start();
        }

        // Position:
        double x(double t) {
            return x0-vT*t+(v0+vT)*(1-Math.exp(-p*t))/p;            // (17)
//            return x0+v0*t+0.5*a*t*t;           // Without Air Resistance
        }

        // Velocity:
        double v(double t) {
            return (v0+vT)*Math.exp(-p*t)-vT;                       // (16)
//            return v0+a*t;                      // Without Air Resistance
        }

        // Acceleration:
        double a(double t) {
            return -g-p*v(t);                       // (16)
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Update time
            t=System.currentTimeMillis()/1000.0-t0;
            
            // Draw
            double x=x(t);
            double v=v(t);
            double a=a(t);
            g.drawString("t="+t, 5, 15);
            g.drawString("x="+x, 5, 30);
            g.drawString("v="+v+" "+vT, 5, 45);
            g.drawString("a="+a, 5, 60);
            g.drawString("p="+p, 5, 75);
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
    Exercise_14_Brooklyn_Bridge frame = new Exercise_14_Brooklyn_Bridge();
    frame.setTitle("Game Physics - Exercise_14_Brooklyn_Bridge");
    frame.setSize(1000, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
