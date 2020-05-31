/**
 * A class to represent a node in the map
 */
package roadgraph;

import java.util.HashSet;
import java.util.Set;

import geography.GeographicPoint;

/**
 * @author UCSD MOOC development team
 * 
 * Class representing a vertex (or node) in our MapGraph
 *
 */
class MapNode implements Comparable<MapNode>
{
	/** The list of edges out of this node */
	private HashSet<MapEdge> edges;
		
	/** the latitude and longitude of this node */
	private GeographicPoint location;
	
	/** the cost to reach this node */
	private double cost;
	
	/** the predicted cost to reach this node */
	private double predictedCost;
	
	/** 
	 * Create a new MapNode at a given Geographic location
	 * @param loc the location of this node
	 */
	MapNode(GeographicPoint loc)
	{
		location = loc;
		edges = new HashSet<MapEdge>();
	}
	
	MapNode(MapNode initNode, double initCost)
	{
		location = initNode.getLocation();
		edges = initNode.getEdges();
		cost = initCost;
	}
	
	MapNode(MapNode initNode, double initCost, double initPredictedCost)
	{
		location = initNode.getLocation();
		edges = initNode.getEdges();
		cost = initCost;
		predictedCost = initPredictedCost;
	}
		
	/**
	 * Add an edge that is outgoing from this node in the graph
	 * @param edge The edge to be added
	 */
	void addEdge(MapEdge edge)
	{
		edges.add(edge);
	}
	
	/**  
	 * Return the neighbors of this MapNode 
	 * @return a set containing all the neighbors of this node
	 */
	Set<MapNode> getNeighbors()
	{
		Set<MapNode> neighbors = new HashSet<MapNode>();
		for (MapEdge edge : edges) {
			neighbors.add(edge.getOtherNode(this));
		}
		return neighbors;
	}
	
	/**
	 * Get the geographic location that this node represents
	 * @return the geographic location of this node
	 */
	GeographicPoint getLocation()
	{
		return location;
	}
	
	/**
	 * return the edges out of this node
	 * @return a set contianing all the edges out of this node.
	 */
	HashSet<MapEdge> getEdges()
	{
		return edges;
	}
	
	/** Returns whether two nodes are equal.
	 * Nodes are considered equal if their locations are the same, 
	 * even if their street list is different.
	 * @param o the node to compare to
	 * @return true if these nodes are at the same location, false otherwise
	 */
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof MapNode) || (o == null)) {
			return false;
		}
		MapNode node = (MapNode)o;
		return node.location.equals(this.location);
	}
	
	/** Because we compare nodes using their location, we also 
	 * may use their location for HashCode.
	 * @return The HashCode for this node, which is the HashCode for the 
	 * underlying point
	 */
	@Override
	public int hashCode()
	{
		return location.hashCode();
	}
	
	/** ToString to print out a MapNode object
	 *  @return the string representation of a MapNode
	 */
	@Override
	public String toString()
	{
		String toReturn = "[NODE at location (" + location + ")";
		toReturn += " intersects streets: ";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += "]";
		return toReturn;
	}

	// For debugging, output roadNames as a String.
	public String roadNamesAsString()
	{
		String toReturn = "(";
		for (MapEdge e: edges) {
			toReturn += e.getRoadName() + ", ";
		}
		toReturn += ")";
		return toReturn;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public int compareTo(MapNode o) {
		if(this.cost < o.cost) {
			return -1;
		}
		else if(this.cost > o.cost) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public int compareToPredict(MapNode o) {
		if(this.predictedCost < o.predictedCost) {
			return -1;
		}
		else if(this.predictedCost > o.predictedCost) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public double computeCost(MapNode neighbor) {
		double length = 0;
		for(MapEdge edge: edges) {
			if(edge.getEndPoint() == neighbor.getLocation()) {
				length = edge.getLength();
			}
		}
		return this.cost + length;
	}

	public double computePredictedCost(MapNode neighbor, double cost) {
		return this.getLocation().distance(neighbor.getLocation()) + cost;
	}
	
	/**
	 * @return the predictedCost
	 */
	public double getPredictedCost() {
		return predictedCost;
	}

	/**
	 * @param predictedCost the predictedCost to set
	 */
	public void setPredictedCost(double predictedCost) {
		this.predictedCost = predictedCost;
	}

}