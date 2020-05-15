/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 3
 * Description: a simple 'tree' class that I created to use the Kruskal algorithm
 */
import java.util.ArrayList;

public class MST {
	
	//root and the height
	Vertex root;
	int height = 0;
	
	//holds the edges and vertices connected to this tree
	ArrayList<String> edges = new ArrayList<>();
	ArrayList<Vertex> vertices = new ArrayList<>();
	
	public void add(Vertex x) {
		root = x;
		x.tree = this;
		vertices.add(x);
		height++;
	}
	
	//merge function to merge two trees
	public void merge(MST o) {
		o.root = this.root;
		height += o.height;
		edges.addAll(o.edges);
		vertices.addAll(o.vertices);
		for (int i = 0; i < o.vertices.size(); i++) {
			o.vertices.get(i).tree = this;
		}
	}
}
