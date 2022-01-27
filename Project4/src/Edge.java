
// Class representing directed edges constituting a graph.
public class Edge {
	
	private int source; // The ID of the the source node of the edge
	private int target; // The ID of the target node of the edge
	private Edge residual; // Corresponding residual edge
	private int capacity; // The flow capacity of the edge
	private int flow; // Current flow on the edge
	
	public Edge(int source, int target, int capacity) {
		this.source = source;
		this.target = target;
		this.capacity = capacity;
		this.flow = 0;
		this.residual = null;
	}
	
	// Getter and setter methods for respective fields
	public int getSource() {
		return source;
	}

	public int getTarget() {
		return target;
	}

	public Edge getResidual() {
		return residual;
	}

	public void setResidual(Edge residual) {
		this.residual = residual;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

}
