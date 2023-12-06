package Basis;

public class Exercise_10_Virtual_Camera_part_Two {
  public static void main(String[] args) {
    System.out.println("A)");
    Camera2 S=new Camera2(200,200,500,400);
    S.moveTo(new V3(5,4,3));
    S.focus(new V3(2,4,1));
    System.out.println("E="+S.E);
    System.out.println("D="+S.D);
    System.out.println("U="+S.U);
    System.out.println("R="+S.R);
    
    System.out.println("B)");
    V3 Q=new V3(3,2,4);
    System.out.println("Camera coordinates="+S.transform(Q));
    
    System.out.println("C)");
    S.z=0.5;
    System.out.println("Projected coordinates="+S.project(Q));
  }
}