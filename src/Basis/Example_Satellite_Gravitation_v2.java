package Basis;

// GamePhysics
// Bjørn Christensen, 28/4-2009
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class Example_Satellite_Gravitation_v2 extends JFrame {
  PaintPanel panel=new PaintPanel();
    
  Example_Satellite_Gravitation_v2() {
    add(panel);
  }  // Constructor
  
  class PaintPanel extends JPanel {
    // Animation
    int frameRate=24;                 // No of frames/second
    int frameDelay = 1000/frameRate;  // time between frames in milli seconds
    Timer myScreenTimer=new Timer(frameDelay, new ScreenTimerListener());
    double t=0;                   // Current time in seconds
    double t0;                    // Start time in seconds

    // Physical constants
    double R=6.378e6;             // Radius of Earth in m
    double M=5.97e24;             // Mass of Earth in kg
    double G=6.6726e-11;          // Gravitational Constant in Nm^2/kg^2
    S2 S2=new S2(30/R,30/R,600,300);  // 30 pix=1 earth radie

    // Setup for Satellite Orbit: Fitzpatrick 12.5
    double w=2*Math.PI/(24*60*60);  // Angular velocity. rad/s
    double speed=6.62*R*w;          // Tangential speed. m/s
    V2 r0=new V2(6.62*R,0);         // Start position (scaled)
    V2 v0=new V2(0,speed);          // Initial velocity (scaled)

    // Motion
    V2 E=new V2(0,0);             // Position of Earth
    V2 a;                         // Acceleration
    V2 v=new V2(v0);              // Velocity
    V2 r=new V2(r0);              // Position

    PaintPanel() {
      // Start simulation
      t0=System.currentTimeMillis()/1000.0;
      myScreenTimer.start();
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      double tp=t;                            // time for previus step
      t=System.currentTimeMillis()/1000.0-t0;
      double dt=t-tp;                         // timestep
      dt=dt*60*60*24/10;                      // scale time: 1 round=10 sec

      // Integration
      a=r.unit().mul(-G*M/(r.dot(r)));
      v=v.add(a.mul(dt));
      r=r.add(v.mul(dt));

      g.drawString("t="+t, 5, 15);
      g.drawString("a="+a, 5, 30);
      g.drawString("v="+v, 5, 45);
      g.drawString("r="+r, 5, 60);
      // Draw
      S2.drawAxis(g);
      S2.drawPoint(g, r, 5);          // Satellite
      S2.drawPoint(g, r0, 5);         // Startposition of Satellite
      S2.drawCircle(g, E, R);         // Earth surface
      S2.drawCircle(g, E, 6.62*R);    // Geostationary orbit
            
      if (r.length()<R) myScreenTimer.stop();    // Hit Earth
    }

    class ScreenTimerListener implements ActionListener {
      public void actionPerformed(ActionEvent evt){
        repaint();
      }
    }
  } // class PaintPanel
    
  public static void main(String[] args) {
    Example_Satellite_Gravitation_v2 frame = new Example_Satellite_Gravitation_v2();
    frame.setTitle("Game Physics - Example_Satellite_Gravitation_v2");
    frame.setSize(1200, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
