package Basis;

// GamePhysics
// Fitzpatrick chp3: Motion in 2 dimension
// Bjørn Christensen, 5/4-2009
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class Exercise_13_Projectile_Motion extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_13_Projectile_Motion() {
        add(panel);
    }  // Constructor
  
    class PaintPanel extends JPanel {
        S2 S2=new S2(40,40,100,500);
        int frameRate=25;                 // No of frames/second
        int frameDelay = 1000/frameRate;  // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Time
        double t;                         // Current time in seconds
        double t0;                        // Start time in seconds

        // Start conditions
        double g=9.82;
        V2 r0=new V2(0,0);  
        double phi=Math.PI/3;
        double speed=10;
        V2 v0=new V2(Math.cos(phi)*speed,Math.sin(phi)*speed);
        V2 a=new V2(0,-g);


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
            g.drawString("t="+t, 5, 15);
            g.drawString("r="+r, 5, 30);
            g.drawString("v="+v, 5, 45);
            S2.drawAxis(g);
            S2.drawPoint(g, r, Color.BLUE, 5);
            S2.drawPoint(g, r0, Color.BLACK, 5);

            // Stop simulation
            if ( r.y<0 ) myTimer.stop();            // Hit ground
//            if ( v.y<0 ) myTimer.stop();            // Top
        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } // class PaintPanel
    
  public static void main(String[] args) {
    Exercise_13_Projectile_Motion frame = new Exercise_13_Projectile_Motion();
    frame.setTitle("Game Physics - Exercise_13_Projectile_Motion");
    frame.setSize(1200, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
