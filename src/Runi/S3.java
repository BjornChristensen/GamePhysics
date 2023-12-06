package Runi;

/*

 * 3D Coordinate System
 */
import java.awt.Graphics;

public class S3 {
  S2 s2;
  
    public S3(double sx, double sy, int ox, int oy){
      s2=new S2(sx, sy, ox, oy);
    }
    
    public V2 project(V3 p){
      return new V2(p.y, p.z);
    }

    public void drawAxes(Graphics g){
      s2.drawAxes(g);
    }
    
    public void drawPoint(Graphics g, V3 v){
      s2.drawPoint(g, project(v));
    }

    public void drawLine(Graphics g, V3 v1, V3 v2){
      s2.drawLine(g, project(v1), project(v2));
    }
} // class S3
