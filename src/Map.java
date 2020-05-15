/* Name: Quan Luu
 * Student ID: 31529099
 * NetID: qluu2
 * Lab section: MW 6h15 - 7h30
 * Project: 3
 * Description: The graphics class
 */
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map extends JFrame{
	
	Canvas canvas;
	ArrayList<Vertex> path = new ArrayList<>();
	ArrayList<String> meridian = new ArrayList<>();
	
	public Map(String name, Graph map) {
		setTitle(name);
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = new Canvas(map);
		add(canvas);
	}
	
	//add the directions
	public void highlight(ArrayList<Vertex> path) {
		this.path = path;
	}
	
	//add the meridian
	public void meridian(ArrayList<String> path) {
		meridian = path;
	}
	
	public class Canvas extends JPanel{
		
		Graph map;
		
		//holds the min max values of the latitudes and longitudes of the vertices
		double x1, x2, y1, y2;
		
		public Canvas(Graph map) {
			this.map = map;
			
			//assign values to x1, x2, y1, y2
			Iterator<Vertex> it = map.verts.values().iterator();
			Vertex begin = it.next();
			x1 = begin.x;
			x2 = begin.x;
			y1 = begin.y;
			y2 = begin.y;
			while (it.hasNext()) {
				Vertex temp = it.next();
				if (temp.x < x1) {
					x1 = temp.x;
				} else if (temp.x > x2) {
					x2 = temp.x;
				}
				if (temp.y < y1) {
					y1 = temp.y;
				} else if (temp.y > y2) {
					y2 = temp.y;
				}
			}
		}
		
		public void paintComponent(Graphics g) {
			
			//some math to get the graph to stay inside the size of the frame
			double scaleX = (getHeight()-100)/(x2-x1);
			double scaleY = (getWidth()-100)/(y2-y1);
			double diffX = x2;
			double diffY = (-y1);
			
			//you will see that the draw functions is using y_1 and y_2 as x-coordinates and x_1 and x_2 as
			//y-coordinates. Well this is just do that I can make the graphic look like the ones in the homework
			//assignment. I don't exactly know why but it does the job, so..
			for (Graph.Edge e : map.eds.values()) {
				int x_1 = 50 - (int) ((e.v1.x - diffX)*scaleX);
				int y_1 = 50 - (int) ((-e.v1.y - diffY)*scaleY);
				int x_2 = 50 - (int) ((e.v2.x - diffX)*scaleX);
				int y_2 = 50 - (int) ((-e.v2.y - diffY)*scaleY);
				g.drawLine(y_1, x_1, y_2, x_2);
			}
			
			//if there is a path to be painted
			if (path != null) {
				g.setColor(Color.BLUE);
				for (int i = 0; i < path.size()-1; i++) {
					int x_1 = 50 - (int) ((path.get(i).x - diffX)*scaleX);
					int y_1 = 50 - (int) ((-path.get(i).y - diffY)*scaleY);
					int x_2 = 50 - (int) ((path.get(i+1).x - diffX)*scaleX);
					int y_2 = 50 - (int) ((-path.get(i+1).y - diffY)*scaleY);
					g.drawLine(y_1, x_1, y_2, x_2);
					
					//highlight the two end points
					if (i == 0) {
						g.setColor(Color.RED);
						g.drawString(path.get(i).ID, y_1, x_1 - 5);
						g.fillOval(y_1 - 4, x_1 - 4, 8, 8);
						g.setColor(Color.BLUE);
					}
					
					if (i == path.size()-2) {
						g.setColor(Color.RED);
						g.drawString(path.get(i+1).ID, y_2, x_2 - 5);
						g.fillOval(y_2 - 4, x_2 - 4, 8, 8);
						g.setColor(Color.BLUE);
					}
				}
				g.setColor(Color.BLACK);
			}
			
			//if there is a meridian map to be painted
			if (meridian != null) {
				g.setColor(Color.BLUE);
				for (int i = 0; i < meridian.size(); i++) {
					int x_1 = 50 - (int) ((map.eds.get(meridian.get(i)).v1.x - diffX)*scaleX);
					int y_1 = 50 - (int) ((-map.eds.get(meridian.get(i)).v1.y - diffY)*scaleY);
					int x_2 = 50 - (int) ((map.eds.get(meridian.get(i)).v2.x - diffX)*scaleX);
					int y_2 = 50 - (int) ((-map.eds.get(meridian.get(i)).v2.y - diffY)*scaleY);
					g.drawLine(y_1, x_1, y_2, x_2);
				}
				g.setColor(Color.BLACK);
			}
		}
	}
}
