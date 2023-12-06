package Basis;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Example_Viewing_Frustrum extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Example_Viewing_Frustrum() {
        add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
        // Animation
        int frameRate=25;                        // No of frames/second
        int frameDelay = 1000/frameRate;         // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Application
        Camera2 s=new Camera2(200,200,500,400);
        V3[] cube=new V3[8];
        V3   center=new V3(0,0,0);

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

            for (int i=0; i<cube.length; i++) center=center.add(cube[i]);
            center=center.mul(1.0/8);

            s.moveTo(new V3(10,5,2));
            s.focus(center);
            s.z=7;

//            myTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Rotate cube
//            for (int i=0; i<cube.length; i++){
//                cube[i]=Rz.mul(cube[i].sub(center)).add(center);
//            }

            // Draw
            s.drawAxis(g, 1, Color.BLUE, 1);
            s.drawLine(g, cube[0], cube[1]);
            s.drawLine(g, cube[1], cube[3]);
            s.drawLine(g, cube[3], cube[2]);
            s.drawLine(g, cube[2], cube[0]);
            s.drawLine(g, cube[4], cube[5]);
            s.drawLine(g, cube[5], cube[7]);
            s.drawLine(g, cube[7], cube[6]);
            s.drawLine(g, cube[6], cube[4]);
            s.drawLine(g, cube[0], cube[4]);
            s.drawLine(g, cube[1], cube[5]);
            s.drawLine(g, cube[3], cube[7]);
            s.drawLine(g, cube[2], cube[6]);
            
            s.yaw(Math.PI/200);
        }
    }
    
  public static void main(String[] args) {
    Example_Viewing_Frustrum frame = new Example_Viewing_Frustrum();
    frame.setTitle("Example_Viewing_Frustrum");
    frame.setSize(1000, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame