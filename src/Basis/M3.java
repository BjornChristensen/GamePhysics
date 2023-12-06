package Basis;

/*
 * 3D matrix
 */
public class M3 {
    public static M3 I3=new M3( 1,0,0, 0,1,0, 0,0,1 );     // 3x3 unit-matrix
    private double[][] arr=new double[3][3];
    
    public M3(){
        for (int r=0; r<3; r++)
            for (int c=0; c<3; c++)
                arr[r][c]=0;
    }

    public M3(double a11, double a12, double a13, double a21, double a22, double a23, double a31, double a32, double a33){
        arr[0][0]=a11;
        arr[0][1]=a12;
        arr[0][2]=a13;
        arr[1][0]=a21;
        arr[1][1]=a22;
        arr[1][2]=a23;
        arr[2][0]=a31;
        arr[2][1]=a32;
        arr[2][2]=a33;
    }
    
    public M3(V3 r0, V3 r1, V3 r2){     // matrix from 3 row vectors
        for (int c=0; c<3; c++){
            arr[0][c]=r0.arr[c];
            arr[1][c]=r1.arr[c];
            arr[2][c]=r2.arr[c];
        }
    }

    public M3(double[][] a){
        for (int r=0; r<3; r++)
            for (int c=0; c<3; c++)
                arr[r][c]=a[r][c];
    }
    
    public double get(int r, int c){
        return arr[r][c];
    }
    
    public V3 row(int r) {
        return new V3(arr[r][0], arr[r][1], arr[r][2]);
    }
    
    public V3 col(int c) {
        return new V3(arr[0][c], arr[1][c], arr[2][c]);
    }
    
    public M3 transpose(){    // 
        M3 res=new M3();
        for (int r=0; r<3; r++)
            for (int c=0; c<3; c++)
                res.arr[r][c]=arr[c][r];
        return res;
    }
    
    public M3 add(M3 m){    // matrix addition
        M3 res=new M3();
        for (int r=0; r<3; r++)
            for (int c=0; c<3; c++)
                res.arr[r][c]=arr[r][c]+m.arr[r][c];
        return res;
    }
    
    public M3 sub(M3 m){    // matrix subtraction
        M3 res=new M3();
        for (int r=0; r<3; r++)
            for (int c=0; c<3; c++)
                res.arr[r][c]=arr[r][c]-m.arr[r][c];
        return res;
    }
    
    public M3 mul(double k){    // scalar multiplication
        M3 res=new M3();
        for (int r=0; r<3; r++)
            for (int c=0; c<3; c++)
                res.arr[r][c]=k*arr[r][c];
        return res;
    }
    
    public V3 mul(V3 v){        // matrix * vector multiplication
        V3 res=new V3();
        for (int r=0; r<3; r++)
            res.set(r,row(r).dot(v));
        return res;
    }
    
    public M3 mul(M3 m){        // matrix multiplication
        M3 res=new M3();
        for (int r=0; r<3; r++)
            for (int c=0; c<3; c++)
                res.arr[r][c]=row(r).dot(m.col(c));
        return res;
    }
    
    public String toString() {
        String result="(";
        for (int r=0; r<3; r++){
            result+=row(r);
            if (r<2) result+=",";
        }
        result+=")";
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Test M3 - 3x3 matrix");

        M3 A=new M3(1,-1,1, -2,0,3, 0,1,-2);
        M3 B=new M3(0,0,1, 0,2,3, 1,2,3);
        M3 C=new M3(1,2,2, 0,1,1, 1,0,1);
        V3 v=new V3(1,0,1);
        
        System.out.println("A="+A);
        System.out.println("B="+B);
        System.out.println("C="+C);
        System.out.println("A.transpose()="+A.transpose());
        System.out.println("A.add(B)="+A.add(B));
        System.out.println("A.sub(B)="+A.sub(B));
        System.out.println("A.mul(2)="+A.mul(2));
        System.out.println("A.mul(B)="+A.mul(B));
        System.out.println("C.mul(v)="+C.mul(v));

        System.out.println("Change of basis");
        V3 X=new V3(1,1,1);
        V3 E=new V3(0,0,0);
        double phi=Math.PI/4;
        V3 D=new V3(Math.cos(phi),Math.sin(phi),0);
        V3 U=new V3(-Math.sin(phi),Math.cos(phi),0);
        V3 R=D.cross(U);
        M3 M=new M3(D,U,R);
        V3 Y=M.mul(X.sub(E));
        System.out.println("X="+X);
        System.out.println("E="+E);
        System.out.println("D="+D);
        System.out.println("U="+U);
        System.out.println("R="+R);
        System.out.println("M="+M);
        System.out.println("Y="+Y);

        M3 m1=new M3(1,2,3,4,5,6,7,8,9);
        M3 m2=new M3(9,8,7,6,5,4,3,2,1);
        M3 m3;
        final int N=10000000;
        double start=System.currentTimeMillis();
        for (int i=0; i<N; i++){
        	m3=m1.mul(m2);
        }
        double stop=System.currentTimeMillis();
        double dt=(stop-start)/1000;
        System.out.println("dt="+dt+" s");
        System.out.println("N/dt="+(int)(N/dt));
        
        
        
    } // main()
} // M3
