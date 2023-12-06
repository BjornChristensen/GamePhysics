package Basis;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Exercise_09_3D_Rotation extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_09_3D_Rotation() {
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
        double theta=Math.PI/100;           // Rotation angle stepsize
        M3 I =new M3( 1, 0, 0,              // Identity matrix
                      0, 1, 0,
                      0, 0, 1);
        M3 Sz=new M3( 0,-1, 0,              // xy-plane rotation around z-axis
                      1, 0, 0,
                      0, 0, 0);
        M3 Sy=new M3( 0, 0, 1,              // xz-plane rotation around y-axis
                      0, 0, 0,
                     -1, 0, 0);
        M3 Sx=new M3( 0, 0, 0,              // yz-plane rotation around x-axis
                      0, 0,-1,
                      0, 1, 0);
        V3 u=new V3(1,2,3).unit();          // Rotation axis
        M3 Su=new M3(     0, -u.z(),  u.y(),      // rotation around u-vector
                      u.z(),      0, -u.x(),
                     -u.y(),  u.x(),     0 );

        M3 Rz=I.add(Sz.mul(Math.sin(theta))).add(Sz.mul(Sz).mul((1-Math.cos(theta)))); // Eq (2.19)
        M3 Ry=I.add(Sy.mul(Math.sin(theta))).add(Sy.mul(Sy).mul((1-Math.cos(theta)))); // Eq (2.20)
        M3 Rx=I.add(Sx.mul(Math.sin(theta))).add(Sx.mul(Sx).mul((1-Math.cos(theta)))); // Eq (2.21)
        M3 Ru=I.add(Su.mul(Math.sin(theta))).add(Su.mul(Su).mul((1-Math.cos(theta)))); // Eq (2.22)

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
                cube[i]=Ru.mul(cube[i]);
                cube[i]=cube[i].add(center);
            }
        }
    }
    
  public static void main(String[] args) {
    Exercise_09_3D_Rotation frame = new Exercise_09_3D_Rotation();
    frame.setTitle("Exercise_09_3D_Rotation");
    frame.setSize(1200, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
