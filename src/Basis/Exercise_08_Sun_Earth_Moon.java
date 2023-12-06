package Basis;

import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;


public class Exercise_08_Sun_Earth_Moon extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_08_Sun_Earth_Moon() {
        add(panel);
    }  // Constructor
  
    class PaintPanel extends JPanel {
        S2 s=new S2(30,30,400,400);

        // Animation
        int frameRate=25;                     // No of frames/second
        int frameDelay = 1000/frameRate;      // time between frames in milli sec
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Time
        double t0;                            // Timestamp of simulationstart in sec
        double t=0;                           // Time in sec since simulation start

        // Sun
        V2 S=new V2(0,0);                     // location of Sun

        // Earth orbit
        V2 E;                                 // location of Earth
        double eE=0.1;                        // Eccentricity of Earth orbit
        double aE=10;                         // Major axis
        double bE=Math.sqrt(aE*aE*(1-eE*eE)); // Minor axis
        V2 OE=new V2(eE*aE, 0);               // Center of orbit
        double TE=10;                         // Orbit period in sec.
        double WE=(2*Math.PI)/TE;             // Angular frequency in rad/sec

        // Moon orbit
        V2 M;                                 // location of Earth
        double eM=0.5;                        // Eccentricity of Earth orbit
        double aM=1;                          // Major axis
        double bM=Math.sqrt(aM*aM*(1-eM*eM)); // Minor axis
        V2 OM=new V2(eM*aM, 0);               // Center of orbit
        double TM=TE*(28.0/365);              // Orbit period in sec.
        double WM=(2*Math.PI)/TM;             // Angular frequency in rad/sec

        PaintPanel() {
            t0=System.currentTimeMillis()/1000.0;
            myTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            s.drawAxis(g);

            // Update time
            t=System.currentTimeMillis()/1000.0-t0;

            // Rotate Earth around Sum
            E=new V2(aE*Math.cos(WE*t), bE*Math.sin(WE*t)).add(OE);
            M=new V2(aM*Math.cos(WM*t), bM*Math.sin(WM*t)).add(E).add(OM);

            // Draw Satellite and Earth
            s.drawPoint(g, S, 20);
            s.drawPoint(g, E, 10);
            s.drawPoint(g, M, 5);
        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } // class PaintPanel

    public static void main(String[] args) {
    Exercise_08_Sun_Earth_Moon frame = new Exercise_08_Sun_Earth_Moon();
    frame.setTitle("Game Physics - Exercise_08_Sun_Earth_Moon");
    frame.setSize(1200, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
