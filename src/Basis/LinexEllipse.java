package Basis;

import java.awt.*;
import javax.swing.*;

public class LinexEllipse extends JFrame {

  public LinexEllipse() {
    add(new DrawPanel());
  }

  public static void main(String[] args) {
    LinexEllipse frame = new LinexEllipse();
    frame.setSize(1000, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setTitle("Line x Ellipse");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  class DrawPanel extends JPanel {
    S2 S=new S2(50,50, 100,300);
    V2 c=new V2(4,3);
    double a=3;
    double b=2;
    V2 l1=new V2(0,-5/3.0);
    V2 l2=new V2(10,15/3.0);

    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      S.drawPoint(g, c);
      for (double p=0; p<2*Math.PI; p+=0.01){
        V2 q=new V2(a*Math.cos(p), b*Math.sin(p)).add(c);
        S.drawPoint(g, q);
      }
      S.drawLine(g, l1, l2);
      S.drawAxis(g);
    }
  }
}
