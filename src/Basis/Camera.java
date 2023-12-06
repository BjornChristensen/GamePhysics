package Basis;

/*
 * Camera
 */
import java.awt.*;

public class Camera {
    V3 O=new V3(0,0,0);
    V3 i=new V3(1,0,0);
    V3 j=new V3(0,1,0);
    V3 k=new V3(0,0,1);

    V3 E=new V3(0,0,0);  // position of camera eye - in world coordinates
    V3 D=new V3(1,0,0);  // 1st-camera-basis vector - Depth direction - in world coordinates
    V3 U=new V3(0,1,0);  // 2nd-camera-basis vector - Up direction - in world coordinates
    V3 R=new V3(0,0,1);  // 3rd-camera-basis vector - Right hand direction - in world coordinates
    double z=4;          // distance from E to projectionplane, in D direction, in camera coordinates
    
    S2 screen;           // 2D projectionplane

    public Camera(int sx, int sy, int ox, int oy){
        screen=new S2(sx,sy, ox,oy);
    }

    V2 project(V3 P){
        V3 EP=P.sub(E);             // P relative to Eye in world coordinates
        double d=D.dot(EP);         // Transform P to Camera coordinates
        double u=U.dot(EP);
        double r=R.dot(EP);
        double rm=r*z/d;            // Projected coordinates
        double um=u*z/d;
        return new V2(rm,um);
    }

    void moveTo(V3 p){                     // Move camera Eye to point p
        E=p.copy();
    }

    void translate(V3 v){                  // Translate camera Eye by vector v
        E=E.add(v);
    }

    // Set D-axis points at point p, U-axis points up and R-axis points right.
    void focus(V3 p){
        D=p.sub(E).unit();
        R=D.cross(k).unit();
        U=R.cross(D).unit();
    }

    void zoom(double zoomFactor){
        z=zoomFactor;
    }

    // rotate around U-axis
    void yaw(int v){ yaw(2*Math.PI*v/360.0);  }   // v in deg
    void yaw(double phi){                         // phi in rad
      M3 I =new M3( 1, 0, 0,                      // Identity matrix
                    0, 1, 0,
                    0, 0, 1);
      M3 Su=new M3(     0, -U.z(),  U.y(),      // rotation around u-vector
                    U.z(),      0, -U.x(),
                   -U.y(),  U.x(),     0 );
      M3 Ru=I.add(Su.mul(Math.sin(phi))).add(Su.mul(Su).mul((1-Math.cos(phi))));
      D=Ru.mul(D);
      // U=Ru.mul(U);  Not changed
      R=Ru.mul(R);
    }

    // rotate around D-axis
    void roll(int v){ roll(2*Math.PI*v/360.0);  }   // v in deg
    void roll(double phi){                          // phi in rad
      M3 I =new M3( 1, 0, 0,                        // Identity matrix
                    0, 1, 0,
                    0, 0, 1);
      M3 Su=new M3(     0, -D.z(),  D.y(),        // rotation around u-vector
                    D.z(),      0, -D.x(),
                   -D.y(),  D.x(),     0 );
      M3 Ru=I.add(Su.mul(Math.sin(phi))).add(Su.mul(Su).mul((1-Math.cos(phi))));
      // D=Ru.mul(D); Not changed
      U=Ru.mul(U);
      R=Ru.mul(R);
    }
    
    // rotate around R-axis
    void pitch(int v){ pitch(2*Math.PI*v/360.0);  }   // v in deg
    void pitch(double phi){                           // phi in rad
      M3 I =new M3( 1, 0, 0,                          // Identity matrix
                    0, 1, 0,
                    0, 0, 1);
      M3 Su=new M3(     0, -R.z(),  R.y(),          // rotation around u-vector
                    R.z(),      0, -R.x(),
                   -R.y(),  R.x(),     0 );
      M3 Ru=I.add(Su.mul(Math.sin(phi))).add(Su.mul(Su).mul((1-Math.cos(phi))));
      D=Ru.mul(D);
      U=Ru.mul(U);
      // R=Ru.mul(R); Not changed
    }

    public void drawAxis(Graphics g){
        // World origo and basis vectors in world coordinates
        drawLine(g, O, i, Color.BLUE);
        drawLine(g, O, j, Color.GREEN);
        drawLine(g, O, k);
    }

    public void drawAxis(Graphics g, double length, Color c, float weight){
        // World origo and basis vectors in world coordinates
        drawLine(g, O, i.mul(length), c, weight);
        drawLine(g, O, j.mul(length), c, weight);
        drawLine(g, O, k.mul(length), c, weight);
        drawString(g, i.mul(length+0.1), "x");
        drawString(g, j.mul(length+0.1), "y");
        drawString(g, k.mul(length+0.1), "z");
    }

    public void drawPoint(Graphics g, V3 p){
        screen.drawPoint(g, project(p));
    }

    public void drawPoint(Graphics g, V3 p, Color c){
        screen.drawPoint(g, project(p), c);
    }

    public void drawPoint(Graphics g, V3 p, Color c, int size){
        screen.drawPoint(g, project(p), c, size);
    }

    public void drawLine(Graphics g, V3 p1, V3 p2){
        screen.drawLine(g, project(p1), project(p2));
    }

    public void drawLine(Graphics g, V3 p1, V3 p2, Color c){
        screen.drawLine(g, project(p1), project(p2), c);
    }

    public void drawLine(Graphics g, V3 p1, V3 p2, Color c, float weight){
        screen.drawLine(g, project(p1), project(p2), c, weight);
    }
    
    public void drawString(Graphics g, V3 pos, String str){
      screen.drawString(g, project(pos), str);
    }
} // class Camera

