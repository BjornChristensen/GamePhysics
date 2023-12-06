package Basis;

public class Exercise_07_3D_Vector {

  public static void main(String[] args) {
    System.out.println("Game Physic - Exercise_07_3D_Vector");
//    exercise_4_17();
//    exercise_4_24();
//    exercise_4_31();
    exercise_4_34();
  } // main()

  static void exercise_4_17() {
      System.out.println("James 4.17, p 257");
      V3 u=new V3(4,0,-2);
      V3 v=new V3(3,1,-1);
      V3 w=new V3(2,1,6);
      V3 s=new V3(1,4,1);
      V3 i=new V3(1,0,0);
      V3 k=new V3(0,0,1);

      System.out.println("u="+u);
      System.out.println("v="+v);
      System.out.println("w="+w);
      System.out.println("s="+s);

      System.out.println("a) u*v="+ u.dot(v));
      System.out.println("b) v*s="+ v.dot(s));
      System.out.println("c) w.unit()="+ w.unit());
      System.out.println("d) (v*s)u.unit()="+ u.unit().mul( v.dot(s) ));
      System.out.println("e) (u*w)(v*s)="+ (u.dot(w))*(v.dot(s)));
      System.out.println("f) (u*i)v+(w*s)k="+ v.mul(u.dot(i)).add(k.mul(w.dot(s))));
  }

  static void exercise_4_24() {
      System.out.println("James 4.24, p 257");
      V3 A=new V3(2,3,4);
      V3 B=new V3(1,2,3);
      V3 C=new V3(1,0,2);
      V3 D=new V3(2,3,-2);
      V3 u=A.sub(B);
      V3 v=C.sub(D);

      System.out.println("u*v="+ u.dot(v));
      // Da prik-produktet er 0, de vektorerne u,v ortogonale
  }

  static void exercise_4_31() {
      System.out.println("James 4.31, p 268");
      V3 p=new V3(1,1,1);
      V3 q=new V3(0,-1,2);
      V3 r=new V3(2,2,1);

      System.out.println("p="+p);
      System.out.println("q="+q);
      System.out.println("r="+r);

      System.out.println("a) pxq="+ p.cross(q));
      System.out.println("b) pxq="+ p.cross(r));
      System.out.println("c) rxq="+ r.cross(q));
      System.out.println("d) (pxr)*q="+ (p.cross(r).dot(q)));
      System.out.println("e) q*(pxr)="+ q.dot(r.cross(p)));
      System.out.println("f) (pxr)xq="+ p.cross(r).cross(q));
  }

  static void exercise_4_34() {
      System.out.println("James 4.34, p 268");
      V3 A=new V3(1,-1,2);
      V3 B=new V3(9,0,8);
      V3 C=new V3(5,0,5);

      V3 u=B.sub(A);
      V3 v=C.sub(A);
      System.out.println("a) AB="+ u);
      System.out.println("a) AC="+ v);

      V3 n=u.cross(v).unit();
      System.out.println("b) Nornal-unit vector="+ n);

      System.out.println("c) area ABC="+ u.cross(v).length()/2);
  }

}
