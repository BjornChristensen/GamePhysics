package Basis;

// GamePhysics
// Fitzpatrick chp3: Motion in 2 dimension
// Bjørn Christensen, 23/4-2011
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;

public class Example_Dropping_A_Bomb extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Example_Dropping_A_Bomb() {
        add(panel);
    }  // Constructor
  
    class PaintPanel extends JPanel {
        S2 S2=new S2(1,1,10,500);
        int frameRate=24;                 // No of frames/second
        int frameDelay = 1000/frameRate;  // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Time
        double t;                         // Current time in seconds
        double t0;                        // Start time in seconds

        // Start conditions
        double g=9.81;
        V2 r0=new V2(0,300);
        double s=500/3.6;                 // speed of aircraft in km/h
        V2 v0=new V2(s,0);
        V2 a=new V2(0,-g);

        ArrayList<V2> trajectory=new ArrayList<V2>();

        PaintPanel() {
            t0=System.currentTimeMillis()/1000.0;
            myTimer.start();
        }

        // Position: s=s0+v0*t+½*a*t^2  (3.33)
        V2 r(double t) {
            return r0.add(v0.mul(t)).add(a.mul(t*t/2));
        }

        // Velocity: v=v0+a*t               (3.34)
        V2 v(double t) {
            return v0.add(a.mul(t));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Update time
            t=System.currentTimeMillis()/1000.0-t0;
            
            // Draw
            V2 r=r(t);
            V2 v=v(t);
            trajectory.add(r);

            g.drawString("t="+t, 5, 15);
            g.drawString("r="+r, 5, 30);
            g.drawString("v="+v, 5, 45);
            S2.drawAxis(g);
            S2.drawLine(g, new V2(0,0), new V2(1100,0));
            S2.drawPoint(g, r, Color.BLUE, 5);
            S2.drawPoint(g, r0, Color.BLACK, 5);
            for (V2 p: trajectory) S2.drawPoint(g, p, Color.BLUE, 5);

            // Stop simulation
            if ( r.y<0 ) myTimer.stop();            // Hit ground
        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } // class PaintPanel
    
  public static void main(String[] args) {
    Example_Dropping_A_Bomb frame = new Example_Dropping_A_Bomb();
    frame.setTitle("Game Physics - Example_Dropping_A_Bomb");
    frame.setSize(1200, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
