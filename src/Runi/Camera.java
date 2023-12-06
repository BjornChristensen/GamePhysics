package Runi;

/*
 * 3D Coordinate System
 */
import java.awt.Graphics;


public class Camera {
  V3 O=new V3(0,0,0);
  V3 i=new V3(1,0,0);
  V3 j=new V3(0,1,0);
  V3 k=new V3(0,0,1);
  
  V3 E=new V3(0,0,0);
  V3 D=new V3(1,0,0);
  V3 U=new V3(0,1,0);
  V3 R=new V3(0,0,1);
  
  double z=4;

  S2 s2;
  
    public Camera(double sx, double sy, int ox, int oy){
      s2=new S2(sx, sy, ox, oy);
    }
    
    public V2 project(V3 p){
      V3 EP=p.sub(E);
      double d=EP.dot(D);
      double u=EP.dot(U);
      double r=EP.dot(R);
      double rm=(r/d)*z;
      double um=(u/d)*z;
      return new V2(rm,um);
    }

    void moveTo(V3 p){
      E=new V3(p.x,p.y,p.z);
    }
    
    void focus(V3 p){
      D=p.sub(E).unit();
      R=D.cross(k).unit();
      U=R.cross(D);
    }
    
    public void drawAxes(Graphics g){
      drawLine(g,O,i);
      drawLine(g,O,j);
      drawLine(g,O,k);
    }
    
    public void drawPoint(Graphics g, V3 v){
      s2.drawPoint(g, project(v));
    }

    public void drawLine(Graphics g, V3 v1, V3 v2){
      s2.drawLine(g, project(v1), project(v2));
    }
} // class S3
