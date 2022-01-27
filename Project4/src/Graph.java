import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

// Class representing a directed graph.
public class Graph {
	
	private HashMap<Integer, ArrayList<Edge>> edges; // A Hashmap storing vertices and their corresponding adjacency list
	private int verticeCount; // The number of vertices
	private int[] height; // An array to store height of each vertice after each BFS
	
	public Graph(int verticeCount) {
		this.edges = new HashMap<Integer, ArrayList<Edge>>();
		this.verticeCount = verticeCount;
		for (int i=0; i<verticeCount; i++) {
			this.edges.put(i, new ArrayList<Edge>());
		}
		this.height = new int[this.verticeCount];
	}
	
	// Method responsible for adding edges to the graph
	// This method handles the edges between source & the bags and the edges between bags & vehicles.
	public void addEdge(int id, ArrayList<Integer> capacities, int increment, int capacity, ArrayList<Integer> gift) {
		if (this.edges.get(id).size() == 0) {
			Edge E1 = new Edge(0, id, gift.get(id));
			Edge E2 = new Edge(id, 0, 0);
			this.edges.get(0).add(E1);
			this.edges.get(id).add(E2);
			E1.setResidual(E2);
			E2.setResidual(E1);
		}
		
		for (int i=1; i<capacities.size()+1 ; i++) {
			int id2 = increment + i;
			if (capacities.get(i-1) == 0) {
				continue;
			}
			Edge E1 = new Edge(id, id2, capacity);
			Edge E2 = new Edge(id2, id, 0);
			this.edges.get(id).add(E1);
			this.edges.get(id2).add(E2);
			E1.setResidual(E2);
			E2.setResidual(E1);
		}
		
	}
	
	// Method for adding edges to the graph
	// This method handles the edges between vehicles and the sink.
	public void addEdge(int increment, ArrayList<Integer> capacities) {
		for (int i=0; i<capacities.size(); i++) {
			int id = increment + i;
			int id2 = this.verticeCount-1;
			if (capacities.get(i) == 0) {
				continue;
			}
			Edge E1 = new Edge(id, id2, capacities.get(i));
			Edge E2 = new Edge(id2, id, 0);
			this.edges.get(id).add(E1);
			this.edges.get(id2).add(E2);
			E1.setResidual(E2);
			E2.setResidual(E1);
		}
	}
	
	// Method for finding the height of the nodes in the graph with a breadth first search.
	public boolean BFS() {
		for (int i=0; i<height.length; i++) {
			height[i] = -1;
		}
		this.height[0] = 0;
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(0);
		while(!queue.isEmpty()) {
			int vertice = queue.poll();
			for (Edge E : this.edges.get(vertice)) {
				if (this.height[E.getTarget()] == -1 && E.getCapacity() > E.getFlow()) {
					this.height[E.getTarget()] = this.height[vertice] + 1;
					queue.add(E.getTarget());
				}
				if (this.height[this.verticeCount-1] != -1) {
					return true;
				}
			}
		}
		
		if (this.height[this.verticeCount-1] == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	// Method for finding the paths suitable for adding more flow with a depth first search.
	// Checks only the nodes with suitable height and remaining capacity
	// Also includes an optimization for possible dead ends encountered during DFS
	public int DFS(int source, int flow, int[] arr) {
		if (source == this.verticeCount-1) {
			return flow;
		}
		if (arr[source] == this.edges.get(source).size()) {
			return 0;
		}
		
		for(;arr[source] < this.edges.get(source).size(); arr[source]++) {
			Edge E = this.edges.get(source).get(arr[source]);
			if (E.getCapacity() > E.getFlow() && height[E.getTarget()] == height[E.getSource()] + 1) {
				int current = Math.min(E.getCapacity() - E.getFlow(), flow);
				int addFlow = DFS(E.getTarget(), current, arr);
				if (addFlow > 0) {
					E.setFlow(E.getFlow() + addFlow);
					E.getResidual().setFlow(E.getResidual().getFlow() - addFlow);
					return addFlow;
				}
			}
		}
		return 0;
	}
	
	// Method for executing Dinic's max flow algorithm.
	public int Dinic() {
		int maxFlow = 0;
		int[] arr = new int[this.verticeCount];
		while(BFS()) {
			int flow = -1;
			for (int i=0; i<arr.length; i++) {
				arr[i] = 0;
			}
			while(flow != 0) {
				flow = DFS(0, Integer.MAX_VALUE, arr);
				maxFlow += flow;
			}
		}
		return maxFlow;
	}

	// Getter method for respective field
	public HashMap<Integer, ArrayList<Edge>> getEdges() {
		return edges;
	}
	
}
