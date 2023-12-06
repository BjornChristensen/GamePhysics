package Basis;

// GamePhysics
// Bjørn Christensen, 28/4-2009
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class Exercise_16_Comet extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_16_Comet() {
        add(panel);
    }  // Constructor
  
    class PaintPanel extends JPanel {
        // Coordinatesystem and Animation
        S2 S2=new S2(40,40,300,300);
        int frameRate=24;                 // No of frames/second
        int frameDelay = 1000/frameRate;  // time between frames in milli seconds
        Timer myDisplayTimer=new Timer(frameDelay, new DisplayTimerListener());
        Timer mySimulationTimer=new Timer(20, new SimulationTimerListener());

        // Simulation time
        double t=0;                   // Current time in seconds
        double t0;                    // Start time in seconds

        // Physical constants
        double R=6.378e6;             // Radius of Earth in m
        double M=5.97e24;             // Mass of Earth in kg
        double G=6.6726e-11;          // Gravitational Constant in Nm^2/kg^2

        // Physical constants scaled to simulation
        // We scale astronimic distandes to fit the computerscreen by R
        // and astronomic time to earthrotations (days)
        double xs=1.0/(3*R);          // 1 unit = 3R
        double ts=10.0/(24*60*60);    // 1 Earth rotation = 10s of simulation
        double Rs=R*xs;               // Earth radius in simulation
        double Gs=G*xs*xs*xs/(ts*ts); // G in simulation. m^3/kg*s^2

        // Startconditions
        V2 r0=new V2(100*Rs,0);       // Start position: 100R
        double phi=5*2*Math.PI/360;   // 5 deg
        double speed=(15000/3.6)*xs/ts;    // 15000 kh/h -> m/s -> sim speed
        V2 v0=new V2(-Math.cos(phi)*speed, Math.sin(phi)*speed); // Initial vel

        // Motion
        V2 E=new V2(0,0);             // Position of Earth
        V2 a;                         // Acceleration
        V2 v=new V2(v0);              // Velocity
        V2 r=new V2(r0);              // Position

        PaintPanel() {
            // Start simulation
            t0=System.currentTimeMillis()/1000.0;
            mySimulationTimer.start();
            myDisplayTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("t="+t, 5, 15);
            g.drawString("Real time (h)="+t/(ts*60*60), 200, 15);
            g.drawString("a="+a, 5, 30);
            g.drawString("v="+v, 5, 45);
            g.drawString("r="+r, 5, 60);
            // Draw
            S2.drawAxis(g);
            S2.drawPoint(g, r, 5);          // Object
            S2.drawPoint(g, r0);            // Startposition of object
            S2.drawCircle(g, E, Rs);        // Earth surface
            S2.drawCircle(g, E, 6.62*Rs);   // Geostationary orbit
        }

        class DisplayTimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }

        class SimulationTimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                double tp=t;                            // time for previus step
                t=System.currentTimeMillis()/1000.0-t0;
                double dt=t-tp;                         // timestep

                // Integration
                double r2=r.dot(r);
                a=r.unit().mul(-Gs*M/r2);
                v=v.add(a.mul(dt));
                r=r.add(v.mul(dt));

                // Stop simulation
//                if (r.length()<Rs) mySimulationTimer.stop();      // Hit Earth
//                if (r.length()<6.62*Rs) mySimulationTimer.stop(); // Geostationary zone
            }
        }
    } // class PaintPanel
    
  public static void main(String[] args) {
    Exercise_16_Comet frame = new Exercise_16_Comet();
    frame.setTitle("Game Physics - Exercise_16_Comet");
    frame.setSize(1200, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
