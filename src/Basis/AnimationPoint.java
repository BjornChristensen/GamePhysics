package Basis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AnimationPoint extends JFrame {
  public AnimationPoint() {
    add(new PaintPanel());
  }

 class PaintPanel extends JPanel {
    // Animation
    int frameRate=25;                // No of frames/second
    int frameDelay = 1000/frameRate; // time between frames in milli sec
    Timer myTimer=new Timer(frameDelay, new TimerListener());
    double t;
    double ts;

    S2 s2=new S2(40,35, 100,500);
    V2 A=new V2(4,2);
    V2 B=new V2(16,5);
    V2 P;
    double s=2;
    V2 r=B.sub(A).unit();
    
    public PaintPanel() {
      ts=System.currentTimeMillis();
      myTimer.start();
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      t=(System.currentTimeMillis()-ts)/1000.0;
      P=A.add(r.mul(s*t));
      if (P.x>=B.x) myTimer.stop();
      
      g.drawString("t="+t, 25, 25);
      s2.drawAxis(g);
      s2.drawLine(g, A, B);
      s2.drawPoint(g, P, 4);
      

    }

    class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        repaint();
      }
    }
  }

  public static void main(String[] args) {
    AnimationPoint frame = new AnimationPoint();
    frame.setTitle("AnimationPoint");
    frame.setSize(1000, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
