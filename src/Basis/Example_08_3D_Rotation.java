package Basis;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Example_08_3D_Rotation extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Example_08_3D_Rotation() {
        add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
        // Animation
        int frameRate=25;                        // No of frames/second
        int frameDelay = 1000/frameRate;         // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Application
        S3 S=new S3(100,100,200,600);
        V3[] cube=new V3[8];
        V3   center=new V3(0,0,0);

        // Rotation matrices for rotating the cube
        double theta=Math.PI/1000;           // Rotation angle stepsize
        M3 I =new M3( 1, 0, 0,              // Identity matrix
                      0, 1, 0,
                      0, 0, 1);
        M3 Sz=new M3( 0,-1, 0,              // xy-plane rotation around z-axis
                      1, 0, 0,
                      0, 0, 0);
        M3 Rz=I.add(Sz.mul(Math.sin(theta)))
                .add(Sz.mul(Sz).mul((1-Math.cos(theta)))); // Eq (2.19)

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){repaint();}
        } // class TimerListener

        PaintPanel() {
            cube[0]=new V3(1,4,1);
            cube[1]=new V3(1,4,3);
            cube[2]=new V3(1,6,1);
            cube[3]=new V3(1,6,3);
            cube[4]=new V3(3,4,1);
            cube[5]=new V3(3,4,3);
            cube[6]=new V3(3,6,1);
            cube[7]=new V3(3,6,3);

            for (int i=0; i<cube.length; i++){
                center=center.add(cube[i]);
            }
            center=center.mul(1.0/8);

            myTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            S.drawAxis(g);
            S.drawLine(g, cube[0], cube[1]);
            S.drawLine(g, cube[1], cube[3]);
            S.drawLine(g, cube[3], cube[2]);
            S.drawLine(g, cube[2], cube[0]);
            S.drawLine(g, cube[4], cube[5]);
            S.drawLine(g, cube[5], cube[7]);
            S.drawLine(g, cube[7], cube[6]);
            S.drawLine(g, cube[6], cube[4]);
            S.drawLine(g, cube[0], cube[4]);
            S.drawLine(g, cube[1], cube[5]);
            S.drawLine(g, cube[3], cube[7]);
            S.drawLine(g, cube[2], cube[6]);

            // Rotate cube
            for (int i=0; i<cube.length; i++){
                cube[i]=cube[i].sub(center);
                cube[i]=Rz.mul(cube[i]);
                cube[i]=cube[i].add(center);
            }
        }
    }
    
  public static void main(String[] args) {
    Example_08_3D_Rotation frame = new Example_08_3D_Rotation();
    frame.setTitle("Example_08_3D_Rotation");
    frame.setSize(1200, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
