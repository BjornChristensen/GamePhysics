package Basis;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;


public class Exercise_05_2D_Transformations extends JFrame {
    PaintPanel panel=new PaintPanel();

    Exercise_05_2D_Transformations() {
        add(panel);
    }  // Constructor

    class PaintPanel extends JPanel {
        S2 s=new S2(60,60,100,300);

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // basisvectors
            s.drawAxis(g);
//            exercise_A(g);
//            exercise_B(g);
//            exercise_C(g);
//            exercise_D(g);
//            exercise_E(g);
            exercise_F_Sol_1(g);
//            exercise_F_Sol_2(g);
//            exercise_F_Sol_3(g);
//            exercise_Projection(g);
        }

        void exercise_A(Graphics g)         // Translation
        {
            // Given Triangle ABC
            V2 A=new V2(2,2);
            V2 B=new V2(3,4);
            V2 C=new V2(4,2);
            V2 v=new V2(-1,1);

            // Draw Triangle
            s.drawLine(g, A, B);
            s.drawLine(g, B, C);
            s.drawLine(g, C, A);

            // Translate ABC by v
            A=A.add(v);
            B=B.add(v);
            C=C.add(v);

            // Draw Triangle
            s.drawLine(g, A, B, Color.BLUE);
            s.drawLine(g, B, C, Color.BLUE);
            s.drawLine(g, C, A, Color.BLUE);
        }

        void exercise_B(Graphics g)         // Rotation
        {
            // Given Square ABCD
            V2 A=new V2(2,2);
            V2 B=new V2(4,2);
            V2 C=new V2(4,4);
            V2 D=new V2(2,4);
            V2 center=A.add(B).add(C).add(D).mul(1.0/4);
            double phi=Math.PI/3;
            M2 M=new M2(Math.cos(phi), -Math.sin(phi),
                        Math.sin(phi),  Math.cos(phi));

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

            // Draw Square
            s.drawLine(g, A, B, Color.BLUE);
            s.drawLine(g, B, C, Color.BLUE);
            s.drawLine(g, C, D, Color.BLUE);
            s.drawLine(g, D, A, Color.BLUE);

//            V2 AA=new V2( (5.0+Math.sqrt(3))/2.0 , (5.0-Math.sqrt(3))/2.0 );
//            s.drawPoint(g, AA, 5);

        }

        void exercise_C(Graphics g)     // Reflection
        {
            // Given Triangle ABC
            V2 A=new V2(2,2);
            V2 B=new V2(3,4);
            V2 C=new V2(4,2);
            M2 M=new M2(1,  0,
                        0, -1);

            // Draw Triangle
            s.drawLine(g, A, B);
            s.drawLine(g, B, C);
            s.drawLine(g, C, A);

            // Reflection in x-axis
            A=M.mul(A);
            B=M.mul(B);
            C=M.mul(C);

            // Draw Triangle
            s.drawLine(g, A, B, Color.BLUE);
            s.drawLine(g, B, C, Color.BLUE);
            s.drawLine(g, C, A, Color.BLUE);

            // Restore Triangle ABC
            A=new V2(2,2);
            B=new V2(3,4);
            C=new V2(4,2);

            // Reflection in line y=3
            V2 v=new V2(0,3);
            A=M.mul(A.sub(v)).add(v);
            B=M.mul(B.sub(v)).add(v);
            C=M.mul(C.sub(v)).add(v);

            // Draw Triangle
            s.drawLine(g, A, B, Color.GREEN);
            s.drawLine(g, B, C, Color.GREEN);
            s.drawLine(g, C, A, Color.GREEN);

        }

        void exercise_D(Graphics g)         // Strech
        {
            V2 C=new V2(2,2);               // center
            double r=1;                     // radius

            // Create points on the circle perimeter
            V2[] p=new V2[300];
            for (int i=0; i<p.length; i++){
                double phi=2*Math.PI*i/p.length;
                p[i]=new V2(r*Math.cos(phi), r*Math.sin(phi)).add(C);
            }
            for (int i=0; i<p.length; i++){ s.drawPoint(g, p[i]); }

            // Stretch
            double a=2;
            double b=1.0/2;
            M2 M=new M2(a, 0,
                        0, b);
            for (int i=0; i<p.length; i++){
                p[i]=M.mul(p[i].sub(C)).add(C);
            }
            for (int i=0; i<p.length; i++){ s.drawPoint(g, p[i], Color.BLUE, 2); }
        }

        void exercise_E(Graphics g)     // Skew (Shearing)
        {
            // Given Square ABCD
            V2 A=new V2(2,2);
            V2 B=new V2(4,2);
            V2 C=new V2(4,4);
            V2 D=new V2(2,4);
            V2 P=A;

            double d=1.0/2;
            M2 M=new M2(1, d,           // Eberly 3D, p27
                        0, 1);

            // Draw Square
            s.drawLine(g, A, B);
            s.drawLine(g, B, C);
            s.drawLine(g, C, D);
            s.drawLine(g, D, A);

            // Skew square
            A=M.mul(A.sub(P)).add(P);
            B=M.mul(B.sub(P)).add(P);
            C=M.mul(C.sub(P)).add(P);
            D=M.mul(D.sub(P)).add(P);

            // Draw Square
            s.drawLine(g, A, B, Color.BLUE);
            s.drawLine(g, B, C, Color.BLUE);
            s.drawLine(g, C, D, Color.BLUE);
            s.drawLine(g, D, A, Color.BLUE);
        }

        void exercise_F_Sol_1(Graphics g)         // Reflection in any line
        {
            // Given Triangle ABC
            V2 A=new V2(2,2);
            V2 B=new V2(3,4);
            V2 C=new V2(4,2);
            V2 P=new V2(1,2);
            V2 Q=new V2(2,4);

            // Draw Triangle
            s.drawLine(g, A, B);
            s.drawLine(g, B, C);
            s.drawLine(g, C, A);

            // Draw reflection line
            s.drawLine(g, P, Q);

            // Reflect
            double phi=Math.atan((Q.y-P.y)/(Q.x-P.x));      // Rotation angle
            M2 R1=new M2(Math.cos(-phi), -Math.sin(-phi),   // Rotate to horisont
                         Math.sin(-phi),  Math.cos(-phi));
            M2 R2=new M2(Math.cos(phi), -Math.sin(phi),     // Rotate back
                         Math.sin(phi),  Math.cos(phi));
            M2 M=new M2(1,  0,                              // Flip
                        0, -1);

            V2 As=R2.mul(M.mul(R1.mul(A)));
            V2 Bs=R2.mul(M.mul(R1.mul(B)));
            V2 Cs=R2.mul(M.mul(R1.mul(C)));

            // Draw reflected Triangle
            s.drawLine(g, As, Bs, Color.BLUE);
            s.drawLine(g, Bs, Cs, Color.BLUE);
            s.drawLine(g, Cs, As, Color.BLUE);
        }

        void exercise_F_Sol_2(Graphics g)         // Reflection in any line
        {
            // Given Triangle ABC
            V2 A=new V2(2,2);
            V2 B=new V2(3,4);
            V2 C=new V2(4,2);
            V2 P=new V2(1,2);
            V2 Q=new V2(2,4);

            // Draw Triangle
            s.drawLine(g, A, B);
            s.drawLine(g, B, C);
            s.drawLine(g, C, A);

            // Draw reflection line
            s.drawLine(g, P, Q);
  
            V2 OP=P;
            V2 PQ=Q.sub(P);
            V2 PQu=PQ.unit();
            V2 PA=A.sub(P);
            V2 PB=B.sub(P);
            V2 PC=C.sub(P);

            V2 As=OP.sub(PA).add(PQu.mul(2*(PQu.dot(PA))));
            V2 Bs=OP.sub(PB).add(PQu.mul(2*(PQu.dot(PB))));
            V2 Cs=OP.sub(PC).add(PQu.mul(2*(PQu.dot(PC))));

            // Draw reflected Triangle
            s.drawLine(g, As, Bs, Color.GREEN);
            s.drawLine(g, Bs, Cs, Color.GREEN);
            s.drawLine(g, Cs, As, Color.GREEN);
        }

        void exercise_F_Sol_3(Graphics g)         // Reflection in any line
        {
            // Given Triangle ABC
            V2 A=new V2(2,2);
            V2 B=new V2(3,4);
            V2 C=new V2(4,2);
            V2 P=new V2(1,2);
            V2 Q=new V2(2,4);

            // Draw Triangle
            s.drawLine(g, A, B);
            s.drawLine(g, B, C);
            s.drawLine(g, C, A);

            // Draw reflection line
            s.drawLine(g, P, Q);

            // Transform to new coordinatesystem in P, with x-axis in PQ direction
            V2 ip=Q.sub(P).unit();          // 1st axix in p system
            M2 Mp=new M2(ip.x, ip.y,        // Rotation to p system
                       -ip.y, ip.x);

            M2 Mo=new M2(ip.x, -ip.y,       // Rotation back to original system
                         ip.y, ip.x);

            M2 S=new M2(1,  0,              // Reflection matrix
                        0, -1);

            V2 O=new V2(0,0);               // Origo in old system
            V2 Op=Mp.mul(O.sub(P));         // Same in p-coordinates

            V2 Ap=Mp.mul(A.sub(P));         // A in p-coordinates
            V2 Aps=S.mul(Ap);               // A reflected in p-coordinates
            V2 As=Mo.mul(Aps.sub(Op));      // A reflected in normal coordinates

            V2 Bs=Mo.mul(S.mul(Mp).mul(B.sub(P)).sub(Op));
            V2 Cs=Mo.mul(S.mul(Mp).mul(C.sub(P)).sub(Op));

            // Draw reflected Triangle
            s.drawLine(g, As, Bs, Color.BLUE);
            s.drawLine(g, Bs, Cs, Color.BLUE);
            s.drawLine(g, Cs, As, Color.BLUE);
        }

        void exercise_Projection(Graphics g)
        {
            V2 A=new V2(4,3);
            V2 P=new V2(2,2);
            V2 Q=new V2(3,4);

            s.drawLine(g, P, Q);             // Draw line
            s.drawPoint(g,A,Color.BLUE,6);   // Draw A
  
            V2 OP=P;
            V2 PQ=Q.sub(P);
            V2 PQu=PQ.unit();
            V2 PA=A.sub(P);
            V2 PR=PQu.mul(PQu.dot(PA));
            V2 OR=OP.add(PR);
            s.drawPoint(g,OR,Color.GREEN,6); // Draw R
        }

    } // class PaintPanel


  public static void main(String[] args) {
    System.out.println("Game Physic - Exercise_05_2D_Transformations");
    Exercise_05_2D_Transformations frame = new Exercise_05_2D_Transformations();
    frame.setTitle("Game Physic - Exercise_05_2D_Transformations");
    frame.setSize(500, 350);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()

} // class MainFrame
