package Basis;

// GamePhysics
// Bjørn Christensen, 28/4-2009
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class Example_Satellite_Gravitation extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Example_Satellite_Gravitation() {
        add(panel);
    }  // Constructor
  
    class PaintPanel extends JPanel {
        // Coordinatesystem and Animation
        S2 S2=new S2(30,30,600,300);
        int frameRate=24;                 // No of frames/second
        int frameDelay = 1000/frameRate;  // time between frames in milli seconds
        Timer myScreenTimer=new Timer(frameDelay, new ScreenTimerListener());
        Timer mySimulationTimer=new Timer(15, new SimulationTimerListener());

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
        double xs=1.0/R;              // 1 unit = R (one Earth radius)
        double ts=10.0/(24*60*60);    // 1 Earth rotation = 10s of simulation
        double Rs=R*xs;               // Earth radius in simulation
        double Gs=G*xs*xs*xs/(ts*ts); // G in simulation. m^3/kg*s^2

        // Setup for Satellite Orbit: Fitzpatrick 12.5
        double w=2*Math.PI/(24*60*60);          // Angular velocity. rad/s
        double speed=0.6*6.62*R*w;                  // Tangential speed. m/s
        V2 r0=new V2(6.62*Rs,0);                // Start position (scaled)
        V2 v0=new V2(0,speed*xs/ts);            // Initial velocity (scaled)

/*
        // Setup for Escape velocity: Fitzpatrick 12.4
        double xs=1.0/R;              // 1 unit = R (one Earth radius)
        double ts=100.0/(24*60*60);   // 1 Earth rotation = 100s of simulation
        double Rs=R*xs;               // Earth radius in simulation
        double Gs=G*xs*xs*xs/(ts*ts); // G in simulation. m^3/kg*s^2
        V2 r0=new V2(Rs,0);           // Start position: Earth surface
        V2 v0=new V2(Math.sqrt(2*Gs*M/Rs),0); // Initial velocity
*/

        // Motion
        V2 E=new V2(0,0);             // Position of Earth
        V2 a;                         // Acceleration
        V2 v=new V2(v0);              // Velocity
        V2 r=new V2(r0);              // Position

        PaintPanel() {
            // Start simulation
            t0=System.currentTimeMillis()/1000.0;
            mySimulationTimer.start();
            myScreenTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawString("t="+t, 5, 15);
            g.drawString("a="+a, 5, 30);
            g.drawString("v="+v, 5, 45);
            g.drawString("r="+r, 5, 60);
            // Draw
            S2.drawAxis(g);
            S2.drawPoint(g, r, 5);          // Satellite
            S2.drawPoint(g, r0, 5);         // Startposition of Satellite
            S2.drawCircle(g, E, Rs);        // Earth surface
            S2.drawCircle(g, E, 6.62*Rs);   // Geostationary orbit
        }

        class ScreenTimerListener implements ActionListener {
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
                if (r.length()<Rs) mySimulationTimer.stop();    // Hit Earth
            }
        }
    } // class PaintPanel
    
  public static void main(String[] args) {
    Example_Satellite_Gravitation frame = new Example_Satellite_Gravitation();
    frame.setTitle("Game Physics - Example_Satellite_Gravitation");
    frame.setSize(1200, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
