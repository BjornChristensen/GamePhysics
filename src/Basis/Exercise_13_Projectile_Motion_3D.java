package Basis;

// GamePhysics
// Fitzpatrick chp3: Motion in 2 dimension
// Bjørn Christensen, 5/4-2009
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;

public class Exercise_13_Projectile_Motion_3D extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_13_Projectile_Motion_3D() {
        add(panel);
    }  // Constructor
  
    class PaintPanel extends JPanel {
        Camera camera=new Camera(100,100,800,400);
        int frameRate=24;                 // No of frames/second
        int frameDelay = 1000/frameRate;  // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Time
        double t;                         // Current time in seconds
        double t0;                        // Start time in seconds

        // Start conditions
        double g=9.81;
        V3 r0=new V3(0,0,0);
        double phi=Math.PI/3;
        double speed=10;
        V3 v0=new V3(0,Math.cos(phi)*speed,Math.sin(phi)*speed);
        V3 a=new V3(0,0,-g);
        ArrayList<V3> trajectory=new ArrayList<V3>();


        PaintPanel() {
            t0=System.currentTimeMillis()/1000.0;
            myTimer.start();
            camera.moveTo(new V3(16,0,1));
            camera.z=12;
        }

        // Position: s=s0+v0*t+½*a*t^2  (3.33)
        V3 r(double t) {
            return r0.add(v0.mul(t)).add(a.mul(t*t/2));
        }

        // Velocity: v=v0+a*t               (3.34)
        V3 v(double t) {
            return v0.add(a.mul(t));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Update time
            t=System.currentTimeMillis()/1000.0-t0;
            
            // Draw
            V3 r=r(t);
            V3 v=v(t);
            trajectory.add(r);
            g.drawString("t="+t, 5, 15);
            g.drawString("r="+r, 5, 30);
            g.drawString("v="+v, 5, 45);
            g.drawString("camera="+camera.E, 5, 60);
//            camera.moveTo(new V3(15,r.y(),1));
            camera.focus(r);
            camera.drawAxis(g);
            camera.drawPoint(g, r, Color.BLUE, 5);
            camera.drawPoint(g, r0, Color.BLACK, 5);
            for (V3 p: trajectory) camera.drawPoint(g, p, Color.BLUE, 5);
            for (int x=-10; x<=10; x++) camera.drawLine(g, new V3(x,-10,0), new V3(x,10,0));
            for (int y=-10; y<=10; y++) camera.drawLine(g, new V3(-10,y,0), new V3(10,y,0));

            // Stop simulation
            if ( r.z()<0 ) myTimer.stop();            // Hit ground
        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } // class PaintPanel
    
  public static void main(String[] args) {
    Exercise_13_Projectile_Motion_3D frame = new Exercise_13_Projectile_Motion_3D();
    frame.setTitle("Game Physics - Exercise_13_Projectile_Motion_3D");
    frame.setSize(1200, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
