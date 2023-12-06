package Basis;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Exercise_11_3D_Camera extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_11_3D_Camera() {
        add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
        // Animation
        int frameRate=25;                        // No of frames/second
        int frameDelay = 1000/frameRate;         // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Application
        Camera3D camera=new Camera3D(200,200,600,400);
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
            System.out.println("center="+center);

            camera.moveTo(new V3(7,6,2));
            camera.focus(center);
            camera.zoom(2);

            System.out.println(camera);
            camera.roll(-30);
//            camera.yaw(-15);
//            camera.pitch(-15);
            System.out.println(camera);


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

            // Draw
            float w=3;
            camera.drawAxis(g, 1, w);
            camera.drawLine(g, cube[0], cube[1], w);
            camera.drawLine(g, cube[1], cube[3], w);
            camera.drawLine(g, cube[3], cube[2], w);
            camera.drawLine(g, cube[2], cube[0], w);
            camera.drawLine(g, cube[4], cube[5], w);
            camera.drawLine(g, cube[5], cube[7], w);
            camera.drawLine(g, cube[7], cube[6], w);
            camera.drawLine(g, cube[6], cube[4], w);
            camera.drawLine(g, cube[0], cube[4], w);
            camera.drawLine(g, cube[1], cube[5], w);
            camera.drawLine(g, cube[3], cube[7], w);
            camera.drawLine(g, cube[2], cube[6], w);

//            int n=7;
//            for (int i=-n; i<=n; i++) camera.drawLine(g, new V3(i,-n,0),  new V3(i,n,0));
//            for (int i=-n; i<=n; i++) camera.drawLine(g, new V3(-n,i,0),  new V3(n,i,0));

        }
    }
    
  public static void main(String[] args) {
    Exercise_11_3D_Camera frame = new Exercise_11_3D_Camera();
    frame.setTitle("Exercise_11_3D_Camera");
    frame.setSize(1000, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame