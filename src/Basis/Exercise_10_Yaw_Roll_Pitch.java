package Basis;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Exercise_10_Yaw_Roll_Pitch extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Exercise_10_Yaw_Roll_Pitch() {
        add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
        // Animation
        int frameRate=25;                        // No of frames/second
        int frameDelay = 1000/frameRate;         // time between frames in milli seconds
        Timer myTimer=new Timer(frameDelay, new TimerListener());

        // Application
        Camera S=new Camera(200,200,500,400);
        V3 P=new V3(2,2,2);

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent evt){repaint();}
        } // class TimerListener


        PaintPanel() {
            S.moveTo(new V3(8,2,2));
            S.D=new V3(-1,0,0);
            S.U=new V3( 0,-Math.sqrt(2)/2,Math.sqrt(2)/2);
            S.R=new V3( 0, Math.sqrt(2)/2,Math.sqrt(2)/2);

            myTimer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw
            S.drawAxis(g, 1, Color.BLUE, 1);
            S.drawPoint(g, P, Color.red, 10);

//          S.yaw(Math.PI/500);
//            S.roll(Math.PI/500);
            S.pitch(Math.PI/500);

            System.out.println("D="+S.D+" ("+S.D.length()+")");
            System.out.println("U="+S.U+" ("+S.U.length()+")");
            System.out.println("R="+S.R+" ("+S.R.length()+")");

        }
    }
    
  public static void main(String[] args) {
    Exercise_10_Yaw_Roll_Pitch frame = new Exercise_10_Yaw_Roll_Pitch();
    frame.setTitle("Exercise_10_Yaw_Roll_Pitch");
    frame.setSize(1000, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame