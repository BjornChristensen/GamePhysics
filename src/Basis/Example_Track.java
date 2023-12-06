package Basis;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.Timer;

public class Example_Track extends JFrame {
    PaintPanel panel=new PaintPanel();
    
    Example_Track() {
      add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
      // Animation
      int frameRate=20;                        // No of frames/second
      int frameDelay = 1000/frameRate;         // time between frames in milli seconds
      Timer myTimer=new Timer(frameDelay, new TimerListener());

      // Application
      Camera S=new Camera(200,200,500,400);
      ArrayList<V3> pointList=new ArrayList<V3>();
      ArrayList<LineSegment> segmentList=new ArrayList<LineSegment>();
      V3 car=new V3(5,0,0);
      double speed=0.5;
      V3 v=new V3(0,speed,0);   // Velocity

      PaintPanel() {
        // Read track data from file
        try {
          File file=new File("src\\Basis\\track.txt");
          Scanner input=new Scanner(file);
          while (input.hasNextLine()){
            String line=input.nextLine();
            String[] elm=line.split(" ");
            if (elm[0].equals("v")){ // Vertex
              double x=Double.valueOf(elm[1]);
              double y=Double.valueOf(elm[2]);
              double z=Double.valueOf(elm[3]);
              pointList.add(new V3(x,y,z));
            }
            if (elm[0].equals("e")){ // Edge
              int i1=Integer.valueOf(elm[1]);
              int i2=Integer.valueOf(elm[2]);
              segmentList.add(new LineSegment(pointList.get(i1), pointList.get(i2)));
            }
            if (elm[0].equals("e")){
              
            }
          }
        } catch (FileNotFoundException e) {System.out.println(e);}

        S.moveTo(new V3(15,5,10));
        S.focus(new V3(0,0,0));

        myTimer.start();
      }

      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw track
        S.focus(car);
        S.drawAxis(g, 1, Color.BLUE, 1);
        for (LineSegment l: segmentList) S.drawLine(g, l.p1, l.p2);

        // Update and draw car
        V3 carNext=car.add(v);
        for (LineSegment l: segmentList) {  // Check collisions
          if (intersect(l.p1, l.p2, car, carNext)){
            v=l.p2.sub(l.p1).unit().mul(speed);
            return;
          }
        }
        S.drawPoint(g,car, Color.BLUE, 10);
        S.drawPoint(g,carNext, Color.BLUE, 10);
        S.drawLine(g, car, carNext);
        car=carNext;
      }

      // Test if linesegments l1-l2 and m1-m2 intersect
      boolean intersect(V3 l1, V3 l2, V3 m1, V3 m2){
        if (!differentSides(l1,l2, m1,m2)) return false;
        if (!differentSides(m1,m2, l1,l2)) return false;
        return true;
      }
      
      // Test if points p and q are on different sides of line through A and B
      boolean differentSides(V3 A, V3 B, V3 p, V3 q){
        V3 AB=B.sub(A);
        V3 Ap=p.sub(A);
        V3 Aq=q.sub(A);
        return AB.cross(Ap).dot(AB.cross(Aq))<=0;
      }
      
      
      class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){repaint();}
      } // class TimerListener
    }
    
    class LineSegment {
      V3 p1,p2;
      LineSegment(V3 p1, V3 p2) {this.p1=p1; this.p2=p2;}
    }
    
  public static void main(String[] args) {
    Example_Track frame = new Example_Track();
    frame.setTitle("Example_Track");
    frame.setSize(1000, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame