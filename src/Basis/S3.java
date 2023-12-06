package Basis;

/*
 * 3D Coordinate System
 * Using orthogonal projection onto 2D system
 */
import java.awt.Color;
import java.awt.Graphics;

public class S3 {
    S2 S2;
    
    // sx,sy: length of unit vector i and j in pixels.
    //        scale=40 ~ 1 cm on my 17' screen at 1280x1024 reoulution
    // ox,oy: position in pixles of Origo
    public S3(int sx, int sy, int ox, int oy){
        S2=new S2(sx,sy, ox,oy);
    }

    // orthogonalProjection onto yz-plane
    private V2 project(V3 v3){
        return new V2(v3.y(),v3.z());
    }

    public void drawAxis(Graphics g){
        S2.drawAxis(g);
    }

    public void drawPoint(Graphics g, V3 A){
        S2.drawPoint(g, project(A));
    }
    
    public void drawPoint(Graphics g, V3 A, int size){
        S2.drawPoint(g, project(A), size);
    }

    public void drawPoint(Graphics g, V3 A, Color c, int size){
        S2.drawPoint(g, project(A), c, size);
    }

    public void drawLine(Graphics g, V3 A, V3 B){
        S2.drawLine(g, project(A), project(B));
    }
} // class S3
