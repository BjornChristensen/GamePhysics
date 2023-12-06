package Basis;

import java.awt.Graphics;
import javax.swing.*;

public class Normalfordeling extends JFrame {
  PaintPanel panel=new PaintPanel();
    
  Normalfordeling() {
    add(panel);
  }  // Constructor
  
  class PaintPanel extends JPanel {
    S2 S2=new S2(5,0.002, -400,750);
    int N=1000;  // Antal slag i én serie
    int S=10000000;  // Antal serier
    int[] fordeling=new int[N+1];

    PaintPanel() {
      for (int i=0; i<S; i++) {
        fordeling[serie(N)]++;
      }
    }

    // Slå ét slag med en terning
    int slå(){
      return (int)(Math.random()*6)+1;
    }

    // Lav en serie med n terning slag
    // Returner antal seksere
    int serie(int n){
      int antal_seksere=0;
      for (int i=0; i<n; i++) {
        int slag=slå();
        if (slag==6) antal_seksere++;
      }
      return antal_seksere;
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      S2.drawAxis(g);
      for (int i=0; i<N; i++) S2.drawLine(g, new V2(i,fordeling[i]), new V2(i+1,fordeling[i+1]));
    }
  } // class PaintPanel
    
  public static void main(String[] args) {
    Normalfordeling frame=new Normalfordeling();
    frame.setTitle("Normalfordeling");
    frame.setSize(1000, 800);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  } // main()
  
} // class MainFrame
