/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 3
 * Description: the main function
 */
import java.io.*;
import java.util.*;

public class Mapping {
	
	static List<Vertex> vertices = new ArrayList<Vertex>();
	
	public static void main(String[] args) {
		try {
			Scanner s = new Scanner(new File(args[0]));
			
			Graph map = null;
			
			//read in all intersections
			while (s.hasNextLine()) {
				String[] data = s.nextLine().split("\\s+");
				if (data[0].equals("i")) {
					vertices.add(new Vertex(data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3])));
				} else {
					map = new Graph(vertices, false);
					map.insert(map.new Edge(data[1], data[2], data[3]));
					break;
				}
			}
			
			//read in all roads
			while (s.hasNextLine()) {
				String[] data = s.nextLine().split("\\s+");
				if (data[0].equals("r")) {
					map.insert(map.new Edge(data[1], data[2], data[3]));
				}
			}
			
			//show
			if (args.length >= 2 && args[1].equals("[-show]")) {
				map.graphics = new Map(args[0].substring(4), map);
				map.graphics.setVisible(true);
			}
			
			//directions
			if (args.length >= 3 && args[2].equals("[-directions")) {
				map.directions(args[3], args[4].substring(0, args[4].length()-1));
			}
			
			//meridian map
			else if (args.length >= 3 && args[2].equals("[-meridianmap]")) {
				map.getMST();
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
	}
}
