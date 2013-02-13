package gwt.client.astar.pathfinder.node.impl;

import gwt.client.astar.pathfinder.node.PathfindingNode;


/**
 * A single node in the search graph
 */
public class SimpleNode implements PathfindingNode {
	/** The x coordinate of the node */
	private int x;
	/** The y coordinate of the node */
	private int y;
	/** The path cost for this node. F in A* terms */
	private int costTotal;
	/** The movement cost so far to this node. G in A* terms */
	private int costSoFar;
	/** The heuristic cost of this node. H in A* terms */
	private int costHeuristic;
	/** Parent node of this node */
	private PathfindingNode parent;
	/** Marks which list the node is on in the Path-finder */
	private int activeList;
	
	/**
	 * Create a new node
	 * 
	 * @param x The x coordinate of the node
	 * @param y The y coordinate of the node
	 */
	public SimpleNode(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @see Comparable#compareTo(Object)
	 */
	public int compareTo(Object other) {
		PathfindingNode o = (PathfindingNode) other;
		
		int f = costTotal;
		int of = o.getCostTotal();
		
		if (f < of) {
			return -1;
		} else if (f > of) {
			return 1;
		} else {
			/*
			 * If nodes resolve to a path of equal length, prefer the node
			 * that is already closer to the target.
			 */
			f = costHeuristic;
			of = o.getCostHeuristic();
			if (f < of){
				return -1;
			}else if (f > of){
				return 1;
			}else{
				return 0;
			}
		}
	}

	public PathfindingNode getParent() {
		return parent;
	}

	public void setParent(PathfindingNode parent) {
		this.parent = parent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCostHeuristic() {
		return costHeuristic;
	}

	public void setCostHeuristic(int costHeuristic) {
		this.costHeuristic = costHeuristic;
	}

	public int getCostSoFar() {
		return costSoFar;
	}

	public void setCostSoFar(int costSoFar) {
		this.costSoFar = costSoFar;
	}

	public int getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(int costTotal) {
		this.costTotal = costTotal;
	}

	public int getActiveList() {
		return activeList;
	}

	public void setActiveList(int activeList) {
		this.activeList = activeList;
	}


}
