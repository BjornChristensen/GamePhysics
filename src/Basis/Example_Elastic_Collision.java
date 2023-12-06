package Basis;

// GamePhysics
// Bjørn Christensen, 28/4-2009
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class Example_Elastic_Collision extends JFrame {

  PaintPanel panel = new PaintPanel();

  public static void main(String[] args) {
    Example_Elastic_Collision frame = new Example_Elastic_Collision();
    frame.setTitle("Game Physics - Example_Elastic_Collision");
    frame.setSize(1200, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()

  Example_Elastic_Collision() {
    add(panel);
  }  // Constructor

  class PaintPanel extends JPanel {
    // Coordinatesystem and Animation

    S2 S2 = new S2(10, 10, 100, 600);
    int frameRate = 24;                 // No of frames/second
    int frameDelay = 1000 / frameRate;  // time between frames in milli seconds
    Timer myTimer = new Timer(frameDelay, new TimerListener());

    // Simulation time
    double t = 0;                   // Current time in seconds
    double t0;                    // Start time in seconds

    // Motion
    double r1=8;                        // radius
    V2 p1 = new V2(40,30);              // Position
    V2 v1 = new V2(0,0);                // Velocity
    double r2=1;                        // radius
    V2 p2 = new V2(20,20);              // Position
    V2 v2 = new V2(10,5);               // Velocity
    double g=9.82;
    V2 a = new V2(0,0);                 // acceleration
    boolean col=false;                  // a collision is taking place

    // Box
    double top=50;
    double botttom=0;
    double left=0;
    double right=100;
    V2 tl=new V2(left,top);
    V2 tr=new V2(right,top);
    V2 bl=new V2(left,botttom);
    V2 br=new V2(right,botttom);

    PaintPanel() {
      // Start simulation
      t0 = System.currentTimeMillis() / 1000.0;
      myTimer.start();
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      // Update time
      double tp = t;                                 // time for previus step
      t = System.currentTimeMillis() / 1000.0 - t0;  // Simulation time
      double dt = t - tp;                            // timestep

      // Simulation
      p1 = p1.add(v1.mul(dt));
      v1 = v1.add(a.mul(dt));
      p2 = p2.add(v2.mul(dt));
      v2 = v2.add(a.mul(dt));

      // Draw
      g.drawString("col="+col, 10, 15);
      S2.drawAxis(g);
      S2.drawLine(g, tl, tr);
      S2.drawLine(g, bl, br);
      S2.drawLine(g, tl, bl);
      S2.drawLine(g, tr, br);
      S2.drawCircle(g, p1, r1);
      S2.drawCircle(g, p2, r2);

      // inside box
      if ((p1.x-left<r1) || (right-p1.x<r1)) v1.x=-v1.x;
      if ((p1.y-botttom<r1) || (top-p1.y<r1)) v1.y=-v1.y;
      if ((p2.x-left<r2) || (right-p2.x<r2)) v2.x=-v2.x;
      if ((p2.y-botttom<r2) || (top-p2.y<r2)) v2.y=-v2.y;
      if (p1.sub(p2).length()<(r1+r2)){
        if (!col) {
          V2 v1temp=collision(p1,v1,r1,p2,v2,r2);
          v2=collision(p2,v2,r2,p1,v1,r1);
          v1=v1temp;
          col=true;
        }
      } else col=false;
    }

    V2 collision(V2 p1, V2 v1, double m1, V2 p2, V2 v2, double m2) {
      // Before collision
      V2 c1=p2.sub(p1).unit();
      V2 v1c=c1.mul(v1.dot(c1));
      V2 v1n=v1.sub(v1c);

      V2 c2=p1.sub(p2).unit();
      V2 v2c=c2.mul(v2.dot(c2));
      V2 v2n=v2.sub(v2c);

      // After collision
      V2 u1n=v1n;
      V2 u1c=v1c.mul((m1-m2)/(m1+m2)).add(v2c.mul((2*m2)/(m1+m2)));
      V2 u1=u1c.add(u1n);
      return u1;
    }

    class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent evt) {
        repaint();
      }
    }

  } // class PaintPanel
} // class MainFrame
