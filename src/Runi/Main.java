package Runi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Main {

	public static void main(String[] args) {
		new Main().run();
	}
	
	public void run(){
		
		JFrame frame1 = new JFrame();
		frame1.setSize(800, 500);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.add(new DrawPanel(new V3(10,5,2), 400,300,100,1));
		frame1.setVisible(true);
		
/*		JFrame frame2 = new JFrame();
		frame2.setSize(800, 500);
		frame2.setLocation(300, 0);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.add(new DrawPanel(new V3(1000,5,2), 400,300,100,2));	
		frame2.setVisible(true);
*/		
//		JFrame frame3 = new JFrame();
//		frame3.setSize(800, 500);
//		frame3.setLocation(300, 0);
//		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame3.add(new DrawPanel(new V3(5,5,2), 400,300,100,3));	
//		frame3.setVisible(true);
	}
	
	class DrawPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		// Animation
		int framerate=25;
		int delay=1000/framerate;
		Timer myTimer=new Timer(delay, new TimerListener());

		// App
		Camera S;
		M3 I=new M3(1, 0, 0,
				0, 1, 0,
				0, 0, 1);
		M3 Sz=new M3(0, -1, 0,
				1,  0, 0,
				0,  0, 0);
		double phi=Math.PI/500;
		M3 Rz=I.add(Sz.mul(Math.sin(phi))).add(Sz.mul(Sz).mul(1-Math.cos(phi)));
		V3 c=new V3(0,0,0);
		ArrayList<Edge<V3>> model;
						
	
//		ArrayList<Edge<V3>> model = FileReader.readFile("Parachute.obj", FileReader.colors);
//		V3 campoint = new V3(1000,5,2);
		
		public DrawPanel(V3 campoint, int ox, int oy, double scale, int modelno) {
			/*
			switch(modelno){
			case 1:
				model = FileReader.readFile("src//uni/Warsong Attack Helicopter.obj", null);
			break;
			case 2:
				model = FileReader.readFile("src/Runi/Parachute.obj", null);
				break;
			case 3:
				model = FileReader.readFile("src/Runi/HQ_Movie cycle.obj",null);
				break;
			default:
				model = FileReader.readFile("src/Runi/Warsong Attack Helicopter.obj", null);
				break;
			}

			 */

			model = FileReader.readFile("src/Runi/Warsong Attack Helicopter.obj", null);
			model = FileReader.readFile("src/Runi/cube.obj", null);
			model = FileReader.readFile("src/Runi/Football.obj", null);
			model = FileReader.readFile("src/Runi/Parachute.obj", null);
			S =new Camera(scale, scale, ox, oy);
			S.moveTo(campoint);
		      S.focus(c);
			S.z=0.1;
//			S.z=200;
			myTimer.start();
		}

		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			for(Edge<V3> e : model){
				for (int i=0; i<e.vertices.length; i++){
					e.vertices[i]=Rz.mul(e.vertices[i].sub(c)).add(c);
				}
			}
			S.focus(c);
			for(Edge<V3> e : model) e.draw(S, g);
			S.drawAxes(g);
		};
		class TimerListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		}
	}
}
