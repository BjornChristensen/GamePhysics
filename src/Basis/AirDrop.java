package Basis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AirDrop extends JFrame {
	AirDrop() {
		add(new PaintPanel());
	}

	class PaintPanel extends JPanel {
		S2 s = new S2(3, 3, 100, 500);

		// Simulating time
		double t0; 													// Timestamp of simulationstart in sec
		double t = 0; 											// Time in sec since simulation start

		// Animation
		int frameRate = 25; 								// No of frames/second
		int frameDelay = 1000 / frameRate; 	// time between frames in milli sec
		Timer myTimer = new Timer(frameDelay, new TimerListener());

		// Application
		double g=9.82;		// m/(s*s)
		V2 r0=new V2(0, 100);	// m
		V2 v0=new V2(200*1000.0/(60*60), 0);			// m/s
		V2 a=new V2(0, -g);		  // m/(s*s)
		ArrayList<V2> trajectory=new ArrayList<V2>();

		PaintPanel() {
			// Start simulation
			t0 = System.currentTimeMillis() / 1000.0;
			myTimer.start();
		}
		
		V2 r(double t){
//			return 0.5*a*t*t+v0*t+x0;
			return a.mul(0.5*t*t).add(v0.mul(t)).add(r0);
		}
		
		V2 v(double t){
//			return a*t+v0;
			return a.mul(t).add(v0);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Update time
			t = System.currentTimeMillis() / 1000.0 - t0;

			// Simulation
			g.drawString("t="+t, 10, 15);
			g.drawString("x="+r(t), 10, 30);
			g.drawString("v="+v(t), 10, 45);
			trajectory.add(r(t));
			s.drawPoint(g, r0, 10);
			s.drawPoint(g, r(t), 10);
			s.drawLine(g, new V2(0,0), new V2(1000,0));
			s.drawAxis(g);
			for (V2 r:trajectory) s.drawPoint(g, r);

			// Stop simulation
			if (r(t).y<0) myTimer.stop();
		}

		class TimerListener implements ActionListener {
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}
		}
	} // class PaintPanel

	public static void main(String[] args) {
		AirDrop frame = new AirDrop();
		frame.setTitle("Game Physics - AirDrop");
		frame.setSize(1000, 700);
		frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	} // main()

} // class MainFrame
