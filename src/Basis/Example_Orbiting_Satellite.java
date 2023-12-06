package Basis;

import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;


public class Example_Orbiting_Satellite extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Example_Orbiting_Satellite() {   // Constructor
        add(panel);
    }
  
    class PaintPanel extends JPanel {
        S2 s=new S2(30,30,50,600);      // Coordinatesystem

        // Simulating time
        double t=0;                      // Time in sec since simulation start
        double t0;                       // Timestamp of simulationstart in sec

        // Animation
        int frameRate=25;                // No of frames/second
        int frameDelay = 1000/frameRate; // time between frames in milli sec
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Application
        V2 P=new V2(11,8);               // Center of Earth
        V2 A, B, C;                      // Points on Satellite
        V2 Om, im, jm;                   // Basis of Satellite coordst.
        V2 Am=new V2(0,0);               // Point A relative to im,jm
        V2 Bm=new V2(1,1);               // Point B relative to im,jm
        V2 Cm=new V2(1,-1);              // Point C relative to im,jm
        double T0=10;                    // Orbit period in sec.
        double W=(2*Math.PI)/T0;         // Angular frequency in rad/sec
        double phi0=-Math.PI/2;          //
        double r=10;                     // Radius of Satellite orbit (Point A)

        PaintPanel() {
            // Start simulation
            t0=System.currentTimeMillis()/1000.0;
            myTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            s.drawAxis(g);

            // Update time
            t=System.currentTimeMillis()/1000.0-t0;

            // Simulation
            double phi=phi0+W*t;
            Om=new V2(r*Math.cos(phi), r*Math.sin(phi)).add(P);
            im=Om.sub(P).unit();
            jm=new V2(-im.y,im.x);
            M2 M=new M2(im.x, im.y,
                        jm.x, jm.y);

            A=M.inv().mul(Am).add(Om);
            B=M.inv().mul(Bm).add(Om);
            C=M.inv().mul(Cm).add(Om);

            // Draw Satellite and Earth
            s.drawLine(g, A, B);
            s.drawLine(g, B, C);
            s.drawLine(g, C, A);
            s.drawPoint(g, P, Color.BLUE, 200);

            // Startposition
            V2 A0=new V2(10,-3);
            V2 B0=new V2(12,-3);
            V2 C0=new V2(11,-2);
            s.drawLine(g, A0, B0);
            s.drawLine(g, B0, C0);
            s.drawLine(g, C0, A0);
            s.drawPoint(g, P);
        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } //

    public static void main(String[] args) {
    Example_Orbiting_Satellite frame = new Example_Orbiting_Satellite();
    frame.setTitle("Game Physics - Exercise_07_Orbiting_Satellite");
    frame.setSize(750, 750);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
