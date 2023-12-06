package Basis;

// GamePhysics
// Bjørn Christensen, 23/4-2009
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class Exercise_15_Human_Cannonball extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_15_Human_Cannonball() {
        add(panel);
    }  // Constructor
  
    class PaintPanel extends JPanel {
        S2 S2=new S2(10,10,100,500);
        int frameRate=24;                 // No of frames/second
        int frameDelay = 1000/frameRate;  // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Time
        double t;                         // Current time in seconds
        double t0;                        // Start time in seconds

        // Start conditions
        double g=9.81;                    // Gravity
        V2 r0=new V2(0,0);                // Start position
        double phi=Math.PI/3;             // Firing angle
        double speed=112/3.6;             // Initial speed = 112 km/h, in m/s
        V2 v0=new V2(Math.cos(phi)*speed,Math.sin(phi)*speed); // Init velocity
        V2 a=new V2(0,-g);

        double p=0.15;                    // Drag coefficient
        double vt=g/p;                    // Terminal velocity

        PaintPanel() {
            t0=System.currentTimeMillis()/1000.0;
            myTimer.start();
            System.out.println("s"+speed);
        }

        // Position - without air resistance: s=s0+v0*t+½*a*t^2  (3.33)
        V2 r(double t) {
            return r0.add(v0.mul(t)).add(a.mul(t*t/2));
        }

        // Velocity - without air resistance: v=v0+a*t               (3.34)
        V2 v(double t) {
            return v0.add(a.mul(t));
        }

        // Position - with air resistance
        V2 ra(double t) {
            V2 v=new V2(0,0);
            v.x=r0.x+v0.x/p*(1-Math.exp(-p*t));
            v.y=r0.y-vt*t+(v0.y+vt)*(1-Math.exp(-p*t))/p;            // (17)
            return v;
        }

        // Velocity - with air resistance
        V2 va(double t) {
            V2 v=new V2(0,0);
            v.x=v0.x*Math.exp(-p*t);
            v.y=(v0.y+vt)*Math.exp(-p*t)-vt;                       // (16)
            return v;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Update time
            t=System.currentTimeMillis()/1000.0-t0;
            
            // Draw
            V2 r=r(t);          // position - without air resistance
            V2 v=v(t);          // velocity - without air resistance
            V2 ra=ra(t);        // position - with air resistance
            V2 va=va(t);        // velocity - with air resistance
            g.drawString("t ="+t,  5, 15);
            g.drawString("ra="+ra, 5, 30);
            g.drawString("va="+va, 5, 45);
            g.drawString("p ="+p,  5, 60);
            g.drawString("t ="+t,  500, 15);
            g.drawString("r="+r,   500, 30);
            g.drawString("v="+v,   500, 45);
            S2.drawAxis(g);
            S2.drawPoint(g, r, Color.BLACK, 5);     // without air resistance
            S2.drawPoint(g, ra, Color.BLUE, 5);     // with air resistance

            // Stop simulation
//            if ( ra.y<0 ) myTimer.stop();            // Hit ground
            if ( r.y<0 ) myTimer.stop();            // Hit ground
        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } // class PaintPanel
    
  public static void main(String[] args) {
    Exercise_15_Human_Cannonball frame = new Exercise_15_Human_Cannonball();
    frame.setTitle("Game Physics - Exercise_15_Human_Cannonball");
    frame.setSize(1200, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
