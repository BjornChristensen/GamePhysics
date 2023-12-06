package Runi;

public class V2 {
	public double x,y;
	
	V2(double x, double y){
		this.x =x;
		this.y =y;
	}
	
	public V2 add(V2 v){
		return new V2(this.x+v.x,this.y+v.y);  
	}

}
