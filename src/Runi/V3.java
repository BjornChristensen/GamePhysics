package Runi;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.text.DecimalFormat;


public class V3 {

	public double x, y, z;
	
	public V3(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	
	public V3 mult(double c){
		return new V3(this.x*c, this.y*c, this.z*c);
	}
	
	public V3 div(double c){
		return new V3(this.x/c, this.y/c, this.z/c);
	}
	
	public V3 add(V3 v){
		return new V3(this.x+v.x, this.y+v.y, this.z+v.z);
	}
	
	public V3 sub(V3 v){
		return new V3(this.x-v.x, this.y-v.y, this.z-v.z);
	}
	
	public double dot(V3 v){
		return this.x*v.x+this.y*v.y+this.z*v.z;
	}
	
	public V3 cross(V3 v){
		return new V3(	y*v.z	-z*v.y, 
						z*v.x	-x*v.z, 
						x*v.y	-y*v.x);
	}
	
	public V3 unit(){
		return this.div(length());
	}
	
	public double length(){
		return sqrt(pow(this.x, 2)+pow(this.y, 2)+pow(this.z, 2));
	}
	
	public String toString(){
		DecimalFormat df = new DecimalFormat("#.00");
		return "V3(x="+df.format(x)+ ", y="+df.format(y) + ", z=" + df.format(z) + ")";
	}
	
	public V3 copy(){
		return new V3(x,y,z);
	}

}
