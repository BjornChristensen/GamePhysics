package Basis;// Liang listing 14.3

import javax.swing.*;
import java.awt.Graphics;

public class TestPanelDrawing extends JFrame {

  public TestPanelDrawing() {
    add(new DrawPanel());
  }

  public static void main(String[] args) {
    TestPanelDrawing frame = new TestPanelDrawing();
    frame.setTitle("Basis.TestPanelDrawing");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(200, 100);
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setVisible(true);
  }

  class DrawPanel extends JPanel {

    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawLine(0, 0, 50, 50);
      g.drawString("Banner", 0, 40);
    }
  }

}
