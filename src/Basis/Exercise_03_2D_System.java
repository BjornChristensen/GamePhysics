package Basis;

import java.awt.Graphics;
import javax.swing.*;


public class Exercise_03_2D_System extends JFrame {
    PaintPanel panel=new PaintPanel();

    Exercise_03_2D_System() {
        add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
        S2 s=new S2(40,40,300,350);

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // basisvectors
            s.drawAxis(g);
//            geometry_1a(g);
//            geometry_2c(g);
            geometry_3a(g);
        }

        void geometry_1a(Graphics g)
        {
            // James ex 1.32 a)
            // Equation of line: y=(3/2)x-2
            double x0=0;                // Two points on the line
            double y0=1.5*x0-2;
            double x1=10;
            double y1=1.5*x1-2;
            V2 P0=new V2(x0,y0);
            V2 P1=new V2(x1,y1);
            s.drawLine(g, P0, P1);
        }

        void geometry_2c(Graphics g)
        {
            // James ex 1.35
            V2 C=new V2(-2,3);               // center
            double r=5;                     // radius
            s.drawPoint(g, C);
            for (double phi=0; phi<2*Math.PI; phi+=0.005){
                s.drawPoint(g, C.add(new V2(r*Math.cos(phi), r*Math.sin(phi))));
            }
        }

        void geometry_3a(Graphics g)
        {
            V2 C=new V2(0,0);               // center
            double a=5;
            double b=4;
            for (double phi=0; phi<2*Math.PI; phi+=0.01){
                s.drawPoint(g, C.add(new V2(a*Math.cos(phi), b*Math.sin(phi))));
            }
        }

    } // class PaintPanel



  public static void main(String[] args) {
    System.out.println("Game Physic - Exercise_03_2D_System");
    Exercise_03_2D_System frame = new Exercise_03_2D_System();
    frame.setTitle("Game Physic - Exercise_02_2D_System");
    frame.setSize(1200, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()

} // class MainFrame
