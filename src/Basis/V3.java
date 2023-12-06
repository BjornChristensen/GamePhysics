package Basis;

/*
 * 3D vector
 */
public class V3 {
    public static V3 O=new V3();            // Null vector
    double[] arr=new double[3];
    
    public V3(){
        for (int i=0; i<3; i++) arr[i]=0;
    }
    
    public V3(double x, double y, double z){
        arr[0]=x;
        arr[1]=y;
        arr[2]=z;
    }

    public double get(int i){
        return arr[i];
    }
    public double x(){ return arr[0]; }
    public double y(){ return arr[1]; }
    public double z(){ return arr[2]; }

    public void set(int i, double value){
        arr[i]=value;
    }

    public V3 copy(){
        V3 r=new V3();
        for (int i=0; i<3; i++)
            r.arr[i]=arr[i];
        return r;
    }
    
    public double length() {
        double d=0;
        for (int i=0; i<3; i++)
            d+=arr[i]*arr[i];
        return Math.sqrt(d);
    }
    
    public V3 unit() {
        return this.mul(1/length());
    }
    
    public V3 add(V3 v){
        V3 r=new V3();
        for (int i=0; i<3; i++)
            r.arr[i]=arr[i]+v.arr[i];
        return r;
    }
    
    public V3 sub(V3 v){
        V3 r=new V3();
        for (int i=0; i<3; i++)
            r.arr[i]=arr[i]-v.arr[i];
        return r;
    }
    
    public V3 mul(double k){
        V3 r=new V3();
        for (int i=0; i<3; i++)
            r.arr[i]=k*arr[i];
        return r;
    }
    
    public double dot(V3 v){
        double r=0;
        for (int i=0; i<3; i++)
            r+=arr[i]*v.arr[i];
        return r;
    }
    
    public V3 cross(V3 v){
        V3 r=new V3();
        r.arr[0]=arr[1]*v.arr[2]-arr[2]*v.arr[1];
        r.arr[1]=arr[2]*v.arr[0]-arr[0]*v.arr[2];
        r.arr[2]=arr[0]*v.arr[1]-arr[1]*v.arr[0];
        return r;
    }
    

    public String toString() {
        String result="(";
        for (int i=0; i<3; i++){
            result+=String.format("%.3f", arr[i]);
            if (i<2) result+=",";
        }
        result+=")";
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Test V2 - 3D vector");
        V3 a=new V3(1,1,2);
        V3 b=new V3(2,-1,1);
        System.out.println("a="+a);
        System.out.println("a.add(a)="+a.add(a));
        System.out.println("a.sub(a)="+a.sub(a));
        System.out.println("a.mul(3)="+a.mul(3));
        System.out.println("a.mul(-1)="+a.mul(-1));
        System.out.println("a.dot(a)="+a.dot(a));
        System.out.println("a.cross(b)="+a.cross(b));
    } // main()
}
