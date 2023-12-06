package Basis;

import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;


public class Example_2D_Animation_Rotating_Square extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Example_2D_Animation_Rotating_Square() {
        add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
        S2 s=new S2(40,40,300,500);
        int frameRate=30;                        // No of frames/second
        int frameDelay = 1000/frameRate;         // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Given Square ABCD
        V2 A=new V2(2,2);
        V2 B=new V2(4,2);
        V2 C=new V2(4,4);
        V2 D=new V2(2,4);
        V2 center=A.add(B).add(C).add(D).mul(1.0/4);

        double phi=Math.PI/100;
        M2 M=new M2(Math.cos(phi), -Math.sin(phi),
                    Math.sin(phi),  Math.cos(phi));

        PaintPanel() {
            myTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            s.drawAxis(g);

            // Draw Square
            s.drawLine(g, A, B);
            s.drawLine(g, B, C);
            s.drawLine(g, C, D);
            s.drawLine(g, D, A);

            // Rotate ABCD by phi
            A=M.mul(A.sub(center)).add(center);
            B=M.mul(B.sub(center)).add(center);
            C=M.mul(C.sub(center)).add(center);
            D=M.mul(D.sub(center)).add(center);
        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } // class PaintPanel

    public static void main(String[] args) {
    Example_2D_Animation_Rotating_Square frame = new Example_2D_Animation_Rotating_Square();
    frame.setTitle("Game Physics - Example_2D_Animation_Rotating_Square");
    frame.setSize(1200, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
