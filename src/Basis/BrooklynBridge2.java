package Basis;

import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class BrooklynBridge2 extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    BrooklynBridge2() {
        add(panel);
    }  // Constructor
  
    class PaintPanel extends JPanel {
        S2 S2=new S2(10,10,100,500);

        // Simulating time
        double t0;                       // Timestamp of simulationstart in sec
        double t=0;                      // Time in sec since simulation start

        // Animation
        int frameRate=25;                // No of frames/second
        int frameDelay = 1000/frameRate; // time between frames in milli sec
        Timer myTimer=new Timer(frameDelay, new TimerListener());


        double x0=41.15;
        double v0=0;
        double g=9.81;
//        double a=-g;
        double p=1.5;
        double vT=g/p;
        
        PaintPanel() {
            // Start simulation
            t0=System.currentTimeMillis()/1000.0;
            myTimer.start();
        }

        double x(double t){
//          return x0+v0*t+0.5*a*t*t;
          return x0-vT*t+(v0+vT)*(1-Math.exp(-p*t))/p;
        }
        
        double v(double t){
//          return v0+a*t;
          return (v0+vT)*Math.exp(-p*t)-vT;
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Update time
            t=System.currentTimeMillis()/1000.0-t0;

            // Simulation
            S2.drawAxis(g);
            S2.drawLine(g, new V2(0,x0), new V2(100,x0));
            S2.drawPoint(g, new V2(0,x(t)), 10);
            g.drawString("x="+x(t), 15, 10);
            g.drawString("v="+v(t), 15, 30);
            g.drawString("t="+t, 15, 50);

            // Stop simulation
            if(x(t)<=0) myTimer.stop();
        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } // class PaintPanel
    
  public static void main(String[] args) {
    BrooklynBridge2 frame =
        new BrooklynBridge2();
    frame.setTitle("Game Physics - BrooklynBridge2");
    frame.setSize(1000, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
