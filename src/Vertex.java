/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 3
 * Description: I took this from lab 13, with an added compareTo to use priority queue
 */
import java.util.*;
import java.util.Map;

public class Vertex implements Comparable<Vertex>{
	
	int index;
	double dist;
	boolean known;
	
	//adjList
	ArrayList<Vertex> adjList = new ArrayList<>();
	
	//for the Kruskal algorithm
	Vertex path;
	MST tree;
	
	String ID;
	double x, y;
	
	public Vertex(int index) {
		this.index = index;
	}
	
	public Vertex(String ID, double x, double y) {
		this.ID = ID;
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return ID + ": " + x + ", " + y;
	}

	@Override
	public int compareTo(Vertex o) {
		if (this.dist - o.dist > 0.0) {
			return 1;
		} else if (this.dist - o.dist < 0.0) {
			return -1;
		}
		return 0;
	}
}
