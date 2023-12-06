package Basis;

/*
 * 2D Coordinate System
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class S2 {
    private V2 O;                            // position in pixels of Origo on graphical surface
    private M2 F;                            // matrix to Flip y-axis
    private M2 S;                            // transform from unit to pixels
    private M2 T;                            // final transformation matrix

    // sx,sy: length of unit vector i and j in pixels. scale=40 ~ 1 cm on my 17' screen at 1280x1024 reoulution
    // ox,oy: position in pixles of Origo
    public S2(double sx, double sy, int ox, int oy){
        O=new V2(ox,oy);                     // O=(ox,oy)
        S=new M2(sx, 0,
                 0 ,sy);
        F=new M2(1 , 0,
                 0 ,-1);
        T=S.mul(F);                          // T=S*F
    }

    public V2 transform(V2 v){               // Input v in world coordinates
        return T.mul(v).add(O);              // return v in pixels
    }

    public void moveTo(V2 p){
        O=S.mul(F).mul(p).add(O);
    }

    public void rotate(double phi){
        M2 R=new M2(Math.cos(phi), -Math.sin(phi),
                    Math.sin(phi), Math.cos(phi));
        T=T.mul(R);
    }

    public void drawAxis(Graphics g){
        drawLine(g, new V2(0,0), new V2(1,0));              // X-axis
        drawLine(g, new V2(0.9,0.1), new V2(1,0));
        drawLine(g, new V2(0.9,-0.1), new V2(1,0));
        drawString(g, new V2(1.1,0), "x");

        drawLine(g, new V2(0,0), new V2(0,1));              // Y-axis
        drawLine(g, new V2(-0.1,0.9), new V2(0,1));
        drawLine(g, new V2(0.1,0.9), new V2(0,1));
        drawString(g, new V2(0,1.1), "y");
    }
    
    public void drawPoint(Graphics g, V2 v){
        V2 p=transform(v);                // v in pixels
        g.fillOval((int)p.x, (int)p.y, 2, 2);
    }
    public void drawPoint(Graphics g, V2 v, int size){
        V2 p=transform(v);                // v in pixels
        g.fillOval((int)p.x-size/2, (int)p.y-size/2, size, size);
    }
    public void drawPoint(Graphics g, V2 v, Color c){
        Color oldColor = g.getColor();
        g.setColor(c);
        drawPoint(g, v);
        g.setColor(oldColor);
    }
    public void drawPoint(Graphics g, V2 v, Color c, int size){
        Color oldColor = g.getColor();
        g.setColor(c);
        drawPoint(g, v, size);
        g.setColor(oldColor);
    }

    public void drawLine(Graphics g, V2 v1, V2 v2){
        V2 p1=transform(v1);              // v1 in pixels
        V2 p2=transform(v2);              // v2 in pixels
        g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
    }
    public void drawLine(Graphics g, V2 v1, V2 v2, Color c){
        Color oldColor = g.getColor();
        g.setColor(c);
        drawLine(g, v1, v2);
        g.setColor(oldColor);
    }
    public void drawLine(Graphics g, V2 v1, V2 v2, Color c, float weight){
        Stroke line1 = new BasicStroke(1.0f);
        Stroke line2 = new BasicStroke(weight);
        Graphics2D g2=(Graphics2D)g;
        g2.setStroke(line2);
        drawLine(g, v1, v2, c);
        g2.setStroke(line1);
    }

    public void drawCircle(Graphics g, V2 c, double r){
        V2 cp=transform(c);              // c in pixels
        double sx=S.a;                   // Scalingfactor in x-axis
        double sy=S.d;                   // Scalingfactor in y-axis
        double rx=sx*r;                  // Scaled raduis in x-axis
        double ry=sy*r;                  // Scaled raduis in y-axis
        g.drawOval((int)(cp.x-rx), (int)(cp.y-ry), (int)(2*rx), (int)(2*ry));
    }

    public void drawString(Graphics g, V2 pos, String str){
        V2 px=transform(pos);              // v1 in pixels
        g.drawString(str, (int)px.x, (int)px.y);
    }
} // class S2
