package Runi;

import static java.awt.Color.black;
import static java.awt.Color.blue;
import static java.awt.Color.cyan;
import static java.awt.Color.darkGray;
import static java.awt.Color.gray;
import static java.awt.Color.green;
import static java.awt.Color.lightGray;
import static java.awt.Color.orange;

import java.awt.Color;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;




public class FileReader {
	private static Charset ENCODING = StandardCharsets.UTF_8;
	private static int[] axes = new int[]{1,3,2}; // change order of axes. must be between 1 and 3
	public static Color[] colors = new Color[]{
		black, blue, cyan, green.darker(), green.darker(), blue.darker(),
		black, orange, gray, black, blue, blue, cyan, darkGray,
		gray, gray, orange, orange, orange, orange, orange, orange,
		orange, orange, lightGray, lightGray, lightGray	
	};
	
	private static FileReader reader;
	private static FileReader getInstance(){
		if(reader == null) reader = new FileReader();
		return reader;
	}
	public static ArrayList<Edge<V3>> readFile(String pathStr, Color[] colors){
		return getInstance().readFileLocal(pathStr, colors);
	}

	/**
	 * 
	 * @param pathStr path of file to read.
	 * @param colors array of colors for each part. colors[0] is default.
	 * @return
	 */
	public ArrayList<Edge<V3>> readFileLocal(String pathStr, Color[] colors){
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(pathStr);

		ArrayList<V3> vertices = new ArrayList<V3>();
		ArrayList<V3> poly_vertices = new ArrayList<V3>();
		ArrayList<Edge<V3>> edges = new ArrayList<Edge<V3>>();
		int colorInd = 1;
		if(colors == null) colors = new Color[]{Color.black}; // always have a default color
		try (Scanner scanner =  new Scanner(is, ENCODING.name())){ // try with resourceses. closing automatically. 


			while (scanner.hasNextLine()){ // check if there is another line

				String line = scanner.nextLine(); // read next line
				line = line.replaceAll("  ", " ");
				String[] args = line.split(" "); // split at space
				if(args[0].trim().equals("v")){ // vertices have v as first argument
					// x,y,z are at index 1,2,3. index 0 is data type (vertex or edge)
					double x = Double.valueOf(args[axes[0]]);
					double y = Double.valueOf(args[axes[1]]);
					double z = Double.valueOf(args[axes[2]]);

					V3 vertex = new V3(x,y,z);
					vertices.add(vertex);

				}else if(args[0].trim().equals("f")){ // edges have f as first argument followed by index of vertices in list
					int[] vertexIndex = new int[args.length-1];

					for(int i = 1; i <= vertexIndex.length; i++)	vertexIndex[i-1] = Integer.valueOf(args[i].split("/")[0])-1; 
					V3[] curVertices = new V3[vertexIndex.length];
					for(int i = 0; i < vertexIndex.length; i++){
						poly_vertices.add(vertices.get(vertexIndex[i]));
						curVertices[i] = vertices.get(vertexIndex[i]);
					}

					Edge<V3> edge = new Edge<V3>(curVertices);

					try{
						edge.color = colors[colorInd];
					}catch(IndexOutOfBoundsException e){
						edge.color = colors[0];
					}
					edges.add(edge);
				}
				if(line.contains("polygon")){
					colorInd++;
					poly_vertices = new ArrayList<V3>();
				}

			} 
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("failed");
			System.out.println(e.getMessage());
		}

		return edges;
	}

}



