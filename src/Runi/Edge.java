package Runi;

import java.awt.Color;
import java.awt.Graphics;

public class Edge<T> {

	public T[] vertices;
	public Color color = Color.black;
	public int stroke = 1;
	public boolean fill = false;
	
	@SafeVarargs
	public Edge(T... vertices){
		this(Color.black, 1, false, vertices);
	}
	@SafeVarargs
	public Edge(Color color, int stroke, boolean fill, T... vertices){
		this.vertices = vertices;
		this.color = color;
		this.stroke = stroke;
		this.fill = fill;
	}
	
	public void draw(Camera cam, Graphics g){
		if(this.getClass().isInstance(new Edge<V3>(new V3(0,0,0)))){
			V3[] vertices = (V3[])this.vertices;
			for(int i = 1; i < vertices.length; i++){
				cam.drawLine(g, vertices[i-1], vertices[i]);
			}
		}
	}
	
	@Override
	public String toString(){
		String s = "Edge{";
		for(T v : vertices){
			s += (v + "\t");
		}
		return s + "}";
	}
	
}
