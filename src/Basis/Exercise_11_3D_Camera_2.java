package Basis;

import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.Timer;

public class Exercise_11_3D_Camera_2 extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_11_3D_Camera_2() {   // Constructor
        add(panel);
    }
  
    class PaintPanel extends JPanel {
        // Animation
        int frameRate=25;                // No of frames/second
        int frameDelay = 1000/frameRate; // time between frames in milli sec
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        Camera cr=new Camera(200,200,500,400);      // Coordinatesystem
        Camera cl=new Camera(200,200,500,400);      // Coordinatesystem
        Color coll=Color.RED;
        Color colr=Color.GREEN;

        // Application
        V3[] cube=new V3[8];
        V3   center=new V3(0,0,0);
        M3 Sz=new M3(0,-1, 0,
                     1, 0, 0,
                     0, 0, 0);
        M3 I =new M3(1, 0, 0,
                     0, 1, 0,
                     0, 0, 1);
        double phi=Math.PI/1000;
        M3 Rz=I.add(Sz.mul(Math.sin(phi))).add(Sz.mul(Sz).mul((1-Math.cos(phi))));

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

            cr.moveTo(new V3(8,3,2.2));
            cr.focus(center);
            cl.moveTo(new V3(8,3.2,2.2));
            cl.focus(center);
            
            // Start simulation
            myTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Simulation
            for (int i=0; i<cube.length; i++){
                cube[i]=cube[i].sub(center);
                cube[i]=Rz.mul(cube[i]);
                cube[i]=cube[i].add(center);
            }

            // Draw
            float w=3;
            cr.drawAxis(g);
            cr.drawLine(g, cube[0], cube[1], colr, w);
            cr.drawLine(g, cube[1], cube[3], colr, w);
            cr.drawLine(g, cube[3], cube[2], colr, w);
            cr.drawLine(g, cube[2], cube[0], colr, w);
            cr.drawLine(g, cube[4], cube[5], colr, w);
            cr.drawLine(g, cube[5], cube[7], colr, w);
            cr.drawLine(g, cube[7], cube[6], colr, w);
            cr.drawLine(g, cube[6], cube[4], colr, w);
            cr.drawLine(g, cube[0], cube[4], colr, w);
            cr.drawLine(g, cube[1], cube[5], colr, w);
            cr.drawLine(g, cube[3], cube[7], colr, w);
            cr.drawLine(g, cube[2], cube[6], colr, w);

            cl.drawLine(g, cube[0], cube[1], coll, w);
            cl.drawLine(g, cube[1], cube[3], coll, w);
            cl.drawLine(g, cube[3], cube[2], coll, w);
            cl.drawLine(g, cube[2], cube[0], coll, w);
            cl.drawLine(g, cube[4], cube[5], coll, w);
            cl.drawLine(g, cube[5], cube[7], coll, w);
            cl.drawLine(g, cube[7], cube[6], coll, w);
            cl.drawLine(g, cube[6], cube[4], coll, w);
            cl.drawLine(g, cube[0], cube[4], coll, w);
            cl.drawLine(g, cube[1], cube[5], coll, w);
            cl.drawLine(g, cube[3], cube[7], coll, w);
            cl.drawLine(g, cube[2], cube[6], coll, w);

        }

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){
                repaint();
            }
        }
    } //

    public static void main(String[] args) {
    Exercise_11_3D_Camera_2 frame = new Exercise_11_3D_Camera_2();
    frame.setTitle("Exercise_11_3D_Camera_2");
    frame.setSize(1200, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
