package Basis;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Exercise_10_Virtual_Camera extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_10_Virtual_Camera() {
        add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
        // Animation
        int frameRate=25;                        // No of frames/second
        int frameDelay = 1000/frameRate;         // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Application
        Camera camera=new Camera(200,200,500,400);
        V3[] cube=new V3[8];
        V3   center=new V3(0,0,0);
        double r=8;                               // radius of camera rotation
        double phi=0;                             // angle of camera rotation

        // rotation matrices for rotating the cube
        M3 Sz=new M3(0,-1, 0,
                     1, 0, 0,
                     0, 0, 0);
        M3 I =new M3(1, 0, 0,
                     0, 1, 0,
                     0, 0, 1);
        double u=Math.PI/1000;
        M3 Rz=I.add(Sz.mul(Math.sin(u))).add(Sz.mul(Sz).mul((1-Math.cos(u))));

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

            camera.moveTo(new V3(10,5,2));
            camera.focus(center);

            myTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Rotate cube
            for (int i=0; i<cube.length; i++){
                cube[i]=cube[i].sub(center);
                cube[i]=Rz.mul(cube[i]);
                cube[i]=cube[i].add(center);
            }

/*
            // Move camera in circle around cube
            phi+=Math.PI/200;
            V3 p=cube.center().add(new V3(r*Math.cos(phi), r*Math.sin(phi), 0));       // position of camera
            camera.moveTo(p);
            camera.focus(cube.center());
 */

            // Draw
            camera.drawAxis(g, 1, Color.BLUE, 1);
            camera.drawLine(g, cube[0], cube[1]);
            camera.drawLine(g, cube[1], cube[3]);
            camera.drawLine(g, cube[3], cube[2]);
            camera.drawLine(g, cube[2], cube[0]);
            camera.drawLine(g, cube[4], cube[5]);
            camera.drawLine(g, cube[5], cube[7]);
            camera.drawLine(g, cube[7], cube[6]);
            camera.drawLine(g, cube[6], cube[4]);
            camera.drawLine(g, cube[0], cube[4]);
            camera.drawLine(g, cube[1], cube[5]);
            camera.drawLine(g, cube[3], cube[7]);
            camera.drawLine(g, cube[2], cube[6]);
        }
    }
    
  public static void main(String[] args) {
    Exercise_10_Virtual_Camera frame = new Exercise_10_Virtual_Camera();
    frame.setTitle("Exercise_10_Virtual_Camera");
    frame.setSize(1000, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame