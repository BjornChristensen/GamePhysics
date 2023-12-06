package Basis;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AnimationDemo extends JFrame {
  public AnimationDemo() {
    add(new PaintPanel());
  }

 class PaintPanel extends JPanel {
    // Animation
    Timer myTimer=new Timer(30, new TimerListener());

    private String message = "Welcome to Java";
    private int xCoordinate = 0;
    private int yCoordinate = 20;

    public PaintPanel() {
      myTimer.start();
    }

    public void paintComponent(Graphics g) {
      super.paintComponent(g);

      xCoordinate++;
      if (xCoordinate > getWidth()) {
        xCoordinate = -100;
      }
      g.drawString(message, xCoordinate, yCoordinate);
    }

    class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        repaint();
      }
    }
  }

  public static void main(String[] args) {
    AnimationDemo frame = new AnimationDemo();
    frame.setTitle("AnimationDemo");
    frame.setSize(400, 200);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
