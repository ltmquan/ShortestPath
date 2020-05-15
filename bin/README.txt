Name: Quan Luu
Student ID: 31529099
NetID: qluu2
Lab section: MW 6h15 - 7h30
Project: 3
Description: this is a Graph-related project with 5 classes
 - Graph class represents a Graph
 - Map class is the Graphics
 - Mapping is the main method
 - MST is something I created to do the meridian map
 - Vertex represents a Vertex
 
Notes: 
 - For this project, the graph is undirected
 
 - The runtime for drawing the graph should be linear, as it contain 2 loops (one for adding vertices and edges,
 and one for drawing) O(V+E)
 
 - The runtime for finding the shortest path should be somewhere around O(V*logV) as it will test every vertices,
 and each time it does, it has to find the minimum value, too, which is O(logV). Although it will eventually test every
 edges, too. So maybe O((V+E)*logV)? or O(V*logV + E)?
 
 - The runtime for the meridian map should be somewhere around O(E*logE) as it will test every edges, and each time,
 it will also have to find the minimum value, which is O(logE)
 
 - Another note is that for the meridian map, I found out that the data for monroe suggests that it might not be 
 a connected graph, so there would be many different spanning trees, and I decided to just draw all of them. Same
 goes to nys, although I have not attempted to check if nys is connected or not.
 
How to run: just run with this format "fileName [-show] [-directions startPoint endPoint] [-meridianmap]". Remember that
-directions and -meridianmap can't happen at the same time. And -show creates the graphics.
