package Runi;

public class M2 {
	public double a,b,
				  c,d;
	
	M2(double a, double b,
	   double c, double d){
		this.a = a;this.b=b;
		this.c = c;this.d=d;
	}
	
	public M2 mul(M2 m){
		return new M2(a*m.a+b*m.c, a*m.b+b*m.d,
					  c*m.a+d*m.c, c*m.b+d*m.d);
	}
	
	public V2 mul(V2 v){
		return new V2(a*v.x+b*v.y,
					  c*v.x+d*v.y);
	}

}
