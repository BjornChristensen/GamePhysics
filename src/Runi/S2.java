package Runi;

import java.awt.Graphics;


public class S2 {
	public V2 o;	 //Origo
	public M2 F,S,T; //Flip, Scale, Transform, 
	
	public S2(double sx, double sy, double ox, double oy) {
		o = new V2(ox,oy);
		F = new M2(1, 0,
				   0,-1);
		S = new M2(sx,0,
				   0, sy);
		T = F.mul(S);
	}
	
	public V2 transform(V2 v){
		return T.mul(v).add(o);
	}
	
	public void drawLine(Graphics g,V2 p1, V2 p2){
		V2 p1w = transform(p1);
		V2 p2w = transform(p2);
		g.drawLine((int)p1w.x, (int)p1w.y, (int)p2w.x, (int)p2w.y);
		
	}
	
	public void drawPoint(Graphics g, V2 p){
		V2 pw = transform(p);
		g.fillOval((int)pw.x, (int)pw.y, 2, 2);
	}
	
	public void drawAxes(Graphics g){
		drawLine(g, new V2(0,0), new V2(1,0));
		//Beautify axes		
		drawLine(g, new V2(0,0), new V2(0,1));
	}
	
}
