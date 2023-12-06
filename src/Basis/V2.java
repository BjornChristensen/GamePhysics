package Basis;

/*
 * 2D vector
 */
public class V2 {
    public double x=0;
    public double y=0;
    
    public V2(double x, double y){
        this.x=x;
        this.y=y;
    }
    
    public V2(V2 v){
        this.x=v.x;
        this.y=v.y;
    }
    
    public V2 add(V2 v){
        return new V2(this.x+v.x, this.y+v.y);
    }
    
    public V2 sub(V2 v){
        return new V2(this.x-v.x, this.y-v.y);
    }
    
    public V2 mul(double k){
        return new V2(x*k, y*k);
    }

    public V2 div(double k){
        return new V2(x/k, y/k);
    }

    public double dot(V2 v){
        return x*v.x+y*v.y;
    }

    public double length() {
        return Math.sqrt(x*x+y*y);
    }

    public V2 unit() {
        return this.mul(1/length());
    }

    public boolean equals(V2 v){
        return ((this.x==v.x) && (this.y==v.y));
    }
    
    public boolean near(V2 v, double r){
        return (x-v.x)*(x-v.x)+(y-v.y)*(y-v.y)<=r*r;
    }
       
    public String toString() {
        return "("+x+","+y+")";
    }

    public static void main(String[] args) {
        System.out.println("Test V2 - 2D vector");
        V2 v=new V2(-4,5);
        System.out.println("v="+v);
        System.out.println("v*3="+v.mul(3));

        System.out.println("v.length()*v.length()="+v.length()*v.length());
        System.out.println("v.dot(v)="+v.dot(v));
  } // main()
}
