package Basis;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// A WaveFrontElement represents a 3D image in vector graphics.
// It is made up of an array of vertices (points in 3D space)
// and an array of edges (line segments between two points)
// Data is loaded from an .obj Wave Front file.
// The file format is defined here: https://en.wikipedia.org/wiki/Wavefront_.obj_file
public class WaveFrontElement {
    V3[] vertices=new V3[0];
    Edge[] edges=new Edge[0];

    WaveFrontElement(String filename){
        load(filename);
    }

    void load(String filename) {
        ArrayList<V3> vList =new ArrayList<>();
        ArrayList<Edge> eList =new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner input=new Scanner(file);
            while (input.hasNextLine()){
                String line=input.nextLine();
                line = line.replaceAll("  ", " ");
                String[] parts=line.split(" ");

                // A Vertex is a 3D point
                if (parts[0].equals("v")){
                    double x=Double.parseDouble(parts[1]);
                    double y=Double.parseDouble(parts[2]);
                    double z=Double.parseDouble(parts[3]);
                    vList.add(new V3(x,y,z));
                    continue;
                }

                // "l" is a polyLine, "f" is a face (polygon=closed polyline)
                // Example og a face: f 1/1 2/2 3/3
                // The indices are 1-based (first index is 1)
                if (parts[0].equals("l")||parts[0].equals("f")){
                    int n =parts.length-1;                                   // index 0 in parts is "l"
                    for (int i = 1; i<n; i++){                               // for each line segment
                        int a=Integer.parseInt(parts[i].split("/")[0])-1;    // each line segment is defined by two indices a and b to vertices in list
                        int b=Integer.parseInt(parts[i+1].split("/")[0])-1;  // The vertices list is 0-based, so subtract 1
                        eList.add(new Edge(a,b));
                    }
                    if (parts[0].equals("f")){                               // close the polygon
                        int a=Integer.parseInt(parts[n].split("/")[0])-1;    // last vertex
                        int b=Integer.parseInt(parts[1].split("/")[0])-1;    // back to first vertex
                        eList.add(new Edge(a,b));
                    }
                    continue;
                }
            }
            vertices=vList.toArray(vertices);   // Store vertices in an array
            edges=eList.toArray(edges);         // Store edges in an array
        } catch (FileNotFoundException e){ e.printStackTrace(); }
    } // load()

    void draw(Camera c, Graphics g){
        for (Edge e: edges) c.drawLine(g, vertices[e.a], vertices[e.b]);
    }

    // An Edge object represents a line segment of a WavwFront object
    // It has two integers a and b that defines the endpoints.
    // a and b are indices in the list of vertices
    class Edge {
        int a,b;
        Edge(int a, int b){ this.a=a; this.b=b; }
        public String toString() { return "("+a+","+b+")"; }
    } // class Edge

    public static void main(String[] args) {
        WaveFrontElement cube=new WaveFrontElement("src/Basis/cube.obj");
        System.out.println(cube.vertices);
        System.out.println(cube.edges);
    }
}
