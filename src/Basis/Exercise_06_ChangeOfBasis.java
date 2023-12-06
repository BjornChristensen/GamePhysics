package Basis;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;


public class Exercise_06_ChangeOfBasis extends JFrame {
    PaintPanel panel=new PaintPanel();

    Exercise_06_ChangeOfBasis() {
        add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
        S2 fixed=new S2(40,40,500,500);
        S2 swing=new S2(40,40,500,500);
        V2 R=new V2(5,7);
        double l=5;
        double phi=Math.PI/4;
        V2 Om=R.add(new V2(Math.sin(phi), -Math.cos(phi)).mul(l));
        
        PaintPanel(){
          swing.moveTo(Om);
          swing.rotate(phi);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            fixed.drawAxis(g);
            fixed.drawLine(g, R, Om);
            fixed.drawPoint(g, new V2(5+4*Math.sqrt(2),7), Color.YELLOW, 7);
            
            swing.drawAxis(g);
            swing.drawPoint(g, new V2(4,1), 3);
        }

    } // class PaintPanel



  public static void main(String[] args) {
    System.out.println("Game Physic - Exercise_06_ChangeOfBasis");
    Exercise_06_ChangeOfBasis frame = new Exercise_06_ChangeOfBasis();
    frame.setTitle("Game Physic - Exercise_06_ChangeOfBasis");
    frame.setSize(1200, 700);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()

} // class MainFrame
