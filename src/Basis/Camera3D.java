// Stereo camera
package Basis;

/*
 * Camera3D
 */
import java.awt.*;

public class Camera3D {
    V3 O=new V3(0,0,0);
    V3 i=new V3(1,0,0);
    V3 j=new V3(0,1,0);
    V3 k=new V3(0,0,1);

    V3 E=new V3(0,0,0);                    // position of camera eye in world coordinates
    V3 R=new V3(1,0,0);                    // 1st-camera-basis vector - Right hand direction - in world coordinates
    V3 U=new V3(0,1,0);                    // 2nd-camera-basis vector - Up direction - in world coordinates
    V3 D=new V3(0,0,1);                    // 3rd-camera-basis vector - Depth direction - in world coordinates

    Camera l;                              // Left camera
    Camera r;                              // Right camera
    double f=0.2;                          // Focal distance between cameras
    Color colr=Color.RED;
//    Color coll=Color.GREEN;
    Color coll=Color.CYAN;

    public Camera3D(int sx, int sy, int ox, int oy){
      l=new Camera(sx,sy, ox,oy);
      r=new Camera(sx,sy, ox,oy);
    }

    void moveTo(V3 p){                     // Move camera Eye to point p
      E=p.copy();
      l.moveTo(E.sub(R.mul(f/2)));
      r.moveTo(E.add(R.mul(f/2)));
    }

    // Set D-axis to point at point p, U-axis to point up and R-axis to point right.
    void focus(V3 p){         // p in wirtual vorld coordinates
      D=p.sub(E).unit();
      R=D.cross(k).unit();
      U=R.cross(D).unit();

      l.moveTo(E.sub(R.mul(f/2)));
      r.moveTo(E.add(R.mul(f/2)));
      l.focus(p);
      r.focus(p);
    }

    void zoom(double zoomFactor){
      r.zoom(zoomFactor);
      l.zoom(zoomFactor);
    }

    // rotate around U-axis
    void yaw(int v){ yaw(2*Math.PI*v/360.0);  }    // v in deg
    void yaw(double phi){                          // phi in rad
      M3 M=new M3(Math.cos(phi),-Math.sin(phi),0, Math.sin(phi), Math.cos(phi),0, 0,0,1);
      R=M.mul(R);
      U=M.mul(U);
      D=M.mul(D);

      l.moveTo(E);
      r.moveTo(E);
      l.yaw(phi);
      r.yaw(phi);
      l.moveTo(E.sub(R.mul(f/2)));
      r.moveTo(E.add(R.mul(f/2)));
    }

    // rotate around D-axis
    void roll(int v){ roll(2*Math.PI*v/360.0);  }   // v in deg
    void roll(double phi){                          // phi in rad
      M3 M=new M3(1,0,0, 0,Math.cos(phi),-Math.sin(phi), 0,Math.sin(phi),Math.cos(phi));
      R=M.mul(R);
      U=M.mul(U);
      D=M.mul(D);

      l.moveTo(E);
      r.moveTo(E);
      l.roll(phi);
      r.roll(phi);
      l.moveTo(E.sub(R.mul(f/2)));
      r.moveTo(E.add(R.mul(f/2)));
    }

    // rotate around R-axis
    void pitch(int v){ pitch(2*Math.PI*v/360.0);  }   // v in deg
    void pitch(double phi){                           // phi in rad
      M3 M=new M3(Math.cos(phi),0,Math.sin(phi), 0,1,0, -Math.sin(phi),0,Math.cos(phi));
      R=M.mul(R);
      U=M.mul(U);
      D=M.mul(D);

      l.moveTo(E);
      r.moveTo(E);
      l.pitch(phi);
      r.pitch(phi);
      l.moveTo(E.sub(R.mul(f/2)));
      r.moveTo(E.add(R.mul(f/2)));
    }

    public void drawAxis(Graphics g, double length, float weight){
      r.drawAxis(g, length, colr, weight);
      l.drawAxis(g, length, coll, weight);
    }

    public void drawPoint(Graphics g, V3 p){
      r.drawPoint(g, p, colr);
      l.drawPoint(g, p, coll);
    }

    public void drawPoint(Graphics g, V3 p, int size){
      r.drawPoint(g, p, colr, size);
      l.drawPoint(g, p, coll, size);
    }

    public void drawLine(Graphics g, V3 p1, V3 p2){
      r.drawLine(g, p1, p2, colr);
      l.drawLine(g, p1, p2, coll);
    }

    public void drawLine(Graphics g, V3 p1, V3 p2, float weight){
      r.drawLine(g, p1, p2, colr, weight);
      l.drawLine(g, p1, p2, coll, weight);
    }

    public String toString(){
      return "R="+R+" U="+U+" D="+D+"\n"+l+"\n"+r+"\n";
    }
} // class Camera

