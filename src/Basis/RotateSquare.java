package Basis;

import javax.swing.*;
import java.awt.Graphics;

public class RotateSquare extends JFrame {

  public RotateSquare() {
    add(new DrawPanel());
  }

  public static void main(String[] args) {
    RotateSquare frame = new RotateSquare();
    frame.setTitle("Basis.RotateSquare");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setVisible(true);
  }

  class DrawPanel extends JPanel {
    S2 S=new S2(50,50, 200,400);
    V2 A=new V2(2,2);
    V2 B=new V2(4,2);
    V2 C=new V2(4,4);
    V2 D=new V2(2,4);
    V2 P=A.add(B).add(C).add(D).mul(1.0/4);
    double phi=Math.PI/3;
    M2 M=new M2(Math.cos(phi), -Math.sin(phi),
                Math.sin(phi),  Math.cos(phi));
    V2 Am=M.mul(A.sub(P)).add(P);
    V2 Bm=M.mul(B.sub(P)).add(P);
    V2 Cm=M.mul(C.sub(P)).add(P);
    V2 Dm=M.mul(D.sub(P)).add(P);

    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      S.drawAxis(g);
      S.drawLine(g, A, B);
      S.drawLine(g, B, C);
      S.drawLine(g, C, D);
      S.drawLine(g, D, A);
      S.drawPoint(g, P);
      S.drawLine(g, Am, Bm);
      S.drawLine(g, Bm, Cm);
      S.drawLine(g, Cm, Dm);
      S.drawLine(g, Dm, Am);
    }
  }

}
