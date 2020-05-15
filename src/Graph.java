/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 3
 * Description: I took this from lab 13, with some adjustments (added functions mostly)
 */
import java.io.*;
import java.util.*;

public class Graph {
	private int vertexCount, edgeCount;
	boolean directed; // false for undirected graphs, true for directed
	
	//instead of arrays or lists, I created maps to hold the vertices and edges as they all have IDs
	java.util.Map<String, Vertex> verts = new HashMap<>();
	java.util.Map<String, Edge> eds = new HashMap<>();
	final int INFINITY = (int) Double.POSITIVE_INFINITY;
	
	//Since I accidentally created a Map class, I have to specify the java.util.Map above, and this one is the graphics
	Map graphics = null;
	
	//constructor to create a graph from a list of vertices
	public Graph(List<Vertex> verticies, boolean isDirected) {
		directed = isDirected;
		vertexCount = verticies.size();
		for (int i = 0; i < verticies.size(); i++) {
			verts.put(verticies.get(i).ID, verticies.get(i));
		}
	}

	public boolean isDirected() { return directed; }

	public int vertices() { return vertexCount; }

	public int edges() { return edgeCount; }
	
	//insert an Edge
	public void insert(Edge e) {
		
		if (!directed) {
			e.v1.adjList.add(e.v2);
			e.v2.adjList.add(e.v1);
		} else {
			e.v1.adjList.add(e.v2);
		}
		eds.put(e.ID, e);
		edgeCount++;
	}
	
	//calculate distance between 2 vertices
	//I had to look up a formula to turn latitudes and longitudes into miles (it's called haversine)
	public double getDist(String v1ID, String v2ID) {
		Vertex v1 = verts.get(v1ID);
		Vertex v2 = verts.get(v2ID);
		Double latDist = (v2.x-v1.x)*Math.PI/180.0;
		Double lonDist = (v2.y-v1.y)*Math.PI/180.0;
		Double a = Math.sin(latDist/2)*Math.sin(latDist/2) + 
				Math.cos(v1.x*Math.PI/180)*Math.cos(v2.x*Math.PI/180) *
				Math.sin(lonDist/2)*Math.sin(lonDist/2);
		Double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		Double distance = 3958.756*c;
		return distance;
	}

	// Of course, we need an edge class
	public class Edge implements Comparable<Edge>{
		String ID;
		Vertex v1, v2;
		double distance;
		
		//constructor to create an edge
		public Edge(String ID, String v1ID, String v2ID) {
			
			this.ID = ID;
			v1 = verts.get(v1ID);
			v2 = verts.get(v2ID);
			
			//calculate the distance between the 2 vertices of the edge
			Double latDist = (v2.x-v1.x)*Math.PI/180.0;
			Double lonDist = (v2.y-v1.y)*Math.PI/180.0;
			Double a = Math.sin(latDist/2.0)*Math.sin(latDist/2.0) + 
					Math.cos(v1.x*Math.PI/180.0)*Math.cos(v2.x*Math.PI/180.0) *
					Math.sin(lonDist/2)*Math.sin(lonDist/2);
			Double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
			distance = 3958.756*c;
		}
		
		//compareTo to use a priority queue
		@Override
		public int compareTo(Edge o) {
			if (this.distance - o.distance > 0) {
				return 1;
			} else if (this.distance - o.distance < 0) {
				return -1;
			}
			return 0;
		}
		
		public String toString() {
			return ID + ": " + v1.ID + " -> " + v2.ID;
		}
	}
	
	//-DIJKSTRA'S ALGORITHM-//
	public void directions(String start, String end)  {
		
		for (Vertex v : verts.values()) {
			v.dist = INFINITY;
			v.path = null;
			v.known = false;
		}
		
		if (verts.get(start) == null) {
			System.out.println("Wrong input");
			return;
		}
		verts.get(start).dist = 0;
		
		//priority queue helps find the minimum value much faster
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
		queue.add(verts.get(start));
		
		//count to keep track of the number of known vertices
		int count = 0;
		
		//found is true when the end vertex is connected to the start
		boolean found = false;
		
		outerloop:
		while(count != vertices()) {
			
			Vertex temp = queue.poll();
			
			//if there is no more in the queue, meaning the graph is disconnected
			if (temp == null) {
				break;
			}
			
			//filter through the duplicates and unnecessary vertices
			while (temp.known == true) {
				temp = queue.poll();
				if (temp == null) {
					break outerloop;
				}
			}
			temp.known = true;
			
			//assign values to vertices
			for (Vertex v : temp.adjList) {
				if (temp.dist + getDist(temp.ID, v.ID) < v.dist && !v.known) {
					v.dist = temp.dist + getDist(temp.ID, v.ID);
					v.path = temp;
					if (v == verts.get(end)) {
						found = true;
					}
				}
				queue.add(v);
			}
			count++;
		}
		
		//if there is a path
		if (found) {
			Vertex comp = verts.get(end);
			
			//painting
			graphics.path.add(comp);
			while (comp.path != null) {
				comp = comp.path;
				graphics.path.add(comp);
				graphics.repaint();
			}
			
			//printing out the path and distance
			double totalDist = 0;
			for (int i = graphics.path.size()-1; i >= 0; i--) {
				if (i == graphics.path.size()-1) {
					System.out.print("Shortest path: ");
				}
				System.out.print(graphics.path.get(i).ID);
				if (i != 0) {
					totalDist += getDist(graphics.path.get(i).ID, graphics.path.get(i-1).ID);
					System.out.print(" -> ");
				}
			}
			System.out.println();
			System.out.println("Total distance: " + totalDist + " miles");
		} else {
			System.out.println("no path found");
		}
	}
	
	//-KRUSKAL ALGORITHM-//
	//find the root of tree of the vertex
	public Vertex find(Vertex v) {
		return v.tree.root;
	}
	
	public void getMST() {
		for (Vertex v : verts.values()) {
			MST temp = new MST();
			temp.add(v);
			v.tree = temp;	
		}
		
		//again, this helps find the min value
		PriorityQueue<Edge> comp = new PriorityQueue<Edge>();
		for (Edge e : eds.values()) {
			comp.add(e);
		}
		
		for (int i = 0; i < edges(); i++) {
			
			//search for the min edge
			Edge minEdge = comp.poll();
			
			//if not in same tree
			if (find(minEdge.v1) != find(minEdge.v2)) {
				
				//merge the shorter tree with the taller
				if (minEdge.v1.tree.height <= minEdge.v2.tree.height) {
					minEdge.v2.tree.merge(minEdge.v1.tree);
					minEdge.v2.tree.edges.add(minEdge.ID);
				} else {
					minEdge.v1.tree.merge(minEdge.v2.tree);
					minEdge.v1.tree.edges.add(minEdge.ID);
				}
				
				//print all paths crossed
				System.out.println(minEdge);
				
				//painting
				graphics.meridian.add(minEdge.ID);
				graphics.repaint();
			}
		}
	}
}
